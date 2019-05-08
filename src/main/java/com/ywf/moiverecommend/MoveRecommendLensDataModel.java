package com.ywf.moiverecommend;

import org.apache.commons.io.Charsets;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.common.iterator.FileLineIterable;

import java.io.*;
import java.util.regex.Pattern;

/**
 * ClassName MoveRecommendLensDataModel
 * 功能: TODO
 * 运行方式与参数: TODO
 * Author yangweifeng
 * Date 2019-04-27 21:57
 * Version 1.0
 **/
public class MoveRecommendLensDataModel extends FileDataModel {
    private static final String  COLON_DELIMITER = "::";
    private static final Pattern COLON_DELIMITER_PATTERN = Pattern.compile(COLON_DELIMITER);
    public MoveRecommendLensDataModel(File dataFile) throws IOException {
        super(ConvertFile(dataFile));
    }
    private static File ConvertFile(File dataFile) {
        /*
         读取原始文件，按指定格式输出为目标格式文件
         */
        File resultFile = new File("F:\\学习视频教程\\基于大数据技术推荐系统算法案例实战教程\\课件文档代码\\temp\\" + dataFile.getName());
        if (resultFile.exists()){
            resultFile.delete();
        }
        try(Writer writer = new OutputStreamWriter(new FileOutputStream(resultFile), Charsets.UTF_8)) {
            for (String line: new FileLineIterable(dataFile, false)){
                String convertedSubLine = COLON_DELIMITER_PATTERN.matcher(line).replaceAll(",");
                writer.write(convertedSubLine);
                writer.write('\n');
            }
        } catch (IOException ioe){
            resultFile.delete();
            ioe.printStackTrace();
        }
        return resultFile;
    }
}
