package Client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class GUI extends JFrame{

    private static final long serialVersionUID = 1L;
    JButton btt_eventList;                      // Button to print the event list
    JButton btt_book;                           // Button to book the seats
    JLabel l1, l2;                              // Panel label
    JTextField tf_eventName, tf_eventSeats;       // Name and num of seats of the event to book
    JTextArea ta_eventList, ta_bookingReturn;     // Event list

    public GUI(){
        super("Client GUI");

        BoxLayout boxLayout = new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS);
        setLayout(boxLayout);
        setDefaultCloseOperation(EXIT_ON_CLOSE);    //terminate frame on exit

        // Panel 1
        l1 = new JLabel("Events available");
        ta_eventList = new JTextArea();
        ta_eventList.setEditable(false);
        ta_eventList.setPreferredSize(new Dimension(200, 200));
        btt_eventList = new JButton("Show Available events");
        MyListener event_handler = new MyListener(this, "eventList");
        btt_eventList.addActionListener(event_handler);


        //Create JPanel with box layout for "Events Available"
        JPanel guiPanel1 = new JPanel();
        guiPanel1.setLayout(new BoxLayout(guiPanel1, BoxLayout.PAGE_AXIS));
        guiPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components
        guiPanel1.add(l1);
        guiPanel1.add(Box.createRigidArea(new Dimension(0,5)));
        guiPanel1.add(ta_eventList);
        guiPanel1.add(btt_eventList);

        //create a bit of space around components so they get away from edges
        guiPanel1.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        //add panel to frame
        add(guiPanel1, BorderLayout.CENTER);

        //-------------------------[Second Frame]-----------------------------------
        // Panel 2
        l2 = new JLabel("Booking:");
        tf_eventName = new JTextField("");
        tf_eventSeats = new JTextField("");
        // Button and its handler
        btt_book = new JButton("Book event");
        MyListener book_handler = new MyListener(this, "book");
        btt_book.addActionListener(book_handler);
        // Result
        ta_bookingReturn = new JTextArea();
        ta_bookingReturn.setEditable(false);
        ta_bookingReturn.setPreferredSize(new Dimension(50, 50));

        //create new panel for "Booking"
        JPanel bookingPanel = new JPanel();
        bookingPanel.setLayout(new BoxLayout(bookingPanel, BoxLayout.PAGE_AXIS));
        bookingPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add components
        bookingPanel.add(l2);
        bookingPanel.add(tf_eventName);
        bookingPanel.add(tf_eventSeats);
        bookingPanel.add(btt_book);
        bookingPanel.add(ta_bookingReturn);

        //add panel to frame
        add(bookingPanel, BorderLayout.CENTER);

        setVisible(true);
        pack();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }

}