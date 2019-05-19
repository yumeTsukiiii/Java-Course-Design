package com.yumetsuki.base.MDDialog;

import com.jfoenix.controls.JFXButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.util.function.Consumer;

/**自定义组件，用于弹窗显示
 * @author 王小伟
 * @version 1.0*/
public class MDDialog {

    private Stage stage = new Stage();

    private String title = "Dialog";

    private VBox vBox = new VBox();

    private JFXButton positiveBtn;  //确认按钮

    private JFXButton negativeBtn;  //取消按钮

    private Consumer<ActionEvent> positiveAction;  //确认按钮事件

    private Consumer<ActionEvent> negativeAction;  //取消按钮事件

    private MDDialog(){}

    /**
     * 显示弹窗*/
    public void show(){
        vBox.setAlignment(Pos.TOP_CENTER);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.GRAY);
        vBox.setEffect(dropShadow);
        Scene scene = new Scene(vBox,vBox.getPrefWidth(),vBox.getPrefHeight());
        stage.setScene(scene);
        stage.setTitle(title);
        stage.show();

        //设置按钮大小
        if (negativeAction != null){
            negativeBtn.setFont(new Font(stage.getScene().getWidth()/30));
        }
        if (positiveBtn != null){
            positiveBtn.setFont(new Font(stage.getScene().getWidth()/30));
        }
    }

    /**
     * 关闭弹窗*/
    public void close(){
        stage.close();
    }

    /**
     * 内部类，用于构造弹窗日志*/
    public static class Builder{

        private MDDialog mdDialog = new MDDialog();

        /**
         * 设置标题
         * @param title 标题文本
         * @return 建造者本身*/
        public Builder setTitle(String title){
            mdDialog.title = title;
            return this;
        }

        /**
         * 设置弹窗视图
         * @param node 视图节点
         * @return 建造者本身*/
        public Builder setView(Node node){
            mdDialog.vBox.getChildren().add(node);
            return this;
        }

        /**
         * 设置是否显示确认按钮
         * @param flag 是否显示
         * @return 建造者本身*/
        public Builder showPositiveButton(boolean flag){
            if (flag) mdDialog.positiveBtn = new JFXButton("确认");
            return this;
        }

        /**
         * 设置是否显示取消按钮
         * @param flag 是否显示
         * @return 建造者本身*/
        public Builder showNegativeButton(boolean flag){
            if (flag) mdDialog.negativeBtn = new JFXButton("取消");
            return this;
        }

        /**
         * 设置确认按钮事件
         * @param consumer 确认按钮事件
         * @return 建造者本身*/
        public Builder setOnPositiveButtonListener(Consumer<ActionEvent> consumer){
            mdDialog.positiveAction = consumer;
            return this;
        }

        /**
         * 设置取消按钮事件
         * @param consumer 取消按钮事件
         * @return 建造者本身*/
        public Builder setOnNegativeButtonListner(Consumer<ActionEvent> consumer){
            mdDialog.negativeAction = consumer;
            return this;
        }

        /**
         * 建造弹窗日志,对弹窗进行初始化
         * @return 弹窗日志*/
        public MDDialog build(){
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);

            //对按钮样式及事件进行初始化
            if (mdDialog.positiveBtn != null) {
                HBox.setMargin(mdDialog.positiveBtn,new Insets(32));
                hBox.getChildren().add(mdDialog.positiveBtn);
                mdDialog.positiveBtn.setOnAction(event -> {
                    if (mdDialog.positiveAction != null){
                        mdDialog.positiveAction.accept(event);
                    }
                    mdDialog.close();
                });

                mdDialog.positiveBtn.setBackground(new Background(
                        new BackgroundFill(Color.DODGERBLUE,new CornerRadii(3),new Insets(0))));
                mdDialog.positiveBtn.setButtonType(JFXButton.ButtonType.RAISED);
                mdDialog.positiveBtn.setTextFill(Color.WHITE);
                mdDialog.positiveBtn.setPadding(new Insets(8));
            }

            if (mdDialog.negativeBtn != null) {
                HBox.setMargin(mdDialog.negativeBtn,new Insets(32));
                hBox.getChildren().add(mdDialog.negativeBtn);
                mdDialog.negativeBtn.setOnAction(event -> {
                    if (mdDialog.negativeAction != null){
                        mdDialog.negativeAction.accept(event);
                    }
                    mdDialog.close();
                });

                mdDialog.negativeBtn.setBackground(new Background(
                        new BackgroundFill(Color.rgb(0x9a,0x9a,0x9a),new CornerRadii(3),new Insets(0))));
                mdDialog.negativeBtn.setButtonType(JFXButton.ButtonType.RAISED);
                mdDialog.negativeBtn.setTextFill(Color.WHITE);
                mdDialog.negativeBtn.setPadding(new Insets(8));

            }

            mdDialog.vBox.getChildren().add(hBox);

            return mdDialog;
        }
    }
}
