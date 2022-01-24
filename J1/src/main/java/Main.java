import models.Personne;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import scala.Tuple2;
import tools.SpContext;
import static org.apache.spark.sql.functions.*;
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
        /*JavaPairRDD rdd = sc.textFile("data/customer-orders.csv").mapToPair(e -> {
            String[] data = e.split(",");
            return new Tuple2(new Integer(data[0]), new Double(data[2]));
        }).reduceByKey((a,b) -> (double)Math.round((double)a + (double)b));

        List result = rdd.collect();*/


        //Exemple SPARK SQL
        //Création d'un dataSet à partir de l'entete de la source.
        SparkSession session = SpContext.getSession();
        //Type dataSet    nom du DataSet  session pour acceder à notre instance spark, lecture à partir du header du schema, fichier css, verifier que le schema convient à la classe personne.
        //Dataset<Personne> dataPersonnes = session.read().option("header", true).option("inferSchema", true).csv("data/friends.csv").as(Encoders.bean(Personne.class));

        //On peut créer la structure explicite.
        StructType type = new StructType()
                .add("id", DataTypes.IntegerType,false)
                .add("name", DataTypes.StringType, false)
                .add("age", DataTypes.IntegerType, false)
                .add("numberFriends", DataTypes.IntegerType, true);
        Dataset<Personne> dataPersonnes = session.read().schema(type).csv("data/friends.csv").as(Encoders.bean(Personne.class));
        dataPersonnes.createOrReplaceTempView("personnes");
        //Object l = session.sql("SELECT * from personnes where age > 25 and age < 30").collect();
        //Utilisation de la fonction filter
        Object l = dataPersonnes.filter((FilterFunction<Personne>) e -> e.getAge() > 25 && e.getAge() < 30).collect();

        //Utilisation de la fonction groupby
        dataPersonnes.groupBy("age").count().show();

        //Exercice 1 avg friends avec DataSet
        dataPersonnes.groupBy("age").agg(avg("numberFriends").alias("averge")).sort("age").show();
    }
}
