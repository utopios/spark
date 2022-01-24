import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.List;

public class FriendsAvrage {

    public List getAvrage() {
        //Création d'un context
        JavaSparkContext sc = new JavaSparkContext("local[*]", "local-1643016514494");


        //Création d'une RDD cle => valeur
        JavaPairRDD rdd = sc.textFile("data/friends.csv")
                //Transformation => (age, ami)
                .mapToPair(l -> {
           String[] dataArrau = l.split(",");
           // => (age, ami)
           return new Tuple2(new Integer(dataArrau[2].toString()), new Integer(dataArrau[3].toString()));
        })
                //Transformation => (age, (totalAmi, totalInstances))
                .mapValues( x -> new Tuple2(x, 1)).reduceByKey((x,y) -> {
                    Tuple2 t1 = (Tuple2)x;
                    Tuple2 t2 = (Tuple2)y;
                    return  new Tuple2(new Integer(t1._1().toString()) + new Integer(t2._1().toString()), new Integer(t1._2().toString()) + new Integer(t2._2().toString()));
                })
                //Transformation => (age, moyenne)
                .mapValues(x -> (int)((Tuple2)x)._1() / (int)((Tuple2)x)._2())
                ;

        return rdd.collect();
    }
}
