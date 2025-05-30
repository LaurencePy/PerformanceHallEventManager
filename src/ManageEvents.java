import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class ManageEvents {

    private List<LiveEvent> events = new ArrayList<>();

    public ManageEvents(String filePath) {
        loadEvents(filePath);
    }

    private void loadEvents(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
            	// v DEBUG to show read lines
            	//System.out.println("DEBUG Read line: " + line);
            	
            	
            	// load events from stock and categorise into music or performance
                String[] parts = line.split(",");
                if (parts.length == 9) {
                    int eventID = Integer.parseInt(parts[0].trim());
                    LiveEventCategory category = LiveEventCategory.valueOf(parts[1].trim().toUpperCase());
                    String eventType = parts[2].trim();
                    String name = parts[3].trim();
                    AgeRestrictionCategory restriction = AgeRestrictionCategory.valueOf(parts[4].trim().toUpperCase());
                    int quantity = Integer.parseInt(parts[5].trim());
                    double fee = Double.parseDouble(parts[6].trim());
                    double price = Double.parseDouble(parts[7].trim());
                    String info = parts[8].trim();

                    LiveEvent event = null;
                    if (category == LiveEventCategory.MUSIC) {
                        event = new MusicEvent(eventID, name, restriction, quantity, fee, price, category, eventType, info);
                    } else if (category == LiveEventCategory.PERFORMANCE) {
                        event = new Performance(eventID, name, restriction, quantity, fee, price, category, eventType, info);
                    }


                    if (event != null) {
                        events.add(event);
                    }
                }
            }
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    // returns list of live events sorted by ticket price (ascending)
    public List<LiveEvent> getAllEvents() {
    	// convert events list into a stream
        return events.stream()
        		// sort stream by ticket price asc
            .sorted(Comparator.comparingDouble(LiveEvent::getTicketPrice))
            // put back into list and sort
            .collect(Collectors.toList());
    }
}
