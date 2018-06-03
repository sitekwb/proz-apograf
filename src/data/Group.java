package data;

import exceptions.ConnException;
import mains.Model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

import static data.BuildingType.*;

public class Group {
    int id;
    String name;
    int day;//from 1 to 7, 1=Monday
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
    public Group(String groupName, int groupDay, String time)throws Exception {
        if(Model.isHacker(groupName) || groupDay<1 || groupDay>7){
            throw new Exception();
        }
        name = groupName;
        day = groupDay;
        setTime(time);
    }
    private void setTime(String time)throws Exception{//hh:mm-hh:mm
        start_hour = new Time(Integer.parseInt(time.substring(0,2)), Integer.parseInt(time.substring(3,5)),0);
        finish_hour = new Time(Integer.parseInt(time.substring(6,8)), Integer.parseInt(time.substring(9,11)),0);
    }
    public Time getStartHour(){ return start_hour; }
    public Time getFinishHour(){ return finish_hour; }
    public int getDayId(){
        return day-1;
    }
    public String getTime(){
        String value = String.format("%02d",start_hour.getHours())+":"+
                String.format("%02d",start_hour.getMinutes())+"-"+
                String.format("%02d",finish_hour.getHours())+":"+
                String.format("%02d",finish_hour.getMinutes());
        return value;
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
    public void setId(int groupId){ id = groupId; }
}
