import java.io.*;
import java.util.*;

public class ManageUsers {
    private List<User> users = new ArrayList<>();

    public ManageUsers(String filePath) {
        loadUsers(filePath);
    }

    private void loadUsers(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] items = line.split(",");
                    String id = items[0].trim();
                    String userID = items[1].trim();
                    String name = items[2].trim();
                    String houseNum = items[3].trim();
                    String postcode = items[4].trim();
                    String city = items[5].trim();
                    String role = items[6].trim().toLowerCase();

                    Address address = new Address(houseNum, postcode, city);

                    if (role.equals("admin")) {
                        users.add(new Admin(userID, name, address));
                    } else if (role.equals("customer")) {
                        users.add(new Customer(userID, name, address));
                    }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserFromName(String idAndName) {
        for (User u : users) {
            String combined = u.getUserID() + " - " + u.getName();
            if (combined.equalsIgnoreCase(idAndName)) {
                return u;
            }
        }
        return null;
    }

    
    public List<User> getUsers() {
        return users;
    }


}
