package roomBookingUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import roomBookingData.Client;
import roomBookingData.DataProcess;

import javax.swing.SpringLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;


/**
 * <h1>AddClient</h1> 
 * User Interface for adding a client
 * 
 * <p>
 * lblClientCounter value is set from dataProcess and read from file.
 * 
 * @author Gareth Tucker
 * @version 0.1.3
 * @since 07-06-2019
 */
@SuppressWarnings("serial")
public class AddClient extends JDialog
{

    private static AddClient dialog = null;

    private DataProcess dataProcess;

    //Windows Builder Fields
    private final JPanel contentPanel = new JPanel();
    private JPanel labelPane;
    private JTextField txtGivenName;
    private JLabel lblEnterClientGiven;
    private JTextField txtFamilyName;
    private JLabel lblEnterClientFamily;
    private JTextField txtEmail;
    private JLabel lblEnterClientEmail;
    private JTextField txtTelNum;
    private JLabel lblEnterClientTelephone;
    private JLabel lblClientCounter;

    /**
     * initialises the dialog window
     * 
     * @param parent // parent window
     * @param dataProcess // class
     */
    public static void addClient(JFrame parent, DataProcess dataProcess)
    {
        initDialog(parent, dataProcess);
        dialog.setTitle("Add Client");
        dialog.setVisible(true);
    }

    /**
     * clears the fields of dialog
     * 
     * @param parent // parent window
     * @param dataProcess // class
     */
    private static void initDialog(JFrame parent, DataProcess dataProcess)
    {
        if (dialog == null) {
            dialog = new AddClient();
        }
        dialog.dataProcess = dataProcess;
        dialog.txtFamilyName.setText("");
        dialog.txtGivenName.setText("");
        dialog.txtEmail.setText("");
        dialog.txtTelNum.setText("");
        dialog.lblClientCounter.setText("ClientCounter " + DataProcess.getClientCounter());
        dialog.setLocationRelativeTo(parent);
    }

    /**
     * when ok is selected this method handles validation calls and then calls
     * the client constructor
     */
    private void handleOk()
    {
        String familyName = txtFamilyName.getText().trim();
        String givenName = txtGivenName.getText().trim();
        String eMail = txtEmail.getText().trim();
        String telNum = txtTelNum.getText().trim();

        if (!Client.isValidName(givenName)) {
            JOptionPane.showMessageDialog(dialog, "Invalid given name", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtGivenName.requestFocusInWindow();
            return;
        }

        if (!Client.isValidName(familyName)) {
            JOptionPane.showMessageDialog(dialog, "Invalid family name", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtFamilyName.requestFocusInWindow();
            return;
        }

        if (!Client.isValidEmail(eMail)) {
            JOptionPane.showMessageDialog(dialog, "Invalid Email Address", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocusInWindow();
            return;
        }

        if (!Client.isValidTelNum(telNum)) {
            JOptionPane.showMessageDialog(dialog, "Invalid Telephone Number", "Input Error", JOptionPane.ERROR_MESSAGE);
            txtTelNum.requestFocusInWindow();
            return;
        }       
        dataProcess.addClient(familyName, givenName, eMail, telNum);
        displayClient();

        dialog.setVisible(false);
    }
    
    /**
     * displays client number
     */
    private void displayClient()
    {
        int clientNumber = DataProcess.getClientCounter();
        JOptionPane.showMessageDialog(dialog, "Client number is " + clientNumber);
    }

    /**
     * Create the dialog.
     */
    public AddClient()
    {
        setFont(new Font("Dialog", Font.PLAIN, 11));
        setBounds(100, 100, 550, 400);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        SpringLayout sl_contentPanel = new SpringLayout();
        contentPanel.setLayout(sl_contentPanel);
        {
            labelPane = new JPanel();
            sl_contentPanel.putConstraint(SpringLayout.NORTH, labelPane, 10, SpringLayout.NORTH, contentPanel);
            sl_contentPanel.putConstraint(SpringLayout.WEST, labelPane, 10, SpringLayout.WEST, contentPanel);
            sl_contentPanel.putConstraint(SpringLayout.SOUTH, labelPane, -6, SpringLayout.SOUTH, contentPanel);
            sl_contentPanel.putConstraint(SpringLayout.EAST, labelPane, 212, SpringLayout.WEST, contentPanel);
            contentPanel.add(labelPane);
        }
        {
            JPanel panel = new JPanel();
            sl_contentPanel.putConstraint(SpringLayout.NORTH, panel, 10, SpringLayout.NORTH, contentPanel);
            sl_contentPanel.putConstraint(SpringLayout.WEST, panel, 29, SpringLayout.EAST, labelPane);
            sl_contentPanel.putConstraint(SpringLayout.SOUTH, panel, -6, SpringLayout.SOUTH, contentPanel);
            sl_contentPanel.putConstraint(SpringLayout.EAST, panel, -10, SpringLayout.EAST, contentPanel);
            SpringLayout sl_labelPane = new SpringLayout();
            labelPane.setLayout(sl_labelPane);
            {
                lblEnterClientGiven = new JLabel("Enter Client Given Name");
                lblEnterClientGiven.setFont(new Font("Dialog", Font.BOLD, 11));
                sl_labelPane.putConstraint(SpringLayout.NORTH, lblEnterClientGiven, 24, SpringLayout.NORTH, labelPane);
                sl_labelPane.putConstraint(SpringLayout.WEST, lblEnterClientGiven, 10, SpringLayout.WEST, labelPane);
                labelPane.add(lblEnterClientGiven);
            }
            {
                lblEnterClientFamily = new JLabel("Enter Client Family Name");
                lblEnterClientFamily.setFont(new Font("Dialog", Font.BOLD, 11));
                sl_labelPane.putConstraint(SpringLayout.NORTH, lblEnterClientFamily, 26, SpringLayout.SOUTH,
                        lblEnterClientGiven);
                sl_labelPane.putConstraint(SpringLayout.WEST, lblEnterClientFamily, 0, SpringLayout.WEST,
                        lblEnterClientGiven);
                labelPane.add(lblEnterClientFamily);
            }
            {
                lblEnterClientEmail = new JLabel("Enter Client Email");
                lblEnterClientEmail.setFont(new Font("Dialog", Font.BOLD, 11));
                sl_labelPane.putConstraint(SpringLayout.NORTH, lblEnterClientEmail, 32, SpringLayout.SOUTH,
                        lblEnterClientFamily);
                sl_labelPane.putConstraint(SpringLayout.WEST, lblEnterClientEmail, 0, SpringLayout.WEST,
                        lblEnterClientGiven);
                sl_labelPane.putConstraint(SpringLayout.EAST, lblEnterClientEmail, 0, SpringLayout.EAST,
                        lblEnterClientGiven);
                labelPane.add(lblEnterClientEmail);
            }
            {
                lblEnterClientTelephone = new JLabel("Enter Telephone Number");
                sl_labelPane.putConstraint(SpringLayout.EAST, lblEnterClientTelephone, 168, SpringLayout.WEST,
                        labelPane);
                lblEnterClientTelephone.setFont(new Font("Dialog", Font.BOLD, 11));
                sl_labelPane.putConstraint(SpringLayout.NORTH, lblEnterClientTelephone, 24, SpringLayout.SOUTH,
                        lblEnterClientEmail);
                sl_labelPane.putConstraint(SpringLayout.WEST, lblEnterClientTelephone, 0, SpringLayout.WEST,
                        lblEnterClientGiven);
                sl_labelPane.putConstraint(SpringLayout.SOUTH, lblEnterClientTelephone, 54, SpringLayout.SOUTH,
                        lblEnterClientEmail);
                labelPane.add(lblEnterClientTelephone);
            }

            lblClientCounter = new JLabel("Client Counter : ");
            sl_labelPane.putConstraint(SpringLayout.NORTH, lblClientCounter, 37, SpringLayout.SOUTH,
                    lblEnterClientTelephone);
            sl_labelPane.putConstraint(SpringLayout.WEST, lblClientCounter, 0, SpringLayout.WEST, lblEnterClientGiven);
            sl_labelPane.putConstraint(SpringLayout.EAST, lblClientCounter, 0, SpringLayout.EAST, lblEnterClientGiven);
            labelPane.add(lblClientCounter);
            contentPanel.add(panel);
            SpringLayout sl_panel = new SpringLayout();
            panel.setLayout(sl_panel);
            {
                txtGivenName = new JTextField();
                sl_panel.putConstraint(SpringLayout.WEST, txtGivenName, 10, SpringLayout.WEST, panel);
                panel.add(txtGivenName);
                txtGivenName.setColumns(10);
            }
            {
                txtFamilyName = new JTextField();
                sl_panel.putConstraint(SpringLayout.NORTH, txtFamilyName, 60, SpringLayout.NORTH, panel);
                sl_panel.putConstraint(SpringLayout.WEST, txtFamilyName, 10, SpringLayout.WEST, panel);
                sl_panel.putConstraint(SpringLayout.EAST, txtFamilyName, -5, SpringLayout.EAST, panel);
                sl_panel.putConstraint(SpringLayout.SOUTH, txtGivenName, -19, SpringLayout.NORTH, txtFamilyName);
                sl_panel.putConstraint(SpringLayout.EAST, txtGivenName, 0, SpringLayout.EAST, txtFamilyName);
                panel.add(txtFamilyName);
                txtFamilyName.setColumns(10);
            }
            {
                txtEmail = new JTextField();
                sl_panel.putConstraint(SpringLayout.NORTH, txtEmail, 25, SpringLayout.SOUTH, txtFamilyName);
                sl_panel.putConstraint(SpringLayout.WEST, txtEmail, 10, SpringLayout.WEST, panel);
                sl_panel.putConstraint(SpringLayout.EAST, txtEmail, -5, SpringLayout.EAST, panel);
                panel.add(txtEmail);
                txtEmail.setColumns(10);
            }
            {
                txtTelNum = new JTextField();
                sl_panel.putConstraint(SpringLayout.NORTH, txtTelNum, 26, SpringLayout.SOUTH, txtEmail);
                sl_panel.putConstraint(SpringLayout.WEST, txtTelNum, 10, SpringLayout.WEST, panel);
                sl_panel.putConstraint(SpringLayout.EAST, txtTelNum, -5, SpringLayout.EAST, panel);
                panel.add(txtTelNum);
                txtTelNum.setColumns(10);
            }
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e)
                    {
                        handleOk();
                    }
                });
                okButton.setActionCommand("OK");
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0)
                    {
                        dialog.setVisible(false);
                    }
                });
                cancelButton.setActionCommand("Cancel");
                buttonPane.add(cancelButton);
            }
        }
    }
}
