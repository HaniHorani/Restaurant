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
//    public long quantity;
    public long Price;
    public List<Component> components;

    public Meal() {
    }
    public Meal(String name,long Price, List<Component> components) {
        this.name = name;
//        this.quantity = quantity;
        this.Price=Price;
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
            List<Meal>meals = new ArrayList<>();
            List<Component> components = Component.loadFromFile();
            List<Component> GrilledChickencomponents =new ArrayList<>();
            GrilledChickencomponents.add(components.get(19));
            GrilledChickencomponents.add(components.get(18));
            GrilledChickencomponents.add(components.get(20));
            GrilledChickencomponents.add(components.get(32));
            GrilledChickencomponents.add(components.get(41));
            GrilledChickencomponents.add(components.get(42));
            GrilledChickencomponents.add(components.get(43));
            List<Component> Burgercomponents = new ArrayList<>();
            Burgercomponents.add(components.get(13));
            Burgercomponents.add(components.get(16));
            Burgercomponents.add(components.get(17));
            Burgercomponents.add(components.get(18));
            Burgercomponents.add(components.get(19));
            Burgercomponents.add(components.get(20));
            Burgercomponents.add(components.get(27));
            Burgercomponents.add(components.get(30));
            Burgercomponents.add(components.get(34));
            Burgercomponents.add(components.get(41));
            Burgercomponents.add(components.get(42));
            List<Component> Pizzacomponents = new ArrayList<>();
            Pizzacomponents.add(components.get(13));
            Pizzacomponents.add(components.get(15));
            Pizzacomponents.add(components.get(16));
            Pizzacomponents.add(components.get(18));
            Pizzacomponents.add(components.get(20));
            Pizzacomponents.add(components.get(30));
            Pizzacomponents.add(components.get(39));
            Pizzacomponents.add(components.get(41));
            Pizzacomponents.add(components.get(42));
            Pizzacomponents.add(components.get(43));
            Pizzacomponents.add(components.get(60));
            List<Component> OnionRingscomponents = new ArrayList<>();
            OnionRingscomponents.add(components.get(16));
            OnionRingscomponents.add(components.get(17));
            OnionRingscomponents.add(components.get(39));
            OnionRingscomponents.add(components.get(41));
            OnionRingscomponents.add(components.get(42));
            OnionRingscomponents.add(components.get(43));
            List<Component> soupcomponents = new ArrayList<>();
            soupcomponents.add(components.get(10));
            soupcomponents.add(components.get(11));
            soupcomponents.add(components.get(15));
            soupcomponents.add(components.get(16));
            soupcomponents.add(components.get(17));
            soupcomponents.add(components.get(18));
            soupcomponents.add(components.get(19));
            soupcomponents.add(components.get(41));
            soupcomponents.add(components.get(42));
            soupcomponents.add(components.get(43));
            meals.add(new Meal("Grilled Chicken",10,GrilledChickencomponents));
            meals.add(new Meal("Burger",10,Burgercomponents));
            meals.add(new Meal("Pizza",10,Pizzacomponents));
            meals.add(new Meal("Onion rings",20,OnionRingscomponents));
            meals.add(new Meal("Soup",15,soupcomponents));
            return meals;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Meal>) ois.readObject();
        }
    }
}