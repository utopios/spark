import models.MovieRate;
import org.apache.spark.ml.Model;
import org.apache.spark.ml.recommendation.ALS;
import org.apache.spark.ml.recommendation.ALSModel;
import org.apache.spark.sql.Dataset;
import tools.SpContext;

import java.util.Arrays;

public class DemoALS {

    public static void main(String[] args) {

        //DataSet Movie rate by user
        Dataset<MovieRate> movieRateDataset = new MovieRatingDataSet(SpContext.getContext(), SpContext.getSession()).getRatingDataSet();

        ALS als = new ALS().setMaxIter(10).setRegParam(0.1).setUserCol("userId").setItemCol("id").setRatingCol("rate");
        ALSModel model = als.fit(movieRateDataset);
//        Dataset users = SpContext.getSession().sqlContext().createDataset(SpContext.getContext().parallelize(Arrays.asList(200)), )
        Dataset recommendations = model.recommendForAllUsers(10);
        recommendations.show();

    }
}
