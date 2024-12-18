package src.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    public enum OrderStatus {
        PENDING, COMPLETED, CANCELLED
    }

    private static String filePath = src.Helper.ordersPath;

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

    public static void saveToFile(List<Order> orders) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(orders);
        }
    }

    public static List<Order> loadFromFile() throws IOException, ClassNotFoundException {
        if (new File(filePath).length() == 0) {
            System.out.println("File is empty. Returning an empty list.");
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Order>) ois.readObject();
        }
    }
}

class OrderDetail implements Serializable {
    public long orderId;
    public long mealId;
    public long count;
    public Order order;
    public Meal meal;

}
