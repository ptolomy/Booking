package roomBookingUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.SpringLayout;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

import com.github.lgooddatepicker.components.DatePicker;

import roomBookingData.DataProcess;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import java.awt.event.KeyEvent;


/**
 * <h1>AddBooking</h1> 
 * User Interface for adding a booking
 * 
 * <p>
 * Booking fields are displayed to screen, user selects the values they wish
 * to use.
 * 
 * @author Gareth Tucker
 * @version 0.1.4
 * @since 07-06-2019
 */
@SuppressWarnings("serial")
public class AddBooking extends JDialog
{

    private static boolean timeChecked = false;
    private static boolean dateCheck = false;
    private static boolean idCheck = false;
    private static boolean book = false;
    private final JPanel contentPanel = new JPanel();
    private static AddBooking dialog = null;
    int clientID;
    static String startTime;
    private DataProcess dataProcess;
    DatePicker datePicker = new DatePicker();
    @SuppressWarnings("rawtypes")
    private JComboBox cboWorkStations;
    @SuppressWarnings("rawtypes")
    private JComboBox cboBreakout;
    @SuppressWarnings("rawtypes")
    private JComboBox cboEndTimeRequired;
    @SuppressWarnings("rawtypes")
    private JComboBox cboStartTimeRequired;
    private static JTextPane txtOut;
    private JTextField txtClientID;
    private JCheckBox chckbxSmart; 
    private JCheckBox chckbxPrint;
    private JButton okButton;

    /**
     * Handles the OK button
     */
    private void handleOk()
    {
        if (!timeChecked && !dateCheck && !idCheck) {
            txtOut.setText("you must check the booking before using confirm.");
            return;
        }
        txtOut.setText("");
        Boolean smart;
        Boolean print;
        
        String workStat = cboWorkStations.getSelectedItem().toString();
        int workStation = Integer.parseInt(workStat);        
        String breakOut = cboBreakout.getSelectedItem().toString();
        int breakOutChairs = Integer.parseInt(breakOut);        
        String endTimeBook = cboEndTimeRequired.getSelectedItem().toString();
        int endTime = Integer.parseInt(endTimeBook);       
        String startTimeBook = cboStartTimeRequired.getSelectedItem().toString();
        int startTime = Integer.parseInt(startTimeBook);        
        LocalDate date = datePicker.getDate();             

        if (chckbxSmart.isSelected()) {
            smart = true;
        }
        else
            smart = false;
        if (chckbxPrint.isSelected()) {
            print = true;
        }
        else {
            print = false;
        }
        
        handleCheckBooking();
        if (book) {
            dataProcess.addBooking(clientID, date, startTime, endTime, workStation, breakOutChairs, smart, print);
            dialog = null;
        }
    }



    /**
     * checks if times match.
     */
    private void handleCheckBooking()
    {
        checkTime(); 
        checkDate();
        isValidID();
        if (timeChecked && dateCheck && idCheck) {
            okButton.setEnabled(true);
            txtOut.setText("Valid Booking parameters");
            book = true;
        }
        else {
            okButton.setEnabled(false);
            txtOut.setText("Parameters changed");
            JOptionPane.showMessageDialog(dialog, "Parameters changed");
            book = false;
        }
    }
    
    /**
     * Checks if id entered is digits 
     */
    private void isValidID()
    {
        String tempId = txtClientID.getText().trim();
        if (tempId == null || "".equals(tempId)) {
            txtOut.setText("Invalid Client ID");
            JOptionPane.showMessageDialog(dialog, "Client ID can not be blank");
            idCheck = false;   
            return;
        }
        try {   
            for (int i = 0; i < tempId.length(); i++) {
                if (!Character.isDigit(tempId.charAt(i))) {
                    idCheck = false;  
                    txtOut.setText("Invalid Client ID");
                    return;
                }                  
            }      
        }
        catch (NullPointerException e) {
            JOptionPane.showMessageDialog(dialog, "Client ID can not be blank");
        }
        if (tempId.length() > 4 || tempId.length() < 4) {
            JOptionPane.showMessageDialog(dialog,
                    "Client ID is 4 digits in length, \n copy paste longer values won't work either");
            txtOut.setText("Invalid Client ID");
            idCheck = false; 
            return;
        }     
        else {
        clientID = Integer.parseInt(tempId);            
        idCheck = true; 
        }
    }

    /**
     * Checks time is valid 
     */
    private void checkTime()
    {
        startTime = cboStartTimeRequired.getSelectedItem().toString();
        int start = Integer.parseInt(startTime);
        String endTime = cboEndTimeRequired.getSelectedItem().toString();
        int end = Integer.parseInt(endTime);
        
        if (startTime.equals(endTime) || start > end) {
            timeChecked = false;
            timeMsg();
        }
        else {
            timeChecked = true;
            txtOut.setText("Valid Time");
            
        }
    }

    /**
     * if date is before today date is invalid.
     */
    public void checkDate()
    {
        LocalDate date = datePicker.getDate();
        LocalDate localDate = LocalDate.now();

        try {
            if (date.isBefore(localDate)) {
                dateCheck = false;
                dateMsg();
            }
            else {
                dateCheck = true;
                txtOut.setText("Valid date");
            }
        }
        catch (NullPointerException e) {
            txtOut.setText("Date field can not be blank");
        }

    }

    /**
     * displays the booking number and the room number when a booking is
     * successful Pressing ok will close the book room frame.
     * 
     * @param bookingCounter // booking counter value, loaded from file
     * @param roomNum // room number of booking being set.
     */
    public static void closeWindow(int bookingCounter, int roomNum)
    {
        JOptionPane.showMessageDialog(dialog,
                "The booking reference is : " + bookingCounter + "\n The room number selected is : " + roomNum, null,
                JOptionPane.OK_OPTION, null);
        close();
    }

    /**
     * if date is before today is matched then this message is displayed to the
     * user.
     */
    public static void dateMsg()
    {
        txtOut.setText("selected date can not be before today");
    }

    /**
     * sets the text area to a message that the room was unavailable
     */
    public static void bestRoomFailed()
    {
        JOptionPane.showMessageDialog(dialog,
                "There was a problem, \nWe have no rooms available \nthat best match your minimum \ncriteria at the required time\n: " + startTime +" is not available \nPlease choose a different time slot", null,
                JOptionPane.OK_OPTION, null);
                close();
    }

    /**
     * if time match occurs a message will be displayed to user in text area.
     */
    public static void timeMsg()
    {
        txtOut.setText("The times are incorrect start and finish time are not the same.");
    }

    /**
     * closes the frame
     */
    private static void close()
    {
        dialog.setVisible(false);
    }

    /**
     * initialises dialog
     * 
     * @param parent // parent frame
     * @param dataProcess // class
     */
    public static void addBooking(JFrame parent, DataProcess dataProcess)
    {
        initDialog(parent, dataProcess);
        dialog.setTitle("Add Booking");
        dialog.setVisible(true);
    }

    /**
     * clears the fields of dialog
     * 
     * @param parent // parent frame
     * @param dataProcess // Class
     */
    private static void initDialog(JFrame parent, DataProcess dataProcess)
    {
        if (dialog == null) {
            dialog = new AddBooking();
        }
        dialog.datePicker.clear();
        dialog.txtClientID.setText("");
        dialog.chckbxPrint.setSelected(false);
        dialog.chckbxSmart.setSelected(false);
        dialog.cboStartTimeRequired.setSelectedIndex(0);
        dialog.cboEndTimeRequired.setSelectedIndex(0);
        dialog.cboWorkStations.setSelectedIndex(0);
        dialog.cboBreakout.setSelectedIndex(0);
        AddBooking.txtOut.setText("");
        dialog.okButton.setEnabled(false);
        dialog.dataProcess = dataProcess;
        dialog.setLocationRelativeTo(parent);
    }

    /**
     * Create the dialog.
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public AddBooking()
    {
        setTitle("Make Booking");
        setBounds(100, 100, 450, 550);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        SpringLayout sl_contentPanel = new SpringLayout();
        contentPanel.setLayout(sl_contentPanel);

        JPanel inputPane = new JPanel();
        sl_contentPanel.putConstraint(SpringLayout.NORTH, inputPane, 10, SpringLayout.NORTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.WEST, inputPane, 10, SpringLayout.WEST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.EAST, inputPane, -4, SpringLayout.EAST, contentPanel);
        contentPanel.add(inputPane);

        JPanel outputPane = new JPanel();
        sl_contentPanel.putConstraint(SpringLayout.SOUTH, inputPane, -18, SpringLayout.NORTH, outputPane);
        sl_contentPanel.putConstraint(SpringLayout.NORTH, outputPane, 365, SpringLayout.NORTH, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.WEST, outputPane, 10, SpringLayout.WEST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.EAST, outputPane, -4, SpringLayout.EAST, contentPanel);
        sl_contentPanel.putConstraint(SpringLayout.SOUTH, outputPane, -10, SpringLayout.SOUTH, contentPanel);
        SpringLayout sl_inputPane = new SpringLayout();
        inputPane.setLayout(sl_inputPane);

        JLabel lblWorkstationsRequired = new JLabel("Workstations Required");
        sl_inputPane.putConstraint(SpringLayout.WEST, lblWorkstationsRequired, 10, SpringLayout.WEST, inputPane);
        sl_inputPane.putConstraint(SpringLayout.EAST, lblWorkstationsRequired, 172, SpringLayout.WEST, inputPane);
        inputPane.add(lblWorkstationsRequired);

        cboWorkStations = new JComboBox();
        sl_inputPane.putConstraint(SpringLayout.NORTH, cboWorkStations, -3, SpringLayout.NORTH, lblWorkstationsRequired);
        sl_inputPane.putConstraint(SpringLayout.WEST, cboWorkStations, 116, SpringLayout.EAST, lblWorkstationsRequired);
        sl_inputPane.putConstraint(SpringLayout.EAST, cboWorkStations, -27, SpringLayout.EAST, inputPane);
        //        cboWorkStations.setModel(new DefaultComboBoxModel(new String[] {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}));
        cboWorkStations.setModel(new DefaultComboBoxModel(new String[] { "0", "6", "18", "20" }));
        inputPane.add(cboWorkStations);

        JLabel lblBreakoutChairs = new JLabel("Breakout chairs");
        sl_inputPane.putConstraint(SpringLayout.NORTH, lblBreakoutChairs, 19, SpringLayout.SOUTH, lblWorkstationsRequired);
        sl_inputPane.putConstraint(SpringLayout.WEST, lblBreakoutChairs, 10, SpringLayout.WEST, inputPane);
        inputPane.add(lblBreakoutChairs);

        cboBreakout = new JComboBox();
        sl_inputPane.putConstraint(SpringLayout.EAST, lblBreakoutChairs, -153, SpringLayout.WEST, cboBreakout);
        sl_inputPane.putConstraint(SpringLayout.NORTH, cboBreakout, -3, SpringLayout.NORTH, lblBreakoutChairs);
        sl_inputPane.putConstraint(SpringLayout.WEST, cboBreakout, 0, SpringLayout.WEST, cboWorkStations);
        sl_inputPane.putConstraint(SpringLayout.EAST, cboBreakout, -27, SpringLayout.EAST, inputPane);
        cboBreakout.setModel(new DefaultComboBoxModel(new String[] { "0", "2", "6", "10", "12", "20" }));
        inputPane.add(cboBreakout);

        JLabel lblSmartboard = new JLabel("Smartboard ");
        sl_inputPane.putConstraint(SpringLayout.NORTH, lblSmartboard, 24, SpringLayout.SOUTH, lblBreakoutChairs);
        sl_inputPane.putConstraint(SpringLayout.WEST, lblSmartboard, 0, SpringLayout.WEST, lblWorkstationsRequired);
        inputPane.add(lblSmartboard);

        JLabel lblPrinter = new JLabel("Printer");
        sl_inputPane.putConstraint(SpringLayout.NORTH, lblPrinter, 19, SpringLayout.SOUTH, lblSmartboard);
        sl_inputPane.putConstraint(SpringLayout.WEST, lblPrinter, 0, SpringLayout.WEST, lblWorkstationsRequired);
        sl_inputPane.putConstraint(SpringLayout.EAST, lblPrinter, -352, SpringLayout.EAST, inputPane);
        inputPane.add(lblPrinter);

        JTextPane txtpnPleaseChooseFrom = new JTextPane();
        sl_inputPane.putConstraint(SpringLayout.NORTH, lblWorkstationsRequired, 17, SpringLayout.SOUTH, txtpnPleaseChooseFrom);
        sl_inputPane.putConstraint(SpringLayout.SOUTH, txtpnPleaseChooseFrom, 46, SpringLayout.NORTH, inputPane);
        sl_inputPane.putConstraint(SpringLayout.NORTH, txtpnPleaseChooseFrom, 10, SpringLayout.NORTH, inputPane);
        sl_inputPane.putConstraint(SpringLayout.WEST, txtpnPleaseChooseFrom, 69, SpringLayout.WEST, inputPane);
        sl_inputPane.putConstraint(SpringLayout.EAST, txtpnPleaseChooseFrom, -59, SpringLayout.EAST, inputPane);
        txtpnPleaseChooseFrom.setEditable(false);
        txtpnPleaseChooseFrom.setForeground(Color.BLUE);
        txtpnPleaseChooseFrom.setFont(new Font("Tahoma", Font.PLAIN, 12));
        txtpnPleaseChooseFrom
                .setText("Please choose from the options below\r\nto be shown what rooms we have available");
        inputPane.add(txtpnPleaseChooseFrom);

        JLabel lblDateRequired = new JLabel("Date required");
        sl_inputPane.putConstraint(SpringLayout.NORTH, lblDateRequired, 20, SpringLayout.SOUTH, lblPrinter);
        sl_inputPane.putConstraint(SpringLayout.WEST, lblDateRequired, 10, SpringLayout.WEST, inputPane);
        inputPane.add(lblDateRequired);

        JLabel lblTimeRequired = new JLabel("End Time");
        inputPane.add(lblTimeRequired);

        cboEndTimeRequired = new JComboBox();
        sl_inputPane.putConstraint(SpringLayout.EAST, lblTimeRequired, -11, SpringLayout.WEST, cboEndTimeRequired);
        sl_inputPane.putConstraint(SpringLayout.NORTH, cboEndTimeRequired, -3, SpringLayout.NORTH, lblTimeRequired);
        sl_inputPane.putConstraint(SpringLayout.WEST, cboEndTimeRequired, 0, SpringLayout.WEST, cboWorkStations);
        sl_inputPane.putConstraint(SpringLayout.EAST, cboEndTimeRequired, -27, SpringLayout.EAST, inputPane);
        cboEndTimeRequired.setModel(new DefaultComboBoxModel(
                new String[] { "0900", "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700" }));
        inputPane.add(cboEndTimeRequired);

        datePicker = new DatePicker();
        sl_inputPane.putConstraint(SpringLayout.EAST, lblDateRequired, -144, SpringLayout.WEST, datePicker);
        sl_inputPane.putConstraint(SpringLayout.NORTH, datePicker, -2, SpringLayout.NORTH, lblDateRequired);
        sl_inputPane.putConstraint(SpringLayout.EAST, datePicker, 0, SpringLayout.EAST, cboWorkStations);
        inputPane.add(datePicker);

        cboStartTimeRequired = new JComboBox();
        sl_inputPane.putConstraint(SpringLayout.NORTH, lblTimeRequired, 3, SpringLayout.NORTH, cboStartTimeRequired);
        sl_inputPane.putConstraint(SpringLayout.WEST, lblTimeRequired, 28, SpringLayout.EAST, cboStartTimeRequired);
        sl_inputPane.putConstraint(SpringLayout.EAST, cboStartTimeRequired, -219, SpringLayout.EAST, inputPane);
        cboStartTimeRequired.setModel(new DefaultComboBoxModel(
                new String[] { "0900", "1000", "1100", "1200", "1300", "1400", "1500", "1600", "1700" }));
        inputPane.add(cboStartTimeRequired);

        JLabel lblStartTimeRequired = new JLabel("Start Time");
        sl_inputPane.putConstraint(SpringLayout.NORTH, cboStartTimeRequired, -3, SpringLayout.NORTH, lblStartTimeRequired);
        sl_inputPane.putConstraint(SpringLayout.WEST, cboStartTimeRequired, 33, SpringLayout.EAST, lblStartTimeRequired);
        sl_inputPane.putConstraint(SpringLayout.NORTH, lblStartTimeRequired, 29, SpringLayout.SOUTH, lblDateRequired);
        sl_inputPane.putConstraint(SpringLayout.WEST, lblStartTimeRequired, 0, SpringLayout.WEST, lblWorkstationsRequired);
        sl_inputPane.putConstraint(SpringLayout.EAST, lblStartTimeRequired, -329, SpringLayout.EAST, inputPane);
        inputPane.add(lblStartTimeRequired);

        JLabel lblClientId = new JLabel("Client ID");
        sl_inputPane.putConstraint(SpringLayout.NORTH, lblClientId, 37, SpringLayout.SOUTH, lblStartTimeRequired);
        sl_inputPane.putConstraint(SpringLayout.WEST, lblClientId, 0, SpringLayout.WEST, lblWorkstationsRequired);
        inputPane.add(lblClientId);

        txtClientID = new JTextField();
        sl_inputPane.putConstraint(SpringLayout.NORTH, txtClientID, -3, SpringLayout.NORTH, lblClientId);
        sl_inputPane.putConstraint(SpringLayout.WEST, txtClientID, 288, SpringLayout.WEST, inputPane);
        sl_inputPane.putConstraint(SpringLayout.EAST, txtClientID, 0, SpringLayout.EAST, cboWorkStations);
        txtClientID.setToolTipText("4 digits only");
        txtClientID.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e)
            {
                if (txtClientID.getText().length() > 3) {
                    e.consume();
                }
            }
        });
        inputPane.add(txtClientID);
        txtClientID.setColumns(4);

        chckbxSmart = new JCheckBox("Smart");
        sl_inputPane.putConstraint(SpringLayout.NORTH, chckbxSmart, -4, SpringLayout.NORTH, lblSmartboard);
        sl_inputPane.putConstraint(SpringLayout.WEST, chckbxSmart, 288, SpringLayout.WEST, inputPane);
        sl_inputPane.putConstraint(SpringLayout.EAST, chckbxSmart, 0, SpringLayout.EAST, cboWorkStations);
        inputPane.add(chckbxSmart);

        chckbxPrint = new JCheckBox("Print");
        sl_inputPane.putConstraint(SpringLayout.NORTH, chckbxPrint, -4, SpringLayout.NORTH, lblPrinter);
        sl_inputPane.putConstraint(SpringLayout.WEST, chckbxPrint, 0, SpringLayout.WEST, cboWorkStations);
        sl_inputPane.putConstraint(SpringLayout.EAST, chckbxPrint, -20, SpringLayout.EAST, inputPane);
        inputPane.add(chckbxPrint);
        contentPanel.add(outputPane);
        SpringLayout sl_outputPane = new SpringLayout();
        outputPane.setLayout(sl_outputPane);

        txtOut = new JTextPane();
        sl_outputPane.putConstraint(SpringLayout.NORTH, txtOut, 35, SpringLayout.NORTH, outputPane);
        txtOut.setToolTipText("I find pop ups intrusive, so I use this text box.\r\n");
        txtOut.setForeground(Color.RED);
        sl_outputPane.putConstraint(SpringLayout.WEST, txtOut, 10, SpringLayout.WEST, outputPane);
        sl_outputPane.putConstraint(SpringLayout.SOUTH, txtOut, -10, SpringLayout.SOUTH, outputPane);
        sl_outputPane.putConstraint(SpringLayout.EAST, txtOut, 400, SpringLayout.WEST, outputPane);
        txtOut.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        txtOut.setEditable(false);
        outputPane.add(txtOut);
        
        JLabel lblInformationField = new JLabel("Information Field, displays system messages");
        lblInformationField.setForeground(Color.RED);
        sl_outputPane.putConstraint(SpringLayout.WEST, lblInformationField, 10, SpringLayout.WEST, outputPane);
        sl_outputPane.putConstraint(SpringLayout.SOUTH, lblInformationField, -6, SpringLayout.NORTH, txtOut);
        sl_outputPane.putConstraint(SpringLayout.EAST, lblInformationField, 0, SpringLayout.EAST, txtOut);
        outputPane.add(lblInformationField);
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                okButton = new JButton("Confirm Booking");
                okButton.setEnabled(false);
                okButton.setToolTipText("Check Booking before selecting this");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0)
                    {
                        handleOk();
                    }
                });

                JButton btnCheckBooking = new JButton("Check Booking");
                btnCheckBooking.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0)
                    {
                        handleCheckBooking();
                    }
                });
                buttonPane.add(btnCheckBooking);
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        setVisible(false);
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }
}
