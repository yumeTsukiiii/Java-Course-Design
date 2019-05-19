package com.yumetsuki.BasicModule.preSettleData;

import com.google.gson.Gson;
import com.yumetsuki.base.BaseManager;

import java.io.*;
import java.util.ArrayList;

/**
 * 预结算结果管理者
 * @author 王小伟
 * @version 1.0*/
public class PreSettleResultManager extends BaseManager<PreSettleResult> {
    private static PreSettleResultManager ourInstance = new PreSettleResultManager();

    /**
     * 获取单例
     * @return 管理者单例*/
    public static PreSettleResultManager getInstance() {
        return ourInstance;
    }

    private PreSettleResultManager() {
        super("out/production/MedicialClass/com/yumetsuki/file/预结算信息.txt","code","id");
    }

    /**
     * 重写父类方法，允许添加id相同的数据
     * @param data 添加的数据项
     * @return 添加是否成功标志*/
    @Override
    public boolean addToFile(PreSettleResult data) {
        try{
            Gson gson = new Gson();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            String json = gson.toJson(data);
            writer.write(json);
            writer.newLine();
            writer.flush();
            writer.close();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
