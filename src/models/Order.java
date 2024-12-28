package src.models;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {

    private static String filePath = src.Helper.ordersPath;
    public enum OrderStatus {
        PENDING, COMPLETED, CANCELLED
    }
    public enum OrderType {
        DELEVERE ,ON_TABLE ,PRIVATE_OR_OTHER
    }
    public enum OrderPay {
        Cash,Card
    }

    // private static long maxId;
//    private static long id = 1;
    private long id;
    private User user;
    private OrderStatus orderStatus = OrderStatus.PENDING;
    private long totalPrice;
    private long tips;
    private OrderType orderType;
    private OrderPay orderPay;
    private String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    private String completedAt;
    private List<OrderDetail> orderDetails;
    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    public void setCompletedAt(String completedAt) {
        this.completedAt = completedAt;
    }

    public long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public long getTips() {
        return tips;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getCompletedAt() {
        return completedAt;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }
    public OrderPay getOrderPay() {
        return orderPay;
    }

    public Order(User user, OrderStatus orderStatus, long totalPrice, long tips, OrderType orderType, OrderPay orderPay,
             List<OrderDetail> orderDetails) throws ClassNotFoundException, IOException {
        this.user = user;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.tips = tips;
        this.orderType = orderType;
        this.orderPay = orderPay;
        this.orderDetails = orderDetails;
         //this.id= loadFromFile().getLast()==null?1:loadFromFile().getLast().id+1;
        //this.id= (loadFromFile().getLast().id)+1;
        List<Order> orders = loadFromFile();
        if (orders.isEmpty()) {
            this.id = 1;
        } else {
            this.id = orders.get(orders.size() - 1).id + 1;
        }
    }

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

