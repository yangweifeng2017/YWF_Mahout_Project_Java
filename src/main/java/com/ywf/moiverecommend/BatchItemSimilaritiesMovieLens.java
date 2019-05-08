package com.ywf.moiverecommend;

import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.precompute.FileSimilarItemsWriter;
import org.apache.mahout.cf.taste.impl.similarity.precompute.MultithreadedBatchItemSimilarities;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.ItemBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.precompute.BatchItemSimilarities;
import org.apache.mahout.cf.taste.similarity.precompute.SimilarItemsWriter;

import java.io.File;

/**
 * ClassName BatchItemSimilaritiesMovieLens
 * 基于物品的推荐
 * Author yangweifeng
 * Date 2019-04-28 17:41
 * Version 1.0
 **/
public class BatchItemSimilaritiesMovieLens {
    private BatchItemSimilaritiesMovieLens(){
    }
    public static void main(String[] args) throws Exception{
        File resultFile = new File("F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\软件包与数据\\结果\\推荐");
        DataModel dataModel = new MoveRecommendLensDataModel(new File("F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\软件包与数据\\数据包\\MovieLens\\ml-1m\\ratings.dat"));
        // 最大自然相似度
        ItemSimilarity similarity = new LogLikelihoodSimilarity(dataModel);
        ItemBasedRecommender recommender = new GenericItemBasedRecommender(dataModel, similarity);
        // 推荐物品数量5
        BatchItemSimilarities batchItemSimilarities = new MultithreadedBatchItemSimilarities(recommender, 5);
        SimilarItemsWriter writer = new FileSimilarItemsWriter(resultFile);
        // 最大持续运行之间 1小时
        int numSimilarites = batchItemSimilarities.computeItemSimilarities(Runtime.getRuntime().availableProcessors(), 1, writer);
        System.out.println("Computed "+ numSimilarites+ " for "+ dataModel.getNumItems()+" items and saved them to "+resultFile.getAbsolutePath());
    }
}
