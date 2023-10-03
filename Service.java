import java.util.Hashtable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Arrays;

class Service{

    // Here, 0 -> Sunday, 1 -> Monday, ..., 6 -> Saturday
    private boolean[] byWeekday;
    private Hashtable<GregorianCalendar, Boolean> exceptions;

    public Service(){
        this.byWeekday  = new boolean[7];
        this.exceptions = new Hashtable<GregorianCalendar, Boolean>();
    }

    public Service(boolean[] weekdays){
        this.byWeekday  = weekdays;
        this.exceptions = new Hashtable<GregorianCalendar, Boolean>();
    }

    public void addException(GregorianCalendar date, int type){
        exceptions.put(date, type == 1);
    }

    public boolean hasService(GregorianCalendar date){
        Boolean exception = exceptions.get(date);

        if (exception != null) return exception;

        return byWeekday[date.get(Calendar.DAY_OF_WEEK)];
    }

    public String toString(){
        return "{weekdays: " + Arrays.toString(byWeekday) + ", exceptions: " + 
               exceptions + "}";
    }
}
