import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        //Création d'un context
        JavaSparkContext sc = new JavaSparkContext("local[*]", "local-1643016514494");

        //Création d'une RDD à partir d'une liste d'entier
        //JavaRDD<Integer> rdd = sc.parallelize(Arrays.asList(2,4,6,8,32));

        //Transformer la rdd => une nouvelle RDD en multipliant chaque element par 5
        //JavaRDD rdd2 = rdd.map( e -> e * 5);

        //Récupération des résultats de la transformation.
        //List result = rdd2.collect();

        //Execution de la classe MovieRating => Récupérer les notes des films
       /* MovieRating movieRating = new MovieRating();

        Map result = movieRating.getMoviesRating();*/

        //Execution de la classe FriendsAvrage => Récupérer la moyenne d'amis par âge
        /*FriendsAvrage friendsAvrage = new FriendsAvrage();
        List result = friendsAvrage.getAvrage();*/

        //Execution de la classe Meteo pour récuperer la temperature min
        //Meteo m = new Meteo();
        //List result = m.getMinTemperature();

        //Execution de la classe wordCounter pour récupérer le nombre de mots de book.txt
        /*WordCounter wordCounter = new WordCounter();
        int result = wordCounter.getWordNumber();*/


        //Correction exercice 1 => Récuperer le total d'achat par client.
        JavaPairRDD rdd = sc.textFile("data/customer-orders.csv").mapToPair(e -> {
            String[] data = e.split(",");
            return new Tuple2(new Integer(data[0]), new Double(data[2]));
        }).reduceByKey((a,b) -> (double)Math.round((double)a + (double)b));

        List result = rdd.collect();
    }
}
