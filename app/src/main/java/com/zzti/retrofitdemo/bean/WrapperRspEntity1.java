package com.zzti.retrofitdemo.bean;

/**
 * @author fengyonggge
 * @date 2017/2/7
 */
public class WrapperRspEntity1<T> {
    private boolean success;
    private T data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
