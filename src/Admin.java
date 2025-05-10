public class Admin extends User {
    public Admin(String userID, String name, Address address) {
        super(userID, name, address);
    }

    @Override
    // to call admin page to be opened if role is admin
    public void openPage() {
        new AdminPage(name).setVisible(true);
    }
}
