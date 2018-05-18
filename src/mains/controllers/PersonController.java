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
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PersonController implements ActionListener {
    protected Model model;
    protected Window window;
    protected Controller cont;
    protected String c = "abcd";

    public PersonController(Controller controller, Model mod){
        cont = controller;
        model = mod;
        window = new Window();
        WelcomeView welcomeView = new WelcomeView();
        window.setInternalFrame(welcomeView);
        try {
            for (int i = 0; i < Window.buttonArraySize; i++) {
                window.getButton(i, c).addActionListener(this);
            }
        }
        catch(Exception e){
            signOut();
        }
    }


    public void actionPerformed(ActionEvent e){
        int a=5;
        try {
            if (e.getSource() == window.getButton(MenuButtons.classes, c)){
                MyClassesController myClassesController = new MyClassesController(this, model);
            }
            else if(e.getSource() == window.getButton(MenuButtons.exit, c)){
                int i=JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
                if(i==0) {
                    model.signOut();
                    System.exit(0);
                }
            }
            else if(e.getSource() == window.getButton(MenuButtons.myStudents, c) ||
                    e.getSource() == window.getButton(MenuButtons.myTeachers, c)   ){
                MyStudentsTeachersController myStudentsTeachersController = new MyStudentsTeachersController(this, model);
            }
            else if(e.getSource()==window.getButton(MenuButtons.signOut,c)){
                signOut();
            }
            else if(e.getSource() == window.getButton(MenuButtons.takeAttendance, c)){
                AttendanceTakingController attendanceTakingController = new AttendanceTakingController(this,model);
            }
            else if(e.getSource() == window.getButton(MenuButtons.timetable, c)){
                TimetableController timetableController = new TimetableController(this,model);
            }
            else if(e.getSource() == window.getButton(MenuButtons.viewAttendance, c)){
                AttendanceController attendanceController = new AttendanceController(this,model);
            }
            else if(e.getSource() == window.getButton(MenuButtons.profile, c)){
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
