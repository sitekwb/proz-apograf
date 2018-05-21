package modules.myclasses;

import mains.controllers.PersonController;
import mains.Model;

import javax.swing.*;

public class MyClassesController extends JInternalFrame {
    private MyClassesView view;

    public MyClassesView getView() {
        return view;
    }
    public MyClassesController(PersonController controller, Model mod){

    }
}
