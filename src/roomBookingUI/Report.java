package roomBookingUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import roomBookingData.Booking;
import roomBookingData.Client;
import roomBookingData.DataProcess;

import javax.swing.SpringLayout;
import javax.swing.JTextPane;
import java.awt.Component;
import javax.swing.Box;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;
import java.awt.Dimension;


/**
 * <h1> Report </h1>
 * Generates a report of clients or Bookings
 * 
 * <p>
 * Produces a report of bookings from bookingList 
 * Produces a report of clients from clientList
 * @author Gareth Tucker
 * @version 0.1.3
 * @since 07-06-2019
 */
@SuppressWarnings("serial")
public class Report extends JDialog
{
    private static Report dialog = null;

    private final JPanel contentPanel = new JPanel();

    DataProcess dataProcess;
    private JTextPane txtReport;
    private static List<Booking> tempBookList = new ArrayList<Booking>();
    private static Set<Client> tempClientList = new HashSet<Client>();

    /**
     * Initialises the dialog and makes a copy of the client and booking lists.
     * 
     * @param roomFrame // main frame.
     * @param dataProcess // gets data from data process class.
     * @param clientList // array list of Clients.
     * @param bookingList // array list of bookings.
     */
    public static void initialise(JFrame roomFrame, DataProcess dataProcess, Set<Client> clientList,
            List<Booking> bookingList)
    {
        tempBookList = bookingList;
        tempClientList = clientList;
        initDialog(roomFrame, dataProcess);
        dialog.setTitle("Report");
        dialog.setVisible(true);
    }

    /**
     * clears the fields of dialog
     * 
     * @param parent // main Window
     * @param dataProcess // Class
     */
    private static void initDialog(JFrame parent, DataProcess dataProcess)
    {
        if (dialog == null) {
            dialog = new Report();
        }
        dialog.dataProcess = dataProcess;
        dialog.txtReport.setText("");
        dialog.setLocationRelativeTo(parent);
    }

    /**
     * Iterates through the booking list and adds the element i to the text pane
     */
    protected void bookingReport()
    {
        StringBuilder sb = new StringBuilder();
        for (Booking i : tempBookList) {
            sb.append("Booking : " + i + "\n");
        }
        txtReport.setText("" + sb);
    }

    /**
     * iterates through the client list and adds the element i to the text pane
     */
    protected void clientReport()
    {
        StringBuilder sb = new StringBuilder();
        for (Client i : tempClientList) {
            sb.append("Client : " + i + "\n");
        }
        txtReport.setText(" " + sb);
    }

    /**
     * Create the dialog.
     */
    public Report()
    {
        setTitle("Report");
        setBounds(100, 100, 450, 500);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        SpringLayout sl_contentPanel = new SpringLayout();
        contentPanel.setLayout(sl_contentPanel);

        txtReport = new JTextPane();
        JScrollPane sp = new JScrollPane(txtReport);
        sl_contentPanel.putConstraint(SpringLayout.NORTH, sp, 10, SpringLayout.NORTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.WEST, sp, 10, SpringLayout.WEST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.SOUTH, sp, -10, SpringLayout.SOUTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.EAST, sp, -10, SpringLayout.EAST, contentPanel);
        sp.setPreferredSize(new Dimension(400, 400));
        sl_contentPanel.putConstraint(SpringLayout.NORTH, txtReport, 19, SpringLayout.NORTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.WEST, txtReport, 10, SpringLayout.WEST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.SOUTH, txtReport, -10, SpringLayout.SOUTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.EAST, txtReport, -66, SpringLayout.EAST, contentPanel);
        contentPanel.add(sp);
        txtReport.setEditable(false);

        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);

            JButton btnBookingReport = new JButton("Booking Report");
            btnBookingReport.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e)
                {
                    bookingReport();
                }
            });
            buttonPane.add(btnBookingReport);

            JButton btnClientReport = new JButton("Client Report");
            btnClientReport.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0)
                {
                    clientReport();
                }
            });
            buttonPane.add(btnClientReport);

            Component horizontalStrut_3 = Box.createHorizontalStrut(20);
            buttonPane.add(horizontalStrut_3);

            Component horizontalStrut_2 = Box.createHorizontalStrut(20);
            buttonPane.add(horizontalStrut_2);

            Component horizontalStrut_1 = Box.createHorizontalStrut(20);
            buttonPane.add(horizontalStrut_1);

            Component horizontalStrut = Box.createHorizontalStrut(20);
            buttonPane.add(horizontalStrut);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        dialog.setVisible(false);
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
        }
    }
}
