package com.ywf.bookcrossing;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;

public class BXRecommenderEvaluator {
    private BXRecommenderEvaluator(){
    }

    public static void main(String[] args) throws IOException, TasteException {
        String fileString = "F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\软件包与数据\\数据包\\Book-Crossing\\BX-Book-Ratings.csv";
        DataModel dataModel = new BXDataModel(new File(fileString), false);
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator(); //均方差
        // 上下 1.3的误差
        double score = evaluator.evaluate(new BXRecommenderBuilder(), null, dataModel, 0.9, 0.3);
        System.out.println("MAE score is "+score);
    }
}
