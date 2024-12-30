package src;

public class Main {
    public static void main(String[] args) {
      new Login();
//       try {
//           List<User> users = User.loadFromFile();
//           User deletuser =null;
//           for (User user : users) {
//               if(user.userName.equals("omar") && user.password.equals("123")) {
//                   deletuser = user;
//               }
//           }
//           users.remove(deletuser);
//           System.out.println("Saved sucessful");
//           User.saveToFile(users);
//       }catch (IOException | ClassNotFoundException e) {
//           throw new RuntimeException(e);
//       }

//       try {
//           List<User> users = User.loadFromFile();
//           for (User user : users) {
////               if (user.userName.equals("bahaa") && user.password.equals("123")) {
//                   user.notification = new ArrayList<>();
//                   System.out.println("Emptyed notifications");
////               }
//           }
//           System.out.println("Saved sucessful");
//           User.saveToFile(users);
//       } catch (IOException | ClassNotFoundException e) {
//           throw new RuntimeException(e);
//       }
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
