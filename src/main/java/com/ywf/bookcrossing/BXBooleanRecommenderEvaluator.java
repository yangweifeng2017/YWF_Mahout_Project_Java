package com.ywf.bookcrossing;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.model.DataModel;

import java.io.File;
import java.io.IOException;

public class BXBooleanRecommenderEvaluator {
    private BXBooleanRecommenderEvaluator(){
    }

    public static void main(String[] args) throws IOException, TasteException {
        String fileString = "F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\软件包与数据\\数据包\\Book-Crossing\\BX-Book-Ratings.csv";
        /*
        DataModel dataModel = new BXDataModel(new File(fileString), true);
        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
        IRStatistics stats = evaluator.evaluate(new BXBooleanRecommenderBuilder(), new BXDataModelBuilder(), dataModel, null, 3, Double.NEGATIVE_INFINITY, 1.0);
        System.out.println("Precision is "+stats.getPrecision()+"; Recall is "+stats.getRecall()+"; F1 is"+stats.getF1Measure());
        */
        DataModel dataModel = new BXDataModel(new File(fileString), true);
        RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
        double score = evaluator.evaluate(new BXBooleanRecommenderBuilder(), null, dataModel, 0.9, 0.3);

        System.out.println("MAE score is "+score);
    }
}
