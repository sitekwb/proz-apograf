package mains;

import mains.controllers.Controller;
import views.Window;

public class Main {
    public static void main(String [] args){
        //Window window = new Window();
        Controller controller = new Controller(new Model());
    }
}
