package data;

import java.util.Date;
import java.util.GregorianCalendar;

public class Attendance {
    private GregorianCalendar date;
    private boolean present;
    public Attendance(GregorianCalendar d, boolean isPresent){
        date = d;
        present = isPresent;
    }
}
