package src.models;

import java.io.Serializable;


public class OrderDetail implements Serializable {
//    public long orderId;
//    public long mealId;
    public long count;
//    public Order order;
    public Meal meal;

    public OrderDetail( long count, Meal meal) {
//        this.orderId = orderId;
//        this.mealId = mealId;
        this.count = count;
//        this.order = order;
        this.meal = meal;
    }
}

