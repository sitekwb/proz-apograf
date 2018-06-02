package data;

import mains.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import static data.BuildingType.*;

public class Group {
    int id;
    String name;
    int day;
    Time start_hour, finish_hour;
    public Group(ResultSet result, BuildingType buildingType) throws SQLException {
        if(buildingType== normal){
            result.next();
        }
        id = result.getInt("Classes.id");
        name = result.getString("Classes.name");
        day = result.getInt("Classes.day");
        start_hour = result.getTime("Classes.start_hour");
        finish_hour = result.getTime("Classes.finish_hour");
    }
    public Group(ResultSet result, boolean small) throws SQLException {
        result.next();
        id = result.getInt("id");
        name = result.getString("name");
    }

    public Group(int tid, String tname)throws SQLException{
        if(Model.isHacker(tname)){
            throw new SQLException();
        }
        id = tid;
        name = tname;
    }
    public String getSchedule(){
        String value;
        switch(day){
            case 1:
                value = "Monday";
                break;
            case 2:
                value = "Tuesday";
                break;
            case 3:
                value = "Wednesday";
                break;
            case 4:
                value = "Thursday";
                break;
            case 5:
                value = "Friday";
                break;
            case 6:
                value = "Saturday";
                break;
            case 7: default:
                value = "Sunday";
        }
        value+=" "+start_hour.getHours()+":"+start_hour.getMinutes()+"-"+finish_hour.getHours()+":"+finish_hour.getMinutes();
        return value;
    }
    public String getName(){ return name;   }
    public int getId(){ return id;}
}
