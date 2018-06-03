package modules.attendance;

import data.Group;
import data.Student;
import mains.Model;
import mains.controllers.PersonController;
import modules.myclasses.MyClassesController;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class AttendanceController implements ActionListener {
    private AttendanceView view;
    private PersonController cont;
    private Model model;
    private Group group;
    private ArrayList<Student> students;

    public AttendanceView getView() {
        return view;
    }

    public AttendanceController(PersonController controller, Model mod, Group gr){
        view = new AttendanceView();
        cont = controller;
        model = mod;
        group = gr;
        try {
            show();
        }
        catch(SQLException e){
            view.getErrLabel().setText("Error in connection. Try again.");
        }
        view.getClassLabel().setText(group.getName());

        if(model.getUserType()==Model.UserType.student){
            view.getConfirmButton().setEnabled(false);
            view.getTable().setEnabled(false);
        }
        view.getCancelButton().addActionListener(this);
        view.getConfirmButton().addActionListener(this);
        view.getRefreshButton().addActionListener(this);
        view.setVisible(true);

    }

    private void show() throws SQLException {
        students = model.getAttendance(group);
        int i = 0;

        DefaultTableModel tableModel = ((DefaultTableModel)(view.getTable().getModel()));
        tableModel.setRowCount(students.size());

        for (Student student : students) {
            view.getTable().getModel().setValueAt(student.getName(), i, 0);
            view.getTable().getModel().setValueAt(student.getAttendance().getDate(), i, 1);
            view.getTable().getModel().setValueAt(student.getAttendance().isPresent(), i, 2);
            i++;
        }

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Go back")){
            if(model.getUserType()==Model.UserType.student){
                cont.setWelcomeWindow();
            }
            else {
                MyClassesController myClassesController = new MyClassesController(cont, model, true);
                cont.setWindow(myClassesController.getView());
            }
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
        else if(e.getActionCommand().equals("Confirm")){
            int i=0;
            for(Student student: students){
                student.getAttendance().setPresent((boolean)(view.getTable().getValueAt(i,2)));
                i++;
                if (i >= 27) {
                    break;
                }
            }
            try{
                model.updateAttendance(students, group);
                view.getErrLabel().setText("Success! Attendance taken");
            }
            catch(SQLException sqlException){
                view.getErrLabel().setText("Error. Try again.");
            }

        }

    }

}
