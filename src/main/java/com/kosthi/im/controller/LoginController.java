package com.kosthi.im.controller;

import com.kosthi.im.entity.User;
import com.kosthi.im.mapper.UserMapper;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Koschei
 * @date 2023/11/1
 */
@Controller
public class LoginController implements Initializable {
    @FXML
    private Label registLabel;

    @FXML
    private Button loginButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Resource
    private UserMapper userMapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registLabel.setOnMouseClicked(actionEvent -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                System.out.println("Empty info");
                return;
            }
            if (showConfirmationDialog()) {
                userMapper.addUser(new User(username.getText(), password.getText()));

                // 创建信息对话框
                Alert alert = new Alert(Alert.AlertType.INFORMATION);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");

                alert.setTitle("注册");
                alert.setHeaderText(null);  // 如果不需要头部文本，设置为null
                alert.setContentText("注册成功!");
                alert.showAndWait();

                System.out.println("注册成功!");
                System.out.println("Username: " + username.getText() + " password: " + password.getText());
            }
        });

        loginButton.setOnAction(actionEvent -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                return;
            }
            User user = userMapper.queryUserByName(username.getText());
            // 登录失败
            if (user == null) {

                // 创建信息对话框
                Alert alert = new Alert(Alert.AlertType.ERROR);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");

                alert.setTitle("登录");
                alert.setHeaderText(null);  // 如果不需要头部文本，设置为null
                alert.setContentText("未找到用户!");
                alert.showAndWait();

                System.out.println("未找到用户: " + username.getText());
                return;
            }
            if (!user.getPassword().equals(password.getText())) {

                // 创建信息对话框
                Alert alert = new Alert(Alert.AlertType.ERROR);

                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");

                alert.setTitle("登录");
                alert.setHeaderText(null);  // 如果不需要头部文本，设置为null
                alert.setContentText("密码错误!");
                alert.showAndWait();

                System.out.println("密码错误: " + password.getText());
                return;
            }

            // 创建信息对话框
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");

            alert.setTitle("登录");
            alert.setHeaderText(null);  // 如果不需要头部文本，设置为null
            alert.setContentText("登录成功!");
            alert.showAndWait();

            System.out.println("登录成功!");
            System.out.println(user.getName() + " " + user.getPassword());
            System.out.println("Username: " + username.getText() + " password: " + password.getText());
        });
    }

    private boolean showConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        // 设置字体样式
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-font-family: 'Times New Roman'; -fx-font-size: 14px;");

        alert.setTitle("注册");
        alert.setHeaderText(null);
        alert.setContentText("请确定是否注册?");

        // 显示对话框并等待用户响应
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}
