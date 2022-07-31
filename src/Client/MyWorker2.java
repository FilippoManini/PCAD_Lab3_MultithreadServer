package Client;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MyWorker2 extends SwingWorker<String, Integer> {

    private final GUI gui;
    String eventList = "";

    public MyWorker2(GUI gui){
        this.gui = gui;
    }

    @Override
    protected String doInBackground() {
        try (Socket socket = new Socket("localhost", 1234))
        {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);      // writing to server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // reading from server

            out.println("eventList");
            out.flush();
            eventList += in.readLine();
            eventList = eventList.replace("&", "\n");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return "Done";
    }

    @Override
    protected void done(){
        gui.ta_eventList.setText(eventList);
        gui.btt_eventList.setEnabled(true);
    }
}
