package cn.org.bachelor.log.operate;



public class OperateLogSubject {

    private String dataBase;
    private String subject;

    public OperateLogSubject(String dataBase, String subject) {
        this.dataBase = dataBase;
        this.subject = subject;
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
