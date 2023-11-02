package com.kosthi.im.mapper;

import com.kosthi.im.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> queryAllUser();

    User queryUserById(int userid);

    void addUser(User user);

    void delUser(int id);

    void updateUser(User user);

    User queryUserByName(String name);
}
