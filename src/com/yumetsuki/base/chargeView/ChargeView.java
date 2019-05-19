package com.yumetsuki.base.chargeView;

import com.jfoenix.controls.JFXRadioButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**自定义组件，用于提示用户选择某项操作
 * @author 王小伟
 * @version 1.0*/
public class ChargeView extends VBox {

    private Label label = new Label();

    private ToggleGroup group = new ToggleGroup();

    private FlowPane flowPane = new FlowPane();

    private ArrayList<Pair<Integer,RadioButton>> list = new ArrayList<>();

    /**
     * 构造方法，初始化视图*/
    public ChargeView(){
        initView();
    }

    /**设置标题
     * @param s 标题内容*/
    public void setTitle(String s){
        label.setText(s);

    }

    /**添加选单选框
     * @param list 单选框列表，包含单选框及其索引*/
    public void addRadioButton(ArrayList<Pair<Integer,RadioButton>> list){
        this.list.addAll(list);
        if (list.isEmpty()){
            Label label = new Label("无");
            label.setFont(new Font(15));
            label.setTextFill(Color.RED);
            flowPane.getChildren().add(label);
        }
        for (Pair<Integer,RadioButton> pair: list){
            pair.getValue().setToggleGroup(group);
            flowPane.getChildren().add(pair.getValue());
        }
    }

    /**获取选择项
     * @return 返回选择项及其索引*/
    public Pair<Integer,RadioButton> getSelectItem(){
        for (Pair<Integer,RadioButton> pair: list){
            if (pair.getValue().isSelected()){
                return pair;
            }
        }
        return null;
    }

    /**
     * 初始化视图*/
    private void initView(){
        this.setAlignment(Pos.CENTER);
        VBox.setMargin(flowPane,new Insets(8));
        VBox.setMargin(label,new Insets(8));
        label.setFont(new Font(15));
        label.setTextFill(Color.rgb(0x9a,0x9a,0x9a));
        flowPane.setAlignment(Pos.CENTER);
        this.getChildren().addAll(label,flowPane);
    }

}
