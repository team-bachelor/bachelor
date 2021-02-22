package cn.org.bachelor.iam.acm.controller;

import cn.org.bachelor.iam.acm.IamValueHolderService;
import cn.org.bachelor.iam.acm.annotation.AcmDomain;
import cn.org.bachelor.iam.acm.annotation.AcmPermission;
import cn.org.bachelor.iam.acm.domain.ObjDomain;
import cn.org.bachelor.iam.acm.domain.ObjOperation;
import cn.org.bachelor.iam.acm.domain.ObjPermission;
import cn.org.bachelor.iam.acm.service.AuthorizeService;
import cn.org.bachelor.iam.acm.service.ObjDomainService;
import cn.org.bachelor.iam.acm.service.ObjOperationService;
import cn.org.bachelor.iam.acm.service.ObjPermissionService;
import cn.org.bachelor.iam.acm.vo.ObjPermissionVo;
import cn.org.bachelor.iam.acm.vo.UserVo;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@RequestMapping(value = "/initPermissions")
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
    private IamValueHolderService valueHolder;


    @ApiOperation(value = "初始化权限点")
    @GetMapping(value = "")
    public ResponseEntity<JsonResponse> initPermissions() {
        List<ObjPermissionVo> list = getObjPermissionVos();
        String userCode = getUserName();
        Map<String, ObjOperation> opMap = new HashMap<>();
        Map<String, ObjDomain> domainMap = new HashMap<>();
        List<ObjPermission> permissions = new ArrayList<>(list.size());
        //将数据写入到权限表中 cmn_auth_objects
        for (ObjPermissionVo m : list) {
            //获取权限点，并加入到list中
            getObjPermission(m, permissions, userCode);

            //获取权限操作，去重并加入到map中
            getOperation(m, opMap, userCode);

            //获取操作域，去重并加入到map中
            getDomain(m, domainMap, userCode);
//            List<ObjPermission> baseObjectsList = baseObjectsService.selectAlls();
//            boolean notExist = true;
//            for (ObjPermission dbperm : baseObjectsList) {
//                String code = dbperm.getName();
//                if (code.equals(m.getCode())) {
//                    notExist = false;
//                }
//            }
//            if (notExist) {
//                baseObjectsService.inserts(newPermission);
//            }
//            List<ObjOperation> baseOperationList = baseOperationService.selectAlls();
//
//            for (ObjOperation baseOperations : baseOperationList) {
//                String code = baseOperations.getCode();
//                if (!code.equals(newOperation.getCode())) {
//                    baseOperationService.inserts(newOperation);
//                }
//            }
//            List<ObjDomain> baseDoMainList = baseDoMainService.selectAlls();
//            for (ObjDomain baseDoMain1 : baseDoMainList) {
//                String code = baseDoMain1.getCode();
//                if (!code.equals(newDoMain.getCode())) {
//                    baseDoMainService.inserts(newDoMain);
//                }
//            }
        }
        permissionService.saveOrUpdate(permissions);
        domainService.saveOrUpdate(new ArrayList<>(domainMap.values()));
        operationService.saveOrUpdate(new ArrayList<>(opMap.values()));
        return JsonResponse.createHttpEntity(list, HttpStatus.OK);
    }

    private void getDomain(ObjPermissionVo m, Map<String, ObjDomain> domainMap, String userCode) {
        ObjDomain newDoMain = new ObjDomain();
        newDoMain.setId(UUID.randomUUID().toString());
        newDoMain.setName(m.getDomainName());
        newDoMain.setCode(m.getDomainCode());
        newDoMain.setSeqOrder((short) 0);
        newDoMain.setIsSys(false);
        newDoMain.setUpdateTime(new Date());
        newDoMain.setUpdateUser(userCode);
        if(!domainMap.containsKey(newDoMain.getCode())){
            domainMap.put(newDoMain.getCode(), newDoMain);
        }
    }

    private void getOperation(ObjPermissionVo m, Map<String, ObjOperation> opMap, String userCode) {
        ObjOperation newOperation = new ObjOperation();
        newOperation.setId(UUID.randomUUID().toString());
        newOperation.setName(m.getOperateName());
        newOperation.setCode(m.getOperate());
        newOperation.setMethod(m.getHttpMethod());
        newOperation.setIsSys(false);
        newOperation.setUpdateTime(new Date());
        newOperation.setUpdateUser(userCode);
        if(!opMap.containsKey(newOperation.getCode())){
            opMap.put(newOperation.getCode(), newOperation);
        }
    }

    private void getObjPermission(ObjPermissionVo m,List<ObjPermission> permissions, String name) {
        ObjPermission newPermission = new ObjPermission();
        newPermission.setId(UUID.randomUUID().toString());
        newPermission.setName(m.getName());
        newPermission.setCode(m.getCode());
        newPermission.setUri(m.getUri());
        newPermission.setOperate(m.getOperate());
        newPermission.setType(m.getType());
        newPermission.setDomainCode(m.getDomainCode());
        newPermission.setSeqOrder(m.getSeqOrder());
        newPermission.setDefAuthOp(m.getDefAuthOp());
//            newPermission.setComment();
        newPermission.setIsSys(false);
        newPermission.setServeFor(m.getServeFor());
        newPermission.setUpdateTime(new Date());
        newPermission.setUpdateUser(name);//获取当前用户
        permissions.add( newPermission);
    }

    private String getUserName() {
        UserVo user = valueHolder.getCurrentUser();
        String name = "system_unknow";
        if(user != null){
            name = user.getCode();
        }
        return name;
    }

    private List<ObjPermissionVo> getObjPermissionVos() {
        // 获取springmvc处理器映射器组件对象 RequestMappingHandlerMapping无法直接注入
        RequestMappingHandlerMapping mapping = applicationContext.getBean(RequestMappingHandlerMapping.class);
        //获取url与类和方法的对应信息
        Map<RequestMappingInfo, HandlerMethod> methodMap = mapping.getHandlerMethods();
        List<ObjPermissionVo> list = new ArrayList<>(methodMap.size());
        for (Map.Entry<RequestMappingInfo, HandlerMethod> m : methodMap.entrySet()) {
            ObjPermissionVo permissionVo = new ObjPermissionVo();
            RequestMappingInfo info = m.getKey();
            HandlerMethod method = m.getValue();
            Class<?> cl1 = method.getBeanType();

            AcmDomain acmDomain = cl1.getAnnotation(AcmDomain.class);

            if (acmDomain != null) {
                permissionVo.setDomainCode(acmDomain.code());//域编码
                permissionVo.setDomainName(acmDomain.value());//域编码
            }

            // 获取url
            RequestMethodsRequestCondition methodsRequestCondition = info.getMethodsCondition();
            PatternsRequestCondition p = info.getPatternsCondition();

            for (RequestMethod requestMethod : methodsRequestCondition.getMethods()) {

                for (String url : p.getPatterns()) {
                    permissionVo.setUri(url);
                    permissionVo.setCode(requestMethod.toString().toLowerCase() + ":" + url);
                    permissionVo.setHttpMethod(requestMethod.toString());//请求方式
                }
                Method[] methods = cl1.getDeclaredMethods();
                for (Method method1 : methods) {
                    if (method1.getName().equals(method.getMethod().getName())) {
                        ApiOperation apiOperation = method1.getAnnotation(ApiOperation.class);
                        AcmPermission acmPermission = method1.getAnnotation(AcmPermission.class);

                        if (null != apiOperation) {
                            permissionVo.setName(apiOperation.value());
                        }
                        if (null != acmPermission) {
                            permissionVo.setDefAuthOp(acmPermission.checkLevel().toString());//接口权限
                            if (acmPermission.opTypeCode() != null) {
                                permissionVo.setOperate(acmPermission.opTypeCode());//操作编码
                            }

                            permissionVo.setType(acmPermission.type().toString());//接口类型
//                            permissionVo.put("value", acmPermission.value());//操作编码名称
                            permissionVo.setSeqOrder(acmPermission.order());
                            permissionVo.setName(acmPermission.opType());
                            permissionVo.setOperateName(acmPermission.opType());
                            permissionVo.setServeFor(Arrays.toString(acmPermission.serveFor()));
                        }
                    }
                }
            }
            // 反射获取url对应类名和方法名
//            permissionVo.put("className", method.getMethod().getDeclaringClass().getName()); // 类名
//            permissionVo.put("method", method.getMethod().getName()); // 方法名
            list.add(permissionVo);
        }
        return list;
    }
}
