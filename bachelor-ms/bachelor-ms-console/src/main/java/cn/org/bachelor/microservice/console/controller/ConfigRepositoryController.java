package cn.org.bachelor.microservice.console.controller;

import cn.org.bachelor.microservice.console.service.ConfigRepositoryService;
import cn.org.bachelor.microservice.console.vo.CommitLogVO;
import cn.org.bachelor.microservice.console.vo.ConfigFileVO;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import cn.org.bachelor.web.json.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @Author Alexhendar
 * @Date: Created in 2019/1/14 18:14
 * @Modified By:
 */
@RestController
public class ConfigRepositoryController {

  @Autowired
  private ConfigRepositoryService configRepositoryService;
  /**
   * <p> listAllConfigFiles: 显示目录下所有的配置文件</p>
   * @Auther: Alexhendar
   * @param
   * @return
   */
  @ApiOperation(value = "获取所有配置文件的列表")
  @GetMapping("/config/all")
  @HystrixCommand(commandKey = "config")
  public HttpEntity<JsonResponse> listAllConfigFiles(){
    List<ConfigFileVO> configFileList = configRepositoryService.listCurrentRepository();
    return JsonResponse.createHttpEntity(configFileList,HttpStatus.OK);
  }
  @ApiOperation(value = "根据文件名和所属阶段查找配置文件")
  @GetMapping("/config/search")
  @HystrixCommand(commandKey = "config")
  public HttpEntity<JsonResponse> searchConfigFile(
          @RequestParam(name = "fileName",defaultValue = "",required = false) String fileName,
          @RequestParam(name="phase",defaultValue = "",required = false) String phase){
    List<ConfigFileVO> configFileList
            = configRepositoryService.searchByNameAndPhase(StringUtils.trim(fileName),StringUtils.trim(phase));
    return JsonResponse.createHttpEntity(configFileList,HttpStatus.OK);
  }

  @ApiOperation(value = "查看指定文件的提交日志记录")
  @GetMapping("/config/log")
  @HystrixCommand(commandKey = "config")
  public HttpEntity<JsonResponse> searchConfigFile(
          @RequestParam(name = "fileName",defaultValue = "",required = false) String fileName){
    List<CommitLogVO> configLogList
            = configRepositoryService.logByFileName(StringUtils.trim(fileName));
    return JsonResponse.createHttpEntity(configLogList,HttpStatus.OK);
  }
  @ApiOperation(value = "对比指定文件最新内容和指定提交的内容对比")
  @GetMapping("/config/diff/{commitId}")
  @HystrixCommand(commandKey = "config")
  public HttpEntity<JsonResponse> diffWithCurrent(
          @PathVariable String commitId,
          @RequestParam(name = "fileName",required = true) String fileName){
    List<String> contentList
            = configRepositoryService.diffWithCurrent(commitId,fileName);
    return JsonResponse.createHttpEntity(contentList,HttpStatus.OK);
  }

  @ApiOperation(value = "指定提交的内容")
  @GetMapping("/config/content/{commitId}")
  @HystrixCommand(commandKey = "config")
  public HttpEntity<JsonResponse> contentInCommit(
          @PathVariable String commitId,
          @RequestParam(name = "fileName",required = true) String fileName){
    String content = configRepositoryService.contentInCommit(commitId,fileName);
    return JsonResponse.createHttpEntity(content,HttpStatus.OK);
  }
  @ApiOperation(value = "推送最新仓库到服务器")
  @PutMapping("/config/push")
  @HystrixCommand(commandKey = "config")
  public HttpEntity<JsonResponse> pushCurrentRepository(){
    configRepositoryService.pushCurrentRepository();
    return JsonResponse.createHttpEntity(HttpStatus.OK);
  }
}
