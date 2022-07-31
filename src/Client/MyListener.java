package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListener implements ActionListener{

    private GUI gui;
    private MyWorker worker;
    private String bttName;

    public MyListener(GUI gui, String bttName){
        this.gui = gui;
        this.bttName = bttName;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if(bttName.equals("eventList")) {
        	gui.btt_eventList.setEnabled(false);
        	worker = new MyWorker(gui, "eventList");
        }
        else if(bttName.equals("book")) {
        	gui.btt_book.setEnabled(false);
        	worker = new MyWorker(gui, "book");
        }
        
        worker.execute();
    }

}
