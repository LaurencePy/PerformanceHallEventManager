

public class PayPalPayment implements PaymentMethod {

    private String email;

    public PayPalPayment(String email) {
        this.email = email;
    }

    @Override
    public Receipt processPayment(double amount, Address fullAddress) {
    	return null;
    }
}
