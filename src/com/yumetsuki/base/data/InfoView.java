package com.yumetsuki.base.data;

/**
 * 统一接口，用于管理数据的基本视图
 * @author 王小伟
 * @version 1.0 */
public interface InfoView<T> {
    /**
     * 设置视图的回调结果
     * @return 返回回调数据结果*/
    T returnData();
}
