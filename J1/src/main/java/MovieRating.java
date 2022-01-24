import org.apache.commons.logging.Log;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;
import java.util.Map;

public class MovieRating {

    public Map<Integer, Long> getMoviesRating() {
        //Création d'un context
        JavaSparkContext sc = new JavaSparkContext("local[*]", "local-1643016514494");


        //Création RDD simple à partir d'un fichier data
        JavaRDD rdd = sc.textFile("data/film.data").map(l -> l.split("\t")[2]);

        //Action qui permet de renvoyer le nombre de chaque valeur.
        return rdd.countByValue();
    }
}
