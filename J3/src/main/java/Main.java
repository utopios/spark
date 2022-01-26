import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.Customer;
import org.apache.spark.api.java.function.VoidFunction2;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import scala.Function1;
import scala.collection.Iterator;
import tools.ApiRestClient;
import tools.SpContext;
import static org.apache.spark.sql.functions.*;
public class Main {

    public static  void main(String[] args) throws Exception {
        //Api Streaming de Spark, pour un récupérer un dataset avec le format kafka
        Dataset initialDataset = SpContext.getSession().readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:9092")
                .option("subscribe", "interact").load();
        //Dataset récupéré par stream, la structuration doit être (customerId, firstName, LastName)
        /*Dataset transformDataSet = initialDataset.mapPartitions((Function1<Iterator, Iterator>) (i) -> {
            ApiRestClient clientHttp = new ApiRestClient();
            i.map(r -> {
                //Extraire les données à partir du message spark pour le structurer
                //Si le customer Id est null => on envoie une requête vers notre API REST.
                Customer  customer = null;
                try {
                    customer = new ObjectMapper().readValue(r.toString(), Customer.class);
                    if(customer.getCusotmerId() == null) {
                        String response = clientHttp.postJSON("/getcustomer",customer);
                        customer = new ObjectMapper().readValue(response, Customer.class);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return customer;
            });
            return i;
        }, Encoders.bean(Customer.class));*/

        //Utiliser une fonction udf pour compléter l'id du client.
        SpContext.getSession().sqlContext().udf().register("GetId", (UDF1<String, Integer>)(v) -> {
            //Logique metier pour récupérer l'id du client à partir de notre api REST
            //Si le customer Id est null => on envoie une requête vers notre API REST.
            ApiRestClient clientHttp = new ApiRestClient();
            Customer customer = null;
            try {
                customer = new ObjectMapper().readValue(v, Customer.class);
                if(customer.getCusotmerId() == null) {
                    String response = clientHttp.postJSON("/getcustomer",customer);
                    customer = new ObjectMapper().readValue(response, Customer.class);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return customer.getCusotmerId();
        }, DataTypes.IntegerType);

        Dataset transformDataSet = initialDataset.withColumn("id", regexp_extract(col("value"), "", 1).alias("id"))
                .withColumn("customerId", callUDF("GetId", col("value")));

        Dataset finalDataSet = transformDataSet.select("customerId").groupBy("customerId").count();
        //On utilise le writeStream pour écrire en sortie avec le mode update, et avec le format foreachBatch
        //Envoyer le resultat dans un topic de kafka
        /*finalDataSet.writeStream().outputMode("update").format("kafka").option("kafka.bootstrap.servers", "localhost:9092")
                .option("topic", "write-interaction")
                .option("checkpointLocation", "folder_checkpoint/kafka_checkpoint")
                .start()
                .awaitTermination();*/

        // => une requête vers notre API rest.
        finalDataSet.writeStream().outputMode("update").foreachBatch((VoidFunction2<Dataset, Long>) (dataSet, batchId) -> {
           //A ne pas faire
            /*for(Object r: dataSet.collect()) {

            }*/
           dataSet.foreach(r -> {
               //Appel de l'api en ecriture.
           });
        });
        initialDataset.writeStream().outputMode("update").format("console").start().awaitTermination();
    }
}
