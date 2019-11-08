package roomBookingUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SpringLayout;

import roomBookingData.DataProcess;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * <h1>RoomBooking Driver</h1>
 * User interface for main window
 * 
 * <p>
 * Driver class which opens the main window allowing user selection of the
 * components of the application.
 * 
 * @author Gareth Tucker
 * @version 0.1.3
 * @since 07-06-2019
 */
public class RoomBookingDriver
{

    private JFrame roomFrame;
    private DataProcess dataProcess;
    private static int clientID;

    /**
     * Launch the application.
     * @param args // main
     */
    public static void main(String[] args)
    {
        EventQueue.invokeLater(new Runnable() {
            public void run()
            {
                try {
                    RoomBookingDriver window = new RoomBookingDriver();
                    window.roomFrame.setVisible(true);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public RoomBookingDriver()
    {
        dataProcess = new DataProcess();
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize()
    {
        roomFrame = new JFrame();
        roomFrame.setTitle("Room Booking");
        roomFrame.setResizable(false);
        roomFrame.setBounds(100, 100, 348, 478);
        roomFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        roomFrame.setLocationRelativeTo(null);
        SpringLayout springLayout = new SpringLayout();
        roomFrame.getContentPane().setLayout(springLayout);

        JButton btnAddClient = new JButton("Add Client");
        btnAddClient.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                addClient();
            }
        });
        springLayout.putConstraint(SpringLayout.NORTH, btnAddClient, 52, SpringLayout.NORTH,
                roomFrame.getContentPane());
        roomFrame.getContentPane().add(btnAddClient);

        JButton btnBookRoom = new JButton("Book Room");
        springLayout.putConstraint(SpringLayout.NORTH, btnBookRoom, 36, SpringLayout.SOUTH, btnAddClient);
        springLayout.putConstraint(SpringLayout.WEST, btnAddClient, 0, SpringLayout.WEST, btnBookRoom);
        springLayout.putConstraint(SpringLayout.EAST, btnAddClient, 0, SpringLayout.EAST, btnBookRoom);
        btnBookRoom.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0)
            {
                addBooking();
            }
        });
        roomFrame.getContentPane().add(btnBookRoom);

        JButton btnCancelBooking = new JButton("Cancel Booking");
        springLayout.putConstraint(SpringLayout.NORTH, btnCancelBooking, 32, SpringLayout.SOUTH, btnBookRoom);
        springLayout.putConstraint(SpringLayout.WEST, btnCancelBooking, 57, SpringLayout.WEST, roomFrame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnCancelBooking, -61, SpringLayout.EAST, roomFrame.getContentPane());
        springLayout.putConstraint(SpringLayout.EAST, btnBookRoom, 0, SpringLayout.EAST, btnCancelBooking);
        springLayout.putConstraint(SpringLayout.WEST, btnBookRoom, 0, SpringLayout.WEST, btnCancelBooking);
        btnCancelBooking.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                cancelBooking();
            }
        });
        roomFrame.getContentPane().add(btnCancelBooking);

        JButton btnReport = new JButton("Report");
        springLayout.putConstraint(SpringLayout.NORTH, btnReport, 40, SpringLayout.SOUTH, btnCancelBooking);
        springLayout.putConstraint(SpringLayout.WEST, btnReport, 0, SpringLayout.WEST, btnAddClient);
        springLayout.putConstraint(SpringLayout.EAST, btnReport, 0, SpringLayout.EAST, btnAddClient);
        btnReport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                createReportDialog();
            }
        });
        roomFrame.getContentPane().add(btnReport);
    }
    /**
     * add a new client
     */
    private void addClient()
    {
        AddClient.addClient(roomFrame, dataProcess);
    }

    /**
     * add a new booking
     */
    private void addBooking()
    {
        AddBooking.addBooking(roomFrame, dataProcess);
    }

    protected void cancelBooking()
    {
        String tempID = "";
        try {
            tempID = JOptionPane.showInputDialog("Enter the client ID you wish to cancel");
            if (tempID.length() > 4) {
                tempID = tempID.substring(0,4);
                JOptionPane.showMessageDialog(null, "ID must not be longer than 4 digits, \nThe entry has been trimmed");
            }
            clientID = Integer.parseInt(tempID);
        }
        catch (NullPointerException e) {
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID must be digits");
        }
        dataProcess.cancelBooking(clientID);

    }

    protected void createReportDialog()
    {
        DataProcess.createReport(roomFrame, dataProcess);
    }

}
