package com.ywf.recommend;

import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;

/**
 * ClassName CreatePreferenceArray
 * 功能: CreatePreferenceArray
 * 运行方式与参数: CreatePreferenceArray
 * Author yangweifeng
 * Date 2019-04-26 14:23
 * Version 1.0
 **/
public class CreatePreferenceArray {
    public CreatePreferenceArray() {
    }
    public static void main(String[] args) {
        getSingleUser();
    }

    public static PreferenceArray getSingleUser() {
        PreferenceArray User1Pref = new GenericUserPreferenceArray(2);
        User1Pref.setUserID(0,1L);  //用户唯一标识

        User1Pref.setItemID(0,101L);//物品唯一标识
        User1Pref.setValue(0,3.0f); //物品值
        User1Pref.setItemID(1,102L);//物品唯一标识
        User1Pref.setValue(1,4.0f); //物品值
        Preference pref = User1Pref.get(1);
        System.out.println(pref.getValue());
        System.out.println(User1Pref);
        return  User1Pref;
    }
}
