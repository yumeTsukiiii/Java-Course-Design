package com.yumetsuki.BasicModule.settleData;

import com.yumetsuki.base.BaseManager;

/**
 * 报销申请信息管理者
 * @author 王小伟
 * @version 1.0*/
public class SettleResultManager extends BaseManager<SettleResult> {
    private static SettleResultManager ourInstance = new SettleResultManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static SettleResultManager getInstance() {
        return ourInstance;
    }

    private SettleResultManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/结算信息.txt","id","name");
    }
}
