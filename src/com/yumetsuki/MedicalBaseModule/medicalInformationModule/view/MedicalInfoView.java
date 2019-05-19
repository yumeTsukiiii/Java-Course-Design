package com.yumetsuki.MedicalBaseModule.medicalInformationModule.view;

import com.yumetsuki.MedicalBaseModule.medicalInformationModule.data.Medical;
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
 * 自定义视图类，用于展示药品信息
 * @author 王小伟
 * @version 1.0*/
public class MedicalInfoView extends VBox implements InfoView<Medical> {

    private Medical medical;

    private Label titleLabel = new Label("药品信息修改");

    private MDTextView codeTextField = new MDTextView();

    private MDTextView nameTextField = new MDTextView();

    private MDTextView maximumPriceTextField = new MDTextView();

    private MDTextView unitTextField = new MDTextView();

    private MDComboBox itemLevelComboBox = new MDComboBox();

    private MDComboBox hospitalLevelComboBox = new MDComboBox();

    private MDTextView hospitalCodeTextField = new MDTextView();

    /**
     * 构造方法，用于初始化视图和进行数据绑定
     * @param medical 绑定的数据*/
    public MedicalInfoView(Medical medical){
        this.medical = medical;
        initView();
    }

    /**
     * 该方法返回视图回传数据
     * @return 视图回传数据*/
    @Override
    public Medical returnData() {

        Medical medical = new Medical();

        if (codeTextField.getText() == null || codeTextField.getText().equals("")){
            Alert.show("请输入药品编码！");
            return null;
        }

        if (nameTextField.getText() == null || nameTextField.getText().equals("")){
            Alert.show("请输入药品名称！");
            return null;
        }

        if (maximumPriceTextField.getText() == null || maximumPriceTextField.getText().equals("")){
            Alert.show("请输入最高限价！");
            return null;
        }

        try {
            medical.setMaximumPrice(Double.parseDouble(maximumPriceTextField.getText()));
            if (Double.parseDouble(maximumPriceTextField.getText()) == 0){
                Alert.show("药品限价必须大于0！");
                return null;
            }
        } catch (NumberFormatException e){
            e.printStackTrace();
            Alert.show("药品最高限价必须为数字！");
            return null;
        }

        if (itemLevelComboBox.getValue() == null){
            Alert.show("请输入收费项目等级！");
            return null;
        }

        if (hospitalLevelComboBox.getValue() == null){
            Alert.show("请输入药品医院等级！");
            return null;
        }

        if (hospitalCodeTextField.getText() == null || hospitalCodeTextField.getText().equals("")){
            Alert.show("请输入医院编码！");
            return null;
        }

        medical.setName(nameTextField.getText());
        medical.setCode(codeTextField.getText());
        medical.setChargeItemLevel(itemLevelComboBox.getValue());
        medical.setDrugDosageUnit(unitTextField.getText());

        medical.setHospitalLevel(hospitalLevelComboBox.getValue());
        medical.setHospitalCode(hospitalCodeTextField.getText());
        return medical;
    }

    /**
     * 初始化视图*/
    public void initView(){
        codeTextField.setTitle("药品编码");
        codeTextField.setText(medical.getCode());
        VBox.setMargin(codeTextField,new Insets(8,0,0,16));

        nameTextField.setTitle("药品名称");
        nameTextField.setText(medical.getName());
        VBox.setMargin(nameTextField,new Insets(8,0,0,16));

        maximumPriceTextField.setTitle("最高限价");
        maximumPriceTextField.setText(String.valueOf(medical.getMaximumPrice()));
        VBox.setMargin(maximumPriceTextField,new Insets(8,0,0,16));

        unitTextField.setTitle("单位名称");
        unitTextField.setText(medical.getDrugDosageUnit());
        VBox.setMargin(unitTextField,new Insets(8,0,0,16));

        itemLevelComboBox.setTitle("收费项目等级");
        itemLevelComboBox.addAll("甲类","乙类","丙类");
        itemLevelComboBox.setValue(medical.getChargeItemLevel());
        VBox.setMargin(itemLevelComboBox,new Insets(8,0,0,16));

        hospitalLevelComboBox.setTitle("药品医院等级");
        hospitalLevelComboBox.addAll("一级","二级","三级");
        hospitalLevelComboBox.setValue(medical.getHospitalLevel());
        VBox.setMargin(hospitalLevelComboBox,new Insets(8,0,0,16));

        hospitalCodeTextField.setTitle("医院编码");
        hospitalCodeTextField.setText(medical.getHospitalLevel());
        VBox.setMargin(hospitalCodeTextField,new Insets(8,0,0,16));

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
        this.getChildren().add(codeTextField);
        this.getChildren().add(nameTextField);
        this.getChildren().add(maximumPriceTextField);
        this.getChildren().add(unitTextField);
        this.getChildren().add(itemLevelComboBox);
        this.getChildren().add(hospitalLevelComboBox);
        this.getChildren().add(hospitalCodeTextField);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(500);
    }

}
