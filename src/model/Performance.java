package model;

public class Performance extends LiveEvent {

    public Performance(int eventID, String eventName, enums.AgeRestrictionCategory restriction, int quantityInStock, double performanceFee, double ticketPrice) {
        super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, enums.LiveEventCategory.PERFORMANCE);
    }

    @Override
    public String toString() {
        return "Performance: " + getEventName();
    }
}