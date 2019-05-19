package com.yumetsuki.base.Alert;


import com.jfoenix.controls.JFXAlert;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


/*** 这个类用于弹出警告框，提示用户错误操作或信息缺失等
 * @author 王小伟
 * @version 1.0
 */
public class Alert {

    /**该方法用于构造并弹出警告窗
     * @param s 提示信息*/
    public static void show(String s){

        //设置构造并样式
        JFXAlert<String> alert = new JFXAlert<>();
        alert.setSize(250,100);
        VBox vBox = new VBox();
        Text text = new Text(s);
        vBox.getChildren().add(text);
        vBox.setAlignment(Pos.CENTER);
        VBox.setMargin(text,new Insets(16));
        text.setFont(new Font(18));
        text.setFill(Color.RED);

        //设置视图并显示
        alert.setContent(vBox);
        alert.show();
    }

}
