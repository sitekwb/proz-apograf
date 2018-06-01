package data;

import java.sql.Date;
import java.util.GregorianCalendar;

public class Attendance {
    private Date date;
    private boolean present;
    public Attendance(Date d, boolean isPresent){
        date = d;
        present = isPresent;
    }
    public Date getDate(){
        return date;
    }
    public boolean isPresent() {
        return present;
    }
    public void setPresent(boolean b){ present = b; }
}
