package com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.view;

import com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.data.TreatmentInfo;
import com.yumetsuki.base.Alert.Alert;
import com.yumetsuki.base.baseInput.MDComboBox;
import com.yumetsuki.base.baseInput.MDTextView;
import com.yumetsuki.base.data.InfoView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 自定义视图类，用于展示诊疗服务信息
 * @author 王小伟
 * @version 1.0*/
public class TreatmentInfoView extends VBox implements InfoView<TreatmentInfo> {

    private TreatmentInfo treatmentInfo;

    private Label titleLabel = new Label("诊疗项目信息修改");

    private MDTextView projectCodeTextView = new MDTextView();  //项目编码

    private MDTextView projectNameTextView = new MDTextView();  //项目名称

    private MDComboBox chargeItemLevelComboBox = new MDComboBox();  //项目等级

    private MDComboBox hospitalLevelComboBox = new MDComboBox(); //医院等级

    private MDTextView hospitalCodeTextView = new MDTextView(); //医院编码

    /**
     * 构造方法，用于初始化视图和进行数据绑定
     * @param treatmentInfo 绑定的数据*/
    public TreatmentInfoView(TreatmentInfo treatmentInfo){
        this.treatmentInfo = treatmentInfo;
        initView();
    }

    /**
     * 该方法返回视图回传数据
     * @return 视图回传数据*/
    @Override
    public TreatmentInfo returnData() {

        TreatmentInfo treatmentInfo = new TreatmentInfo();

        if (projectCodeTextView.getText() == null || projectCodeTextView.getText().equals("")){
            Alert.show("请输入诊疗设备编码");
            return null;
        }

        if (projectNameTextView.getText() == null || projectNameTextView.getText().equals("")){
            Alert.show("请输入诊疗设备名称");
            return null;
        }

        if (hospitalLevelComboBox.getValue() == null){
            Alert.show("请输入诊疗设备医院等级");
            return null;
        }

        if (hospitalCodeTextView.getText() == null || hospitalCodeTextView.getText().equals("")){
            Alert.show("请输入医院编码");
            return null;
        }


        treatmentInfo.setChargeItemLevel(chargeItemLevelComboBox.getValue());
        treatmentInfo.setHospitalLevel(hospitalLevelComboBox.getValue());
        treatmentInfo.setProjectCode(projectCodeTextView.getText());
        treatmentInfo.setProjectName(projectNameTextView.getText());
        treatmentInfo.setHospitalCode(hospitalCodeTextView.getText());
        return treatmentInfo;
    }

    /**
     * 初始化视图*/
    private void initView(){

        projectCodeTextView.setTitle("项目编码");
        projectCodeTextView.setText(treatmentInfo.getProjectCode());
        VBox.setMargin(projectCodeTextView,new Insets(8,0,0,16));

        projectNameTextView.setTitle("项目名称");
        projectNameTextView.setText(treatmentInfo.getProjectName());
        VBox.setMargin(projectNameTextView,new Insets(8,0,0,16));

        chargeItemLevelComboBox.setTitle("项目等级");
        chargeItemLevelComboBox.addAll("甲类","乙类","丙类");
        chargeItemLevelComboBox.setValue(treatmentInfo.getChargeItemLevel());
        VBox.setMargin(chargeItemLevelComboBox,new Insets(8,0,0,16));

        hospitalLevelComboBox.setTitle("医院等级");
        hospitalLevelComboBox.addAll("一级","二级","三级");
        hospitalLevelComboBox.setValue(treatmentInfo.getHospitalLevel());
        VBox.setMargin(hospitalLevelComboBox,new Insets(8,0,0,16));

        hospitalCodeTextView.setTitle("医院编码");
        hospitalCodeTextView.setText(treatmentInfo.getHospitalCode());
        VBox.setMargin(hospitalCodeTextView,new Insets(8,0,0,16));

        ImageView imageView = new ImageView(new Image("/com/yumetsuki/base/icon/edit_white.png"));
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        titleLabel.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.DODGERBLUE,new CornerRadii(0),new Insets(0)))
        );

        titleLabel.setMinWidth(500);
        titleLabel.setPadding(new Insets(16));
        titleLabel.setFont(new Font(20));
        titleLabel.setGraphic(imageView);
        titleLabel.setTextFill(Color.WHITE);
        this.getChildren().add(titleLabel);
        this.getChildren().add(projectCodeTextView);
        this.getChildren().add(projectNameTextView);
        this.getChildren().add(chargeItemLevelComboBox);
        this.getChildren().add(hospitalLevelComboBox);
        this.getChildren().add(hospitalCodeTextView);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(500);
    }
}
