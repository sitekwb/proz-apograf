package people;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Student extends Person {
    private int id, student_id;
    private String name, group;

    public Student (ResultSet result) throws SQLException{
        result.next();
        id = result.getInt("id");
        mail = result.getString("mail");
        password = result.getString("pass");
        name = result.getString("name");
        student_id = result.getInt("student_id");
        group = result.getString("class");
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
