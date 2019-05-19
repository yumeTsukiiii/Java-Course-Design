package com.yumetsuki.otherModule.prescriptionDetailsModule.view;

import com.yumetsuki.base.Alert.Alert;
import com.yumetsuki.base.baseInput.MDTextView;
import com.yumetsuki.base.data.InfoView;
import com.yumetsuki.otherModule.prescriptionDetailsModule.data.PrescriptionDetails;
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
import javafx.util.Pair;

/**
 * 自定义视图类，用于展示处方明细信息
 * @author 王小伟
 * @version 1.0*/
public class PresciptionInfoView extends VBox implements InfoView<PrescriptionDetails> {

    private PrescriptionDetails details;

    private Label titleLabel = new Label("处方信息修改");

    private MDTextView clinicNumberTextView = new MDTextView();  //门诊号

    private MDTextView itemCodeTextView = new MDTextView();  //药品编码

    private MDTextView priceTextView  = new MDTextView();  //单价

    private MDTextView countTextView  = new MDTextView(); //数量

    /**
     * 构造方法，用于初始化视图和进行数据绑定
     * @param details 绑定的数据*/
    public PresciptionInfoView(PrescriptionDetails details){
        this.details = details;
        initView();
    }

    /**
     * 该方法返回视图回传数据
     * @return 视图回传数据*/
    @Override
    public PrescriptionDetails returnData() {

        PrescriptionDetails details = new PrescriptionDetails();

        if (clinicNumberTextView.getText() == null || clinicNumberTextView.getText().equals("")){
            Alert.show("请输入门诊号/住院号！");
            return null;
        }

        if (countTextView.getText() == null || countTextView.getText().equals("")){
            Alert.show("请输入项目数量！");
            return null;
        }

        if (itemCodeTextView.getText() == null || itemCodeTextView.getText().equals("")){
            Alert.show("请输入项目编码！");
            return null;
        }

        if (priceTextView.getText() == null || itemCodeTextView.getText().equals("")){
            Alert.show("请输入项目单价！");
            return null;
        }

        details.setClinicNumber(clinicNumberTextView.getText());

        try {
            details.setCount(Integer.parseInt(countTextView.getText()));
        } catch (NumberFormatException e){
            e.printStackTrace();
            Alert.show("项目数量必须为整数！");
            return null;
        }

        details.setItemCode(itemCodeTextView.getText());

        try {
            details.setPrice(Double.parseDouble(priceTextView.getText()));
        } catch (NumberFormatException e){
            e.printStackTrace();
            Alert.show("单价必须为数字！");
            return null;
        }

        return details;
    }

    /**
     * 初始化视图*/
    private void initView(){

        clinicNumberTextView.setTitle("住院号");
        clinicNumberTextView.setText(details.getClinicNumber());
        VBox.setMargin(clinicNumberTextView,new Insets(8,0,0,16));

        itemCodeTextView.setTitle("项目编码");
        itemCodeTextView.setText(details.getItemCode());
        VBox.setMargin(itemCodeTextView,new Insets(8,0,0,16));

        priceTextView.setTitle("单价");
        priceTextView.setText(String.valueOf(details.getPrice()));
        VBox.setMargin(priceTextView,new Insets(8,0,0,16));

        countTextView.setTitle("数量");
        countTextView.setText(String.valueOf(details.getCount()));
        VBox.setMargin(countTextView,new Insets(8,0,0,16));

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
        this.getChildren().add(clinicNumberTextView);
        this.getChildren().add(itemCodeTextView);
        this.getChildren().add(priceTextView);
        this.getChildren().add(countTextView);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(500);
    }
}
