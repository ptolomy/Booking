package roomBookingData;

import java.io.Serializable;

import roomBookingData.Client;


/**
 * <h1>Client</h1> 
 * Defines the characteristics of a client
 * 
 * <p>
 * Creates a client Object using variables passed from the User interface
 * 
 * @author Gareth Tucker
 * @version 0.1.3
 * @since 07-06-2019
 */
public class Client implements Comparable<Client>, Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    protected String familyName;
    protected String givenName;
    protected String eMail;
    protected String telNum;

    /**
     * Construct a new Client
     * 
     * @param clientCounter // client counter generated by system
     * @param familyName // required, characters only
     * @param givenName // required, characters only
     * @param eMail // required.
     * @param telNum // not required, digits only
     */
    public Client(int clientCounter, String familyName, String givenName, String eMail, String telNum)
    {
        this.familyName = familyName;
        this.givenName = givenName;
        this.eMail = eMail;
        this.telNum = telNum;
    }

    /**
     * Overloaded Constructor Construct a new client without a telephone number
     * 
     * @param clientCounter // client counter generated by system
     * @param familyName // required, characters only
     * @param givenName // required, characters only
     * @param eMail // required.
     */
    public Client(int clientCounter, String familyName, String givenName, String eMail)
    {
        this(clientCounter, familyName, givenName, eMail, "00000");
    }

    /**
     * validates name is characters, reused for family and given name.
     * 
     * @param name // name of client
     * @return // whether name is a valid string
     */
    public static boolean isValidName(String name)
    {
        if (name == null || "".equals(name)) {
            return false;
        }

        for (int i = 0; i < name.length(); i++) {
            if (!Character.isAlphabetic(name.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * validates email
     * 
     * @param eMail // email of client
     * @return true always no validation
     */
    public static boolean isValidEmail(String eMail)
    {
        return true;
    }

    /**
     * validates the telephone number is digits
     * 
     * @param telNum // telephone number of client
     * @return // true if valid
     */
    public static boolean isValidTelNum(String telNum)
    {
        if (telNum == null || "".equals(telNum)) {
            return true;
        }

        for (int i = 0; i < telNum.length(); i++) {
            if (!Character.isDigit(telNum.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        sb.append("\n[ FamilyName = \"");
        sb.append(familyName);
        sb.append("\" ,\nGiven name = \"");
        sb.append(givenName);
        sb.append("\" ,\nEmail address = \"");
        sb.append(eMail);
        sb.append(" ,\nTelephone Number = ");
        sb.append(telNum);
        sb.append(" ]\n");

        return sb.toString();
    }

    @Override
    public int compareTo(Client arg0)
    {
        return 0;
    }

}
