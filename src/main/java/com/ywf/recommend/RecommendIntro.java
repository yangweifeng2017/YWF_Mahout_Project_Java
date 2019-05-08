package com.ywf.recommend;

import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.util.List;

/**
 * ClassName RecommendIntro
 * 功能: TODO
 * 运行方式与参数: TODO
 * Author yangweifeng
 * Date 2019-04-27 14:43
 * Version 1.0
 **/
public class RecommendIntro {
    /*
    DataModel 实现存储并为计算提供所需的所有偏好（Mahout中数据是以偏好的形式表示的 格式为用户ID、物品ID、偏爱程度的数值）、用户和物品数据
    UserSimilarity 实现给出两个用户的相似度，可以从多种可能度量或计算中选用一种作为依据
    UserNeighborhood 实现明确了与给定用户最相似的一组用户
    Recommender实现合并所有这些组件为用户推荐商品
    intro.csv中的数据含义是 前面是用户ID 中间是书籍ID 最后是偏好程度
    程序最后的数据结果是 RecommendedItem[item:104, value:4.257081] 含义是将104号书籍推荐给用户1
     */
    public RecommendIntro() {
    }
    public static void main(String[] args) {
        try {
            // FileDataModel支持:包含四列数据(逗号或者制表符分割) ：用户id,物品id,评分，时间戳(可选)
            DataModel dataModel = new FileDataModel(new File("F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\软件包与数据\\数据包\\MovieLens\\ua.base"));
            System.out.println(dataModel.getNumUsers());
            UserSimilarity userSimilarity = new PearsonCorrelationSimilarity(dataModel);
            // KNN最近邻算法
            UserNeighborhood userNeighborhood = new NearestNUserNeighborhood(3,userSimilarity,dataModel);
            Recommender recommender = new GenericUserBasedRecommender(dataModel,userNeighborhood,userSimilarity);
            /*
            // 给第1个用户 推荐2个物品
            List<RecommendedItem> recommend1 = recommender.recommend(1, 2); // 参数:用户id，推荐物品数量
            // 给第2个用户 推荐2个物品
            List<RecommendedItem> recommend2 = recommender.recommend(2, 2);
            // 给第2个用户 推荐1个物品
            List<RecommendedItem> recommend3 = recommender.recommend(2, 1);
            System.out.println("-----------------");
            for (RecommendedItem recommendedItem : recommend1){
               System.out.println(recommendedItem);
            }
            */
            System.out.println("----循环所有用户进行推荐-------------");
            LongPrimitiveIterator userIDs = recommender.getDataModel().getUserIDs();
            while (userIDs.hasNext()){
                Long userId = userIDs.next();
                System.out.println("------" + userId);
                List<RecommendedItem> recommend = recommender.recommend(userId,20);
                for (RecommendedItem recommendedItem : recommend){
                    System.out.println(recommendedItem);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
