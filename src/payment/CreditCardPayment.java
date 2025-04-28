package payment;

import model.Address;

public class CreditCardPayment implements PaymentMethod {

    private String cardNumber;
    private String securityCode;

    public CreditCardPayment(String cardNumber, String securityCode) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
    }

    @Override
    public void processPayment(double amount, Address fullAddress) {

    }
}
