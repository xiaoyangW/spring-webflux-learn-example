package com.springbootwebflux.model;

/**
 * @author WXY
 */
public class User {
    private Integer uid;
    private String name;
    private String psw;

    public Integer getUid() {
        return uid;
    }

    public User() {
    }

    public User(Integer uid, String name, String psw) {
        this.uid = uid;
        this.name = name;
        this.psw = psw;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", psw='" + psw + '\'' +
                '}';
    }
}
