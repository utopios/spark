import models.Customer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import scala.Function1;
import scala.collection.Iterator;
import tools.ApiRestClient;
import tools.SpContext;

public class Main {

    public static  void main(String[] args) throws Exception {
        //Api Streaming de Spark, pour un récupérer un dataset avec le format kafka
        Dataset initialDataset = SpContext.getSession().readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "interact").load();
        //Dataset récupéré par stream, la structuration doit être (customerId, firstName, LastName)
        Dataset transformDataSet = initialDataset.mapPartitions((Function1<Iterator, Iterator>) (i) -> {
            ApiRestClient clientHttp = new ApiRestClient();
            i.map(r -> {
                //Extraire les données à partir du message spark pour le structurer.
               return new Customer();
            });
            return i;
        }, Encoders.bean(Customer.class));
        //Si le customer Id est null => on envoie une requête vers notre API REST.

        //On utilise le writeStream pour écrire en sortie avec le mode update, et avec le format foreachBatch
        // => une requête vers notre API rest.
        initialDataset.writeStream().outputMode("update").format("console").start().awaitTermination();
    }
}
