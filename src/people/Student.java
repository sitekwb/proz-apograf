package people;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends Person {
    private int id, student_id;
    private String name, phone, group;

    public Student (ResultSet result) throws SQLException{
        id = result.getInt("id");
        mail = result.getString("mail");
        password = result.getString("password");
        name = result.getString("name");
        student_id = result.getInt("student_id");
        phone = result.getString("phone");
        group = result.getString("group");
    }

    public Student(boolean admin, ResultSet result) throws SQLException{

    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }


}
