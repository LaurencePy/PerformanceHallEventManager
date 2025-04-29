

public class MusicEvent extends LiveEvent {

    public MusicEvent(int eventID, String eventName, AgeRestrictionCategory restriction, int quantityInStock, double performanceFee, double ticketPrice) {
        super(eventID, eventName, restriction, quantityInStock, performanceFee, ticketPrice, LiveEventCategory.MUSIC);
    }

    @Override
    public String toString() {
        return "Music Event: " + getEventName();
    }
}