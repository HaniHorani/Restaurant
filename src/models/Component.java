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

public class Component implements Serializable {

    private final static String filePath = Helper.componentsPath;
    public String name;
    // public List<Meal> meals;

    public static Boolean check(Component newComponent,  List<Component> components) throws ClassNotFoundException, IOException {
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
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Component>) ois.readObject();
        }
    }

}
