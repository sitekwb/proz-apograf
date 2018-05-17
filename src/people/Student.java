package people;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends Person {
    private int student_id;
    private String group;

    public Student (ResultSet result) throws SQLException{
        result.next();
        id = result.getInt("id");
        mail = result.getString("mail");
        password = result.getString("pass");
        name = result.getString("name");
        student_id = result.getInt("student_id");
        group = result.getString("class");
    }

    public Student(boolean adminAccess, ResultSet result) throws SQLException{

    }
    public String getStudentID(){return String.valueOf(student_id);}
    public String getGroup(){ return group;}

}
