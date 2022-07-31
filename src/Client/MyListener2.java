package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListener2 implements ActionListener {
    private final GUI gui;

    public MyListener2(GUI gui){
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        MyWorker2 worker;
        gui.btt_eventList.setEnabled(false);
        worker = new MyWorker2(gui);
        worker.execute();
    }
}
