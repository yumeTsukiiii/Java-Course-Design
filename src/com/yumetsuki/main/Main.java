package com.yumetsuki.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * 主视图，应用入口
 * @author 王小伟
 * @version 1.0*/
public class Main extends Application {

    /**
     * 加载并显示视图
     * @param primaryStage 视图容器*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("1707-20174998-王小伟");
        Scene scene = new Scene(root, 1024, 768);
        primaryStage.setMaxWidth(1024);
        primaryStage.setMaxHeight(768);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> System.exit(0) );
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
