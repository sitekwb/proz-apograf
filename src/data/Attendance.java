package data;

import java.sql.Date;
/**
 * Class, which objects keep set of data about student's attendance.
 * @author Wojciech Sitek
 * @version 1.0
 * @since 2018-06-03
 * @see Student
 */
public class Attendance {

    /**
     * Date of attendance taking
     */
    private Date date;
    /**
     * Bool indicating if student was present or no
     */
    private boolean present;

    /**
     * Copying constructor
     * @param d date of taking attendance
     * @param isPresent bool giving information, whether student was present or no
     */
    public Attendance(Date d, boolean isPresent){
        date = d;
        present = isPresent;
    }

    /**
     * Get method for date
     * @return {@link data.Attendance#date}, private object of class Attendance
     */
    public Date getDate(){
        return date;
    }
    /**
     * Get method for present
     * @return {@link data.Attendance#present}, private object of class Attendance
     */
    public boolean isPresent() {
        return present;
    }

    /**
     * Method setting value of field "present" as given
     * @param b bool parameter, to which is set class field present
     */
    public void setPresent(boolean b){
        present = b;
    }
}
