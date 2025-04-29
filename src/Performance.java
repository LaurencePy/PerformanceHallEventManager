

public class Performance extends LiveEvent {

    public Performance(int eventID, String eventName, AgeRestrictionCategory restriction, int quantityInStock, double performanceFee, double ticketPrice) {
        super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, LiveEventCategory.PERFORMANCE);
    }

    @Override
    public String toString() {
        return "Performance: " + getEventName();
    }
}