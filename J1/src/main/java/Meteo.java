import models.InfoMeteo;
import org.apache.spark.SparkExecutorInfo;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.List;

public class Meteo {
    public List getMinTemperature() {
        JavaSparkContext sc = new JavaSparkContext("local[*]", "meteo");
        JavaRDD<InfoMeteo> rdd = sc.textFile("data/meteo.csv").map((row) -> {
            String[] data = row.split(",");
            return new InfoMeteo(new Float(data[3]), data[0], data[2]);
        }).filter(e -> e.getTypeInfo().equals("TMIN"));

        // => (identifiant station, val temperature)
        JavaPairRDD pairRdd = rdd.mapToPair(e -> new Tuple2(e.getStationId(), e.getTemperature())).reduceByKey((a,b) -> Math.min(new Float(a.toString()), new Float(b.toString())));
        return pairRdd.collect();
    }
}
