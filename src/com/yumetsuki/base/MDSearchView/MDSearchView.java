package com.yumetsuki.base.MDSearchView;

import com.jfoenix.controls.JFXButton;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.function.Consumer;

/**
 * 自定义组件，用于信息搜索
 * @author 王小伟
 * @version 1.0*/
public class MDSearchView extends GridPane {

    private TextField textField = new TextField();  //搜索文本框

    private JFXButton button = new JFXButton();  //搜索按钮

    /**
     * 构造方法，初始化视图*/
    public MDSearchView(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0x9a,0x9a,0x9a,0.5));
        this.getStylesheets().add("/com/yumetsuki/base/MDSearchView/md_search_view.css");
        initSearchButton();
        textField.setEffect(dropShadow);
        button.setEffect(dropShadow);
        this.setHgap(8);
        this.addRow(0,textField);
        this.addRow(0,button);
        HBox.setMargin(button,new Insets(0,0,0,8));
    }

    /**设置文本框无文本时显示内容
     * @param hint 文本框无文本时显示内容*/
    public void setHint(String hint) {
        this.textField.setPromptText(hint);
    }

    /**
    * 设置搜索按钮事件
     * @param onClickListener 搜索按钮事件*/
    public void setOnSearchButtonClickListener(final Consumer<String> onClickListener){
        button.setOnAction(event -> onClickListener.accept(textField.getText()));
    }

    /**
     * 初始化搜索按钮样式*/
    private void initSearchButton(){
        ImageView imageView = new ImageView(new Image("/com/yumetsuki/base/icon/search.png"));
        imageView.setFitHeight(26);
        imageView.setFitWidth(26);
        button.setGraphic(imageView);
    }
}
