package modules.timetable;

import mains.Model;
import mains.controllers.PersonController;

import javax.swing.*;

public class TimetableController extends JInternalFrame {
    private TimetableView view;

    public TimetableView getView() {
        return view;
    }

    public TimetableController(PersonController controller, Model model){

    }
}
