package user;

import java.util.ArrayList;
import java.util.List;
import model.LiveEvent;

public class Basket {
    private List<LiveEvent> tickets;

    public Basket() {
        tickets = new ArrayList<>();
    }

    public void addTicket(LiveEvent event) {
        tickets.add(event);
    }

    public void clearBasket() {
        tickets.clear();
    }

    public List<LiveEvent> getTickets() {
        return tickets;
    }
}