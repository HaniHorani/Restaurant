package src;

import java.io.*;
import java.nio.file.Paths;
import src.models.User;

public class Helper implements Serializable {

    public static User myUser;
    public static String usersPath = FileUtils.getOrCratePath("users");
    public static String mealsPath = FileUtils.getOrCratePath("meals");
    public static String componentsPath = FileUtils.getOrCratePath("components");
    public static String ordersPath = FileUtils.getOrCratePath("orders");
}

class FileUtils {

    public static String getOrCratePath(String fileName) {
        String path = (Paths.get("").toAbsolutePath().toString()) + "/src/fiels/" + fileName + ".dat"; // ضع مسار الملف هنا
        File file = new File(path);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Directories created: " + parentDir.getAbsolutePath());
            } else {
                System.out.println("Failed to create directories.");
            }

        }

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to create file.");
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return file.getPath();
    }
}
