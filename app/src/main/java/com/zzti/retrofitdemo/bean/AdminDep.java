package com.zzti.retrofitdemo.bean;

public class AdminDep {
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