package org.bachelor.architecture.ms.console.service;

import com.jcraft.jsch.Session;
import org.apache.commons.lang3.StringUtils;
import org.bachelor.architecture.ms.console.utils.GitUtil;
import org.bachelor.architecture.ms.console.vo.CommitLogVO;
import org.bachelor.architecture.ms.console.vo.ConfigFileVO;
import org.bachelor.core.exception.SystemException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.JschConfigSessionFactory;
import org.eclipse.jgit.transport.OpenSshConfig;
import org.eclipse.jgit.transport.SshSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @Author Alexhendar
 * @Modified By:
 */
@Service
@Configuration
public class ConfigRepositoryService {
  private static final Logger logger = LoggerFactory.getLogger(GitUtil.class);

 /* private String lineSeparator = "\n";

  private String webPageNewLine = "<br/>";*/

  @Value("${config.git.filePath}")
  private String filePath;
  @Value("${config.git.repository}")
  private String repository;

  public static void main(String[] args) {
    ConfigRepositoryService configService = new ConfigRepositoryService();

    configService.listCurrentRepository();
  }

  public List<ConfigFileVO> listCurrentRepository(){
    File configRepository = new File(filePath);

    try(Git git = Git.open(configRepository)){
      GitUtil.pull(git);
    }catch(IOException ioe){
      logger.warn("git.pull.error",ioe);
    }

    if (!configRepository.exists()){
      // 配置文件目录不存在，返回异常
      throw new SystemException("config.repository.notexists");
    }

    File[] files = listFiles(configRepository);
    List<ConfigFileVO> configFileList = new ArrayList<>();
    for(File file:files){
      ConfigFileVO vo = new ConfigFileVO();
      vo.setFileName(file.getName());
      vo.setPhase(getPhase(file.getName()));
      configFileList.add(vo);
    }
    return configFileList;
  }


  public String getPhase(String fileName){
    if(fileName.contains("-")){
      return fileName.substring(fileName.lastIndexOf("-")+1,fileName.lastIndexOf("."));
    }
    return fileName.substring(0,fileName.lastIndexOf("."));
  }


  public List<ConfigFileVO> searchByNameAndPhase(String fileName,String phase){
    List<File> fileList = Arrays.asList(searchByNameAndPhase(new File(filePath),fileName,phase));
    return fileList.stream().map(file -> {
      ConfigFileVO vo = new ConfigFileVO();
      vo.setFileName(file.getName());
      vo.setPhase(getPhase(file.getName()));
      return vo;
    }).collect(Collectors.toList());
  }


  public List<CommitLogVO> logByFileName(String fileName){
    List<CommitLogVO> commitLogVOList = new ArrayList<>();
    try(Git git = Git.open(new File(filePath))){
      Iterable<RevCommit> commits = GitUtil.log(git,fileName);
      for(RevCommit commit:commits){
        CommitLogVO commitLog = new CommitLogVO();
        commitLog.setCommitId(commit.getId().getName());
        commitLog.setAuthor(commit.getAuthorIdent().getName());
        commitLog.setCommitTime(commit.getCommitterIdent().getWhen());
        commitLog.setMessage(commit.getFullMessage());

        commitLogVOList.add(commitLog);
      }
      return  commitLogVOList;
    }catch (IOException |GitAPIException gae){
      logger.error("浏览文件提交历史异常！");
    }
    throw new SystemException("git.file.not.load");
  }

  /**
   * <p> diffWithCurrent : 比较当前内容与指定提交的文件内容</p>
   * @Auther: Alexhendar
   * @param
   * @return
   */
  public List<String> diffWithCurrent(String commitId,String fileName){
    List<String> contentList = new ArrayList<>();
    try(Git git = Git.open(new File(filePath))){
      ObjectId lastCommitId = git.getRepository().resolve(Constants.HEAD);

      String currentContent = GitUtil.fileContentByCommit(git,lastCommitId,fileName);
//      contentList.add(currentContent.replaceAll(lineSeparator,webPageNewLine));
      contentList.add(currentContent);

      String originalContent = GitUtil.fileContentByCommit(git,ObjectId.fromString(commitId),fileName);
      contentList.add(originalContent);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return contentList;
  }

  /**
   * <p>contentInCommit : 查看指定提交中文件内容</p>
   * @Auther: Alexhendar
   * @param
   * @return
   */
  public String contentInCommit(String commitId,String fileName){
    try(Git git = Git.open(new File(filePath))){
      ObjectId lastCommitId = git.getRepository().resolve(Constants.HEAD);

      String currentContent = GitUtil.fileContentByCommit(git,lastCommitId,fileName);
      return currentContent;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return StringUtils.EMPTY;
  }

  /**
   * <p> pushCurrentRepository: 推送当前配置到仓库</p>
   * @Auther: Alexhendar
   * @param
   * @return
   */
  public void pushCurrentRepository(){
    try(Git git = Git.open(new File(filePath))){
//      git.commit().call();
      // 这里直接推送，commit操作由内容修改操作执行
      GitUtil.push(git);
    } catch (GitAPIException | IOException e) {
      e.printStackTrace();
      throw new SystemException("config.git.push.error",e);
    }
  }

  public File[] listFiles(File dir){
    return dir.listFiles(accessFile->!accessFile.isHidden()&&accessFile.isFile());
  }

  private File[] searchByNameAndPhase(File dir, String fileName, String phase){
    return dir.listFiles(accessFile->!accessFile.isHidden()&&accessFile.isFile()
            &&(accessFile.getName().contains(fileName)&&accessFile.getName().contains(phase)));
  }

  @PostConstruct
  public void initClone(){
    File localRepository = new File(filePath);
    if(!localRepository.exists()){
      // 目录不存在则创建
      logger.info("repository not exists,create it!,{}",filePath);
      localRepository.mkdirs();
    }

    try{
      Git.open(localRepository);
    } catch (IOException e) {
      // 首次启动，本地不存在仓库，执行clone操作
      logger.info("clone repository from : {} to {}",repository,filePath);
      SshSessionFactory.setInstance(new JschConfigSessionFactory() {
        public void configure(OpenSshConfig.Host hc, Session session) {
          session.setConfig("StrictHostKeyChecking", "no");
        }
      });

      GitUtil.cloneRepository(repository,filePath);
    }
  }
}
