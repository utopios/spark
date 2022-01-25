import models.MovieName;
import models.MovieRate;
import org.apache.arrow.flatbuf.Int;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import tools.SpContext;

public class MovieRatingDataSet {

    private JavaSparkContext context;
    private SparkSession session;

    public MovieRatingDataSet(JavaSparkContext context, SparkSession session) {
        this.context = context;
        this.session = session;
    }

    //Fonction pour récupérer un dataSet qui contient movieRate (movieId, rate) => film.data
    public  Dataset<MovieRate> getRatingDataSet() {
        /*JavaSparkContext context = SpContext.getContext();
        SparkSession session = SpContext.getSession();*/
        JavaRDD<MovieRate> rddMovieRate = context.textFile("data/film.data").map(r -> {
            String[] data = r.split("\t");
            return  new MovieRate(new Integer(data[1]), new Integer(data[2]));
        });
        Dataset<MovieRate> movieRateDataset = session.sqlContext().createDataset(rddMovieRate.rdd(), Encoders.bean(MovieRate.class));

        return movieRateDataset;
    }

    //Fonction pour récupérer un dataSet qui contient movieName (movieId, name) => names.item
    public Dataset<MovieName> getMovieNameDataSet() {
        JavaRDD<MovieName> rddMovieName = context.textFile("data/names.item").map( r-> {
            String[] data = r.split(",");
            return new MovieName(new Integer(data[0]), data[1]);
        });
        Dataset<MovieName> movieNameDataset = session.sqlContext().createDataset(rddMovieName.rdd(), Encoders.bean(MovieName.class));
        return movieNameDataset;
    }
}
