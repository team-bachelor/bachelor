package cn.org.bachelor.jms.rabbit.pojo;

/**
 * Created by Ric on 2016/12/26.
 */
public class MsgData {

    private String msg;
    private long date;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public MsgData(String msg){
        this.msg = msg;
        date = System.currentTimeMillis();
    }

//    @Override
//    public String toString(){
//        return toStr(this);
//    }
}
