package com.yumetsuki.main;

import com.jfoenix.controls.*;
import com.yumetsuki.BasicModule.preSettleData.PreSettleResult;
import com.yumetsuki.BasicModule.preSettleData.PreSettleResultManager;
import com.yumetsuki.BasicModule.preSettleData.view.PreSettleResultView;
import com.yumetsuki.BasicModule.reimburseData.ReimburseManager;
import com.yumetsuki.BasicModule.reimburseData.ReimburseTableData;
import com.yumetsuki.BasicModule.reimburseData.ReimburseTableDataManager;
import com.yumetsuki.BasicModule.settleData.SettleResult;
import com.yumetsuki.MedicalBaseModule.ServiceInfoModule.data.ServiceInfo;
import com.yumetsuki.MedicalBaseModule.TreatmentInfoModule.data.TreatmentInfo;
import com.yumetsuki.MedicalBaseModule.medicalInformationModule.data.Medical;
import com.yumetsuki.base.Alert.Alert;
import com.yumetsuki.base.MDDialog.MDDialog;
import com.yumetsuki.base.MDToast.MDToast;
import com.yumetsuki.base.baseInfoSearchView.BaseInfoSearchView;
import com.yumetsuki.base.chargeView.ChargeView;
import com.yumetsuki.otherModule.personnelInformationModule.data.PersonnelVisitInformation;
import com.yumetsuki.otherModule.prescriptionDetailsModule.data.PrescriptionDetails;
import com.yumetsuki.publicServiceModule.personInformationModule.data.MedicalStaff;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import org.apache.poi.POIDocument;

import java.io.IOException;
import java.util.ArrayList;


public class MainController {
    @FXML private Button mPublicBusinessBtn;  //公共业务

    @FXML private Button mMedicalBaseInformationBtn;  //医疗基本信息

    @FXML private Button mMedicalInformationBtn;  //医疗服务信息

    @FXML private Button mReimburseBtn;  //报销相关

    @FXML private VBox list_one;  //公共业务列表项

    @FXML private VBox list_two;  //医疗基本信息列表项

    @FXML private VBox list_three;  //医疗服务信息列表项

    @FXML private VBox list_four;  //报销相关列表项

    @FXML private BorderPane mMDTable;  //主操作视图容器

    private ArrayList<Button> itemsOne = new ArrayList<>();

    private ArrayList<Button> itemsTwo = new ArrayList<>();

    private ArrayList<Button> itemsThree = new ArrayList<>();

    private ArrayList<Button> itemsFour = new ArrayList<>();

    /**
     * 初始化列表项按钮*/
    public MainController(){
        initItemsOne();
        initItemsTwo();
        initItemsThree();
        initItemFour();
    }

    /**
     * 公共业务按钮点击事件
     * @param event 事件*/
    @FXML
    public void onPublicBusinessBtnClick(ActionEvent event){
        if (list_one.getChildren().isEmpty()){
            list_one.getChildren().addAll(itemsOne);
        } else {
            list_one.getChildren().removeAll(itemsOne);
        }
    }

    /**
     * 医疗基本信息按钮点击事件
     * @param event 事件*/
    @FXML
    public void onMedicalBaseInfoBtnClick(ActionEvent event){
        if (list_two.getChildren().isEmpty()){
            list_two.getChildren().addAll(itemsTwo);
        } else {
            list_two.getChildren().removeAll(itemsTwo);
        }
    }

    /**
     * 医疗服务按钮点击事件
     * @param event 事件*/
    @FXML
    public void onMedicalInfoBtnClick(ActionEvent event){
        if (list_three.getChildren().isEmpty()){
            list_three.getChildren().addAll(itemsThree);
        } else {
            list_three.getChildren().removeAll(itemsThree);
        }
    }

    /**
     * 退出按钮点击事件
     * @param event 事件*/
    @FXML
    public void onExitBtnClick(ActionEvent event){
        System.exit(0);
    }

    /**
     * 报销按钮点击事件
     * @param event 事件*/
    @FXML
    public void onReimburseBtnClick(ActionEvent event){
        if (list_four.getChildren().isEmpty()){
            list_four.getChildren().addAll(itemsFour);
        } else {
            list_four.getChildren().removeAll(itemsFour);
        }
    }

    /**
     * 初始化第一列表项*/
    private void initItemsOne(){
        Button button = new Button("个人基本信息");
        button.getStyleClass().add("button-item");
        button.setAlignment(Pos.CENTER_LEFT);
        button.setOnAction(event -> {
            mMDTable.setCenter(null);
            BaseInfoSearchView<MedicalStaff> searchView = new BaseInfoSearchView<>(MedicalStaff.class);
            searchView.setTitle("个人基本信息查询");
            searchView.setHint("请输入个人id");
            searchView.setRowTitle("个人id","姓名","性别","民族","生日","证件类型","证件编码");
            mMDTable.setCenter(searchView);
        });
        itemsOne.add(button);
    }

    /**
     * 初始化第二列表项*/
    private void initItemsTwo(){
        Button button1 = new Button("药品信息");
        button1.getStyleClass().add("button-item");
        button1.setAlignment(Pos.CENTER_LEFT);

        button1.setOnAction(event -> {
            mMDTable.setCenter(null);
            BaseInfoSearchView<Medical> searchView = new BaseInfoSearchView<>(Medical.class);
            searchView.setTitle("药品信息查询");
            searchView.setHint("请输入药品编码");
            searchView.setRowTitle("药品编码","药品名称","最高限价","药品计量单位","收费等级","药品医院等级","医院编码");
            mMDTable.setCenter(searchView);
        });

        Button button2 = new Button("诊疗项目信息");
        button2.getStyleClass().addAll("button-item");
        button2.setAlignment(Pos.CENTER_LEFT);

        button2.setOnAction(event -> {
            mMDTable.setCenter(null);
            BaseInfoSearchView<TreatmentInfo> searchView = new BaseInfoSearchView<>(TreatmentInfo.class);
            searchView.setTitle("诊疗项目查询");
            searchView.setHint("请输入项目编码");
            searchView.setRowTitle("项目编码","项目名称","收费等级","项目医院等级","医院编码");
            mMDTable.setCenter(searchView);
        });

        Button button3 = new Button("服务设施项目信息");
        button3.getStyleClass().addAll("button-item");
        button3.setAlignment(Pos.CENTER_LEFT);
        button3.setOnAction(event -> {
            mMDTable.setCenter(null);
            BaseInfoSearchView<ServiceInfo> searchView = new BaseInfoSearchView<>(ServiceInfo.class);
            searchView.setTitle("服务设施项目查询");
            searchView.setHint("请输入项目编码");
            searchView.setRowTitle("项目编码","服务名称");
            mMDTable.setCenter(searchView);
        });

        itemsTwo.add(button1);
        itemsTwo.add(button2);
        itemsTwo.add(button3);
    }

    /**
     * 初始化第三列表项*/
    private void initItemsThree(){
        Button button1 = new Button("人员就诊信息");
        button1.getStyleClass().add("button-item");
        button1.setAlignment(Pos.CENTER_LEFT);
        button1.setOnAction(event -> {
            mMDTable.setCenter(null);
            BaseInfoSearchView<PersonnelVisitInformation> searchView = new BaseInfoSearchView<>(PersonnelVisitInformation.class);
            searchView.setTitle("人员就诊信息查询");
            searchView.setHint("请输入个人id");
            searchView.setRowTitle("id","门诊号","医疗类别","医院编号","医院名称","入院日期","出院日期","出院原因");
            mMDTable.setCenter(searchView);
        });

        Button button2 = new Button("处方信息");
        button2.getStyleClass().add("button-item");
        button2.setAlignment(Pos.CENTER_LEFT);
        button2.setOnAction(event -> {
            mMDTable.setCenter(null);
            BaseInfoSearchView<PrescriptionDetails> searchView = new BaseInfoSearchView<>(PrescriptionDetails.class);
            searchView.setTitle("处方信息查询");
            searchView.setHint("请输入门诊号");
            searchView.setRowTitle("门诊号","项目编码","单价","数量","金额");
            mMDTable.setCenter(searchView);
        });

        itemsThree.add(button1);
        itemsThree.add(button2);

    }

    /**
     * 初始化第四列表项*/
    private void initItemFour(){

        Button button1 = new Button("结算与打印");
        button1.getStyleClass().add("button-item");
        button1.setAlignment(Pos.CENTER_LEFT);
        button1.setOnAction(event -> {

            mMDTable.setCenter(null);
            VBox vBox = new VBox();
            BaseInfoSearchView<ReimburseTableData> searchView = new BaseInfoSearchView<>(ReimburseTableData.class);
            searchView.setTitle("医疗报销");
            searchView.setHint("请输入人员id");
            searchView.setRowTitle("人员id","姓名","证件编号","报销编码(门诊号)","报销金额","预结算状态","结算状态");
            vBox.getChildren().add(searchView);

            JFXButton preSettleButton = new JFXButton("预结算");
            preSettleButton.setOnAction(event1 -> {
                if (searchView.getData().isEmpty() || searchView.getData().get(0) == null){
                    MDToast.makeText("未查询到报销清单",searchView.getScene().getWindow(),MDToast.SHORT).show();
                } else {
                    MDDialog.Builder builder = new MDDialog.Builder();
                    ChargeView chargeView = new ChargeView();
                    chargeView.setTitle("请选择预结算对象");
                    ArrayList<Pair<Integer,RadioButton>> radioButtons = new ArrayList<>();
                    for (int i = 0; i < searchView.getData().size(); i++) {
                        if (searchView.getData().get(i).isAlreadyReimburse()) continue;
                        radioButtons.add(new Pair<>(i,new JFXRadioButton(searchView.getData().get(i).getCode()+"： "+searchView.getData().get(i).getName())));
                    }
                    chargeView.addRadioButton(radioButtons);

                    //选择预结算对象后进行结算
                    builder.setView(chargeView)
                            .showPositiveButton(true)
                            .showNegativeButton(true)
                            .setOnPositiveButtonListener(event2 -> {
                                try{
                                    Pair<Integer,RadioButton> pair = chargeView.getSelectItem();
                                    if (pair == null){
                                        Alert.show("请选择预结算人员对象！");
                                        return;
                                    }

                                    ReimburseTableData data = searchView.getData().get(pair.getKey());

                                    ReimburseManager manager = ReimburseManager.getInstance();
                                    //进行预结算，获取预结算结果
                                    PreSettleResult result = manager.preSettle(data);

                                    // 展现预结算结果
                                    manager.showPreSettleResult(result);
                                    //将预结算结果写入文件
                                    manager.writePreSettleResultToFile(result);
                                    // 更新该人当次报销单信息
                                    ReimburseTableData newData = new ReimburseTableData();
                                    newData = manager.upDateReimburseTableData(data,result,newData);
                                    searchView.upDateData(searchView.getData().indexOf(data),newData);


                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                            }).build().show();
                }
            });

            JFXButton settleButton = new JFXButton("结算");
            settleButton.setOnAction(event1 -> {
                if (searchView.getData().isEmpty() || searchView.getData().get(0) == null){
                    MDToast.makeText("未查询到报销清单",searchView.getScene().getWindow(),MDToast.SHORT).show();
                } else {
                    ReimburseManager manager = ReimburseManager.getInstance();
                    try {

                        for (ReimburseTableData data: searchView.getData()){
                            if (!data.isAlreadyReimburse()) {
                                Alert.show("有未预结算信息！");
                                return;
                            }
                            if (data.isFinallyReimburse()){
                                Alert.show("有已结算信息！");
                                return;
                            }
                        }

                        SettleResult result = manager.settle(searchView.getData());
                        if (result != null){
                            manager.showSettleResult(result);
                            manager.writeSettleResultToFile(result);
                            for (ReimburseTableData data: searchView.getData()){
                                ReimburseTableData newData = new ReimburseTableData();
                                newData = manager.upDateReimburseTableData(data,newData);
                                searchView.upDateData(searchView.getData().indexOf(data),newData);
                            }

                        } else {
                            Alert.show("未查询到预结算信息！");
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }
            });

            JFXButton printButton = new JFXButton("打印结算信息");
            printButton.setOnAction(event1 -> {
                if (searchView.getData().isEmpty() || searchView.getData().get(0) == null){
                    MDToast.makeText("未查询到报销清单",searchView.getScene().getWindow(),MDToast.SHORT).show();
                } else {
                    ReimburseManager manager = ReimburseManager.getInstance();
                    try {
                        for (ReimburseTableData data: searchView.getData()){
                            if (!data.isAlreadyReimburse()) {
                                Alert.show("有未预结算信息！");
                                return;
                            }
                            if (!data.isFinallyReimburse()){
                                Alert.show("有未结算信息！");
                                return;
                            }
                        }

                        manager.printSettleResultToExcel(searchView.getData().get(0));
                        MDToast.makeText("打印成功！",vBox.getScene().getWindow(),MDToast.SHORT).show();


                    } catch (IOException e){
                        e.printStackTrace();
                    }

                }
            });

            HBox hBox = new HBox();
            hBox.getChildren().addAll(preSettleButton,settleButton,printButton);
            hBox.setAlignment(Pos.CENTER);
            HBox.setMargin(settleButton,new Insets(0,0,0,32));
            HBox.setMargin(printButton,new Insets(0,0,0,32));

            VBox.setMargin(hBox,new Insets(32));
            vBox.getChildren().add(hBox);
            vBox.setAlignment(Pos.TOP_CENTER);
            mMDTable.setCenter(vBox);
        });

        itemsFour.add(button1);

    }


}
