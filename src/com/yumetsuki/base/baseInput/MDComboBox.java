package com.yumetsuki.base.baseInput;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Random;

/**
 * 自定义组件，用于选择多项数据选择
 * @author 王小伟
 * @version 1.0 */
public class MDComboBox extends VBox {

    private Label titleLabel = new Label();

    private MenuButton button = new MenuButton();

    /**
     * 构造方法，用于初始化视图*/
    public MDComboBox(){
        initView();
    }

    /**
     * 设置控件标题
     * @param s 标题文本*/
    public void setTitle(String s){
        titleLabel.setText(s);
    }

    /**
     * 添加选择项
     * @param strings 可变参数列表，用于构造选择项*/
    public void addAll(String ... strings){
        for (String s: strings){
            MenuItem menuItem = new MenuItem(s);
            menuItem.setOnAction(event -> {
                button.setText(s);
            });
            button.getItems().add(menuItem);
        }
    }

    /**
     * 添加选择项
     * @param list 列表，用于构造选择项*/
    public void addAll(ArrayList<String> list){
        for (String s: list){
            MenuItem menuItem = new MenuItem(s);
            menuItem.setOnAction(event -> {
                button.setText(s);
            });
            button.getItems().add(menuItem);
        }
    }

    /**
     * 添加不可修改选择项的选择项，只用于显示
     * @param list 列表，用于构造选择项*/
    public void addAllCanNotEditable(ArrayList<String> list){
        for (String s: list){
            MenuItem menuItem = new MenuItem(s);
            button.getItems().add(menuItem);
        }
    }

    /**
     * 获取选择项的值
     * @return 返回选择项的值*/
    public String getValue(){
        return button.getText();
    }

    /**
     * 设置选择项的值
     * @param s 选择项的值*/
    public void setValue(String s){
        button.setText(s);
    }

    /**
     * 初始化视图*/
    private void initView(){
        this.getStylesheets().add("/com/yumetsuki/base/baseInput/md_combo_box.css");
        titleLabel.getStyleClass().add("titleLabel");
        VBox.setMargin(titleLabel,new Insets(0,0,8,0));
        button.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    titleLabel.setTextFill(Color.DODGERBLUE);
                } else {
                    titleLabel.setTextFill(Color.rgb(0x9a,0x9a,0x9a));
                }
            }
        });
        this.getChildren().add(titleLabel);
        this.getChildren().add(button);
    }

}
