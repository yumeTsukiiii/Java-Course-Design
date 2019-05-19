package com.yumetsuki.MedicalBaseModule.ServiceInfoModule.data;

/**
 * 医疗服务设施信息类
 * @author 王小伟
 * @version 1.0*/
public class ServiceInfo {

    private String code;    //(项目编码): String

    private String serviceFacilityName;  //(服务名称): String

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getServiceFacilityName() {
        return serviceFacilityName;
    }

    public void setServiceFacilityName(String serviceFacilityName) {
        this.serviceFacilityName = serviceFacilityName;
    }
}
