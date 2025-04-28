package payment;

import model.Address;

public interface PaymentMethod {
    void processPayment(double amount, Address fullAddress);
}