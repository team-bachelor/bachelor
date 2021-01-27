package cn.org.bachelor.demo.web.controller;

import cn.org.bachelor.demo.web.domain.Organization;
import cn.org.bachelor.demo.web.domain.OrganizationTree;
import cn.org.bachelor.demo.web.service.OrganizationService;
import cn.org.bachelor.demo.web.service.TreeService;
import cn.org.bachelor.demo.web.common.annotation.AppointLog;
import cn.org.bachelor.demo.web.vo.ContactOrganizationVo;
import cn.org.bachelor.web.json.JsonResponse;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author 易罐
 */
@Slf4j
@RestController
@RequestMapping(value = "/contact/organization")
@Api(tags = {"通讯录组织机构相关接口服务"})
public class OrganizationController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    @Resource
    OrganizationService organizationService;
    @Resource
    TreeService treeService;

    @ApiOperation(value = "通讯录组织机构根据id查询接口")
    @GetMapping(value = "/{id}")
    public ResponseEntity<JsonResponse> findById(@PathVariable("id") int id) {

        Organization bookGroup = organizationService.selectByPrimaryKey(id);
        if (bookGroup != null) {
            return JsonResponse.createHttpEntity(bookGroup, HttpStatus.OK);
        } else {
            return JsonResponse.createHttpEntity(bookGroup, HttpStatus.NO_CONTENT);
        }
    }

    @AppointLog(module = "通讯录组织机构相关接口服务", method = "通讯录组织机构修改接口")
    @ApiOperation(value = "通讯录组织机构修改接口", notes = "根据主键更新实体全部字段")
    @PutMapping(value = "")
    public ResponseEntity<JsonResponse> update(HttpServletRequest request, @RequestBody Organization organization) {
        // 修改操作，日志记录
        logger.info("update eprs_contacts_organization with {} : ",
                ReflectionToStringBuilder.toString(organization, ToStringStyle.SHORT_PREFIX_STYLE));
        int res = organizationService.update(request, organization);
        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 修改失败
            return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
        }
    }

    @AppointLog(module = "通讯录组织机构相关接口服务", method = "通讯录组织机构新增接口")
    @ApiOperation(value = "通讯录组织机构新增接口")
    @PostMapping(value = "")
    public ResponseEntity<JsonResponse> add(HttpServletRequest request, @RequestBody Organization organization) {
        // 新增操作，日志记录
        logger.info("add eprs_contacts_organization with {} : ",
                ReflectionToStringBuilder.toString(organization, ToStringStyle.SHORT_PREFIX_STYLE));

        int res = organizationService.insert(request, organization);

        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 插入失败
            return JsonResponse.createHttpEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @AppointLog(module = "通讯录组织机构相关接口服务", method = "通讯录组织机构删除接口")
    @ApiOperation(value = "通讯录组织机构删除接口", notes = "通讯录组织机构删除接口")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<JsonResponse> delete(@PathVariable("id") int id) {
        // 删除操作，日志记录
        logger.info("delete eprs_contacts_organization {} : ", id);
        int res = organizationService.deleteByPrimaryKey(id);
        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 删除失败
            return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
        }
    }

    @AppointLog(module = "通讯录组织机构相关接口服务", method = "通讯录组织机构多条件查询")
    @ApiOperation(value = "通讯录组织机构多条件查询", notes = "通讯录组织机构多条件查询，分页使用pageNum,pageSize参数,按照sort_num降序排列")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页当前页码", paramType = "query", required = false,
                    dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每个分页元素个数", paramType = "query", required = false,
                    dataType = "Integer")})
    @PostMapping(value = "/params")
    public ResponseEntity<JsonResponse> selectByParams(@RequestBody Organization organization,
                                                       @RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "10") int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Organization> organizationPageInfo = organizationService.selectByParams(organization);
        return JsonResponse.createHttpEntity(organizationPageInfo, HttpStatus.OK);
    }

    @AppointLog(module = "通讯录组织机构相关接口服务", method = "通过通讯录查询组织机构，两表联查")
    @ApiOperation(value = "通过通讯录查询组织机构，两表联查", notes = "通过通讯录查询组织机构，两表联查，分页使用start,page参数")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页当前页码", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每个分页元素个数", paramType = "query", required = false, dataType = "Integer")})
    @GetMapping(value = "/all")
    public ResponseEntity<JsonResponse> selectAllByPage(@RequestParam(defaultValue = "1") int pageNum,
                                                        @RequestParam(defaultValue = "10") int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<ContactOrganizationVo> allVo = organizationService.selectAllVo();
        return JsonResponse.createHttpEntity(allVo, HttpStatus.OK);
    }


    @AppointLog(module = "通讯录组织机构相关接口服务", method = "查询组织机构树")
    @ApiOperation(value = "查询组织机构树", notes = "查询组织机构树")
    @GetMapping(value = "/findOrganizationTree")
    public ResponseEntity<JsonResponse> findOrganizationTree() {
        List<OrganizationTree> tree = treeService.findOrganizationTree();
        return JsonResponse.createHttpEntity(tree, HttpStatus.OK);
    }

}
