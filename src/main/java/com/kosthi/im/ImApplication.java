package com.kosthi.im;

import com.kosthi.im.controller.ServerListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.net.URL;

@SpringBootApplication
public class ImApplication extends Application {
    // 任何地方都可以通过这个applicationContext获取springboot的上下文
    public static ConfigurableApplicationContext applicationContext;
    private static String[] args;

    public static void main(String[] args) {
        ImApplication.args = args;
        launch(args);
        new ServerListener().start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL resource = getClass().getResource("/login.fxml");
        if (resource == null) {
            throw new Exception();
        }
        // 加载 fxml 下面的逻辑可以单独封装
        FXMLLoader loader = new FXMLLoader(resource);
        loader.setControllerFactory(new Callback<Class<?>, Object>() {
            @Override
            public Object call(Class<?> param) {
                // 控制器工厂提供bean注入，此处的缺点是不能根据bean名字注入，只能通过class类型注入bean
                // 解决方案：
                // 1、SpringbootJavafxDemoApplication.applicationContext.getBean("Bean Name", Bean.class);
                // 2、@Autowired private ApplicationContext applicationContext;
                // Object bean_name = applicationContext.getBean("bean Name", Bean.class);
                return applicationContext.getBean(param);
            }
        });
        // 加载
        VBox root = loader.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {
        // 启动springboot
        applicationContext = SpringApplication.run(ImApplication.class, args);
    }

    @Override
    public void stop() throws Exception {
        // 关闭springboot
        applicationContext.stop();
    }
}
