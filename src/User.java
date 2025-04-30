public abstract class User {
    protected String userID;
    protected String name;
    protected Address address;

    public User(String userID, String name, Address address) {
        this.userID = userID;
        this.name = name;
        this.address = address;
    }

    public String getName() { return name; }
    public Address getAddress() { return address; }

    public abstract void openPage();
}
