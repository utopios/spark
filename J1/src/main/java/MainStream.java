import models.Personne;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import tools.SpContext;
import static org.apache.spark.sql.functions.*;
public class MainStream {
    public static void main(String[] args) throws Exception {
        Logger.getLogger("org").setLevel(Level.ERROR);

        //Input rate => uniquement pour les tests.
        /*Dataset dataset = SpContext.getSession().readStream().format("rate").load().withColumn("result", col("value"));
        dataset.writeStream().format("console").outputMode("complete").start().awaitTermination();*/

        StructType type = new StructType()
                .add("id", DataTypes.IntegerType,false)
                .add("name", DataTypes.StringType, false)
                .add("age", DataTypes.IntegerType, false)
                .add("numberFriends", DataTypes.IntegerType, true);

        //Input file
        Dataset<Personne> personneDataset = SpContext.getSession().readStream().format("csv").option("header", true).option("path", "data/customers").schema(type).load().as(Encoders.bean(Personne.class));
        //Socket
        //Dataset datasetSocket = SpContext.getSession().readStream().format("socket").option("host", "localhost").option("port", 7777).load();
        //Kafka
        Dataset datasetKafka = SpContext.getSession().readStream().format("kafka").option("kafka.bootstrap.servers", "localhost:9092").option("subscribe", "test").load();
        personneDataset.groupBy("numberFriends").count().writeStream().format("console").outputMode("complete").start().awaitTermination();


    }
}
