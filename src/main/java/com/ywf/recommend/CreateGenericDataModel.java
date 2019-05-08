package com.ywf.recommend;


import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;

/**
 * ClassName CreateGenericDataModel
 * 功能: CreateGenericDataModel
 * 运行方式与参数: CreateGenericDataModel
 * Author yangweifeng
 * Date 2019-04-26 16:05
 * Version 1.0
 **/
public class CreateGenericDataModel {
    public static void main(String[] args) {
        FastByIDMap<PreferenceArray> preferenceArrayFastByIDMap = new FastByIDMap<>();
        PreferenceArray User1Pref = CreatePreferenceArray.getSingleUser();
        // 可以put多条数据
        preferenceArrayFastByIDMap.put(1L,User1Pref);
        preferenceArrayFastByIDMap.put(2L,User1Pref);
        DataModel dataModel = new GenericDataModel(preferenceArrayFastByIDMap);
        System.out.println(dataModel);



    }
}
