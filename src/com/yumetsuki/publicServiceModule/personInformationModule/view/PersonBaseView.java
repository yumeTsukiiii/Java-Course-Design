package com.yumetsuki.publicServiceModule.personInformationModule.view;

import com.jfoenix.controls.JFXAlert;
import com.yumetsuki.base.Alert.Alert;
import com.yumetsuki.base.MDDatePicker.MDDatePicker;
import com.yumetsuki.base.MDToast.MDToast;
import com.yumetsuki.base.Value;
import com.yumetsuki.base.baseInput.MDComboBox;
import com.yumetsuki.base.baseInput.MDTextView;
import com.yumetsuki.base.data.InfoView;
import com.yumetsuki.publicServiceModule.personInformationModule.data.MedicalStaff;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 自定义视图类，用于展示人员基本信息
 * @author 王小伟
 * @version 1.0*/
public class PersonBaseView extends VBox implements InfoView<MedicalStaff> {

    private MedicalStaff medicalStaff;

    private Label titleLabel = new Label("人员基本信息修改");

    private MDTextView idTextField = new MDTextView();

    private MDComboBox sexListView = new MDComboBox();

    private MDDatePicker mdDatePicker = new MDDatePicker();

    private MDComboBox certificateTypeBox = new MDComboBox();

    private MDTextView nameTextField = new MDTextView();

    private MDTextView nationalityTextField = new MDTextView();

    private MDTextView certificateNumberTextField = new MDTextView();

    /**
     * 构造方法，用于初始化视图和进行数据绑定
     * @param medicalStaff 绑定的数据*/
    public PersonBaseView(MedicalStaff medicalStaff){
        this.medicalStaff = medicalStaff;
        initView();
    }

    /**
     * 该方法返回视图回传数据
     * @return 视图回传数据*/
    @Override
    public MedicalStaff returnData(){

        MedicalStaff medicalStaff = new MedicalStaff();

        if (idTextField.getText() == null || idTextField.getText().equals("")){
            Alert.show("请填写id！");
            return null;
        }

        if (nameTextField.getText() == null || nameTextField.getText().equals("")){
            Alert.show("请填写姓名！");
            return null;
        }

        medicalStaff.setName(nameTextField.getText());
        System.out.println(nameTextField.getText());
        if (mdDatePicker.getValue() != null){
            medicalStaff.setBirthday(mdDatePicker.getValue().getYear()+"-"+mdDatePicker.getValue().getMonthValue()+"-"+mdDatePicker.getValue().getDayOfMonth());
        } else {
            medicalStaff.setBirthday("");
        }

        medicalStaff.setId(idTextField.getText());
        medicalStaff.setCertificateNumber(certificateNumberTextField.getText());
        medicalStaff.setTypeOfCertificate(certificateTypeBox.getValue());
        medicalStaff.setNationality(nationalityTextField.getText());
        medicalStaff.setSex(sexListView.getValue());
        return medicalStaff;
    }

    /**
     * 初始化视图*/
    private void initView(){
        idTextField.setTitle("id");
        idTextField.setText(medicalStaff.getId());
        VBox.setMargin(idTextField,new Insets(32,0,0,16));

        nameTextField.setTitle("姓名");
        nameTextField.setText(medicalStaff.getName());
        VBox.setMargin(nameTextField,new Insets(8,0,0,16));

        //性别
        sexListView.setTitle("性别");
        sexListView.setValue(medicalStaff.getSex());
        sexListView.addAll(Value.Sex.MALE.getName(),Value.Sex.FEMALE.getName());
        VBox.setMargin(sexListView,new Insets(8,0,0,16));

        nationalityTextField.setTitle("民族");
        nationalityTextField.setText(medicalStaff.getNationality());
        VBox.setMargin(nationalityTextField,new Insets(8,0,0,16));


        if (medicalStaff.getBirthday() != null){
            String [] arr = medicalStaff.getBirthday().split("-");
            int year,month,day;
            year = Integer.parseInt(arr[0]);
            month = Integer.parseInt(arr[1]);
            day = Integer.parseInt(arr[2]);
            mdDatePicker.setValue(year,month,day);
        }

        //出生日期
        mdDatePicker.setTitle("出生日期");
        VBox.setMargin(mdDatePicker,new Insets(8,0,0,16));

        //证件类型
        certificateTypeBox.setTitle("证件类型");
        certificateTypeBox.addAll("居民身份证","医保卡");
        certificateTypeBox.setValue(medicalStaff.getTypeOfCertificate());
        VBox.setMargin(certificateTypeBox,new Insets(8,0,0,16));

        certificateNumberTextField.setTitle("证件编号");
        certificateNumberTextField.setText(medicalStaff.getCertificateNumber());
        VBox.setMargin(certificateNumberTextField,new Insets(8,0,0,16));

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
        this.getChildren().add(idTextField);
        this.getChildren().add(nameTextField);
        this.getChildren().add(sexListView);
        this.getChildren().add(mdDatePicker);
        this.getChildren().add(nationalityTextField);
        this.getChildren().add(certificateTypeBox);
        this.getChildren().add(certificateNumberTextField);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(500);
    }
}
