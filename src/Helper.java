package src;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import src.models.Meal;
import src.models.Order;
import src.models.OrderDetail;
import src.models.User;

public class Helper implements Serializable {

    enum TypeReport{
        numberDaliyOrders,
        meals,
        daliySal,
        daliyUser
    }
    public  class  Reports{
        public static HashMap<String, Integer> countDailyOrdersReport() throws IOException, ClassNotFoundException {
            // تحميل الطلبات من الملف
            List<Order> orders = Order.loadFromFile();

            // إنشاء خريطة لتجميع الطلبات اليومية
            HashMap<String, Integer> dailyOrderCount = new HashMap<>();

            // تنسيق التاريخ المستخدم في createdAt
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            for (Order order : orders) {
                // استخراج التاريخ فقط (بدون الوقت)
                String date = LocalDate.parse(order.getCreatedAt().split(" ")[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

                // تحديث العدد في الخريطة
                dailyOrderCount.put(date, dailyOrderCount.getOrDefault(date, 0) + 1);
            }
            return dailyOrderCount;
        }
        public static HashMap<String, Long> dailyRevenueReport() throws IOException, ClassNotFoundException {
            // تحميل الطلبات من الملف
            List<Order> orders = Order.loadFromFile();

            // إنشاء خريطة لتجميع العائدات اليومية
            HashMap<String, Long> dailyRevenue = new HashMap<>();

            // تنسيق التاريخ المستخدم في createdAt
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            for (Order order : orders) {
                // استخراج التاريخ فقط (بدون الوقت)
                String date = LocalDate.parse(order.getCreatedAt().split(" ")[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")).toString();

                // حساب العائد الكلي (totalPrice + tips)
                long orderRevenue = order.getTotalPrice() + order.getTips();

                // تحديث إجمالي العائدات في الخريطة
                dailyRevenue.put(date, dailyRevenue.getOrDefault(date, 0L) + orderRevenue);
            }
            return dailyRevenue;
        }
        public static HashMap<String, Integer> rankCustomersByVisitsReport() throws IOException, ClassNotFoundException {
            // تحميل الطلبات من الملف
            List<Order> orders = Order.loadFromFile();

            // خريطة لتخزين عدد زيارات كل زبون
            HashMap<String, Integer> customerVisits = new HashMap<>();

            // حساب عدد الطلبات لكل زبون
            for (Order order : orders) {
                User user = order.getUser();
                customerVisits.put(user.userName, customerVisits.getOrDefault(user, 0) + 1);
            }
            return customerVisits;
        }
        public static HashMap<String, Long> rankMealsByOrdersReport() throws IOException, ClassNotFoundException {
            // تحميل الطلبات من الملف
            List<Order> orders = Order.loadFromFile();

            // خريطة لتجميع الطلبات حسب الوجبات
            HashMap<String, Long> mealOrders = new HashMap<>();

            // حساب عدد الطلبات لكل وجبة
            for (Order order : orders) {
                for (OrderDetail detail : order.getOrderDetails()) {
                    String mealName = detail.meal.name;
                    long count = detail.count;
                    // تجميع العدد الإجمالي لكل وجبة
                    mealOrders.put(mealName, mealOrders.getOrDefault(mealName, 0L) + count);
                }
            }
            return mealOrders;
        }
    }
    public static User myUser=new User();
    public static String usersPath = FileUtils.getOrCratePath("users");
    public static String mealsPath = FileUtils.getOrCratePath("meals");
    public static String componentsPath = FileUtils.getOrCratePath("components");
    public static String ordersPath = FileUtils.getOrCratePath("orders");
}

class FileUtils {

    public static String getOrCratePath(String fileName) {
        String path = (Paths.get("").toAbsolutePath().toString()) + "/src/fiels/" + fileName + ".dat"; // ضع مسار الملف هنا
        File file = new File(path);
        File parentDir = file.getParentFile();

        if (parentDir != null && !parentDir.exists()) {
            if (parentDir.mkdirs()) {
                System.out.println("Directories created: " + parentDir.getAbsolutePath());
            } else {
                System.out.println("Failed to create directories.");
            }

        }

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("File created: " + file.getAbsolutePath());
                } else {
                    System.out.println("Failed to create file.");
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
        return file.getPath();
    }
}
