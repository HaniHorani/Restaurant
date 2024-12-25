package src;
import src.models.Order;
import src.models.UserNotification;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
            new Login();
    }
}
/*
List<User> users = User.loadFromFile();

for (int i = 0; i < 10; i++) {
    users.add(new User("hani" + i, "123" + i, UserType.CUSTOMER));
}
User u = new User(null, null, null);
if (User.check(u)) {
    users.add(u);
}
User.saveToFile(users);
users = User.loadFromFile();
for (User user : users) {
    System.out.println(user.userName);
}

List<Meal> eMeals = Meal.loadFromFile();
for (int i = 0; i < 10; i++) {
    eMeals.add(new Meal("checken" + i, 1, null));
}
Meal.saveToFile(eMeals);
eMeals = Meal.loadFromFile();

for (Meal meal : eMeals) {
    System.out.println(meal.name);
} */
