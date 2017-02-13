package com.zzti.retrofitdemo.bean;

/**
 * Created by fengyongge on 2017/2/11.
 */

public class filesbean {
    private String name;
    private String type;
    private String size;
    private String path;
    private String url;
    private String delete_url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDelete_url() {
        return delete_url;
    }

    public void setDelete_url(String delete_url) {
        this.delete_url = delete_url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "filesbean{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", size='" + size + '\'' +
                ", path='" + path + '\'' +
                ", url='" + url + '\'' +
                ", delete_url='" + delete_url + '\'' +
                '}';
    }
}
