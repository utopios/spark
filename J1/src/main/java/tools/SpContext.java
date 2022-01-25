package tools;

import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

public class SpContext {

    private static final SparkSession session = makeSession();
    private static final JavaSparkContext context = makeContext();

    public  static SparkSession getSession() {
        return session;
    }

    public static JavaSparkContext getContext() {return  context;}

    private static SparkSession makeSession() {
        return SparkSession.builder().appName("ourSparkApp").master("local[*]").getOrCreate();
    }

    private static JavaSparkContext makeContext() {
       return new JavaSparkContext(session.sparkContext());
    }
    public static void closeSession() {
        session.close();
    }
}
