package modules.attendancetaking;

import mains.Model;
import mains.controllers.PersonController;

import javax.swing.*;

public class AttendanceTakingController extends JInternalFrame {
    private AttendanceTakingView view;

    public AttendanceTakingView getView() {
        return view;
    }
    public AttendanceTakingController(PersonController controller, Model model){

    }
}
