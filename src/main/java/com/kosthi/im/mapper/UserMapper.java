package com.kosthi.im.mapper;

import com.kosthi.im.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> queryAllUser();

    User queryUserById(int userid);

    void addUser(User user);

    void delUser(int id);

    void updateUser(User user);

    User queryUserByAccount(String account);

    boolean queryUserIfOnline(String account);

    void addFriend(@Param("fromId") Long fromId, @Param("toId") Long toId);

    boolean checkIfFriend(@Param("fromId") Long fromId, @Param("toId") Long toId);

    Long findUserIdByAccount(String account);

    int updateUserStatus(@Param("userId") Long userId, @Param("isOnline") Boolean isOnline);

    List<User> queryAllFriends(Long userId);

    Boolean isInSession(@Param("userId") Long userId);

    void addSession(@Param("userId") Long userId);

    void addMsg(@Param("sender_id") Long sender, @Param("received_id") Long receiver, @Param("content") String content);
}
