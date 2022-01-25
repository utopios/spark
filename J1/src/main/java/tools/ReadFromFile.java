package tools;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ReadFromFile {
    public static Map<Integer, String> getMapFilmsName() {
        Map<Integer, String> result = new HashMap<>();
        try {

            File file = new File("data/names.item");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(",");
                result.put(new Integer(data[0]), data[1]);
            }
        }catch (Exception ex) {

        }
        return result;
    }
    public static Map<Integer, String> getSuperHeroNames() {
        Map<Integer, String> result = new HashMap<>();
        try {

            File file = new File("data/Marvel-names.item");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(" \"");
                result.put(new Integer(data[0]), data[1].replace("\"", ""));
            }
        }catch (Exception ex) {

        }
        return result;
    }
}
