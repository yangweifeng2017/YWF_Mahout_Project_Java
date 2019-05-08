package com.ywf.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.IRStatistics;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.eval.GenericRecommenderIRStatsEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;

/**
 * ClassName EvaluatorIR
 * 功能: 基于IR(信息检索)指标来评估推荐的优劣
 * 运行方式与参数: 进行查全率和查准率的评价
 * Author yangweifeng
 * Date 2019-04-27 19:46
 * Version 1.0
 **/
public class EvaluatorIR {
    public EvaluatorIR() {
    }
    public static void main(String[] args) throws TasteException, IOException {
        RandomUtils.useTestSeed();
        DataModel model=new FileDataModel(new File("F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\软件包与数据\\数据包\\MovieLens\\ua.base"));
        RecommenderIRStatsEvaluator evaluator = new GenericRecommenderIRStatsEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel model) throws TasteException {
                UserSimilarity similarity=new PearsonCorrelationSimilarity(model);
                UserNeighborhood neighborhood=new NearestNUserNeighborhood(100, similarity, model);
                return new GenericUserBasedRecommender(model, neighborhood, similarity);
            }
        };
        // 参数5 代表推荐5个
        IRStatistics stats = evaluator.evaluate(recommenderBuilder, null, model, null, 5, GenericRecommenderIRStatsEvaluator.CHOOSE_THRESHOLD, 1.0);
        /*
         查准率为0.75 上面设置的参数为2，表示 Precision at 2（推荐两个结果时的查准率），平均有3/4的推荐结果是好的
         */
        System.out.println(stats.getPrecision()); // 查准率
        /*
         Recall at 2 推荐两个结果的查全率是1.0 表示所有的好的推荐都包含在这些推荐结果中
         */
        System.out.println(stats.getRecall()); //查全率
        System.out.println(stats.getF1Measure());
    }
}
