package com.kosthi.im.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.jackson.Jacksonized;

// 使用Builder模式+链式调用来设置多个属性的对象
@Setter
@Getter
@Jacksonized
@Builder
public class User {

    private String account;
    private String password;
    private String username;
    private Character sex;
    private Byte age;
    private String telephone;
    private String address;
    private String label;
    private String head;
    private String background;

//    @JsonCreator
//    public User(@JsonProperty("account") String account,
//                @JsonProperty("password") String password,
//                @JsonProperty("username") String username,
//                @JsonProperty("sex") String sex,
//                @JsonProperty("age") String age,
//                @JsonProperty("phoneNumber") String phoneNumber,
//                @JsonProperty("address") String address,
//                @JsonProperty("label") String label,
//                @JsonProperty("head") String head,
//                @JsonProperty("background") String background) {
//        this.account = account;
//        this.password = password;
//        this.username = username;
//        this.sex = sex;
//        this.age = age;
//        this.phoneNumber = phoneNumber;
//        this.address = address;
//        this.label = label;
//        this.head = head;
//        this.background = background;
//    }
}
