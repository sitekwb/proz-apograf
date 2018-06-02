package modules.myclasses;

import data.Group;
import mains.controllers.PersonController;
import mains.Model;
import modules.attendance.AttendanceController;
import modules.attendancetaking.AttendanceTakingController;
import views.WelcomeView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyClassesController implements ActionListener {
    private MyClassesView view;
    private boolean viewAttendanceFlag;
    private PersonController cont;
    private Model model;
    private int showingState;
    private boolean endOfGroups;
    private ArrayList<Group> groups;
    public MyClassesView getView() {
        return view;
    }
    public MyClassesController(PersonController controller, Model mod, boolean wantToView){
        viewAttendanceFlag = wantToView;
        model = mod;
        cont= controller;
        view = new MyClassesView();
        showingState = 0;
        endOfGroups = false;
        try {
            show();
        }
        catch(SQLException e){
            view.getErrLabel().setText("Error in connection. Try again.");
        }
        view.getCancelButton().addActionListener(this);
        view.getConfirmButton().addActionListener(this);
        view.getShowNextButton().addActionListener(this);
        view.setVisible(true);
    }
    private void show() throws SQLException {
        groups = model.getGroups(showingState);
        int i = 0;
        try {
            for (Group group: groups) {
                view.getGroup(i).setText((i + 1) + ". " + group.getName());
                view.getGroup(i).setVisible(true);
                i++;
                if (i >= 27) {
                    break;
                }
            }
            if(i<27) {
                endOfGroups = true;
                do{
                    view.getGroup(i).setVisible(false);
                    i++;
                }while(i<27);
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            model.signOut();
            cont.signOut();
        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Go back")){
            cont.setWelcomeWindow();
            view.setVisible(false);
        }
        else if(e.getActionCommand().equals("Confirm")){
            int i;
            for(i=0;i<27;i++){
                if(view.getGroup(i).isSelected()){
                    break;
                }
            }
            if(i==27){
                view.getErrLabel().setText("You didn't select anything. Try again.");
            }
            else if(viewAttendanceFlag){
                AttendanceController attendanceController = new AttendanceController(cont,model, groups.get(i));
                cont.setWindow(attendanceController.getView());
                view.setVisible(false);
            }
            else {//take attendance
                AttendanceTakingController attendanceTakingController = new AttendanceTakingController(cont,model, groups.get(i));
                cont.setWindow(attendanceTakingController.getView());
                view.setVisible(false);
            }

        }
        else{//show next
            if(endOfGroups){
                showingState=0;
                endOfGroups=false;
            }
            else{
                showingState++;
            }
            try {
                show();
                view.getErrLabel().setText("Success! Next values shown.");
            }
            catch(SQLException sqlException){
                view.getErrLabel().setText("Error. Try again.");
            }

        }

    }
}
