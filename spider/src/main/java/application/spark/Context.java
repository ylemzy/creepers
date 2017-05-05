package application.spark;/**
 * Created by huangzebin on 2017/4/11.
 */

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;

import java.util.Arrays;
import java.util.List;

public class Context {
    private static final Logger logger = LogManager.getLogger();

    private JavaSparkContext sc;

    public Context conf(){
        SparkConf conf = new SparkConf().setAppName("spider").setMaster("local[2]");
        sc = new JavaSparkContext(conf);
        return this;
    }

    public Integer parallelizedCollection(){
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);

        return distData.reduce((integer, integer2) -> integer + integer2);
    }

    public Integer externalDatasets(){
        //either a local path on the machine, or a hdfs://, s3n://, etc UR
        JavaRDD<String> distFile = sc.textFile("D:/data/pxy.m.acc.log-2");
        //add up the sizes of all the lines using the map and reduce
        return distFile.map(s -> s.length()).reduce((a, b) -> a + b);
    }
}
