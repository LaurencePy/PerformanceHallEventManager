public class Admin extends User {
    public Admin(String userID, String name, Address address) {
        super(userID, name, address);
    }

    @Override
    public void openPage() {
        new AdminPage(name).setVisible(true);
    }
}
