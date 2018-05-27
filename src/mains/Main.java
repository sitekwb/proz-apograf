package mains;

import mains.controllers.Controller;
import views.Window;

import java.sql.SQLException;

public class Main {
    public static void main(String [] args){
        //Window window = new Window();
        try {
            Controller controller = new Controller(new Model());
        }
        catch(SQLException e){
            System.exit(1);
        }
    }
}
