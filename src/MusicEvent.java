public class MusicEvent extends LiveEvent {

    private String additionalInfo;

    public MusicEvent(int eventID, String eventName, AgeRestrictionCategory restriction,
            int quantityInStock, double performanceFee, double ticketPrice,
            LiveEventCategory category, String eventType, String additionalInfo) {
    	
			super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, category, eventType);
			this.additionalInfo = additionalInfo;
			}


    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    public String toString() {
        return "Music Event: " + getEventName();
    }
}
