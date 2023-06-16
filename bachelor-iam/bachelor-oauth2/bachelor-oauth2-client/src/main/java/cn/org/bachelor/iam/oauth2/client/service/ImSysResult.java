package cn.org.bachelor.iam.oauth2.client.service;

/**
 * @描述:
 * @创建人: liuzhuo
 * @创建时间: 2019/7/24
 */
public class ImSysResult<T> {
    private String result;
    private String message;
    private int total;
    private T rows;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getRows() {
        return rows;
    }

    public void setRows(T rows) {
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
