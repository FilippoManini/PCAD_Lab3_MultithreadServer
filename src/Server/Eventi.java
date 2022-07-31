package Server;

import java.util.concurrent.*;

import static javax.swing.UIManager.get;

public class Eventi
{
    private final ConcurrentHashMap<String, Integer> eventi;
    public Eventi() { eventi = new ConcurrentHashMap<>(); }

    // Create the given event with posti seats if does not already exist
    public void create(String nome, Integer posti)
    {
        eventi.putIfAbsent(nome,posti);
    }

    // Add seats to a given event
    public void add(String nome, int posti)
    {
        synchronized (this) {
            if (eventi.get(nome) == null)
                return;
        }
        eventi.replace(nome, eventi.get(nome) +posti);
    }

    // To book events
    public String book(String nome, int posti)
    {
        synchronized(this) {
            if (eventi.isEmpty()) return "No events available";

            if (eventi.get(nome) == null) return "No event with the given name";

            if (eventi.get(nome) - posti < 0) return "Not enough seats available";
        }
        eventi.replace(nome, eventi.get(nome)-posti);

        synchronized (this){return "Seats booked";}
    }

    // Delete all events and unlock awaiting clients
    public void close()
    {
        eventi.clear();
        System.out.println("Events list closed");
        synchronized(this) {this.notifyAll();}
    }

    // To visualize final event list on the server
    public synchronized String printEventList()
    {
        String s = "Event Name\tAvailable Seats\n";

        if(eventi.isEmpty())
            return "No events available";

        for (String i : eventi.keySet()) {
            s = s.concat(i.concat("\t\t".concat(eventi.get(i) + "\n")));
        }

        return s;
    }

    public synchronized String formatEventList()
    {
        String s = "Event Name\tAvailable Seats&";

        if(eventi.isEmpty()) return "No events available";

        for (String i : eventi.keySet()) s = s.concat(i.concat("\t".concat(eventi.get(i) + "&")));

        return s;
    }
}