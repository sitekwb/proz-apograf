package data;

import exceptions.ConnException;

import java.sql.ResultSet;
import java.sql.SQLException;

import static data.BuildingType.full;
import static data.BuildingType.normal;
import static exceptions.ConnException.ErrorTypes.hacker;
import static mains.Model.isHacker;

public class Teacher extends Person {
    private boolean admin;
    public Teacher(String mailN, String passN, String nameN) throws ConnException {
        if(isHacker(mailN) || isHacker(passN) || isHacker(nameN)){
            throw new ConnException(hacker);
        }
        mail = mailN;
        pass = passN;
        name = nameN;
        admin = false;

    }
    public Teacher (ResultSet result, BuildingType buildingType) throws SQLException {
        if(buildingType==full){
            group = new Group(result, full);
        }
        else{
            result.next();
        }
        id = result.getInt("id");
        mail = result.getString("mail");
        pass = result.getString("pass");
        name = result.getString("name");
        admin = result.getBoolean("admin");

    }
    public boolean isAdmin(){return admin;}
}
