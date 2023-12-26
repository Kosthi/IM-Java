package com.kosthi.im.controller;

import com.kosthi.im.entity.UpdateUserStatusRequest;
import com.kosthi.im.entity.User;
import com.kosthi.im.mapper.UserMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Koschei
 * @date 2023/11/1
 */
@RestController
public class LoginController {
    @Resource
    private UserMapper userMapper;

    @GetMapping("/exist")
    public boolean checkIfUserExists(@RequestParam("account") String account) {
        // 这里应该包含逻辑来检查用户是否存在
        // 例如，您可以查询数据库来检查帐户是否存在
        // 假设您有一个userService来处理这些逻辑
        return userMapper.queryUserByAccount(account) != null;
    }

    @GetMapping("/online")
    public boolean checkIfUserOnline(@RequestParam("account") String account) {
        // 这里应该包含逻辑来检查用户是否存在
        // 例如，您可以查询数据库来检查帐户是否存在
        // 假设您有一个userService来处理这些逻辑
        System.out.println(userMapper.queryUserIfOnline(account));
        return userMapper.queryUserIfOnline(account);
    }

    @GetMapping("/user")
    public ResponseEntity<?> queryUserByAccount(@RequestParam("account") String account) {
        User foundUser = userMapper.queryUserByAccount(account);
        if (foundUser != null) {
            // 移除敏感信息后返回User
            foundUser.setPassword(null);
            return ResponseEntity.ok(foundUser);
        }
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("错误：账号不存在");
    }

    @RequestMapping("/register")
    public String register(@RequestBody User user) {
        // 在这里添加注册逻辑，例如检查用户名是否存在，密码强度是否足够等等
        userMapper.addUser(user);
        // 注册成功
        return "User " + user.getUsername() + " registered successfully!";
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User foundUser = userMapper.queryUserByAccount(user.getAccount());

        if (foundUser != null && foundUser.getPassword().equals(user.getPassword())) {
            // 查询会话表中是否有该用户数据，若有，则更新登录状态，若无，插入一条上线状态
            Long userId = userMapper.findUserIdByAccount(user.getAccount());
            if (userMapper.isInSession(userId)) {
                userMapper.updateUserStatus(userId, true);
            } else {
                userMapper.addSession(userId);
            }
            // 密码匹配，移除敏感信息后返回User
            foundUser.setPassword(null); // 确保不返回密码
            return ResponseEntity.ok(foundUser);
        } else {
            // 密码不匹配，返回错误消息
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("错误：账号不存在或密码不匹配");
        }
    }

    @RequestMapping("/addFriend")
    public boolean addFriend(@RequestParam("from") String from,
                             @RequestParam("to") String to) {
        Long userId1 = userMapper.findUserIdByAccount(from);
        Long userId2 = userMapper.findUserIdByAccount(to);

        if (userId1 != null && userId2 != null) {
            userMapper.addFriend(userId1, userId2);
            userMapper.addFriend(userId2, userId1);
            return true;
        } else {
            return false;
        }
    }

    @GetMapping("/friend")
    public boolean checkIfFriend(@RequestParam("from") String from,
                                 @RequestParam("to") String to) {
        Long userId1 = userMapper.findUserIdByAccount(from);
        Long userId2 = userMapper.findUserIdByAccount(to);

        if (userId1 != null && userId2 != null) {
            return userMapper.checkIfFriend(userId1, userId2);
        }
        return false;
    }

    @PutMapping("/status")
    public ResponseEntity<String> updateUserStatus(@RequestBody UpdateUserStatusRequest userStatusReq) {
        Long userId = userMapper.findUserIdByAccount(userStatusReq.getAccount());
        int rows = userMapper.updateUserStatus(userId, userStatusReq.getIsOnline());
        // System.out.println(rows);
        if (rows > 0) {
            return ResponseEntity.ok("User status updated successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating user status");
    }

    @GetMapping("/getFriends")
    public ResponseEntity<List<User>> updateUserStatus(@RequestParam("account") String account) {
        Long userId = userMapper.findUserIdByAccount(account);
        List<User> userList = userMapper.queryAllFriends(userId);
        return ResponseEntity.ok(userList);
    }

//    @GetMapping("/sendMsg")
//    public ResponseEntity<?> sendMsg(@RequestParam("account") String account) {
//        Long userId = userMapper.findUserIdByAccount(account);
//        List<User> userList = userMapper.queryAllFriends(userId);
//        return ResponseEntity.ok(userList);
//    }
}
