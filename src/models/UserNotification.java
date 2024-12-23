package src.models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserNotification implements Serializable {
    public String createdAt = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
    // public String title;
    public String message;
    public Order order;
    // public User user;
    public UserNotification(String message, Order order) {
        this.message = message;
        this.order = order;
    }
}
