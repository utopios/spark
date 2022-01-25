import models.MovieRate;
import org.apache.arrow.flatbuf.Int;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import tools.SpContext;

public class MovieRatingDataSet {

    //Fonction pour récupérer un dataSet qui contient movieRate (movieId, rate) => film.data
    public  Dataset<MovieRate> getRatingDataSet() {
        JavaSparkContext context = SpContext.getContext();
        SparkSession session = SpContext.getSession();
        JavaRDD<MovieRate> rddMovieRate = SpContext.getContext().textFile("data/film.data").map(r -> {
            String[] data = r.split("\t");
            return  new MovieRate(new Integer(data[1]), new Integer(data[2]));
        });
        Dataset<MovieRate> movieRateDataset = session.sqlContext().createDataset(rddMovieRate.rdd(), Encoders.bean(MovieRate.class));

        return movieRateDataset;
    }

    //Fonction pour récupérer un dataSet qui contient movieName (movieId, name) => names.item
}
