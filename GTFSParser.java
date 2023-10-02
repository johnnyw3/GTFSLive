import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.LinkedList;
import java.util.Hashtable;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class GTFSParser{
    static final int TRIP_ROUTEID_COL = 0;
    static final int TRIP_SERVICEID_COL = 1;
    static final int TRIP_ID_COL = 2;
    static final int TRIP_HEADSIGN_COL = 3;
    static final int TRIP_BLOCKID_COL = 6;

    static final int CALDATES_SERVICEID_COL = 0;
    static final int CALDATES_DATE_COL = 1;
    static final int CALDATES_TYPE_COL = 2;

    public static void main(String[] argv) throws FileNotFoundException{
        if (argv.length < 1){
            System.out.println("Usage: GTFSParser <GTFS archive dir>");
            System.exit(1);
        }

        File tripFile = new File(argv[0] + "/trips.txt");
        Scanner tripFileContents = new Scanner(tripFile);
        Hashtable<String, Trip> trips = readTrips(tripFileContents);
        tripFileContents.close();

        //System.out.println(trips);

        File calendarFile = new File(argv[0] + "/calendar.txt");
        Scanner calendarFileContents = new Scanner(calendarFile);
        Hashtable<String, Service> services = readCalendar(calendarFileContents);

        File calendarDatesFile = new File(argv[0] + "/calendar_dates.txt");
        Scanner calendarDatesContents = new Scanner(calendarDatesFile);
        readCalendarDates(services, calendarDatesContents);

        System.out.println(services);
    }

    public static void readCalendarDates(Hashtable<String, Service> services, 
                                         Scanner fileContents){
        // skip header line
        fileContents.nextLine();

        while (fileContents.hasNext()){
            String curLine = fileContents.nextLine();
            String[] lineContents = curLine.split(",");
            
            Service curService = services.get(lineContents[CALDATES_SERVICEID_COL]);
            if (curService == null){
                curService = new Service();
                services.put(lineContents[CALDATES_SERVICEID_COL], curService);
            }

            GregorianCalendar curDate = parseDate(lineContents[CALDATES_DATE_COL]);
            int curType = Integer.valueOf(lineContents[CALDATES_TYPE_COL]);
            curService.addException(curDate, curType);
        }
    }

    public static Hashtable<String, Service> readCalendar(Scanner calendarFile){
        Hashtable<String, Service> services = new Hashtable<String, Service>();
        
        // skip header line
        calendarFile.nextLine();

        while (calendarFile.hasNext()){
            String curLine = calendarFile.nextLine();
            String[] lineContents = curLine.split(",");

            boolean[] byWeekday = new boolean[7];
            for (int idx = 1; idx < 8; idx++){
                byWeekday[idx % 7] = lineContents[idx].equals("1");
            }
            services.put(lineContents[0], new Service(byWeekday));
        }

        return services;

    }

    public static Hashtable<String, Trip> readTrips(Scanner tripFile){
        Hashtable<String, Trip> trips = new Hashtable<String, Trip>(1000);
        
        // skip header line
        tripFile.nextLine(); 
        
        while (tripFile.hasNext()){
            String curLine = tripFile.nextLine();
            String[] lineContents = curLine.split(",");

            Trip curTrip = new Trip(lineContents[TRIP_ID_COL], 
                                    lineContents[TRIP_SERVICEID_COL],
                                    lineContents[TRIP_ROUTEID_COL], 
                                    lineContents[TRIP_HEADSIGN_COL],
                                    lineContents[TRIP_BLOCKID_COL]);
            
            trips.put(lineContents[TRIP_ID_COL], curTrip);

       }
       
       return trips;
    }

    public static GregorianCalendar parseDate(String GTFSDate){
        int year = Integer.valueOf(GTFSDate.substring(0, 4));
        int month= Integer.valueOf(GTFSDate.substring(4, 6));
        int day  = Integer.valueOf(GTFSDate.substring(6));

        return new GregorianCalendar(year, month, day);
    }
}    
