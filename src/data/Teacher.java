package data;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Teacher extends Person {
    private boolean admin;
    public Teacher (ResultSet result) throws SQLException {
        result.next();
        id = result.getInt("id");
        mail = result.getString("mail");
        password = result.getString("pass");
        name = result.getString("name");
        admin = result.getBoolean("admin");
    }
    public boolean isAdmin(){return admin;}
    public Group getGroup(){ return null;}
}
