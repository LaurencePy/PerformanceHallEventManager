

public class Customer extends User {
    public Customer(String userID, String name, Address address) {
        super(userID, name, address);
    }
    
    
    // opens customer page if the user is a customer selected on loginFrame
    @Override
    public void openPage() {
        new CustomerPage(this).setVisible(true);
    }
}

