import models.MovieRate;
import org.apache.spark.sql.Dataset;
import tools.SpContext;
import static org.apache.spark.sql.functions.*;
public class MovieSimilarities {

    public  static void main(String[] args) {
        Dataset<MovieRate> dataset = new MovieRatingDataSet(SpContext.getContext(),SpContext.getSession()).getRatingDataSet();
        Dataset join = dataset.as("ratings1")
                .join(dataset.as("ratings2"), col("ratings1.rate").equalTo(col("ratings2.rate"))).repartition(100);
        join.show();
    }
}
