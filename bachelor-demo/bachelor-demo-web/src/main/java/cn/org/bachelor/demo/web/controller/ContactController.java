package cn.org.bachelor.demo.web.controller;

import cn.org.bachelor.demo.web.service.ContactService;
import cn.org.bachelor.demo.web.common.annotation.AppointLog;
import cn.org.bachelor.demo.web.domain.Contact;
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

/**
 * @author lixiaolong
 */
@Slf4j
@RestController
@RequestMapping(value = "/contact")
@Api(tags = {"通讯录服务"})
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    @Resource
    ContactService contactService;


    @AppointLog(module = "通讯录服务", method = "新增通讯录")
    @ApiOperation(value = "新增通讯录")
    @PostMapping(value = "")
    public ResponseEntity<JsonResponse> add(HttpServletRequest request, @RequestBody Contact contact) {
        logger.info("add user with {} : ",
                ReflectionToStringBuilder.toString(contact, ToStringStyle.SHORT_PREFIX_STYLE));

        int res = contactService.insert(request, contact);
        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 插入失败
            return JsonResponse.createHttpEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @ApiOperation(value = "通讯录根据id查询")
    @GetMapping(value = "/{id}")
    public ResponseEntity<JsonResponse> findById(@PathVariable("id") int id) {

        ContactOrganizationVo contactOrganizationVo = contactService.selectAllById(id);
        if (contactOrganizationVo != null) {
            return JsonResponse.createHttpEntity(contactOrganizationVo, HttpStatus.OK);
        } else {
            return JsonResponse.createHttpEntity(contactOrganizationVo, HttpStatus.NO_CONTENT);
        }
    }


    @AppointLog(module = "通讯录服务", method = "修改通讯录")
    @ApiOperation(value = "修改通讯录", notes = "根据主键更新实体全部字段，字段为null或空的不更新")
    @PutMapping(value = "")
    public ResponseEntity<JsonResponse> update(HttpServletRequest request, @RequestBody Contact contact) {
        // 修改操作，日志记录
        logger.info("update user with {} : ",
                ReflectionToStringBuilder.toString(contact, ToStringStyle.SHORT_PREFIX_STYLE));
        int res = contactService.update(request, contact);
        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 修改失败
            return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
        }
    }


    @AppointLog(module = "通讯录服务", method = "通讯录删除")
    @ApiOperation(value = "通讯录删除", notes = "根据主键删除")
    @ApiImplicitParam(name = "id", value = "id", paramType = "path", required = true)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<JsonResponse> delete(@PathVariable("id") int id) {
        // 删除操作，日志记录
        logger.info("delete eprs_contacts_contact {} : ", id);
        int res = contactService.deleteByPrimaryKey(id);
        if (res == 1) {
            return JsonResponse.createHttpEntity(HttpStatus.OK);
        } else {
            // 删除失败
            return JsonResponse.createHttpEntity(HttpStatus.NOT_FOUND);
        }
    }

    @AppointLog(module = "通讯录服务", method = "通讯录列表查询")
    @ApiOperation(value = "通讯录列表查询", notes = "查询通讯录全表，分页使用pageNum,pageSize参数,按照sort_num降序排列")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页当前页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每个分页元素个数", paramType = "query", dataType = "Integer")})
    @GetMapping(value = "/page")
    public ResponseEntity<JsonResponse> findListByPage(@RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "10") int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Contact> bookGroupPageInfo = contactService.selectAllByPage();
        return JsonResponse.createHttpEntity(bookGroupPageInfo, HttpStatus.OK);
    }

    @AppointLog(module = "通讯录服务", method = "通讯录多条件查询")
    @ApiOperation(value = "通讯录多条件查询", notes = "通讯录多条件查询，分页使用start,page参数,按照sort_num降序排列")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum", value = "分页当前页码", paramType = "query", dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每个分页元素个数", paramType = "query", dataType = "Integer")})
    @PostMapping(value = "/params")
    public ResponseEntity<JsonResponse> selectByParams(@RequestBody Contact contact,
                                                       @RequestParam(defaultValue = "1") int pageNum,
                                                       @RequestParam(defaultValue = "10") int pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        PageInfo<Contact> bookGroupPageInfo = contactService.selectByParams(contact);
        return JsonResponse.createHttpEntity(bookGroupPageInfo, HttpStatus.OK);
    }
}
