package frezc.bangumitimemachine.app.entity;

/**
 * Created by freeze on 2015/6/7.
 */
public class Response {
    private String request;
    private int code = 200;
    private String error;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
