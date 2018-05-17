package controllers;

import views.Window;

public class PersonController {
    Window window;
    Controller cont;

    public Window getWindow(){
        return window;
    }
    public void signOut(){
        window.setVisible(false);
        cont.signOut();
    }

}
