package com.zzti.retrofitdemo.bean;

/**
 * Created by fengyongge on 2017/2/11.
 */

public class BodyBean {

    private String supplier_id;
    private String operator_id;
    private String tagids;
    private String timestamp;


    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSupplier_id() {
        return supplier_id;
    }

    public void setSupplier_id(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getTagids() {
        return tagids;
    }

    public void setTagids(String tagids) {
        this.tagids = tagids;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    @Override
    public String toString() {
        return "BodyBean{" +
                "supplier_id='" + supplier_id + '\'' +
                ", operator_id='" + operator_id + '\'' +
                ", tagids='" + tagids + '\'' +
                '}';
    }
}
