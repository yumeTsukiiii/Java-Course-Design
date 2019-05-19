package com.yumetsuki.base.MDDatePicker;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.time.LocalDate;

/**
 * 自定义组件，用于选择日期
 * @author 王小伟
 * @version 1.0*/
public class MDDatePicker extends VBox{

    private Label label = new Label();

    private DatePicker datePicker = new DatePicker();

    /**
     * 构造方法，初始化视图*/
    public MDDatePicker(){
        initView();
    }

    /**
     * 设置标题
     * @param s 标题内容*/
    public void setTitle(String s){
        label.setText(s);
    }

    /**设置值
     * @param year 年份
     * @param month 月份
     * @param day 日期*/
    public void setValue(int year, int month, int day){
        datePicker.setValue(LocalDate.of(year,month,day));
    }

    /**
     * 获取值
     * @return 日期值*/
    public LocalDate getValue(){
        return datePicker.getValue();
    }

    /**
     * 初始化视图*/
    private void initView(){
        datePicker.getStylesheets().add("/com/yumetsuki/base/MDDatePicker/md_date_picker.css");
        datePicker.setEditable(false);
        datePicker.showingProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    if (newValue){
                        label.setTextFill(Color.DODGERBLUE);
                        datePicker.getStyleClass().add("date-picker-show");
                    } else {
                        label.setTextFill(Color.rgb(0x9a,0x9a,0x9a));
                        datePicker.getStyleClass().remove("date-picker-show");
                    }
            }
        });
        label.setTextFill(Color.rgb(0x9a,0x9a,0x9a));
        VBox.setMargin(label,new Insets(0,0,8,0));
        this.getChildren().add(label);
        this.getChildren().add(datePicker);
    }
}
