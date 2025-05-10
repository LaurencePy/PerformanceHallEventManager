

public class Customer extends User {
    public Customer(String userID, String name, Address address) {
        super(userID, name, address);
    }

    @Override
    public void openPage() {
        new CustomerPage(this).setVisible(true);
    }
}

