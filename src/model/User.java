package src.model;

import java.sql.Date;

public class User {
    public int id ;
    public String userName;
    public String password;
    public Date createdDate;
    enum UserType {
        Admin, Worker, Customer
    }

    // List<order> orders;
}
