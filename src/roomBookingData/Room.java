package roomBookingData;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1>Room</h1> 
 * Create a room object
 * 
 * <p>
 * Creates a room list which is used to define a room for comparison purposes of
 * individual elements
 * 
 * @author Gareth Tucker
 * @version 0.1.4
 * @since 07-06-2019
 */
public class Room implements Comparable<Room>, Serializable
{

    private static final long serialVersionUID = 1L;
    private int workStat;
    private int breakout;
    private Boolean smart;
    private Boolean print;
    private static List<Room> roomList = new ArrayList<Room>();
    private static List<Room> tempList = new ArrayList<Room>();
    private int roomNum;
    private static boolean roomBooked = false;
    private static int bestMatch = 0;

    static Room room006 = new Room(6, 0, 12, false, false);
    static Room room008 = new Room(8, 18, 10, true, true);
    static Room room011 = new Room(11, 20, 0, true, true);
    static Room room013 = new Room(13, 6, 0, false, true);
    static Room room014 = new Room(14, 18, 2, true, true);
    static Room room015 = new Room(15, 18, 10, true, true);
    static Room room017 = new Room(17, 18, 10, true, true);
    static Room room108 = new Room(108, 0, 20, true, false);
    static Room room120 = new Room(120, 18, 0, true, true);
    static Room room301 = new Room(301, 18, 6, true, true);

    /**
     * room constructor creates all the rooms and adds them to an array of
     * rooms. This is required to do comparisons and allow us to create a temp
     * array later.
     * 
     * @param roomNum // local value of room number.
     * @param workStat // local value of workstations.
     * @param breakout // local value of breakout chairs.
     * @param smart // local value of smartboard.
     * @param print // local value of printer.
     */
    public Room(int roomNum, int workStat, int breakout, boolean smart, boolean print)
    {
        this.roomNum = roomNum;
        this.workStat = workStat;
        this.breakout = breakout;
        this.smart = smart;
        this.print = print;
    }

    /**
     * This method will cycle through the room list. Each cycle
     * through will find the closest match to the number of computers selected.
     * If that room is not booked then the room will be added to the tempList.
     * 
     * Only if a room is not booked and matches within requirements will it be added to the tempList
     * 
     * Iterates through the room list looking for an exact match to workStat2, if match found adds room to tempList and breaks from loop.
     * If no exact match found, Iterates through room list to find a room with workStationCount greater than workStat2, if match found adds to room list.
     * 
     * If no match found, and WorkStat2 = 0 then iterate through room list using BreakOut.
     * if breakOut is exact match to Breakout2 then add room to list and break from loop.
     * if no exact match found, iterates through room list to find room with best match to breakOut.
     * 
     * if no match found for breakout2, iterates through room list to find any room with greater workStationCount than BreakOut2 required.
     * 
     * @param roomNum // room number used in booking
     * @param workStat2 // value set by user in UI.
     * @param breakout2 // value set by user in UI.
     * @param smart2 // Boolean value set by user in UI.
     * @param print2 // Boolean value set by user in UI.
     * @param bookingList // Array list of bookings.
     * @param endTime // end time of booking selected by user.
     * @param startTime // start time of booking selected by user.
     * @param date // date of booking selected by user.
     * @return // returns int value of a room number as the best matching room
     *         from the users entries.
     */
    public static int bestRoom(int roomNum, int workStat2, int breakout2, Boolean smart2, Boolean print2,
            LocalDate date, int startTime, int endTime, List<Booking> bookingList)
    {
        roomList.add(room006);
        roomList.add(room008);
        roomList.add(room011);
        roomList.add(room013);
        roomList.add(room014);
        roomList.add(room015);
        roomList.add(room017);
        roomList.add(room108);
        roomList.add(room120);
        roomList.add(room301);

        int roomTest = 0;                           
        for (Room rm : roomList) {            
            
            roomTest = rm.getRoomNum();
            int workStationCount = rm.getWorkStat();           
            
            // this checks for workstation comparisons.
            if(workStat2 > 0) {
            if (workStat2 ==  workStationCount) {
                checkBooking(date, startTime, endTime, bookingList, roomTest, rm);
                if(isRoomBooked()) {
                    addBookingToList(rm);
                    break;
                    }
                }
                if (workStat2 < workStationCount ) {
                    checkBooking(date, startTime, endTime, bookingList, roomTest, rm);
                    if (isRoomBooked()) {
                        addBookingToList(rm);
                        }
                    }
                }
                    // this checks for breakout chair comparisons
                    int breakOut = rm.getBreakout();           
                    if(getBestMatch() == 0 && workStat2 == 0) {
                        if (breakout2 == breakOut) {           
                            checkBooking(date, startTime, endTime, bookingList, roomTest, rm);
                            if (isRoomBooked()) {
                                addBookingToList(rm);              
                                break;
                                }            
                            }
                            if (breakout2 < breakOut) {                                     
                                checkBooking(date, startTime, endTime, bookingList, roomTest, rm);
                                if (isRoomBooked()) {
                                    addBookingToList(rm);
                                    continue;
                                    }
                                }                                                                                      
                    }//
                        //This if statement exists to provide a room of any kind that has enough chairs to meet the requirements 
                        //of a breakout chair value if no other matches are possible
                        if(workStat2 == 0 && getBestMatch() == 0) {
                            if(breakout2 < workStationCount) {
                                checkBooking(date, startTime, endTime, bookingList, roomTest, rm);//
                                if (isRoomBooked()) {
                                    addBookingToList(rm);
                                    continue;
                                    }
                                }
                            } 
        }  
        if (tempList.isEmpty()) {
            return -1;
        }
        else {
            tempList.clear();
            return getBestMatch();
        }
    }

    private static void addBookingToList(Room rm)
    {
        setRoomBooked(false);
        tempList.add(rm);
        setBestMatch(rm.getRoomNum());
    }
    /**
     * 
     * @param date // date of booking requested by user
     * @param startTime // start time of booking requested by user
     * @param endTime // end time of booking requested by user
     * @param bookingList // Array list of bookings.
     * @param roomTest // room number being checked 
     * @param rm // object being checked.
     */
    private static void checkBooking(LocalDate date, int startTime, int endTime, List<Booking> bookingList,
            int roomTest, Room rm)
    {
        if (!isRoomBooked()) {
            setRoomBooked(Booking.roomBooked(roomTest, date, startTime, endTime, bookingList));
            if (!isRoomBooked()) {
                tempList.remove(rm);
                }
        }
    }

    @Override
    public int compareTo(Room o)
    {
        return 0;
    }

    /**
     * Collection of accessors and modifiers for each variable.
     */
    /**
     * @return the workStat
     */
    public int getWorkStat()
    {
        return workStat;
    }

    /**
     * @param workStat the workStat to set
     */
    public void setWorkStat(int workStat)
    {
        this.workStat = workStat;
    }

    /**
     * @return the breakout
     */
    public int getBreakout()
    {
        return breakout;
    }

    /**
     * @param breakout // the breakout to set
     */
    public void setBreakout(int breakout)
    {
        this.breakout = breakout;
    }

    /**
     * @return the smart
     */
    public Boolean getSmart()
    {
        return smart;
    }

    /**
     * @param smart // the smart to set
     */
    public void setSmart(Boolean smart)
    {
        this.smart = smart;
    }

    /**
     * @return the print
     */
    public Boolean getPrint()
    {
        return print;
    }

    /**
     * @param print // the print to set
     */
    public void setPrint(Boolean print)
    {
        this.print = print;
    }

    /**
     * @return the roomNum
     */
    public int getRoomNum()
    {
        return roomNum;
    }

    /**
     * @param roomNum // the roomNum to set
     */
    public void setRoomNum(int roomNum)
    {
        this.roomNum = roomNum;
    }

    /**
     * @return the roomBooked
     */
    public static boolean isRoomBooked()
    {
        return roomBooked;
    }

    /**
     * @param roomBooked // the roomBooked to set
     */
    public static void setRoomBooked(boolean roomBooked)
    {
        Room.roomBooked = roomBooked;
    }

    /**
     * @return the bestMatch
     */
    public static int getBestMatch()
    {
        return bestMatch;
    }

    /**
     * @param bestMatch // the bestMatch to set
     */
    public static void setBestMatch(int bestMatch)
    {
        Room.bestMatch = bestMatch;
    }

}