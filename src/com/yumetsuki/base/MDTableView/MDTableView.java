package com.yumetsuki.base.MDTableView;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;

/**
 * 自定义组件，用于展示数据列表和提供数据操作
 * @author 王小伟
 * @version 1.0*/
public class MDTableView<E> extends ScrollPane{

    private ArrayList<E> data = new ArrayList<>();  //数据列表

    //列表项的操作按钮及其事件
    private ArrayList<Pair<Function<Button,Button>,OnActionButtonListener<E>>> buttons = new ArrayList<>();

    //表头文本
    private ArrayList<String> rowTitles = new ArrayList<>();

    private GridPane gridPane = new GridPane();

    /**
     * 构造方法，用于初始化视图，以及确认绑定数据类型
     * @param eClass 表格绑定的数据类型*/
    public MDTableView(Class<E> eClass){
        setDefaultRowTitle(eClass);
        initMdTableView();
        this.setContent(gridPane);
    }

    /**获取表格中所有数据
     * @return 表格中所有数据的列表*/
    public ArrayList<E> getDatas(){
        return data;
    }

    /**
     * 获取单项数据
     * @param index 数据索引
     * @return 数据项*/
    public E getData(int index){
        return data.get(index);
    }

    /**
     * 更新视图数据
     * @param index 数据在表格中的索引
     * @param e 更新的数据
     * */
    public void upDateData(int index,E e){
        data.set(index,e);
        try {
            updateTable(data);
        } catch (IllegalAccessException e1){
            e1.printStackTrace();
        }
    }

    /**
     * 设置表头文本，用于覆盖默认表格文本
     * @param strings 可变参数列表，表格文本项*/
    public void setRowTitle(String ...strings){
        try {
            rowTitles.clear();
            rowTitles.addAll(Arrays.asList(strings));
            updateTable(this.data);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }

    }

    /**
     * 重构表头数据*/
    private void reBuildRowTitle(){
        for (String s: rowTitles){
            Label label = new Label(s);
            label.getStyleClass().add("row-item");
            gridPane.addRow(0,label);
            this.gridPane.addRow(1,new Separator());
        }
    }

    /**
     * 向表格中添加多项数据
     * @param data 可变参数列表，添加的数据项*/
    public void addData(E ... data){
        this.data.addAll(Arrays.asList(data)) ;
        try {
            updateTable(this.data);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    /**
     * 重构表格数据列表
     * @param data 可变参数列表，重构的数据项*/
    public void reSetData(E ... data){
        this.data.clear();
        this.data.addAll(Arrays.asList(data));
        try {
            updateTable(this.data);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    /**
     * 重构表格数据列表
     * @param list 列表，重构的数据项*/
    public void reSetData(ArrayList<E> list){
        this.data.clear();
        this.data.addAll(list);
        try {
            updateTable(this.data);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    /**
    * 从表格中移除某一项
     * @param index 移除项的数据索引*/
    public void removeItem(int index){
        this.data.remove(index);
        try {
            updateTable(this.data);
        }catch (IllegalAccessException e){
            e.printStackTrace();
        }
    }

    /**
     * 添加动作按钮，用于为每一条数据添加操作
     * @param function 用于对button进行设置的函数
     * @param onActionButtonListener button的事件监听器*/
    public void addActionButton(Function<Button,Button> function, OnActionButtonListener<E> onActionButtonListener){
        this.buttons.add(new Pair<>(function,onActionButtonListener));
        try {
            updateTable(this.data);
        } catch (IllegalAccessException e){
            e.printStackTrace();
        }

    }

    /**
     * 初始化表格样式*/
    private void initMdTableView(){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0x9a,0x9a,0x9a,0.5));
        this.setEffect(dropShadow);
        this.getStylesheets().add("/com/yumetsuki/base/MDTableView/md_table_view.css");
        this.gridPane.getStyleClass().add("table");
    }

    /**更新表格数据
     * @param data 新的表格数据
     * @exception IllegalAccessException 通过反射构造数据项时抛出的受检型异常*/
    private void updateTable(List<E> data) throws IllegalAccessException{
        gridPane.getChildren().clear();
        reBuildRowTitle();
        int i = 2;
        for (E t: data){
            if (t == null){
                return;
            }
            Field [] fields = t.getClass().getDeclaredFields();
            Field.setAccessible(fields,true);
            for (Field field: fields){
                if (field.get(t) != null){
                    this.gridPane.addRow(i,itemLabel(field.get(t).toString()));
                } else {
                    this.gridPane.addRow(i,itemLabel(""));
                }
            }
            for (Pair<Function<Button,Button>,OnActionButtonListener<E>> pair: this.buttons){
                Button btn = new Button();
                btn = pair.getKey().apply(btn);
                btn.setOnAction(event ->  {
                    pair.getValue().onClick(data.indexOf(t),t);
                });
                this.gridPane.addRow(i,btn);
            }
            i++;
        }
    }

    /**
     * 设置默认表头，默认表头为数据类的属性的名称
     * @param eClass 数据类型*/
    private void setDefaultRowTitle(Class<E> eClass){
        Field [] fields= eClass.getDeclaredFields();
        Field.setAccessible(fields,true);
        for (Field field: fields){
            rowTitles.add(field.getName());
            this.gridPane.addRow(0,titleLabel(field.getName()));
        }
        for (String s: rowTitles){
            this.gridPane.addRow(1,new Separator());
        }
    }

    /**
     * 构造表头样式
     * @param s 表头名称
     * @return 显示表头的label控件*/
    private Label titleLabel(String s){
        Label label = new Label(s);
        label.getStyleClass().add("row-item");
        return label;
    }

    /**
     * 构造表格数据项样式
     * @param s 数据项文本
     * @return 显示数据的label控件*/
    private Label itemLabel(String s){
        Label label = new Label(s);
        label.getStyleClass().add("column-item");
        return label;
    }

    /**
     * 用于设置数据项操作按钮的接口*/
    public interface OnActionButtonListener<T>{
        /**
         * 数据项按钮点击操作
         * @param index 数据项索引
         * @param data 数据项*/
        void onClick(int index, T data);
    }

}
