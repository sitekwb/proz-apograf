package data;

import java.sql.ResultSet;
import java.sql.SQLException;

import static data.BuildingType.full;

public abstract class Person {
    protected String mail, pass, name;
    protected Group group;
    protected int id;
    Person (){}

    public Group getGroup(){ return group;}
    public String getMail(){
        return mail;
    }
    public String getName(){
        return name;
    }
    public int getId(){
        return id;
    }
    public void setMail(String m){
        mail = m;
    }
    public void setName(String n){
        name = n;
    }
    public void setPass(String p) { pass = p; }
    public String getPass(){ return pass; }
}
