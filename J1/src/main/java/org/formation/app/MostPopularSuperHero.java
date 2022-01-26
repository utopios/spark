package org.formation.app;


import models.SuperHeroConnection;
import models.SuperHeroName;
import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import tools.ReadFromFile;
import tools.SpContext;

import java.util.Map;

import static org.apache.spark.sql.functions.*;

public class MostPopularSuperHero {
    public static void main(String[] args) {
        StructType superHeroNameSchema = new StructType().add("id", DataTypes.IntegerType,false).add("name", DataTypes.StringType, false);
        Dataset<SuperHeroName> superHeroNameDataset = SpContext.getSession().read().schema(superHeroNameSchema).option("sep", " ").csv("data/Marvel-names.txt").as(Encoders.bean(SuperHeroName.class));
        Dataset<SuperHeroConnection> superHeroConnectionDataset = SpContext.getSession().read().text("data/Marvel-graph.txt").as(Encoders.bean(SuperHeroConnection.class));
        Dataset connections = superHeroConnectionDataset.withColumn("id",split(col("value"), " ").getItem(0))
                .withColumn("connections", size(split(col("value"), " ")))
                .groupBy("id").agg(sum("connections").alias("connections")).sort(col("connections").desc()).cache();
        Row mostPopular = (Row)connections.first();
        Object result = superHeroNameDataset.filter((FilterFunction<SuperHeroName>) e -> e.getId() == new Integer(mostPopular.getString(0))).collect();

        System.out.println(result);
    }

}
