package mains.controllers;

import internalframes.attendance.AttendanceController;
import internalframes.attendancetaking.AttendanceTakingController;
import mains.Model;
import internalframes.myclasses.MyClassesController;
import internalframes.mystudentsteachers.MyStudentsTeachersController;
import internalframes.profile.ProfileController;
import internalframes.timetable.TimetableController;
import views.WelcomeView;
import views.Window;
import views.Window.MenuButtons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonController implements ActionListener {
    protected Model model;
    protected WelcomeView welcomeView;
    protected Controller cont;
    protected Window window;
    public PersonController(Controller controller, Model mod) {
        cont = controller;
        model = mod;
        welcomeView = new WelcomeView();
        window = welcomeView;
        for (int i = 0; i < Window.buttonArraySize; i++) {
            window.getMenuItem(i).addActionListener(this);
        }

    }


    public void actionPerformed(ActionEvent e){
        int a=5;
        try {
            if (e.getSource() == window.getMenuItem(MenuButtons.classes)){
                MyClassesController myClassesController = new MyClassesController(this, model);
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.exit)){
                int i=JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
                if(i==0) {
                    model.signOut();
                    System.exit(0);
                }
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.myStudents) ||
                    e.getSource() == window.getMenuItem(MenuButtons.myTeachers)   ){
                MyStudentsTeachersController myStudentsTeachersController = new MyStudentsTeachersController(this, model);
            }
            else if(e.getSource()==window.getMenuItem(MenuButtons.signOut)){
                signOut();
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.takeAttendance)){
                AttendanceTakingController attendanceTakingController = new AttendanceTakingController(this,model);
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.timetable)){
                TimetableController timetableController = new TimetableController(this,model);
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.viewAttendance)){
                AttendanceController attendanceController = new AttendanceController(this,model);
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.profile)){
                ProfileController profileController = new ProfileController(this, model);
            }
        }
        catch(Exception exception){
            signOut();
        }
    }


    public Window getWindow(){
        return window;
    }
    public void signOut(){
        model.signOut();
        window.setVisible(false);
        cont.signOut();
    }

}
