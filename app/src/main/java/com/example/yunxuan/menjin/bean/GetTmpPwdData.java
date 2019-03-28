package com.example.yunxuan.menjin.bean;

public class GetTmpPwdData {

    /**
     * status : 0
     * pwd : 805813
     * message : ok
     */

    private String status;
    private String pwd;
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "TmpPwdData{" +
                "status=" + status +
                ", pwd='" + pwd + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
