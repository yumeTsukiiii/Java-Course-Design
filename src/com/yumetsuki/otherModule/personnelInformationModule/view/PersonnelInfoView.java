package com.yumetsuki.otherModule.personnelInformationModule.view;

import com.yumetsuki.base.Alert.Alert;
import com.yumetsuki.base.MDDatePicker.MDDatePicker;
import com.yumetsuki.base.baseInput.MDComboBox;
import com.yumetsuki.base.baseInput.MDTextView;
import com.yumetsuki.base.data.InfoView;
import com.yumetsuki.otherModule.personnelInformationModule.data.PersonnelVisitInformation;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * 自定义视图类，用于展示人员就诊信息
 * @author 王小伟
 * @version 1.0*/
public class PersonnelInfoView extends VBox implements InfoView<PersonnelVisitInformation> {

    private PersonnelVisitInformation information;

    private Label titleLabel = new Label("人员诊疗信息修改");

    private MDTextView idTextField = new MDTextView();

    private MDTextView clinicNumberTextField = new MDTextView();

    private MDComboBox medicalCategoryComboBox = new MDComboBox();

    private MDTextView hospitalCodeTextField = new MDTextView();

    private MDTextView hospitalNameTextField = new MDTextView();

    private MDDatePicker admissionDatePicker = new MDDatePicker();

    private MDDatePicker dischargeDatePicker = new MDDatePicker();

    private MDTextView reasonForDischargeTextField = new MDTextView();

    /**
     * 构造方法，用于初始化视图和进行数据绑定
     * @param information 绑定的数据*/
    public PersonnelInfoView(PersonnelVisitInformation information){
        this.information = information;
        initView();
    }

    /**
     * 该方法返回视图回传数据
     * @return 视图回传数据*/
    @Override
    public PersonnelVisitInformation returnData() {

        PersonnelVisitInformation information = new PersonnelVisitInformation();

        if (admissionDatePicker.getValue() != null){
            information.setAdmissionDate(admissionDatePicker.getValue().getYear()+"-"+ admissionDatePicker.getValue().getMonthValue()+"-"+ admissionDatePicker.getValue().getDayOfMonth());
        } else {
            information.setAdmissionDate("");
        }

        if (dischargeDatePicker.getValue() != null){
            information.setDischargeDate(dischargeDatePicker.getValue().getYear()+"-"+dischargeDatePicker.getValue().getMonthValue()+"-"+dischargeDatePicker.getValue().getDayOfMonth());
        } else {
            information.setDischargeDate("");
        }

        if (idTextField.getText() == null || idTextField.getText().equals("")){
            Alert.show("请输入人员id！");
            return null;
        }

        if (clinicNumberTextField.getText() == null || clinicNumberTextField.getText().equals("")){
            Alert.show("请输入门诊号/住院号！");
            return null;
        }

        if (medicalCategoryComboBox.getValue() == null) {
            Alert.show("请输入诊疗类别！");
            return null;
        }

        if (hospitalCodeTextField.getText() == null || hospitalCodeTextField.getText().equals("")){
            Alert.show("请输入医院编码！");
            return null;
        }

        if (hospitalNameTextField.getText() == null || hospitalNameTextField.getText().equals("")){
            Alert.show("请输入医院名称！");
            return null;
        }


        information.setClinicNumber(clinicNumberTextField.getText());
        information.setHospitalCode(hospitalCodeTextField.getText());
        information.setHospitalName(hospitalNameTextField.getText());
        information.setMedicalCategory(medicalCategoryComboBox.getValue());
        information.setId(idTextField.getText());
        information.setReasonForDischarge(reasonForDischargeTextField.getText());
        return information;
    }

    /**
     * 初始化视图*/
    private void initView(){

        if (information.getAdmissionDate() != null){
            String [] arr = information.getAdmissionDate().split("-");
            int year,month,day;
            year = Integer.parseInt(arr[0]);
            month = Integer.parseInt(arr[1]);
            day = Integer.parseInt(arr[2]);
            admissionDatePicker.setValue(year,month,day);
        }

        if (information.getDischargeDate() != null){
            String [] arr = information.getDischargeDate().split("-");
            int year,month,day;
            year = Integer.parseInt(arr[0]);
            month = Integer.parseInt(arr[1]);
            day = Integer.parseInt(arr[2]);
            dischargeDatePicker.setValue(year,month,day);
        }

        admissionDatePicker.setTitle("入院日期");
        VBox.setMargin(admissionDatePicker,new Insets(8,0,0,16));
        dischargeDatePicker.setTitle("出院日期");
        VBox.setMargin(dischargeDatePicker,new Insets(8,0,0,16));


        idTextField.setTitle("id");
        idTextField.setText(information.getId());
        VBox.setMargin(idTextField,new Insets(8,0,0,16));

        clinicNumberTextField.setTitle("住院号");
        clinicNumberTextField.setText(information.getClinicNumber());
        VBox.setMargin(clinicNumberTextField,new Insets(8,0,0,16));

        medicalCategoryComboBox.setTitle("诊疗类别");
        medicalCategoryComboBox.setValue(information.getMedicalCategory());
        medicalCategoryComboBox.addAll("在职职工", "退休人员", "享受最低保障的在职人员","享受最低保障的退休人员");
        VBox.setMargin(medicalCategoryComboBox,new Insets(8,0,0,16));

        hospitalCodeTextField.setTitle("医院编码");
        hospitalCodeTextField.setText(information.getHospitalCode());
        VBox.setMargin(hospitalCodeTextField,new Insets(8,0,0,16));

        hospitalNameTextField.setTitle("医院名称");
        hospitalNameTextField.setText(information.getHospitalName());
        VBox.setMargin(hospitalNameTextField,new Insets(8,0,0,16));

        reasonForDischargeTextField.setTitle("出院原因");
        reasonForDischargeTextField.setText(information.getReasonForDischarge());
        reasonForDischargeTextField.setMinWidth(400);
        VBox.setMargin(reasonForDischargeTextField,new Insets(8,0,0,16));


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
        this.getChildren().add(clinicNumberTextField);
        this.getChildren().add(medicalCategoryComboBox);
        this.getChildren().add(hospitalCodeTextField);
        this.getChildren().add(hospitalNameTextField);
        this.getChildren().add(admissionDatePicker);
        this.getChildren().add(dischargeDatePicker);
        this.getChildren().add(reasonForDischargeTextField);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(500);
    }
}
