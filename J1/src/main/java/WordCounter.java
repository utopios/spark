import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.Arrays;

public class WordCounter {

    public int getWordNumber() {
        JavaSparkContext sc = new JavaSparkContext("local[*]", "wordCounter");
        //FlatMap peremt de découper chaque row de notre source en row du rdd.
        JavaRDD rdd = sc.textFile("data/book.txt").flatMap(e -> Arrays.stream(e.split(" ")).iterator());

        return rdd.collect().size();
    }
}
