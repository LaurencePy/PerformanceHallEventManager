package model;

public class Address {
    private String houseNumber;
    private String postcode;
    private String city;

    public Address(String houseNumber, String postcode, String city) {
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.city = city;
    }

    public String getFullAddress() {
        return houseNumber + ", " + postcode + ", " + city;
    }
}