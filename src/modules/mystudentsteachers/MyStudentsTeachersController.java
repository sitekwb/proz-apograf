package modules.mystudentsteachers;

import data.Group;
import data.Person;
import data.Student;
import data.Teacher;
import exceptions.ConnException;
import mains.Model;
import mains.controllers.PersonController;
import modules.attendance.AttendanceView;
import modules.myclasses.MyClassesController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class MyStudentsTeachersController implements ActionListener {
    private MyStudentsTeachersView view;
    private PersonController cont;
    private Model model;
    private ArrayList<Person> people;
    private boolean wantStudentsFlag;
    public MyStudentsTeachersView getView() {
        return view;
    }

    public MyStudentsTeachersController(PersonController controller, Model mod, boolean wantStudents){
        wantStudentsFlag=wantStudents;
        view = new MyStudentsTeachersView();
        cont = controller;
        model = mod;

        DefaultTableModel tableModel = ((DefaultTableModel)(view.getTable().getModel()));
        if(model.getUserType()==Model.UserType.student){
            tableModel.addColumn("Class");
            tableModel.addColumn("Class date");
            view.getConfirmButton().setEnabled(false);
            view.getTable().setEnabled(false);
            view.getTitle1().setText("My Teachers");
        }
        else if(model.getUserType()==Model.UserType.teacher){
            tableModel.addColumn("Student ID");
            tableModel.addColumn("Degree");
            tableModel.addColumn("Class");
            tableModel.addColumn("Class date");
            view.getConfirmButton().setEnabled(false);
            view.getTable().setEnabled(false);
            view.getTitle1().setText("My Students");
        }
        else{//admin
            tableModel.addColumn("Enter new password");
            if(wantStudentsFlag) {
                tableModel.addColumn("Student_id");
                tableModel.addColumn("Degree");
                view.getTitle1().setText("My Students");
            }
            else{
                view.getTitle1().setText("My Teachers");
            }
            tableModel.addColumn("Enter new class (full name of class)");
        }
        try {
            show();
        }
        catch(SQLException e){
            view.getErrLabel().setText("Error in connection. Try again.");
        }


        view.getCancelButton().addActionListener(this);
        view.getConfirmButton().addActionListener(this);
        view.getRefreshButton().addActionListener(this);
        view.setVisible(true);

    }

    private void show() throws SQLException {
        people = model.getPeople(wantStudentsFlag);
        DefaultTableModel tableModel = ((DefaultTableModel)(view.getTable().getModel()));
        tableModel.setRowCount(people.size()+1);
        int i=0;
        for (Person person: people) {
            view.getTable().getModel().setValueAt(person.getName(), i, 0);
            view.getTable().getModel().setValueAt(person.getMail(), i, 1);
            if(model.getUserType()==Model.UserType.student){
                view.getTable().getModel().setValueAt(person.getGroup().getName(), i, 2);
                view.getTable().getModel().setValueAt(person.getGroup().getSchedule(), i, 3);
            }
            else if(model.getUserType()==Model.UserType.teacher){
                view.getTable().getModel().setValueAt(((Student)person).getStudentID(), i, 2);
                view.getTable().getModel().setValueAt(((Student)person).getGenGroup(), i, 3);
                view.getTable().getModel().setValueAt(person.getGroup().getName(), i, 4);
                view.getTable().getModel().setValueAt(person.getGroup().getSchedule(), i, 5);
            }
            else{//admin
                view.getTable().getModel().setValueAt("", i, 2);//new password (empty)
                if(wantStudentsFlag) {
                    view.getTable().getModel().setValueAt(((Student)person).getStudentID(), i, 3);
                    view.getTable().getModel().setValueAt(((Student)person).getGenGroup(), i, 4);
                    view.getTable().getModel().setValueAt("", i, 5);//new class (empty)
                }
                else {
                    view.getTable().getModel().setValueAt("", i, 3);//new class (empty)
                }
            }
            i++;
        }
        for(int j=0; j<view.getTable().getModel().getColumnCount(); j++){
            view.getTable().getModel().setValueAt("", i, j);
        }


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Go back")){
            cont.setWelcomeWindow();
            view.setVisible(false);
        }
        else if(e.getActionCommand().equals("Refresh")){
            try {
                show();
            }
            catch(SQLException sqlException){
                view.getErrLabel().setText("Error in connection. Try again.");
            }
        }
        else if(e.getActionCommand().equals("Confirm") && model.getUserType()==Model.UserType.admin){


            int i=0;
            try{
                for(Person person: people){
                    if(! person.getName().equals((String)view.getTable().getValueAt(i,0))){
                        model.changeName(person, (String)view.getTable().getValueAt(i,0));
                    }
                    if(! person.getMail().equals( view.getTable().getValueAt(i,1)) ){
                        model.changeMail(person, (String)view.getTable().getValueAt(i,1) );
                    }

                    String newPass = (String)view.getTable().getValueAt(i,2);
                    if(!newPass.isEmpty()){
                        model.changePass(person, newPass);
                    }

                    if(wantStudentsFlag){
                        if (person instanceof Student){
                            String newStudentID = (String)view.getTable().getValueAt(i,3);
                            if(! ((Student) person).getStudentID().equals( newStudentID )){
                                model.changeStudentID((Student)person, Integer.parseInt(newStudentID ));
                                ((Student) person).setStudentID(Integer.parseInt(newStudentID));
                            }
                            String newGenGroup = (String)view.getTable().getValueAt(i,4);
                            if(! ((Student) person).getGenGroup().equals( newGenGroup )){
                                model.changeGenGroup((Student)person, newGenGroup );
                                ((Student) person).setGenGroup(newGenGroup);
                            }
                        }
                    }

                    String newGroup = (String)view.getTable().getValueAt(i,(wantStudentsFlag)?5:3);
                    if(!(newGroup==null || newGroup.equals(""))){
                        try {
                            int groupId = model.getGroupIdByName(newGroup);
                            model.addGroup(person, groupId);
                        }
                        catch(ConnException connExc){//group not found => we want to create new
                            if(connExc.getErrorType()!=ConnException.ErrorTypes.notExitsting){
                                throw connExc;
                            }
                            JLabel text = new JLabel("What is the day of new class "+newGroup+"? (1-7, 1=Monday)");
                            text.setFont(new Font("Times New Roman", Font.PLAIN, 40));
                            int day = Integer.parseInt(JOptionPane.showInputDialog(text,text));
                            if( day<1 || day>7){
                                i++;
                                continue;
                            }
                            text.setText("What is the start hour and finish hour of new class "+newGroup+"? (hh:mm-hh:mm)");
                            String time = JOptionPane.showInputDialog(text,text);

                            Group group;
                            try{
                                group = new Group(newGroup, day, time);
                            }
                            catch(Exception exception){
                                i++;
                                continue;
                            }
                            model.createGroup(group);
                            model.addGroup(person, group.getId());
                        }
                    }
                    i++;
                }
                TableModel m = view.getTable().getModel();
                String name = (String)m.getValueAt(i, 0);
                String mail = (String)m.getValueAt(i, 1);
                String pass = (String)m.getValueAt(i, 2);
                String studentId="";
                String genGroup="";
                if(wantStudentsFlag){
                    studentId = (String)m.getValueAt(i, 3);
                    genGroup = (String)m.getValueAt(i, 4);
                }


                if(!(name==null || mail==null || pass==null || name.equals("") || mail.equals("") || pass.equals("") )){//all conditions passed
                    if(wantStudentsFlag){
                        model.addPerson(new Student(mail, pass, name, Integer.parseInt(studentId), genGroup));
                    }
                    else{
                        model.addPerson(new Teacher(mail, pass, name));
                    }
                }

                view.getErrLabel().setText("Success! Values updated");
            }
            catch(SQLException sqlException){
                view.getErrLabel().setText("Invalid query. Try again.");
            }
            catch(ConnException connException){
                view.getErrLabel().setText(connException.getErrorMessage());

            }
            try {
                show();
            }
            catch(SQLException sqlException2){
                view.getErrLabel().setText("Error in connection. Try again.");
            }
        }

    }

}
