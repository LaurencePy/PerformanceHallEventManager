public class Performance extends LiveEvent {

    private String additionalInfo;

    public Performance(int eventID, String eventName, AgeRestrictionCategory restriction,
                       int quantityInStock, double performanceFee, double ticketPrice,
                       LiveEventCategory category, String additionalInfo) {
        super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, category);
        this.additionalInfo = additionalInfo;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public String toString() {
        return "Performance: " + getEventName();
    }
}
