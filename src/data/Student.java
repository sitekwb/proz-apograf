package data;

import exceptions.ConnException;
import mains.Model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import static data.BuildingType.*;
import static exceptions.ConnException.ErrorTypes.hacker;
import static mains.Model.isHacker;

public class Student extends Person{
    private int student_id;
    private String genGroup;
    private Attendance attendance;
    public Student(String mailN, String passN, String nameN, int student_idN, String genGroupN) throws ConnException{
        if(isHacker(mailN) || isHacker(passN) || isHacker(nameN)  || isHacker(genGroupN)){
            throw new ConnException(hacker);
        }
        mail = mailN;
        pass = passN;
        name = nameN;
        student_id = student_idN;
        genGroup = genGroupN;

    }
    public Student (ResultSet result, BuildingType buildingType) throws SQLException{
        if(buildingType==full){
            group = new Group(result, full);
        }
        else{
            result.next();
        }
        id = result.getInt("Students.id");
        mail = result.getString("Students.mail");
        pass = result.getString("Students.pass");
        name = result.getString("Students.name");
        student_id = result.getInt("Students.student_id");
        genGroup = result.getString("Students.gen_class");
    }
    public Student(ResultSet result, boolean attendanceFlag) throws SQLException{
        result.next();
        id = result.getInt("Students.id");
        name = result.getString("Students.name");
        if(attendanceFlag){
            attendance = new Attendance(result.getDate("date"), result.getBoolean("present"));
        }
    }
    public void setStudentID(int studentID){
        student_id = studentID;
    }
    public String getGenGroup(){
        return genGroup;
    }
    public void setGenGroup(String g){ genGroup = g; }


    public void setAttendance(Attendance at){
        attendance = at;
    }
    public Attendance getAttendance(){
        return attendance;
    }
    public String getStudentID(){return String.valueOf(student_id);}

}
