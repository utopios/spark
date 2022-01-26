import models.MovieRate;
import org.apache.spark.sql.Dataset;
import tools.SpContext;
import static org.apache.spark.sql.functions.*;
public class MovieSimilarities {

    public  static void main(String[] args) {
        Dataset<MovieRate> dataset = new MovieRatingDataSet(SpContext.getContext(),SpContext.getSession()).getRatingDataSet().cache();
        /*dataset.map(r -> {
            //Logique
        });
        dataset.mapPartitions((i) -> {
            //Logique
            Object o = i.map((r, b) -> {

            });
            return o;
        });*/
        Dataset join = dataset.groupBy("id").count();
        join.explain("formatted");
        join.show();
    }
}
