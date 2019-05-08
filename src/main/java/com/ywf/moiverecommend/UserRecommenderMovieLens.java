package com.ywf.moiverecommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class UserRecommenderMovieLens {
    private UserRecommenderMovieLens(){
    }

    public static void main(String[] args) throws Exception {
        File resultFile = new File("userRcomed.csv");
        DataModel dataModel = new MoveRecommendLensDataModel(new File("F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\软件包与数据\\数据包\\MovieLens\\ml-1m\\ratings.dat"));
        UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
        Recommender recommender = new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
        // 增加缓存
        Recommender cachingRecommender = new CachingRecommender(recommender);
        //评估器 Evaluate ------------------------------------------------------------------------------------------------------------------------
        RMSRecommenderEvaluator evaluator = new RMSRecommenderEvaluator();
        RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
            @Override
            public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                UserSimilarity similarity = new PearsonCorrelationSimilarity(dataModel);
                UserNeighborhood neighborhood = new NearestNUserNeighborhood(100, similarity, dataModel);
                return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
            }
        };
        double score = evaluator.evaluate(recommenderBuilder, null, dataModel, 0.9, 0.5);
        System.out.println("RMSE score is "+score);
        // ----------------------------------------------------------------------------------------------------------------------------------------
        try(PrintWriter writer = new PrintWriter(resultFile)){
            for (int userID = 1; userID <= dataModel.getNumUsers(); userID++){
                List<RecommendedItem> recommendedItems = cachingRecommender.recommend(userID, 2); // 推荐2个用户
                String line = userID + " | ";
                for (RecommendedItem recommendedItem: recommendedItems){
                    line += recommendedItem.getItemID() + ":" + recommendedItem.getValue() + ",";
                }
                if (line.endsWith(",")){
                    line = line.substring(0, line.length()-1);
                }
                writer.write(line);
                writer.write('\n');
            }
        } catch (IOException ioe){
            resultFile.delete();
            throw ioe;
        }
        System.out.println("Recommended for "+dataModel.getNumUsers()+" users and saved them to "+resultFile.getAbsolutePath());
    }
}
