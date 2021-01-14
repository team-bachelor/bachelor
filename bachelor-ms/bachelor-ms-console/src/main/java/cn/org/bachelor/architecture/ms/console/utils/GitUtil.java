package cn.org.bachelor.architecture.ms.console.utils;

import cn.org.bachelor.core.exception.SystemException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Description: 封装Git相关基础API
 *
 * @Author Alexhendar
 * @Date: Created in 2019/1/2 9:39
 */
public class GitUtil {
  private static final Logger logger = LoggerFactory.getLogger(GitUtil.class);
  private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");//24小时制


  /**
   * <p>cloneRepository : 根据uri和本地文件目录克隆项目</p>
   *
   * @param uri       项目地址
   * @param directory 本地磁盘目录
   * @return
   * @Date: 2019/1/2 9:46
   */
  public static Git cloneRepository(String uri, File directory) {
    Git git = null;
    try {
      git = Git.cloneRepository()
              .setURI(uri)
              .setDirectory(directory)
              .call();
    } catch (GitAPIException e) {
      logger.warn(e.getMessage(), e);
    }
    return git;
  }

  /**
   * <p> 结束时关闭 Git
   * 注意那些会返回实例的命令例如 Init 命令或者 Clone 命令当不再需要时如果没有明确地关闭（git.close()）的话，可能会造成文件句柄泄露。</p>
   * <p>幸运的是，Git实现了 Autocloseable 以便你可以使用 try-with-resources 声明 </p>
   *
   * @param
   * @return
   * @Date: 2019/1/2 10:13
   */
  public static Git cloneRepository(String uri, String dir) {
    File directory = new File(dir);
    if (!directory.exists()) {
      directory.mkdirs();
    }
    return cloneRepository(uri, directory);
  }

  /**
   * <p> pull: 强制更新，遇到冲突先重置再更新</p>
   *
   * @param
   * @return
   * @Date: 2019/1/4 21:18
   */
  public static void pull(Git git) throws SystemException {

    try {
      git.pull().call();
    } catch (GitAPIException ge) {
      logger.warn(ge.getMessage());
      try {
        git.reset().setMode(ResetCommand.ResetType.HARD).setRef("refs/heads/master").call();
        git.pull().call();
      } catch (GitAPIException ioe) {
        logger.error("还原更新失败", ioe.getMessage());
        throw new SystemException("还原更新失败", ioe);
      }
    }
  }

  /**
   * <p>resetToCommitId : 回滚到指定版本</p>
   *
   * @param commitId 指定提交的标识
   * @return
   */
  public static void resetToCommitId(Git git, String commitId) throws GitAPIException {
    git.reset().setMode(ResetCommand.ResetType.HARD).setRef(commitId).call();

  }

  /**
   * <p> log: 查看指定文件的提交历史肌瘤</p>
   *
   * @param
   * @return
   * @Auther: Alexhendar
   */
  public static Iterable<RevCommit> log(Git git, String fileName) throws GitAPIException {
    LogCommand logCommand = git.log();
    Iterable<RevCommit> commits = logCommand.addPath(fileName).call();
    return commits;
  }

  public static String fileContentByCommit(Git git, AnyObjectId commitId, String fileName) throws IOException {
    Repository repository = git.getRepository();
    try (RevWalk revWalk = new RevWalk(repository))
    {
      RevCommit commit = revWalk.parseCommit(commitId);
      // and using commit's tree find the path
      RevTree tree = commit.getTree();
      try (TreeWalk treeWalk = new TreeWalk(repository)) {
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(fileName));
        if (!treeWalk.next()) {
          throw new IllegalStateException("Did not find expected file '" + fileName + "'");
        }

        ObjectId objectId = treeWalk.getObjectId(0);
        ObjectLoader loader = repository.open(objectId);

        ByteArrayOutputStream contentToBytes = new ByteArrayOutputStream();
        // and then one can the loader to read the file
        loader.copyTo(contentToBytes);
        return new String(contentToBytes.toByteArray(), "utf-8");
      }
    }
  }

  /**
   * <p> history : 浏览工程提交历史</p>
   *
   * @param
   * @return
   * @Auther: Alexhendar
   */
  public static void history(Git git) throws IOException {

    Repository repository = git.getRepository();
    try (RevWalk revWalk = new RevWalk(repository)) {
      ObjectId commitId = repository.resolve("refs/heads/master");
      revWalk.markStart(revWalk.parseCommit(commitId));
      for (RevCommit commit : revWalk) {
        logger.info("commitId:{},update time: {},message: {},and author: {}",
                commit.getId(),
                sdf.format(commit.getCommitterIdent().getWhen()),
                commit.getFullMessage(),
                commit.getAuthorIdent().getName());
      }
    }
  }

  /**
   * <p> push: 推送更新到服务器</p>
   * @Auther: Alexhendar
   * @param
   * @return
   */
  public static void push(Git git) throws GitAPIException {
    git.push().call();
  }


  public static void main(String[] args) {

    File dir = new File("D:\\tmp\\config-repo");
    /*Git git = GitUtil.cloneRepository("git@43.250.238.158:framework/config-repo.git"
    , "D:\\tmp\\config-repo");
    git.close();*/

    // 浏览git提交历史记录
    try (Git git = Git.open(dir)) {
//      GitUtil.pull(git);
//      GitUtil.history(git);
      // 回滚到指定版本
//      GitUtil.resetToCommitId(git,"46ca40dd655d783310d9dc016f5939dc4a4a125c");

//      GitUtil.log(git);

      String content = GitUtil.fileContentByCommit(git, ObjectId.fromString("588f8dd3af2c6fe52941ca7a8146e97a9212ade0"), "user-login-local.properties");

      logger.info(content);
    } catch (IOException  ioe) {
      logger.warn(ioe.getMessage());
    }


//    File[] files = dir.listFiles(new FileFilter() {
//      @Override
//      public boolean accept(File accessFile) {
//        return !accessFile.isHidden();
//      }
//    });
   /* File[] files = searchByNameAndPhase(dir,"","dev");
    for(File file:files){
      if(file.isDirectory())
        logger.info("{} is {}/{}",file.getName(),file.isDirectory(),file.isFile());
    }
    for(File file:files){
      if(file.isFile())
        logger.info("{} is {}/{}",file.getName(),file.isDirectory(),file.isFile());
    }*/

  }
}
