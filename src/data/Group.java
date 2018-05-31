package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

public class Group {
    int id;
    String name;
    int day;
    Time start_hour, finish_hour;
    public Group(ResultSet result) throws SQLException {
        result.next();
        id = result.getInt("id");
        name = result.getString("name");
        day = result.getInt("day");
        start_hour = result.getTime("start_hour");
        finish_hour = result.getTime("finish_hour");
    }
    public Group(ResultSet result, boolean small) throws SQLException {
        result.next();
        id = result.getInt("id");
        name = result.getString("name");
    }

    public String getName(){ return name;   }
    public int getId(){ return id;}
}
