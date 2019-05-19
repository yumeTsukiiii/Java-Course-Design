package com.yumetsuki.base.baseInfoSearchView;

import com.jfoenix.controls.JFXButton;
import com.yumetsuki.BasicModule.reimburseData.ReimburseTableData;
import com.yumetsuki.BasicModule.reimburseData.ReimburseTableDataManager;
import com.yumetsuki.MedicalBaseModule.ServiceInfoModule.ServiceInfoManager;
import com.yumetsuki.MedicalBaseModule.ServiceInfoModule.data.ServiceInfo;
import com.yumetsuki.MedicalBaseModule.ServiceInfoModule.view.ServiceInfoView;
import com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.TreatmentInfoManager;
import com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.data.TreatmentInfo;
import com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.view.TreatmentInfoView;
import com.yumetsuki.MedicalBaseModule.medicalInformationModule.MedicalManager;
import com.yumetsuki.MedicalBaseModule.medicalInformationModule.data.Medical;
import com.yumetsuki.MedicalBaseModule.medicalInformationModule.view.MedicalInfoView;
import com.yumetsuki.base.BaseManager;
import com.yumetsuki.base.MDDialog.MDDialog;
import com.yumetsuki.base.MDSearchView.MDSearchView;
import com.yumetsuki.base.MDTableView.MDTableView;
import com.yumetsuki.base.MDToast.MDToast;
import com.yumetsuki.base.data.InfoView;
import com.yumetsuki.otherModule.personnelInformationModule.VisitInformationManager;
import com.yumetsuki.otherModule.personnelInformationModule.data.PersonnelVisitInformation;
import com.yumetsuki.otherModule.personnelInformationModule.view.PersonnelInfoView;
import com.yumetsuki.otherModule.prescriptionDetailsModule.PrescriptionDetailsManager;
import com.yumetsuki.otherModule.prescriptionDetailsModule.data.PrescriptionDetails;
import com.yumetsuki.otherModule.prescriptionDetailsModule.view.PresciptionInfoView;
import com.yumetsuki.publicServiceModule.personInformationModule.MedicalStaffManager;
import com.yumetsuki.publicServiceModule.personInformationModule.data.MedicalStaff;
import com.yumetsuki.publicServiceModule.personInformationModule.view.PersonBaseView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 该类为增删改查视图的封装，用于所有信息的增删改查
 * @author 王小伟
 * @version 1.0
 * */
public class BaseInfoSearchView<T> extends VBox {

    /**
     * 基础增删改查管理者*/
    private BaseManager<T> manager;

    /**
     * 管理的数据类型*/
    private Class<T> type;

    /**
     * 标题*/
    private Label titleLabel = new Label();

    /**
     * 添加数据按钮*/
    private JFXButton addDataButton = new JFXButton("添加");

    /**
     * 自定义数据表格*/
    private MDTableView<T> mdTableView;

    /**
     * 自定义搜索框*/
    private MDSearchView mdSearchView = new MDSearchView();

    /**构造方法，用于初始化视图及管理者
     * @param tClass 视图所管理的数据类型*/
    public BaseInfoSearchView(Class<T> tClass){
        this.type = tClass;
        initManager(type);
        mdTableView = new MDTableView<>(tClass);
        initView();
    }

    /**
     * 设置标题
     * @param s 标题文本*/
    public void setTitle(String s){
        titleLabel.setText(s);
    }

    /**
     * 获取表格中的数据
     * @return 表格中的所有数据项,若无，则为空集合*/
    public ArrayList<T> getData(){
        return mdTableView.getDatas();
    }

    /**向表格中添加数据
     * @param ts 可变参数，向表格中添加的数据*/
    public void addData(T...ts){
        mdTableView.addData(ts);
    }

    /**设置搜索框无内容时文本
     * @param s 搜索框无内容时文本*/
    public void setHint(String s){
        mdSearchView.setHint(s);
    }

    /**设置表头，用于覆盖默认表头
     * @param strings 可变参数字符串列表，*/
    public void setRowTitle(String ...strings){
        mdTableView.setRowTitle(strings);
    }

    /**更新表格中的数据
     * @param index 表格中某一行数据的位置
     * @param t 表格中某一行数据*/
    public void upDateData(int index,T t){
        mdTableView.upDateData(index,t);
    }

    /**
     * 初始化视图*/
    private void initView(){
        this.getStylesheets().add("/com/yumetsuki/base/baseInfoSearchView/base_info_search_view.css");
        initLabel();  //初始化标题
        initAddButton();  //初始化添加按钮
        initTable();  //初始化表格

        //设置搜索框搜索按钮事件
        mdSearchView.setOnSearchButtonClickListener( s -> {
            try {
                // 每一次都先按多项搜索进行搜索，无内容再按id单项搜索
                ArrayList<T> list = manager.searchByMark(s,type);
                if (searchByMarkDoing(list)) return;

                T t = manager.searchById(s,type);
                if (t == null){
                    MDToast.makeText("未查询到相关信息",this.getScene().getWindow(),MDToast.SHORT).show();
                }

                //重设置数据，更新表格内容
                mdTableView.reSetData(t);

            } catch (IOException e){
                e.printStackTrace();
            }
        });
        this.getChildren().add(titleLabel);
        HBox hBox = new HBox();
        hBox.getChildren().add(mdSearchView);
        hBox.getChildren().add(addDataButton);
        hBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setMargin(addDataButton,new Insets(0,0,0,300));
        this.setMaxWidth(256);
        VBox.setMargin(hBox,new Insets(0,0,8,0));
        this.getChildren().add(hBox);
        this.getChildren().add(mdTableView);
    }

    /**
     * 初始化标题*/
    private void initLabel(){
        titleLabel.setFont(new Font(48));
        VBox.setMargin(titleLabel,new Insets(96,0,48,0));
    }

    /**
     * 初始化添加按钮*/
    private void initAddButton(){
        ImageView imageView = new ImageView(new Image("/com/yumetsuki/base/icon/plus-circle.png"));
        imageView.setFitWidth(24);
        imageView.setFitHeight(24);
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);
        dropShadow.setColor(Color.rgb(0x9a,0x9a,0x9a,0.5));
        addDataButton.setGraphic(imageView);
        addDataButton.setEffect(dropShadow);
        //设置添加按钮事件
        addDataButton.setOnAction(event -> addBtnBuildDialog(type));
    }

    /**
     * 初始化表格*/
    private void initTable(){
        mdTableView.getStyleClass().add("md-table");

        //初始化表格项编辑事件
        mdTableView.addActionButton(button -> {
            ImageView imageView = new ImageView(new Image("/com/yumetsuki/base/icon/edit.png"));
            imageView.setFitHeight(15);
            imageView.setFitWidth(15);
            button.setGraphic(imageView);
            return button;
        },(index, data) -> {
            MDDialog dialog = buildMDDialog(index,data,0);
            if (dialog != null){
                dialog.show();
            }
        });

        //初始化表格删除事件
        mdTableView.addActionButton(button -> {
            ImageView imageView = new ImageView(new Image("/com/yumetsuki/base/icon/delete.png"));
            imageView.setFitHeight(15);
            imageView.setFitWidth(15);
            button.setGraphic(imageView);
            return button;
        },(index, data) -> {
            try {
                manager.removeFromFile(data);
                mdTableView.removeItem(index);
                MDToast.makeText("删除成功",this.getScene().getWindow(),MDToast.SHORT).show();
            } catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    /**构造弹窗日志，用于添加或修改数据
     * @param index 表格中某一行数据项的位置索引
     * @param t 需要进行添加或修改的数据
     * @param mode 构造表格的模式，用于区分添加和编辑
     * @return 构造好的弹窗日志*/
    private MDDialog buildMDDialog(int index, T t,int mode){
        String name = t.getClass().getSimpleName();
        InfoView<T> view = null;
        String title = null;
        //通过泛型判断是操作哪一种数据类型，从而构造不同弹窗
        switch (name){
            case "MedicalStaff":
                view = (InfoView<T>) new PersonBaseView((MedicalStaff) t);
                title = "人员基本信息";
                break;
            case "Medical":
                view = (InfoView<T>) new MedicalInfoView((Medical) t);
                title = "药品信息";
                break;
            case "TreatmentInfo":
                view = (InfoView<T>) new TreatmentInfoView((TreatmentInfo) t);
                title = "诊疗项目信息";
                break;
            case "PrescriptionDetails":
                view = (InfoView<T>) new PresciptionInfoView((PrescriptionDetails) t);
                title = "处方明细";
                break;
            case "ServiceInfo":
                view = (InfoView<T>) new ServiceInfoView((ServiceInfo) t);
                title = "服务设施项目信息";
                break;

            case "PersonnelVisitInformation":
                view = (InfoView<T>) new PersonnelInfoView((PersonnelVisitInformation) t);
                title = "人员就诊信息";
                break;

        }

        //如果没有设置可操作的对应的数据，则返回null
        if (view == null && title == null) return null;
        MDDialog.Builder builder = new MDDialog.Builder();
        final InfoView<T> trueView = view;
        builder.setTitle(title)
                .setView((Node) view)
                .showPositiveButton(true)
                .showNegativeButton(true)
                .setOnPositiveButtonListener(event -> {
                    //设置弹窗确认按钮点击事件
                    try {
                        //mode == 0 时，则更新数据
                        if (mode == 0){
                            T data = trueView.returnData();
                            if (data == null) return;
                            if (manager.upDate(trueView.returnData(),t)){
                                MDToast.makeText("更新成功",this.getScene().getWindow(),MDToast.SHORT).show();
                                mdTableView.upDateData(index, trueView.returnData());
                            } else {
                                MDToast.makeText("更新失败",this.getScene().getWindow(),MDToast.SHORT).show();
                            }

                            //mode == 1时，添加数据
                        } else if (mode == 1){
                            T data = trueView.returnData();
                            if (data != null){
                                if (manager.addToFile(data)){
                                    MDToast.makeText("添加成功",this.getScene().getWindow(),MDToast.SHORT).show();
                                    mdTableView.addData(data);
                                } else {
                                    MDToast.makeText("添加失败,有相同id数据",this.getScene().getWindow(),MDToast.SHORT).show();
                                }
                            }
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                });
        return builder.build();
    }

    /**初始化数据管理者，用于对不同数据进行管理
     * @param type 数据类型，用于判断构造不同管理者*/
    private void initManager(Class<T> type){
        switch (type.getSimpleName()) {
            case "MedicalStaff":
                manager = (BaseManager<T>) MedicalStaffManager.getInstance();
            break;
            case "Medical":
                manager = (BaseManager<T>) MedicalManager.getInstance();
                break;
            case "TreatmentInfo":
                manager = (BaseManager<T>) TreatmentInfoManager.getInstance();
                break;
            case "PrescriptionDetails":
                manager = (BaseManager<T>) PrescriptionDetailsManager.getInstance();
                break;
            case "ServiceInfo":
                manager = (BaseManager<T>) ServiceInfoManager.getInstance();
                break;
            case "PersonnelVisitInformation":
                manager = (BaseManager<T>) VisitInformationManager.getInstance();
                break;
            case "ReimburseTableData":
                manager = (BaseManager<T>) ReimburseTableDataManager.getInstance();
                break;
        }
    }

    /**判断多项搜索是否搜索到了多项数据
     * @param list 多项搜索的结果列表
     * @return 多项搜索列表是否为空，为空则返回false，否则为true*/
    private boolean searchByMarkDoing(ArrayList<T> list){
        if (list.isEmpty()){
            return false;
        } else {
            mdTableView.reSetData(list);
            return true;
        }
    }

    /**判断添加按钮应该构造哪一个弹窗视图
     * @param type 数据类型*/
    private void addBtnBuildDialog(Class<T> type){
        switch (type.getSimpleName()) {
            case "MedicalStaff":
                buildMDDialog(0,(T) new MedicalStaff(),1).show();
                break;
            case "Medical":
                buildMDDialog(0,(T) new Medical(),1).show();
                break;
            case "TreatmentInfo":
                buildMDDialog(0,(T) new TreatmentInfo(),1).show();
                break;
            case "PrescriptionDetails":
                buildMDDialog(0,(T) new PrescriptionDetails(),1).show();
                break;
            case "ServiceInfo":
                buildMDDialog(0,(T) new ServiceInfo(),1).show();
                break;

            case "PersonnelVisitInformation":
                buildMDDialog(0,(T) new PersonnelVisitInformation(),1).show();
                break;
        }
    }

}
