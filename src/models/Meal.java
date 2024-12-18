package src.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import src.Helper;

public class Meal implements Serializable {

    private static String filePath = Helper.mealsPath;
    public long id;
    public String name;
    public long quantity;
    public List<Component> components;

    public Meal() {
    }

    public Meal(String name, long quantity, List<Component> components) {
        this.name = name;
        this.quantity = quantity;
        this.components = components;
    }

    public static Boolean check(Meal newMeal) throws ClassNotFoundException, IOException {
        List<Meal> meals = loadFromFile();
        for (Meal meal : meals) {
            if (newMeal.name.equals(meal.name)) {
                return false;
            }
        }
        return true;
    }

    public static void saveToFile(List<Meal> meals) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(meals);
        }
    }

    public static List<Meal> loadFromFile() throws IOException, ClassNotFoundException {
        if (new File(filePath).length() == 0) {
            System.out.println("File is empty. Returning an empty list.");
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Meal>) ois.readObject();
        }
    }
}