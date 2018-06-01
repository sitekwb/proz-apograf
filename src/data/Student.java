package data;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends Person{
    private int student_id;
    private Group group;
    private Attendance attendance;
    public Student (ResultSet result) throws SQLException{
        result.next();
        id = result.getInt("id");
        mail = result.getString("mail");
        password = result.getString("pass");
        name = result.getString("Students.name");
        student_id = result.getInt("student_id");
        group = new Group(result.getInt("class"), result.getString("Classes.name"));
    }

    public Student(ResultSet result, boolean attendanceFlag) throws SQLException{
        result.next();
        id = result.getInt("Students.id");
        name = result.getString("Students.name");
        if(attendanceFlag){
            attendance = new Attendance(result.getDate("date"),result.getBoolean("present"));
        }
    }

    public void setAttendance(Attendance at){
        attendance = at;
    }
    public Attendance getAttendance(){
        return attendance;
    }
    public String getStudentID(){return String.valueOf(student_id);}
    public Group getGroup(){ return group;}

}
