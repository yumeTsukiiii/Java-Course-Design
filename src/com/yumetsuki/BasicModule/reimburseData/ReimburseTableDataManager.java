package com.yumetsuki.BasicModule.reimburseData;

import com.yumetsuki.base.BaseManager;
import javafx.scene.control.ComboBox;

/**
 * 报销申请信息管理者
 * @author 王小伟
 * @version 1.0*/
public class ReimburseTableDataManager extends BaseManager<ReimburseTableData> {
    private static ReimburseTableDataManager ourInstance = new ReimburseTableDataManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static ReimburseTableDataManager getInstance() {
        return ourInstance;
    }

    private ReimburseTableDataManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/报销申请清单.txt","code","id");
    }

}
