package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.iam.IamContext;
import cn.org.bachelor.iam.acm.annotation.AcmDomain;
import cn.org.bachelor.iam.acm.annotation.AcmPermission;
import cn.org.bachelor.iam.acm.domain.ObjDomain;
import cn.org.bachelor.iam.acm.domain.ObjOperation;
import cn.org.bachelor.iam.acm.domain.ObjPermission;
import cn.org.bachelor.iam.acm.service.ObjDomainService;
import cn.org.bachelor.iam.acm.service.ObjOperationService;
import cn.org.bachelor.iam.acm.service.ObjPermissionService;
import cn.org.bachelor.iam.acm.vo.ObjPermissionVo;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by gxf on 2021/1/21 9:49
 */
@RestController
@RequestMapping(value = "/acm")
@Api(tags = {"初始化权限数据"})
public class AcmInitController {

    @Resource
    WebApplicationContext applicationContext;
    @Resource
    ObjPermissionService permissionService;
    @Resource
    ObjOperationService operationService;
    @Resource
    ObjDomainService domainService;
    @Autowired
    private IamContext iamContext;


    @ApiOperation(value = "初始化权限点")
    @GetMapping(value = "/init")
    public ResponseEntity<JsonResponse> initPermissions() {
        List<ObjPermissionVo> list = getObjPermissionVos();
        String userCode = iamContext.getCurrentUserCode();
        Map<String, ObjOperation> opMap = new HashMap<>();
        Map<String, ObjDomain> domainMap = new HashMap<>();
        List<ObjPermission> permissions = new ArrayList<>(list.size());
        //将数据写入到权限表中 cmn_auth_objects
        for (ObjPermissionVo p : list) {
            //获取权限点，并加入到list中
            getObjPermission(p, permissions, userCode);

            //获取权限操作，去重并加入到map中
            getOperation(p, opMap, userCode);

            //获取操作域，去重并加入到map中
            getDomain(p, domainMap, userCode);

        }
        permissionService.saveOrUpdate(permissions);
        domainService.saveOrUpdate(new ArrayList<>(domainMap.values()));
        operationService.saveOrUpdate(new ArrayList<>(opMap.values()));
        return JsonResponse.createHttpEntity(list, HttpStatus.OK);
    }

    private void getDomain(ObjPermissionVo p, Map<String, ObjDomain> domainMap, String userCode) {
        ObjDomain newDoMain = new ObjDomain();
        newDoMain.setId(UUID.randomUUID().toString());
        newDoMain.setName(p.getDomainName());
        newDoMain.setCode(p.getDomainCode());
        newDoMain.setSeqOrder((short) 0);
        newDoMain.setIsSys(false);
        newDoMain.setUpdateTime(new Date());
        newDoMain.setUpdateUser(userCode);
        if (!domainMap.containsKey(newDoMain.getCode())) {
            domainMap.put(newDoMain.getCode(), newDoMain);
        }
    }

    private void getOperation(ObjPermissionVo p, Map<String, ObjOperation> opMap, String userCode) {
        ObjOperation newOperation = new ObjOperation();
        newOperation.setId(UUID.randomUUID().toString());
        newOperation.setName(p.getOperateName());
        newOperation.setCode(p.getOperate());
        newOperation.setMethod(p.getHttpMethod());
        newOperation.setIsSys(false);
        newOperation.setUpdateTime(new Date());
        newOperation.setUpdateUser(userCode);
        if (!opMap.containsKey(newOperation.getCode())) {
            opMap.put(newOperation.getCode(), newOperation);
        }
    }

    private void getObjPermission(ObjPermissionVo p, List<ObjPermission> permissions, String userCode) {
        ObjPermission newPermission = new ObjPermission();
        newPermission.setId(UUID.randomUUID().toString());
        newPermission.setName(p.getName());
        newPermission.setCode(p.getCode());
        newPermission.setUri(p.getUri());
        newPermission.setOperate(p.getOperate());
        newPermission.setType(p.getType());
        newPermission.setDomainCode(p.getDomainCode());
        newPermission.setSeqOrder(p.getSeqOrder());
        newPermission.setDefAuthOp(p.getDefAuthOp());
//            newPermission.setComment();
        newPermission.setIsSys(false);
        newPermission.setServeFor(p.getServeFor());
        newPermission.setUpdateTime(new Date());
        newPermission.setUpdateUser(userCode);//获取当前用户
        permissions.add(newPermission);
    }

    private List<ObjPermissionVo> getObjPermissionVos() {
        // 获取springmvc处理器映射器组件对象 RequestMappingHandlerMapping无法直接注入
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        List<ObjOperation> dbOperations = operationService.getOperations(true);
        Map<String, ObjOperation> dbopMap = new HashMap<>(dbOperations.size());
        dbOperations.forEach(objOperation -> {
            if (!dbopMap.containsKey(objOperation.getMethod()))
                dbopMap.put(objOperation.getMethod().toLowerCase(), objOperation);
        });
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        List<ObjPermissionVo> list = new ArrayList<>(methodMap.size());
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : methodMap.entrySet()) {
            //循环每一个映射
            //获取映射信息
            RequestMappingInfo info = m.getKey();
            // 获取httpmethod和url信息
            RequestMethodsRequestCondition methodsCondition = info.getMethodsCondition();
            PatternsRequestCondition patternsCondition = info.getPatternsCondition();
            if (patternsCondition.getPatterns() == null || patternsCondition.getPatterns().size() == 0
                    || methodsCondition.getMethods() == null || methodsCondition.getMethods().size() == 0) {
                continue;
            }
            //获取映射处理器信息
            HandlerMethod handler = m.getValue();

            ObjPermissionVo permissionVo = new ObjPermissionVo();
            Method method = handler.getMethod();
            //获取swagger的api定义
            ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
            //如果有swagger定义则使用swagger信息命名当前权限
            if (null != apiOperation) {
                permissionVo.setName(apiOperation.value());
            }

            //获取权限定义
            AcmPermission acmPermission = method.getAnnotation(AcmPermission.class);
            //跳过没有权限定义的映射
            if (null == acmPermission) {
                continue;
            }

            //获取域定义
            AcmDomain acmDomain = handler.getBeanType().getAnnotation(AcmDomain.class);
            if (acmDomain != null) {
                String code = acmDomain.code();
                if (isEmptyString(code)) {
                    code = acmDomain.value();
                }
                permissionVo.setDomainCode(code);//域编码
                if (isEmptyString(acmDomain.value())) {
                    permissionVo.setDomainName(acmDomain.name());//域编码
                } else {
                    permissionVo.setDomainName(acmDomain.value());//域编码
                }
            }

            String url = patternsCondition.getPatterns().toArray()[0].toString();
            String httpMethod = methodsCondition.getMethods().toArray()[0].toString().toLowerCase();

            //设置基本信息
            permissionVo.setUri(url);
            permissionVo.setCode(httpMethod + ":" + url);
            permissionVo.setHttpMethod(httpMethod);//请求方式
            permissionVo.setDefAuthOp(acmPermission.checkLevel().toString());//接口权限

            // 设置操作信息
            // 默认用当前
            permissionVo.setOperate(httpMethod);//操作编码
            if (isEmptyString(acmPermission.opTypeCode())) {
                if (dbopMap.containsKey(httpMethod)) {
                    ObjOperation dbop = dbopMap.get(httpMethod);
                    permissionVo.setOperate(dbop.getCode());//操作编码
                    permissionVo.setOperateName(dbop.getName());
                }
            } else {
                permissionVo.setOperate(acmPermission.opTypeCode());//操作编码
                permissionVo.setOperateName(acmPermission.opType());
            }

            //设置权限名称
            if (isEmptyString(acmPermission.value())) {
                StringBuilder name = new StringBuilder(permissionVo.getOperateName());
                if (acmDomain != null) {
                    name.append(acmDomain.name() == null ? acmDomain.value() : acmDomain.name());
                }
                permissionVo.setName(name.toString());
            } else {
                permissionVo.setName(acmPermission.value());
            }
            permissionVo.setType(acmPermission.type().toString());//接口类型
            permissionVo.setSeqOrder(acmPermission.order());
            permissionVo.setServeFor(Arrays.toString(acmPermission.serveFor()));

            list.add(permissionVo);
        }
        return list;
    }

    private boolean isEmptyString(String s) {
        return s == null || "".equals(s);
    }
}

