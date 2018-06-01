package mains.controllers;

import mains.Model;
import views.Window;

class TeacherController  extends PersonController{
    public TeacherController(Controller controller, Model mod){
        super(controller, mod);
        window.getMenuItem(Window.MenuButtons.myTeachers).setEnabled(false);

    }

}
