import java.sql.ResultSet;
import java.sql.SQLException;

public class Student {
    private int id;
    private String name,surname,mail, phone, country, password;

    public Student (ResultSet result) throws SQLException{
        try {
            id = result.getInt("id");
            name = result.getString("name");
            surname = result.getString("surname");
            mail = result.getString("mail");
            phone = result.getString("phone");
            country = result.getString("country");
            password = result.getString("password");
        }
        catch(SQLException e){
            id = 0;
            name = "";
            surname = "";
            mail = "";
            phone = "";
            country = "";
            password = "";
        }
    }
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }

}
