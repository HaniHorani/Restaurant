package src.models;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import src.Helper;

public class User implements Serializable {

    public enum UserType {
        ADMIN, CUSTOMER, EMPLOYEE
    }
    
    private static final String filePath = Helper.usersPath;
    public long id;
    public String userName;
    public String password;
    public UserType type;
    public String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    public List<Order> orders;
    public List<UserNotification> notification ;

    public User() {
    }

    public User(String userName, String password, UserType type) {
        this.userName = userName;
        this.password = password;
        this.type = type;
    }

    public static Boolean check(User newUser) throws ClassNotFoundException, IOException {
        List<User> users = loadFromFile();
        for (User user : users) {
            if (newUser.userName.equals(user.userName)) {
                return false;
            }
        }
        return true;
    }

    public static void saveToFile(List<User> users) throws IOException {
        System.out.println("=================" + filePath + "=================");
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
        }
    }

    public static List<User> loadFromFile() throws IOException, ClassNotFoundException {
        if (new File(filePath).length() == 0) {
            System.out.println("File is empty. Returning an empty list.");
            List<User> users = new ArrayList<>();
            users.add(new User("hani", "123", UserType.ADMIN));
            saveToFile(users);
            return users;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<User>) ois.readObject();
        }
    }
}

/*
class Meal implements Serializable {

    public long id;
    public String name;
    public long quantity;
    public List<MealComponent> mealComponents;

    public static void addMeal(List<Meal> meals, Meal meal) {
        meals.add(meal);
    }

    public static void deleteMeal(List<Meal> meals, long mealId) {
        meals.removeIf(m -> m.id == mealId);
    }

    public static void updateMeal(List<Meal> meals, Meal updatedMeal) {
        for (int i = 0; i < meals.size(); i++) {
            if (meals.get(i).id == updatedMeal.id) {
                meals.set(i, updatedMeal);
                break;
            }
        }
    }

    public static Meal getMealById(List<Meal> meals, long mealId) {
        return meals.stream().filter(m -> m.id == mealId).findFirst().orElse(null);
    }
}
 */
 /*
class User implements Serializable {

    public enum UserType {
        ADMIN, CUSTOMER, MANAGER, EMPLOYEE
    }

    public long id;
    public String userName;
    public String password;
    public UserType type;
    public Instant createdAt;
    public List<Order> orders; // Relationship: One User to Many Orders

    // Getters and Setters
    // Constructor(s)
    // toString() method (optional)
    public static void saveToFile(List<User> users, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                writer.write(user.toString());
                writer.newLine();
            }
        }
    }

    public static List<User> loadFromFile(String filePath) throws IOException {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Assuming toString format is parsable
                // Example: parseUser(line);
                // users.add(parseUser(line));
            }
        }
        return users;
    }

    public static void deleteFromFile(long userId, String filePath) throws IOException {
        List<User> users = loadFromFile(filePath);
        users.removeIf(user -> user.id == userId);
        saveToFile(users, filePath);
    }

    public static User findById(long userId, String filePath) throws IOException {
        List<User> users = loadFromFile(filePath);
        for (User user : users) {
            if (user.id == userId) {
                return user;
            }
        }
        return null;
    }

    public static void updateUser(User updatedUser, String filePath) throws IOException {
        List<User> users = loadFromFile(filePath);
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).id == updatedUser.id) {
                users.set(i, updatedUser);
                break;
            }
        }
        saveToFile(users, filePath);
    }
}
 */
 /*
enum orderStatus {
    pending, preparing, delivered, cancelled
}

enum orderType {
    dinein, delivery
}


class Order implements Serializable {

    public long id;
    public long userId;
    public orderStatus status;
    public orderType type;
    public long totalPrice;
    public long tips;
    public Instant createdAt;
    public User user;
    public List<OrderDetail> orderDetails;

    public static void addOrder(List<Order> orders, Order order) {
        orders.add(order);
    }

    public static void deleteOrder(List<Order> orders, long orderId) {
        orders.removeIf(o -> o.id == orderId);
    }

    public static void updateOrder(List<Order> orders, Order updatedOrder) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).id == updatedOrder.id) {
                orders.set(i, updatedOrder);
                break;
            }
        }
    }

    public static Order getOrderById(List<Order> orders, long orderId) {
        return orders.stream().filter(o -> o.id == orderId).findFirst().orElse(null);
    }
}
 */
