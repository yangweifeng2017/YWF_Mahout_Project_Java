package com.ywf.recommend;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.RMSRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;

import java.io.File;
import java.io.IOException;

/**
 * ClassName Evaluator
 * 功能: 算法评估器 对推荐程序做差值评价
 * Author yangweifeng
 * Date 2019-04-27 18:22
 * Version 1.0
 **/
public class Evaluator {
    /*
    整个评估的原理就是将数据集中的一部分数据作为测试数据。也就是这一部分数据程序不可见，然后推荐引擎通过剩余的训练数据推测测试数据的值，然后将推测值与真实的测试数据进行比较。
    比较的方法有平均差值，就是每一项与真实数据做差然后求平均值，另一种方式就是均方根。就是差值求平方和，然后求平方和的平均值，然后取平方根。这种方式会放大差值的影响，比如推荐的结果中差一个星值，产生的影响远大于1倍的影响就可以使用这种评估算法。
    评估推荐引擎，此处使用的AverageAbsoluteDifferenceRecommenderEvaluator 采用的均方差进行评估。
    这只是为了方便，通常会采用均方根的方式进行评估，也就是使用RMSRecommenderEvaluator 均方根误差 替代前面的评估器
     */
    public Evaluator() {
    }
    public static void main(String[] args) {
        RandomUtils.useTestSeed(); //仅限测试使用
        try {
             /*
             此处采用的是从网上下载的一个大数据集，最后的评价结果是0.8761682242990649，表示的是平均与真实结果的差值是0.9.
             */
            DataModel dataModel = new FileDataModel(new File("F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\软件包与数据\\数据包\\MovieLens\\ua.base"));
            // 评估算法
            /*
            //我们创建了一个推荐器生成器，因为评估的时候我们需要将源数据中的一部分作为测试数据，其他作为算法的训练数据，需要通过新训练的DataModel构建推荐器，所以采用生成器的方式生成推荐器
             */
            RecommenderEvaluator recommenderEvaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
            RecommenderEvaluator recommenderEvaluator1 = new RMSRecommenderEvaluator();
            // 推荐构建器
            RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
                @Override
                public Recommender buildRecommender(DataModel dataModel) throws TasteException {
                    //UserSimilarity similarity=new PearsonCorrelationSimilarity(dataModel);//Pearson系数
                   // UserSimilarity similarity=new EuclideanDistanceSimilarity(dataModel); //欧机里得距离
                    UserSimilarity similarity=new UncenteredCosineSimilarity(dataModel); //cos相似度等计算相似度算法
                    UserNeighborhood neighborhood=new NearestNUserNeighborhood(3, similarity, dataModel);
                    return new GenericUserBasedRecommender(dataModel, neighborhood, similarity);
                }
            };
             //参数中0.7代表训练数据为70%，测试数据是30%。(每一个用户的数据的70% 而非整个数据集的)
            // 最后的1.0代表的是选取数据集的多少数据做整个评估。（选取百分之多少的用户作为评估）
            // 如果你想更快的做出评估，可以选取0.1.表示的是10%的数据做整个评估测试，在这10%的数据集中70%作为训练，30%作为测试。
            // score代表的是与实际值的误差（0.85），实际值可能是5，预测值为4.15，值越小越好
            double score = recommenderEvaluator.evaluate(recommenderBuilder, null, dataModel, 0.7, 1.0);
            double score1 = recommenderEvaluator1.evaluate(recommenderBuilder, null, dataModel, 0.7, 1.0);
            //此处第二个null处，使用null就可以满足基本需求，
            //但是如果我们有特殊需求，比如使用特殊的DataModel，在这里可以使用DataModelBuilder的一个实例。
            System.out.println(score);
            System.out.println(score1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
