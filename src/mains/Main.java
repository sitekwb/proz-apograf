package mains;

import controllers.Controller;
import views.Window;

public class Main {
    public static void main(String [] args){
        Controller controller = new Controller(new Model());
    }
}
