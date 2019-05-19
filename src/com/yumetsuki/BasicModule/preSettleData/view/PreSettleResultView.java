package com.yumetsuki.BasicModule.preSettleData.view;

import com.yumetsuki.BasicModule.preSettleData.PreSettleResult;
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

import java.io.PipedOutputStream;

/**
 * 用于展示预结算结果的类
 *  * @author 王小伟
 *  * @version 1.0*/
public class PreSettleResultView extends ScrollPane {

    private PreSettleResult result;

    private Label titleLabel = new Label("预结算结果");

    private MDTextView idTextView = new MDTextView();  //人员id

    private MDTextView nameTextView = new MDTextView();  //人员名称

    private MDTextView codeTextView = new MDTextView(); //本次报销编码

    private MDTextView certificateNumberTextView = new MDTextView(); //证件编号

    private MDTextView startStandardTextView = new MDTextView(); //起付标准

    private MDTextView totalExpensesTextView = new MDTextView(); //本次费用总额

    private MDTextView reimbursementAmountTextView = new MDTextView();  //本次报销金额

    private MDTextView selfFundedAmountTextView = new MDTextView();//自费金额

    private MDTextView yearTotalReimbursementAmountTextView = new MDTextView(); //年度总计报销

    private MDTextView firstRangeAmountTextView = new MDTextView();  //第一阶段自费

    private MDTextView secondRangeAmountTextView = new MDTextView();  //第二阶段自费

    private MDTextView thirdRangeAmountTextView = new MDTextView();   //第三阶段自费

    /**
     * 构造方法，初始化视图和邦迪数据
     * @param result 绑定的视图书籍*/
    public PreSettleResultView(PreSettleResult result){
        this.result = result;
        initView();
    }

    /**
     * 初始化视图*/
    private void initView() {

        this.getStylesheets().add("/com/yumetsuki/BasicModule/preSettleData/view/scroll-view.css");

        idTextView.setTitle("人员id");
        idTextView.setText(result.getId());
        idTextView.setEditable(false);
        VBox.setMargin(idTextView,new Insets(32,0,0,16));


        nameTextView.setTitle("人员姓名");
        nameTextView.setText(result.getName());
        nameTextView.setEditable(false);
        VBox.setMargin(nameTextView,new Insets(8,0,0,16));

        codeTextView.setTitle("报销编码");
        codeTextView.setText(result.getCode());
        codeTextView.setEditable(false);
        VBox.setMargin(codeTextView,new Insets(8,0,0,16));

        certificateNumberTextView.setTitle("证件编号");
        certificateNumberTextView.setText(result.getCertificateNumber());
        certificateNumberTextView.setEditable(false);
        VBox.setMargin(certificateNumberTextView,new Insets(8,0,0,16));

        startStandardTextView.setTitle("起付标准");
        startStandardTextView.setText(String.valueOf(result.getStartStandard()));
        startStandardTextView.setEditable(false);
        VBox.setMargin(startStandardTextView,new Insets(8,0,0,16));

        totalExpensesTextView.setTitle("本次费用总额");
        totalExpensesTextView.setText(String.valueOf(result.getTotalExpenses()));
        totalExpensesTextView.setEditable(false);
        VBox.setMargin(totalExpensesTextView,new Insets(8,0,0,16));

        reimbursementAmountTextView.setTitle("本次报销金额");
        reimbursementAmountTextView.setText(String.valueOf(result.getReimbursementAmount()));
        reimbursementAmountTextView.setEditable(false);
        VBox.setMargin(reimbursementAmountTextView,new Insets(8,0,0,16));

        selfFundedAmountTextView.setTitle("本次自费金额");
        selfFundedAmountTextView.setText(String.valueOf(result.getSelfFundedAmount()));
        selfFundedAmountTextView.setEditable(false);
        VBox.setMargin(selfFundedAmountTextView,new Insets(8,0,0,16));

        yearTotalReimbursementAmountTextView.setTitle("年度总计报销");
        yearTotalReimbursementAmountTextView.setText(String.valueOf(result.getYearTotalReimbursementAmount()));
        yearTotalReimbursementAmountTextView.setEditable(false);
        VBox.setMargin(yearTotalReimbursementAmountTextView,new Insets(8,0,0,16));

        firstRangeAmountTextView.setTitle("第一阶段自费金额");
        firstRangeAmountTextView.setText(String.valueOf(result.getFirstRangeAmount()));
        firstRangeAmountTextView.setEditable(false);
        VBox.setMargin(firstRangeAmountTextView,new Insets(8,0,0,16));

        secondRangeAmountTextView.setTitle("第二阶段自费金额");
        secondRangeAmountTextView.setText(String.valueOf(result.getSecondRangeAmount()));
        secondRangeAmountTextView.setEditable(false);
        VBox.setMargin(secondRangeAmountTextView,new Insets(8,0,0,16));

        thirdRangeAmountTextView.setTitle("第三阶段自费金额");
        thirdRangeAmountTextView.setText(String.valueOf(result.getThirdRangeAmount()));
        thirdRangeAmountTextView.setEditable(false);
        VBox.setMargin(thirdRangeAmountTextView,new Insets(8,0,0,16));

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
                idTextView,nameTextView,codeTextView,certificateNumberTextView,startStandardTextView,
                totalExpensesTextView,reimbursementAmountTextView,
                selfFundedAmountTextView,yearTotalReimbursementAmountTextView,
                firstRangeAmountTextView,secondRangeAmountTextView,thirdRangeAmountTextView);
        this.setContent(vBox);
        this.setMaxWidth(400);
        this.setMaxHeight(680);
    }

}
