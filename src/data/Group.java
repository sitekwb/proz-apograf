package data;

import mains.Model;

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
    public Group(int groupId)throws SQLException{

    }

    public Group(int tid, String tname)throws SQLException{
        if(Model.isHacker(tname)){
            throw new SQLException();
        }
        id = tid;
        name = tname;
    }

    public String getName(){ return name;   }
    public int getId(){ return id;}
}
