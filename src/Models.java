
import java.io.*;
import java.time.Instant;
import java.util.List;

class OrderDetail implements Serializable {

    public long orderId;
    public long mealId;
    public long count;
    public Order order;
    public Models meal;

    public static void addOrderDetail(List<OrderDetail> details, OrderDetail detail) {
        details.add(detail);
    }

    public static void deleteOrderDetail(List<OrderDetail> details, long orderId, long mealId) {
        details.removeIf(d -> d.orderId == orderId && d.mealId == mealId);
    }
}

class Component implements Serializable {

    public long id;
    public String name;
    public List<MealComponent> mealComponents;

    public static void addComponent(List<Component> components, Component component) {
        components.add(component);
    }

    public static void deleteComponent(List<Component> components, long componentId) {
        components.removeIf(c -> c.id == componentId);
    }
}

class MealComponent implements Serializable {

    public long mealId;
    public long componentId;
    public Models meal;
    public Component component;
}

class Notification implements Serializable {

    public long id;
    public String title;
    public String message;
    public User user;

    public static void addNotification(List<Notification> notifications, Notification notification) {
        notifications.add(notification);
    }

    public static void deleteNotification(List<Notification> notifications, long notificationId) {
        notifications.removeIf(n -> n.id == notificationId);
    }
}

class User {

    public enum UserType {
        ADMIN, CUSTOMER, MANAGER, EMPLOYEE
    }

    public long id;
    public String userName;
    public String password;
    public UserType type;
    public Instant createdAt;
    public List<Order> orders; // Relationship: One User to Many Orders

    public static void saveToFileBinary(List<User> users, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
        }
    }

    public static List<User> loadFromFileBinary(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<User>) ois.readObject();
        }
    }
}

class Models {

    public long id;
    public String name;
    public long quantity;
    public List<MealComponent> mealComponents; // Relationship: One Meal to Many MealComponents

    public static void saveToFileBinary(List<Models> meals, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(meals);
        }
    }

    public static List<Models> loadFromFileBinary(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Models>) ois.readObject();
        }
    }
}

class Order {

    public enum OrderStatus {
        PENDING, COMPLETED, CANCELLED
    }

    public long id;
    public long userId;
    public OrderStatus orderStatus;
    public long totalPrice;
    public long tips;
    public long status;
    public long orderType;
    public Instant createdAt;
    public User user; // Relationship: Many Orders to One User
    public List<OrderDetail> orderDetails; // Relationship: One Order to Many OrderDetails

    public static void saveToFileBinary(List<Order> orders, String filePath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(orders);
        }
    }

    public static List<Order> loadFromFileBinary(String filePath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Order>) ois.readObject();
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
