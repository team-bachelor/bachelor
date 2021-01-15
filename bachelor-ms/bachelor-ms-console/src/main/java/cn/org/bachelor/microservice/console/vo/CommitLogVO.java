package cn.org.bachelor.microservice.console.vo;

import org.eclipse.jgit.lib.ObjectId;

import java.util.Date;

/**
 * Description:
 *
 * @Author Alexhendar
 * @Modified By:
 */
public class CommitLogVO {

  private String commitId;
  private String message;
  private Date commitTime;

  private String author;

  public String getCommitId() {
    return commitId;
  }

  public void setCommitId(String commitId) {
    this.commitId = commitId;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getCommitTime() {
    return commitTime;
  }

  public void setCommitTime(Date commitTime) {
    this.commitTime = commitTime;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }
}
