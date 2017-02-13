package com.zzti.retrofitdemo.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author fengyongge
 * @date 2017/1/3 0003
 * @description
 * 登录bean
 */

public class LoginBean implements Serializable {


    private int id;

    private int supplier_id;

    private String username;

    private String mobile;

    private int gender;

    private String email;

    private String password;

    private int status;

    private int is_admin;

    private int role_id;

    private String staff_tag;

    private int add_staff_id;

    private int operator_id;

    private String login_ip;

    private String last_login_time;

    private String created_at;

    private String updated_at;

    private int is_delete;

    private String staff_image;

    private List<BelongDep> belongDep ;

    private List<AdminDep> adminDep ;

    private SupplierInfo supplierInfo;

    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    public void setSupplier_id(int supplier_id){
        this.supplier_id = supplier_id;
    }
    public int getSupplier_id(){
        return this.supplier_id;
    }
    public void setUsername(String username){
        this.username = username;
    }
    public String getUsername(){
        return this.username;
    }
    public void setMobile(String mobile){
        this.mobile = mobile;
    }
    public String getMobile(){
        return this.mobile;
    }
    public void setGender(int gender){
        this.gender = gender;
    }
    public int getGender(){
        return this.gender;
    }
    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public int getStatus(){
        return this.status;
    }
    public void setIs_admin(int is_admin){
        this.is_admin = is_admin;
    }
    public int getIs_admin(){
        return this.is_admin;
    }
    public void setRole_id(int role_id){
        this.role_id = role_id;
    }
    public int getRole_id(){
        return this.role_id;
    }
    public void setStaff_tag(String staff_tag){
        this.staff_tag = staff_tag;
    }
    public String getStaff_tag(){
        return this.staff_tag;
    }
    public void setAdd_staff_id(int add_staff_id){
        this.add_staff_id = add_staff_id;
    }
    public int getAdd_staff_id(){
        return this.add_staff_id;
    }
    public void setOperator_id(int operator_id){
        this.operator_id = operator_id;
    }
    public int getOperator_id(){
        return this.operator_id;
    }
    public void setLogin_ip(String login_ip){
        this.login_ip = login_ip;
    }
    public String getLogin_ip(){
        return this.login_ip;
    }
    public void setLast_login_time(String last_login_time){
        this.last_login_time = last_login_time;
    }
    public String getLast_login_time(){
        return this.last_login_time;
    }
    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
    public String getCreated_at(){
        return this.created_at;
    }
    public void setUpdated_at(String updated_at){
        this.updated_at = updated_at;
    }
    public String getUpdated_at(){
        return this.updated_at;
    }
    public void setIs_delete(int is_delete){
        this.is_delete = is_delete;
    }
    public int getIs_delete(){
        return this.is_delete;
    }
    public void setStaff_image(String staff_image){
        this.staff_image = staff_image;
    }
    public String getStaff_image(){
        return this.staff_image;
    }
    public void setBelongDep(List<BelongDep> belongDep){
        this.belongDep = belongDep;
    }
    public List<BelongDep> getBelongDep(){
        return this.belongDep;
    }
    public void setAdminDep(List<AdminDep> adminDep){
        this.adminDep = adminDep;
    }
    public List<AdminDep> getAdminDep(){
        return this.adminDep;
    }
    public void setSupplierInfo(SupplierInfo supplierInfo){
        this.supplierInfo = supplierInfo;
    }
    public SupplierInfo getSupplierInfo(){
        return this.supplierInfo;
    }







}
