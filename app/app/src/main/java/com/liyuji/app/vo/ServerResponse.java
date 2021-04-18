package com.liyuji.app.vo;

/**
 * 封装前端返回的统一实体类
 *
 * @author L*/

public class ServerResponse<T> {
    //状态 0:接口调用成功
    private int status;
    //当status=0，将返回的数据封装到data中
    private T data;
    //提示信息

    private String msg;


    public  ServerResponse(){}



    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
