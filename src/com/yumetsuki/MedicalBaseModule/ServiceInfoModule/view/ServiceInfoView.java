package com.yumetsuki.MedicalBaseModule.ServiceInfoModule.view;

import com.yumetsuki.MedicalBaseModule.ServiceInfoModule.data.ServiceInfo;
import com.yumetsuki.base.Alert.Alert;
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
 * 自定义视图类，用于展示医疗服务设施信息
 * @author 王小伟
 * @version 1.0*/
public class ServiceInfoView extends VBox implements InfoView<ServiceInfo> {

    private ServiceInfo serviceInfo;

    private Label titleLabel = new Label("服务设施项目信息修改");

    private MDTextView codeTextView = new MDTextView();  //项目编码

    private MDTextView serviceFacilityNameTextView = new MDTextView();  //服务名称

    /**
     * 构造方法，用于初始化视图和进行数据绑定
     * @param serviceInfo 绑定的数据*/
    public ServiceInfoView(ServiceInfo serviceInfo) {
        this.serviceInfo = serviceInfo;
        initView();
    }

    /**
     * 该方法返回视图回传数据
     * @return 视图回传数据*/
    @Override
    public ServiceInfo returnData() {

        ServiceInfo serviceInfo = new ServiceInfo();

        if (codeTextView.getText() == null || codeTextView.getText().equals("")){
            Alert.show("请输入服务设施编码！");
            return null;
        }

        serviceInfo.setCode(codeTextView.getText());
        serviceInfo.setServiceFacilityName(serviceFacilityNameTextView.getText());
        return serviceInfo;
    }

    /**
     * 初始化视图*/
    private void initView(){

        codeTextView.setTitle("项目编码");
        codeTextView.setText(serviceInfo.getCode());
        VBox.setMargin(codeTextView,new Insets(8,0,0,16));

        serviceFacilityNameTextView.setTitle("服务名称");
        serviceFacilityNameTextView.setText(serviceInfo.getServiceFacilityName());
        VBox.setMargin(serviceFacilityNameTextView,new Insets(8,0,0,16));

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
        this.getChildren().add(codeTextView);
        this.getChildren().add(serviceFacilityNameTextView);
        this.setAlignment(Pos.CENTER);
        this.setMaxWidth(500);
    }
}
