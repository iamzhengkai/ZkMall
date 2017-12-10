package com.zk.ec.bean;

/**
 * Created by Administrator on 2017/12/10.
 */

public class User {
    private String objectId;
    private String username;
    private String password;
    private String mobilePhoneNumber;
    private String email;

    public User(String username, String password, String mobilePhoneNumber, String email) {
        this.username = username;
        this.password = password;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.email = email;
    }

    public User(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
