package modules.attendancetaking;

import data.Attendance;
import data.Group;
import data.Student;
import mains.Model;
import mains.controllers.PersonController;
import modules.attendance.AttendanceController;
import modules.myclasses.MyClassesController;
import modules.myclasses.MyClassesView;
import views.WelcomeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AttendanceTakingController implements ActionListener {
    private AttendanceTakingView view;
    private PersonController cont;
    private Model model;
    private Group group;
    private int showingState;
    private boolean endOfGroups;
    private ArrayList<Student> students;
    public AttendanceTakingView getView() {
        return view;
    }
    public AttendanceTakingController(PersonController controller, Model mod, Group gr){
        model = mod;
        cont= controller;
        group = gr;
        view = new AttendanceTakingView();
        showingState = 0;
        endOfGroups = false;
        try {
            show();
        }
        catch(SQLException e){
            view.getErrLabel().setText("Error in connection. Try again.");
        }
        view.getClassLabel().setText(group.getName());
        view.getCancelButton().addActionListener(this);
        view.getConfirmButton().addActionListener(this);
        view.getShowNextButton().addActionListener(this);
        view.setVisible(true);
    }
    private void show() throws SQLException {
        students = model.getStudents(showingState, group);
        int i = 0;
        try {
            for (Student student: students) {
                view.getStudent(i).setText((i + 1) + ". " + student.getName());
                view.getStudent(i).setVisible(true);
                view.getStudent(i).setSelected(true);
                i++;
                if (i >= 27) {
                    break;
                }
            }
            if(i<27) {
                endOfGroups = true;
                do{
                    view.getStudent(i).setVisible(false);
                    i++;
                }while(i<27);
            }
        }
        catch(ArrayIndexOutOfBoundsException e){
            model.signOut();
            cont.signOut();
        }
    }
    private boolean isDate(String d){
        //TODO check if date is good with class Group
        int day, month,year;
        try {
            day = Integer.parseInt(d.substring(0, 1));
            month = Integer.parseInt(d.substring(3, 4));
            year = Integer.parseInt(d.substring(6, 9));
            new GregorianCalendar(year,month-1,day);
        }
        catch(Exception e){
            return false;
        }
        if(d.charAt(2)!='-' || d.charAt(5)!='-' || d.length()!=10){
            return false;
        }
        return true;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Go back")){
            MyClassesController myClassesController = new MyClassesController(cont, model,false);
            cont.setWindow(myClassesController.getView());
            view.setVisible(false);
        }
        else if(e.getActionCommand().equals("Confirm")){
            JLabel text = new JLabel("What is the date of classes? (DD-MM-YYYY)");
            text.setFont(new Font("Times New Roman", Font.PLAIN, 40));
            String calendar = JOptionPane.showInputDialog(text,text);
            while(!isDate(calendar)){
                text.setText("Error, so AGAIN! What is the date of classes? (DD-MM-YYYY)");
                calendar = JOptionPane.showInputDialog(text,text);
            }
            int day = Integer.parseInt(calendar.substring(0, 2));
            int month = Integer.parseInt(calendar.substring(3, 5));
            int year = Integer.parseInt(calendar.substring(6, 10));
            Date date = new Date(year-1900,month-1,day);

            int i=0;
            for(Student student: students){
                student.setAttendance(new Attendance(date, view.getStudent(i).isSelected()));
                i++;
                if (i >= 27) {
                    break;
                }
            }
            try{
                model.takeAttendance(students, group);
                view.getErrLabel().setText("Success! Attendance taken");
            }
            catch(SQLException sqlException){
                view.getErrLabel().setText("Error. Try again.");
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
                view.getErrLabel().setText("Next students shown.");
            }
            catch(SQLException sqlException){
                view.getErrLabel().setText("Error! Try again.");
            }
        }

    }
}
