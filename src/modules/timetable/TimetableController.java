package modules.timetable;

import data.Group;
import data.Person;
import data.Student;
import data.Teacher;
import exceptions.ConnException;
import mains.Model;
import mains.controllers.PersonController;
import modules.mystudentsteachers.MyStudentsTeachersView;
import views.Window;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class TimetableController implements ActionListener {
    private TimetableView view;
    private PersonController cont;
    private Model model;
    private ArrayList<Group> groups;
    public Window getView() {
        return view;
    }

    public TimetableController(PersonController controller, Model mod){
        view = new TimetableView();
        cont = controller;
        model = mod;

        DefaultTableModel tableModel = ((DefaultTableModel)(view.getTable().getModel()));
        view.getConfirmButton().setEnabled(false);
        view.getTable().setEnabled(false);
        try {
            show();
        }
        catch(SQLException e){
            view.getErrLabel().setText("Error in connection. Try again.");
        }


        view.getCancelButton().addActionListener(this);
        view.getRefreshButton().addActionListener(this);
        view.setVisible(true);

    }

    private void show() throws SQLException {
        groups = model.getTimetable();
        DefaultTableModel tableModel = ((DefaultTableModel)(view.getTable().getModel()));
        tableModel.setRowCount(groups.size());
        int row[] = new int[7];
        for (Group group: groups) {
            view.getTable().getModel().setValueAt(group.getTime()+" "+group.getName(), row[group.getDayId()], group.getDayId());
            row[group.getDayId()]++;
        }
        int max=0;
        for(int e: row){
            if(e>max){
                max=e;
            }
        }
        tableModel.setRowCount(max);

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
    }
}
