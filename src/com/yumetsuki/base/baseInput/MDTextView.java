package com.yumetsuki.base.baseInput;

import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


/**自定义组件，用于显示和编辑数据项
 * @author 王小伟
 * @version 1.0 */
public class MDTextView extends HBox {

    private Label label = new Label();

    private JFXTextField textField = new JFXTextField();

    /**
     * 构造方法，初始化视图*/
    public MDTextView(){
        initView();
    }

    /**
     * 设置标题
     * @param s 标题文本*/
    public void setTitle(String s){
        label.setText(s);
    }

    /**
     * 设置文本框无内容时显示的内容
     * @param s 文本框无内容时显示的内容*/
    public void setHint(String s){
        textField.setPromptText(s);
    }

    /**
     * 获取文本框内容
     * @return 文本框内容*/
    public String getText(){
        return textField.getText();
    }

    /**
     * 设置文本框内容
     * @param s 文本框内容*/
    public void setText(String s){
        textField.setText(s);
    }

    /**设置文本框是否可编辑
     * @param flag 文本框是否可编辑*/
    public void setEditable(boolean flag){
        textField.setEditable(false);
    }

    /**
     * 初始化视图*/
    private void initView(){
        this.getStylesheets().add("/com/yumetsuki/base/baseInput/base_text_view.css");
        VBox vBox = new VBox();
        label.setMinWidth(this.getWidth());
        label.setTextFill(Color.rgb(0x9a,0x9a,0x9a));
        vBox.getChildren().add(label);
        vBox.getChildren().add(textField);
        VBox.setMargin(label,new Insets(0,0,8,0));
        this.getChildren().add(vBox);
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    label.setTextFill(Color.DODGERBLUE);
                } else {
                    label.setTextFill(Color.rgb(0x9a,0x9a,0x9a));
                }
            }
        });
        HBox.setMargin(textField,new Insets(0,0,0,8));
        HBox.setMargin(label,new Insets(0,0,0,8));
        this.setAlignment(Pos.BOTTOM_LEFT);
    }

}
