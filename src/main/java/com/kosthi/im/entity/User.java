package com.kosthi.im.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    // 使实例可序列化为json, 指定json的键值
    @JsonCreator
    public User(@JsonProperty("username") String name_, @JsonProperty("password") String password_) {
        name = name_;
        password = password_;
    }

    private String name;
    private String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name_) {
        name = name_;
    }

    public void setPassword(String password_) {
        password = password_;
    }
}
