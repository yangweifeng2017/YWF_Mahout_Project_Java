package com.ywf.bookcrossing;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.Recommender;

public class BXRecommenderBuilder implements RecommenderBuilder {
    @Override
    public Recommender buildRecommender(DataModel dataModel) throws TasteException {
        // 将相似度等代码全部定义在推荐类里面，为后续使用方便
        return new BXRecommender(dataModel);
    }
}
