package Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyListener implements ActionListener{

    private GUI gui;
    private MyWorker worker;

    public MyListener(GUI gui){
        this.gui = gui;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        gui.btt_book.setEnabled(false);
        worker = new MyWorker(gui);
        worker.execute();
    }

}
