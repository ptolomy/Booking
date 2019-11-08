package roomBookingData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import roomBookingUI.AddBooking;
import roomBookingUI.Report;


/**
 * <h1>DataProcess</h1> 
 * Processes the data passed from user interface classes to their respective
 * classes
 * 
 * <p>
 * Processes all the data between classes.
 * 
 * @author Gareth Tucker
 * @version 0.1.3
 * @since 07-06-2019
 */
public class DataProcess implements Iterable<Booking>, Serializable
{
    private static final long serialVersionUID = 1L;
    private static Set<Client> clientList;
    private static List<Booking> bookingList;
    private static int bookingCounter = 1;
    private static int clientCounter = 1000;
    private static final String FILE_NAME_CLIENTS = "clientList.bin";
    private static final String FILE_NAME_BOOKING = "bookingList.bin";
    private static boolean readClientFromFile = false;

    /**
     * Initialise storage
     */
    public DataProcess()
    {
        clientList = new HashSet<>();
        bookingList = new ArrayList<>();
        if (!readClientFromFile) {
            readBookingObjects();
            readClientObjects();
            readClientFromFile = true;
        }
        writeBookingObjects();
        writeClientObjects();
    }

    /**
     * Method to add client to client list calls client class.
     * 
     * @param familyName // surname required for adding a client.
     * @param givenName // first name required for adding a client.
     * @param eMail // email required for a client.
     * @param telNum // telephone number for adding a client.
     * @return // returns Client object.
     */
    public Client addClient(String familyName, String givenName, String eMail, String telNum)
    {
        setClientCounter(getClientCounter() + 1);

        Client c = new Client(getClientCounter(), familyName, givenName, eMail, telNum);
        clientList.add(c);
        writeClientObjects();
        return c;
    }

    /**
     * Method to add client to client list calls client class.
     * 
     * @param familyName // surname required for adding a client.
     * @param givenName // first name required for adding a client.
     * @param eMail // email required for a client.
     * @return // returns Client object.
     */
    public Client addClient(String familyName, String givenName, String eMail)
    {
        setClientCounter(getClientCounter() + 1);

        Client c = new Client(getClientCounter(), familyName, givenName, eMail);
        clientList.add(c);
        writeClientObjects();
        return c;
    }

    /**
     * Method to create add new Booking first does a method call to check for
     * best matching room if booking possible adds booking to list.
     * 
     * @param clientID // attaches to booking when creating the object.
     * @param date // date of booking required for booking.
     * @param startTime // required and represents the start time of the booking.
     * @param endTime // required and represents the end time of the booking.
     * @param workStat // number of workstations required.
     * @param breakout // number of breakout chairs required.
     * @param smart // smart board required boolean.
     * @param print // printer required boolean.
     * @return // returns object or null if no booking.
     */
    public Booking addBooking(int clientID, LocalDate date, int startTime, int endTime, int workStat, int breakout,
            Boolean smart, Boolean print)
    {
        boolean bestRoom = false;
        int roomNum = 0;

        if (!bestRoom) {
            Room.setRoomBooked(false);
            Room.setBestMatch(0);
            roomNum = Room.bestRoom(roomNum, workStat, breakout, smart, print, date, startTime, endTime, bookingList);
            bestRoom = true;
            if (roomNum <= 0) {
                bestRoom = false;
            }
        }
        if (bestRoom) {
            Booking b = new Booking(getBookingCounter(), clientID, roomNum, date, startTime, endTime, workStat,
                    breakout, smart, print);

            bookingList.add(b);

            AddBooking.closeWindow(getBookingCounter(), roomNum);

            setBookingCounter(getBookingCounter() + 1);
            bestRoom = false;
            writeBookingObjects();
            return b;
        }
        else {
            bestRoom = false;
            AddBooking.bestRoomFailed();
            return null;
        }
    }

    /**
     * calls the report initialise dialog and passes through the current booking
     * and client lists
     * 
     * @param roomFrame // main window
     * @param dataProcess // class
     */
    public static void createReport(JFrame roomFrame, DataProcess dataProcess)
    {
        Report.initialise(roomFrame, dataProcess, clientList, bookingList);
    }

    /**
     * Iterates clients
     * 
     * @return // iterator
     */
    public Iterator<Booking> iterator()
    {
        return bookingList.iterator();
    }

    /**
     * Write the Client objects to the specified file.
     * 
     * @see #FILE_NAME_CLIENTS
     */
    private static void writeClientObjects()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILE_NAME_CLIENTS)))) {
            oos.writeInt(getClientCounter());
            oos.writeObject(clientList);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Write the Booking objects to the specified file.
     * 
     * @see #FILE_NAME_BOOKING
     */
    static void writeBookingObjects()
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(FILE_NAME_BOOKING)))) {
            oos.writeInt(getBookingCounter());
            oos.writeObject(bookingList);
            oos.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Read Client objects back from the specified file.
     * 
     * @see #FILE_NAME_CLIENTS
     */
    @SuppressWarnings("unchecked")
    private static void readClientObjects()
    {
        File f = new File(FILE_NAME_CLIENTS);

        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                setClientCounter(ois.readInt());
                clientList = (Set<Client>) ois.readObject();
                ois.close();
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.printf("Unable to locate file: %s\n", f);
        }
    }

    /**
     * Read Booking objects back from the specified file.
     * 
     * @see #FILE_NAME_BOOKING
     */
    @SuppressWarnings("unchecked")
    private static void readBookingObjects()
    {
        File f = new File(FILE_NAME_BOOKING);

        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                setBookingCounter(ois.readInt());
                bookingList = (List<Booking>) ois.readObject();
                ois.close();
            }
            catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        else {
            System.err.printf("Unable to locate file: %s\n", f);
        }
    }

    /**
     * @return the bookingCounter
     */
    public static int getBookingCounter()
    {
        return bookingCounter;
    }

    /**
     * @param bookingCounter the bookingCounter to set
     */
    public static void setBookingCounter(int bookingCounter)
    {
        DataProcess.bookingCounter = bookingCounter;
    }

    /**
     * @return the clientCounter
     */
    public static int getClientCounter()
    {
        return clientCounter;
    }

    /**
     * @param clientCounter the clientCounter to set
     */
    public static void setClientCounter(int clientCounter)
    {
        DataProcess.clientCounter = clientCounter;
    }

    public void cancelBooking(int clientID)
    {
        Booking.cancelBooking(clientID, bookingList);
    }

}
