package com.kosthi.im.entity;

import org.apache.ibatis.annotations.Mapper;

public class User {

    public User(String name_, String password_) {
        name = name_;
        password = password_;
    }

    private final String name;
    private final String password;

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
