package mains.controllers;

import mains.Model;
import internalframes.profile.ProfileController;
import views.WelcomeView;
import views.Window.MenuButtons;

class StudentController extends PersonController {
    public StudentController(Controller controller, Model mod){
        super(controller, mod);

        try {
            window.getButton(MenuButtons.myStudents, c).setEnabled(false);
            window.getButton(MenuButtons.takeAttendance, c).setEnabled(false);
            window.getButton(MenuButtons.classes, c).setEnabled(false);

        }
        catch(Exception e){
            super.signOut();
        }
    }


}
