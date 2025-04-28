package model;

import enums.AgeRestrictionCategory;
import enums.LiveEventCategory;

public abstract class LiveEvent {
    private int eventID;
    private String eventName;
    private AgeRestrictionCategory restriction;
    private int quantityInStock;
    private double performanceFee;
    private double ticketPrice;
    private LiveEventCategory liveEventCategory;

    public LiveEvent(int eventID, String eventName, AgeRestrictionCategory restriction, int quantityInStock, double performanceFee, double ticketPrice, LiveEventCategory liveEventCategory) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.restriction = restriction;
        this.quantityInStock = quantityInStock;
        this.performanceFee = performanceFee;
        this.ticketPrice = ticketPrice;
        this.liveEventCategory = liveEventCategory;
    }

    public int getEventID() { return eventID; }
    public String getEventName() { return eventName; }
    public AgeRestrictionCategory getAgeRestriction() { return restriction; }
    public int getQuantityInStock() { return quantityInStock; }
    public void setQuantityInStock(int newQuantityInStock) { this.quantityInStock = newQuantityInStock; }
    public double getPerformanceFee() { return performanceFee; }
    public double getTicketPrice() { return ticketPrice; }
    public LiveEventCategory getEventCategory() { return liveEventCategory; }

    public abstract String toString();
}