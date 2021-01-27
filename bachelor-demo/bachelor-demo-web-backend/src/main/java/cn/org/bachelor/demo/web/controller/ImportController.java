package cn.org.bachelor.demo.web.controller;

import cn.org.bachelor.core.exception.SystemException;
import cn.org.bachelor.demo.web.service.ImportService;
import cn.org.bachelor.web.json.JsonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lixiaolong
 * @date 2020/12/3 16:17
 * @description 预约入园接口
 */
@Api(tags = "预约入园接口")
@RequestMapping("/import/")
@RestController
public class ImportController {

    private static final Logger logger = LoggerFactory.getLogger(ImportController.class);

    @Resource
    ImportService importService;

    /**
     * <p>
     * appointExcel : 批量导入预约申请
     * </p>
     *
     * @param
     * @return ResponseEntity<JsonResponse>
     * @Auther: lixiaolong
     * @Date: 2020/12/7 09:34
     */
    @ResponseBody
    @PostMapping(value = "/importBookExcel", headers = "content-type=multipart/form-data")
    @ApiOperation(value = "通信录导入", notes = "批量导入")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "待导入的excel文件", paramType = "query", dataType = "MultipartFile"),
            @ApiImplicitParam(name = "dataType", value = "文件类型 1:通讯录组织机构 2通讯录信息", paramType = "query", dataType = "String")
    })
    public ResponseEntity<JsonResponse> book(HttpServletRequest request,
                                             @RequestParam MultipartFile file,
                                             @RequestParam String dataType) {
        logger.info("importBookExcel dataType with {} : ",
                ReflectionToStringBuilder.toString(dataType, ToStringStyle.SHORT_PREFIX_STYLE));

        try {
            int res = importService.importExcel(request, file, dataType);
            if (res > 0) {
                return JsonResponse.createHttpEntity(HttpStatus.OK);
            } else {
                return JsonResponse.createHttpEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            // 数据转换异常处理，向上抛出异常
            throw new SystemException(e);
        }
    }
}
