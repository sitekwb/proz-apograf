package mains.controllers;

import mains.Model;
import views.Window.MenuButtons;

class StudentController extends PersonController {
    public StudentController(Controller controller, Model mod) {
        super(controller, mod);


        window.getMenuItem(MenuButtons.myStudents).setEnabled(false);
        window.getMenuItem(MenuButtons.takeAttendance).setEnabled(false);


    }


}
