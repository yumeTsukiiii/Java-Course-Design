package com.yumetsuki.BasicModule.settleData;

import com.yumetsuki.base.baseInput.MDComboBox;
import com.yumetsuki.base.baseInput.MDTextView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.util.ArrayList;

/**
 * 自定义视图类，用于展示结算信息
 * @author 王小伟
 * @version 1.0*/
public class SettleResultView extends ScrollPane {

    private SettleResult result;

    private Label titleLabel = new Label("结算结果");

    private MDTextView idTextView = new MDTextView();  //个人编号

    private MDTextView nameTextView = new MDTextView();  //人员姓名

    private MDTextView categoryTextView = new MDTextView(); //人员类别

    private MDTextView settleDateTextView = new MDTextView(); //结算日期

    private MDTextView startStandardTextView = new MDTextView(); //起付标准

    private MDComboBox medicalHospitalComboBox = new MDComboBox(); //就诊医院

    private MDComboBox medicalTimeRangeComboBox = new MDComboBox();  //就诊时段

    private MDTextView inHospitalTimeTextView = new MDTextView(); //住院次数

    private MDTextView totalExpensesTextView = new MDTextView(); //年度费用总额

    private MDTextView selfFundedAmountTextView = new MDTextView();//年度自费金额

    private MDTextView yearTotalReimbursementAmountTextView = new MDTextView(); //年度总计报销

    private MDComboBox selfFundedItemComboBox = new MDComboBox();  //自费项目

    private MDComboBox bClassItemComboBox = new MDComboBox(); //乙类项目

    /**
     * 构造方法，用于初始化视图和绑定数据
     * @param result 绑定的数据*/
    public SettleResultView(SettleResult result){
        this.result = result;
        initView();
    }

    /**
     * 初始化视图*/
    private void initView() {

        this.getStylesheets().add("/com/yumetsuki/BasicModule/preSettleData/view/scroll-view.css");

        idTextView.setTitle("个人编号");
        idTextView.setText(result.getId());
        idTextView.setEditable(false);
        VBox.setMargin(idTextView,new Insets(32,0,0,16));


        nameTextView.setTitle("姓名");
        nameTextView.setText(result.getName());
        nameTextView.setEditable(false);
        VBox.setMargin(nameTextView,new Insets(8,0,0,16));

        categoryTextView.setTitle("人员类别");
        categoryTextView.setText(result.getCategory());
        categoryTextView.setEditable(false);
        VBox.setMargin(categoryTextView,new Insets(8,0,0,16));

        settleDateTextView.setTitle("结算日期");
        settleDateTextView.setText(result.getSettleDate());
        settleDateTextView.setEditable(false);
        VBox.setMargin(settleDateTextView,new Insets(8,0,0,16));

        startStandardTextView.setTitle("起付标准");
        startStandardTextView.setText(String.valueOf(result.getStartStandard()));
        startStandardTextView.setEditable(false);
        VBox.setMargin(startStandardTextView,new Insets(8,0,0,16));

        medicalHospitalComboBox.setTitle("就诊医院");
        medicalHospitalComboBox.setValue(result.getMedicalHospital().get(0));
        medicalHospitalComboBox.addAllCanNotEditable(result.getMedicalHospital());
        VBox.setMargin(medicalHospitalComboBox,new Insets(8,0,0,16));


        medicalTimeRangeComboBox.setTitle("就诊时段");
        medicalTimeRangeComboBox.setValue(result.getMedicalTimeRange().get(0));
        medicalTimeRangeComboBox.addAllCanNotEditable(result.getMedicalTimeRange());
        VBox.setMargin(medicalTimeRangeComboBox,new Insets(8,0,0,16));


        inHospitalTimeTextView.setTitle("住院次数");
        inHospitalTimeTextView.setText(String.valueOf(result.getInHospitalTime()));
        inHospitalTimeTextView.setEditable(false);
        VBox.setMargin(inHospitalTimeTextView,new Insets(8,0,0,16));

        totalExpensesTextView.setTitle("年度费用总额");
        totalExpensesTextView.setText(String.valueOf(result.getTotalExpenses()));
        totalExpensesTextView.setEditable(false);
        VBox.setMargin(totalExpensesTextView,new Insets(8,0,0,16));

        selfFundedAmountTextView.setTitle("年度自费金额");
        selfFundedAmountTextView.setText(String.valueOf(result.getSelfFundedAmount()));
        selfFundedAmountTextView.setEditable(false);
        VBox.setMargin(selfFundedAmountTextView,new Insets(8,0,0,16));

        yearTotalReimbursementAmountTextView.setTitle("年度总计报销");
        yearTotalReimbursementAmountTextView.setText(String.valueOf(result.getTotalReimbursementAmount()));
        yearTotalReimbursementAmountTextView.setEditable(false);
        VBox.setMargin(yearTotalReimbursementAmountTextView,new Insets(8,0,0,16));

        selfFundedItemComboBox.setTitle("自费项目");
        if (result.getSelfFundedItem().isEmpty()) {
            selfFundedItemComboBox.setValue("无");
        } else {
            selfFundedItemComboBox.setValue(result.getSelfFundedItem().get(0));
        }

        selfFundedItemComboBox.addAllCanNotEditable(result.getSelfFundedItem());
        VBox.setMargin(selfFundedItemComboBox,new Insets(8,0,0,16));

        bClassItemComboBox.setTitle("乙类项目");
        if (result.getBClassItem().isEmpty()){
            bClassItemComboBox.setValue("无");
        } else {
            bClassItemComboBox.setValue(result.getBClassItem().get(0));
        }
        bClassItemComboBox.addAllCanNotEditable(result.getBClassItem());
        VBox.setMargin(bClassItemComboBox,new Insets(8,0,0,16));

        ImageView imageView = new ImageView(new Image("/com/yumetsuki/base/icon/edit_white.png"));
        imageView.setFitHeight(24);
        imageView.setFitWidth(24);
        titleLabel.setBackground(
                new Background(
                        new BackgroundFill(
                                Color.DODGERBLUE,new CornerRadii(0),new Insets(0)))
        );

        titleLabel.setMinWidth(400);
        titleLabel.setPadding(new Insets(16));
        titleLabel.setFont(new Font(20));
        titleLabel.setGraphic(imageView);
        titleLabel.setTextFill(Color.WHITE);

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(titleLabel,
                idTextView,nameTextView, categoryTextView, settleDateTextView,startStandardTextView,
                medicalHospitalComboBox,medicalTimeRangeComboBox,inHospitalTimeTextView,
                totalExpensesTextView, selfFundedAmountTextView,yearTotalReimbursementAmountTextView,
                selfFundedItemComboBox,bClassItemComboBox);
        this.setContent(vBox);
        this.setMaxWidth(400);
        this.setMaxHeight(680);
    }

}
