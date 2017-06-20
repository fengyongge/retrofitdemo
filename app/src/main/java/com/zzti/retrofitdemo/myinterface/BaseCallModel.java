package com.zzti.retrofitdemo.myinterface;

public class BaseCallModel<T> {
    public int errno;
    public String msg;
    public T data;
}