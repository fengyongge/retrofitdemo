package com.zzti.retrofitdemo.bean;

/**
 * @author fengyongge
 * @date 2017/1/3 0003
 * @description
 * 门店bean,会员详情bean
 */

public class BelongDep {

    private int department_id;

    private String name;

    public void setDepartment_id(int department_id){
        this.department_id = department_id;
    }
    public int getDepartment_id(){
        return this.department_id;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
}
