



public class CreditCardPayment implements PaymentMethod {

    private String cardNumber;
    private String securityCode;

    public CreditCardPayment(String cardNumber, String securityCode) {
        this.cardNumber = cardNumber;
        this.securityCode = securityCode;
    }

    @Override
    public Receipt processPayment(double amount, Address fullAddress) {
    	return null;
    }
}
