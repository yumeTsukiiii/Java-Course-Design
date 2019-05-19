package com.yumetsuki.base.MDToast;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Tooltip;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Window;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 自定义组件，用于显示提示信息，仿android的Toast
 * @author 王小伟
 * @version 1.0*/
public class MDToast{

    //2000ms的显示时间
    public static final int SHORT = 2000;

    /**
     * 初始化toast建造者
     * @param text toast文本
     * @param window toast所在窗口
     * @param duration 显示时间
     * @return 建造者实例*/
    public static Builder makeText(String text, Window window, int duration){
        Builder builder = new Builder();
        builder.tooltip = new Tooltip(text);
        builder.window = window;
        builder.duration = duration;
        return builder;
    }

    /**
     * toast建造者，用于显示toast文本*/
    public static class Builder {

        private Tooltip tooltip;

        private Window window;

        private int duration = 2000;

        /**
         * 显示toast*/
        public void show(){
            tooltip.setFont(new Font(25));
            tooltip.setTextAlignment(TextAlignment.CENTER);
            tooltip.setTextOverrun(OverrunStyle.CENTER_ELLIPSIS);
            tooltip.setMinSize(300,100);
            tooltip.show(window);

            //toast在duration时间后消失
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> tooltip.hide());
                    this.cancel();
                }
            },duration,1000);
        }

    }

}
