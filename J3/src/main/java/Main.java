public class Main {

    public static  void main(String[] args) {
        //Api Streaming de Spark, pour un récupérer un dataset avec le format kafka

        //Dataset récupéré par stream, la structuration doit être (customerId, firstName, LastName)

        //Si le customer Id est null => on envoie une requête vers notre API REST.

        //On utilise le writeStream pour écrire en sortie avec le mode update, et avec le format foreachBatch
        // => une requête vers notre API rest.

    }
}
