package mains.controllers;

import modules.attendance.AttendanceController;
import modules.attendancetaking.AttendanceTakingController;
import mains.Model;
import modules.myclasses.MyClassesController;
import modules.mystudentsteachers.MyStudentsTeachersController;
import modules.profile.ProfileController;
import modules.timetable.TimetableController;
import views.WelcomeView;
import views.Window;
import views.Window.MenuButtons;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonController implements ActionListener {
    protected Model model;
    protected Controller cont;
    protected Window window;
    public PersonController(Controller controller, Model mod) {
        cont = controller;
        model = mod;
        WelcomeView welcomeView = new WelcomeView();
        window = welcomeView;
        for (int i = 0; i < Window.buttonArraySize; i++) {
            window.getMenuItem(i).addActionListener(this);
        }

    }


    public void actionPerformed(ActionEvent e){
        try {
            window.setVisible(false);
            if (e.getSource() == window.getMenuItem(MenuButtons.classes)){
                MyClassesController myClassesController = new MyClassesController(this, model);
                window = myClassesController.getView();
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
                window = myStudentsTeachersController.getView();
            }
            else if(e.getSource()==window.getMenuItem(MenuButtons.signOut)){
                signOut();
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.takeAttendance)){
                AttendanceTakingController attendanceTakingController = new AttendanceTakingController(this,model);
                window = attendanceTakingController.getView();
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.timetable)){
                TimetableController timetableController = new TimetableController(this,model);
                window = timetableController.getView();
            }
            else if(e.getSource() == window.getMenuItem(MenuButtons.viewAttendance)){
                AttendanceController attendanceController = new AttendanceController(this,model);
                window = attendanceController.getView();
            }
            else{ //if(e.getSource() == window.getMenuItem(MenuButtons.profile)){
                ProfileController profileController = new ProfileController(this, model);
                window = profileController.getView();
            }
            for (int i = 0; i < Window.buttonArraySize; i++) {
                window.getMenuItem(i).addActionListener(this);
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
