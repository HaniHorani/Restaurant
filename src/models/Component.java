package src.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import src.Helper;

public class Component implements Serializable {

    private final static String filePath = Helper.componentsPath;
    public String name;
    // public List<Meal> meals;

    public Component() {
    }

    public Component(String name) {
        this.name = name;
    }

    public static Boolean check(Component newComponent, List<Component> components) throws ClassNotFoundException, IOException {
        for (Component component : components) {
            if (newComponent.name.equals(component.name)) {
                return false;
            }
        }
        return true;
    }

    public static void saveToFile(List<Component> components) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(components);
        }
    }

    public static List<Component> loadFromFile() throws IOException, ClassNotFoundException {
        if (new File(filePath).length() == 0) {
            System.out.println("File is empty. Returning an empty list.");
//                                                                     0           1          2          3            4              5          6              7         8          9           10        11         12           13          14             15           16         17       18            19        20         21      22      23      24      25        26      27        28      29       30       31        32         33      34      35        36       37      38        39        40      41      42          43          44             45      46           47          48          49          50            51               52               53            54            55       56          57      58       59          60
            List<String> ingredints = new ArrayList<>(Arrays.asList("Apples", "Bananas", "Oranges", "Grapes", "Strawberries", "Pineapples", "Watermelon", "Mangoes", "Peaches", "Cherries", "Carrots", "Broccoli", "Spinach", "Tomatoes", "Cucumbers", "Bell peppers", "Onions", "Garlic", "Mushrooms", "Potatoes", "Chicken", "Beef", "Pork", "Lamb", "Fish", "Shrimp", "Tofu", "Eggs", "Turkey", "Milk", "Cheese", "Yogurt", "Butter", "Rice", "Bread", "Pasta", "Quinoa", "Oats", "Barley", "Flour", "Sugar", "Salt", "Pepper", "Olive oil", "Coconut oil", "Cumin", "Turmeric", "Almonds", "Walnuts", "Chia seeds", "Flaxseeds", "Sunflower seeds", "Pumpkin seeds", "Honey", "Maple syrup", "Soy sauce", "Vinegar", "Coffee", "Tea", "Chocolate","Olive"));
            List<Component> componentList = new ArrayList<>();
            for (String s :ingredints){
                componentList.add(new Component(s));
            }
            return componentList;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Component>) ois.readObject();
        }
    }

}