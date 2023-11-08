package com.kosthi.im.controller;

import com.kosthi.im.entity.User;
import com.kosthi.im.mapper.UserMapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * @author Koschei
 * @date 2023/11/1
 */
@RestController
public class LoginController implements Initializable {
    @FXML
    private Label registLabel;

    @FXML
    private Button loginButton;

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @Resource
    private UserMapper userMapper;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RestTemplate okRestTemplate;

    @RequestMapping("/index")
    @ResponseBody
    public String index() throws URISyntaxException {
        URI uri = new URI("http://vv.video.qq.com/checktime?otype=json");
        // 返回值类型最好与接口提供给方保持一致，否则数据类型不一致无法转换时就会报错。或者使用字符串类型接收，无论什么类型都可以转成字符串类型。
        // 如果没有返回值，则可以指定为 Void.class
        ResponseEntity<String> forEntity = okRestTemplate.getForEntity(uri, String.class);
        // http响应状态码，如成功时返回 200
        int statusCodeValue = forEntity.getStatusCodeValue();
        // http响应的正文内容，即对方返回的数据，如 {"uid":"3030","code":200,"agencyId":"20807"}
        String body = forEntity.getBody();
        // http响应的头信息，如 Content-Type=[text/plain;charset=UTF-8], Content-Length=[44], Date=[Tue, 24 Mar 2020 12:45:43 GMT]}
        HttpHeaders headers = forEntity.getHeaders();
        // headers={Date=[Sun, 02 Apr 2023 03:27:11 GMT], Content-Type=[application/x-javascript; charset=utf-8], Content-Length=[104], Connection=[keep-alive]}
        // statusCodeValue=200
        // body=QZOutputJson={"s":"o","t":1680406031,"ip":"113.247.3.14","pos":"---","rand":"ozmn0gKceH1jmGNakHjMQQ=="};
        System.out.println("headers=" + headers + "\nstatusCodeValue=" + statusCodeValue + "\nbody=" + body);
        return body;
    }

    public String registerUser(User user) {
        // String uri = "http://vv.video.qq.com/checktime?otype={otype}";
        // String uri = "http://localhost:8080/register?username={username}&password={password}";
        String uri = "http://localhost:8080/register";

//        Object[] uriParam = {user.getName(), user.getPassword()};
//
//        // 返回值类型最好与接口提供给方保持一致，否则数据类型不一致无法转换时就会报错。或者使用字符串类型接收，无论什么类型都可以转成字符串类型。
//        // 如果没有返回值，则可以指定为 Void.class
//        return okRestTemplate.postForObject(uri, user, String.class, uriParam);

        // url查询参数.
//        Map<String, String> uriParam = new HashMap<>(4);
//        uriParam.put("username", user.getName());
//        uriParam.put("password", user.getPassword());

        // 创建请求头,添加请求头信息
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "UTF-8");

        // 创建请求体,并添加数据（body参数不需要时，可以省略，需要时添加到bodyParam中即可.）
        Map<String, String> bodyParam = new HashMap<>(2);
        // 与json中的键值一致
        bodyParam.put("username", user.getName());
        bodyParam.put("password", user.getPassword());
        HttpEntity<Map> httpEntity = new HttpEntity<>(bodyParam, httpHeaders);

        // 发送post请求
        // 返回值类型最好与接口提供给方保持一致，否则数据类型不一致无法转换时就会报错。或者使用字符串类型接收，无论什么类型都可以转成字符串类型。
        // 如果没有返回值，则可以指定为 Void.class
        ResponseEntity<String> forEntity = okRestTemplate.postForEntity(uri, httpEntity, String.class);
        // http响应状态码，如成功时返回 200
        int statusCodeValue = forEntity.getStatusCodeValue();
        // http响应的正文内容，即对方返回的数据，如 {"uid":"3030","code":200,"agencyId":"20807"}
        String body = forEntity.getBody();
        // http响应的头信息，如 Content-Type=[text/plain;charset=UTF-8], Content-Length=[44], Date=[Tue, 24 Mar 2020 12:45:43 GMT]}
        HttpHeaders headers = forEntity.getHeaders();
        // ResponseEntity=<200,QZOutputJson={"s":"o","t":1680406100,"ip":"113.247.3.14","pos":"---","rand":"Lv31AXCzAfugJSiHTP_YVg=="};,
        // {Date=[Sun, 02 Apr 2023 03:28:20 GMT],
        // Content-Type=[application/x-javascript; charset=utf-8],
        // Content-Length=[104], Connection=[keep-alive]}>
        System.out.println("ResponseEntity=" + forEntity);
        return body;
    }

    @RequestMapping("/register")
    public String register(@RequestBody User user) {

        // 在这里添加注册逻辑，例如检查用户名是否存在，密码强度是否足够等等
        // ...
        userMapper.addUser(user);

        // 注册成功
        return "User " + user.getName() + " registered successfully!";
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        registLabel.setOnMouseClicked(actionEvent -> {
            if (username.getText().isEmpty() || password.getText().isEmpty()) {
                System.out.println("Empty info");
                return;
            }
            if (showConfirmationDialog()) {

                // back-end service
                String resp = registerUser(new User(username.getText(), password.getText()));
                System.out.println("Back-end: " + resp);

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
