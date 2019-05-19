package com.yumetsuki.MedicalBaseModule.ServiceInfoModule;

import com.yumetsuki.base.BaseManager;
import com.yumetsuki.MedicalBaseModule.ServiceInfoModule.data.ServiceInfo;

/**
 * 医疗服务设施信息管理者
 * @author 王小伟
 * @version 1.0*/
public class ServiceInfoManager extends BaseManager<ServiceInfo> {
    private static ServiceInfoManager ourInstance = new ServiceInfoManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static ServiceInfoManager getInstance() {
        return ourInstance;
    }

    private ServiceInfoManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/服务设施项目信息.txt","code","serviceFacilityName");
    }
}
