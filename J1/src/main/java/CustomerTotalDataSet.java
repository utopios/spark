import models.Customer;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructType;
import tools.SpContext;
import static org.apache.spark.sql.functions.*;

public class CustomerTotalDataSet {
    public Object getTotalByCustomer() {
        SparkSession session = SpContext.getSession();
        StructType type = new StructType()
                .add("id", DataTypes.IntegerType, false)
                .add("productId", DataTypes.IntegerType, false)
                .add("amount", DataTypes.DoubleType, false);
        Dataset<Customer> customers = session.read().schema(type).csv("data/customer-orders.csv").as(Encoders.bean(Customer.class));
        return customers.groupBy("id").agg(sum("amount").alias("total")).collect();
    }
}
