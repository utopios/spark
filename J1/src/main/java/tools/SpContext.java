package tools;

import org.apache.spark.sql.SparkSession;

public class SpContext {

    private static final SparkSession session = makeSession();

    public  static SparkSession getSession() {
        return session;
    }

    private static SparkSession makeSession() {
        return SparkSession.builder().appName("ourSparkApp").master("local[*]").getOrCreate();
    }

    public static void closeSession() {
        session.close();
    }
}
