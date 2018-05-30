package modules.profile;

import exceptions.ConnException;
import mains.controllers.PersonController;
import mains.Model;
import data.Student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ProfileController implements ActionListener {
    private PersonController cont;
    private Model model;
    private ProfileView view;
    public ProfileController(PersonController controller, Model mod) throws Exception{
        cont = controller;
        model = mod;
        view = new ProfileView();
        //internal frame settings

        setShowingSettings("Password: ***");
        for(int i=0;i<5;i++){
            view.getButton(i).addActionListener(this);
        }

    }
    public ProfileView getView(){
        return view;
    }

    private void setShowingSettings(String errMsg) throws ConnException{
        for(int i=0;i<5;i++){
            view.getButton(i).setVisible(true);
            view.getLabel(i).setVisible(true);
            view.getTextField(i).setVisible(false);

        }
        view.getLabel(0).setText(errMsg);//errLabel
        view.getButton(0).setText("Change password");

        view.getTextField(0).setText("");
        view.getTextField(1).setText("");

        view.getLabel(1).setText("Mail: "+model.getMe().getMail());
        view.getButton(1).setText("Change mail");

        view.getLabel(2).setText("Name: "+model.getMe().getName());

        switch(model.getUserType()){
            case student:
                view.getButton(2).setText("Ask for change of name");

                view.getLabel(3).setText("Student ID: "+((Student)(model.getMe())).getStudentID());
                view.getButton(3).setText("Ask for change of student ID");

                view.getLabel(4).setText("Group: "+((Student)(model.getMe())).getGroup());
                view.getButton(4).setText("Ask for change of group");
                break;
            case teacher:
                view.getButton(2).setText("Ask for change of name");

                view.getLabel(3).setText("Type of user: TEACHER");
                view.getButton(3).setVisible(false);

                view.getLabel(4).setVisible(false);
                view.getButton(4).setVisible(false);
                break;
            case admin: default:
                view.getButton(2).setText("Change name");

                view.getLabel(3).setText("Type of user: ADMIN");
                view.getButton(3).setVisible(false);

                view.getLabel(4).setVisible(false);
                view.getButton(4).setVisible(false);
        }
    }



    private static boolean isInteger(String s){
        try{
            Integer.parseInt(s);
        }
        catch(NumberFormatException e){
            return false;
        }
        return true;
    }

    private static String labels[] = {"password","mail", "name", "student ID", "group"};
    private int typeOfChange;
    public void actionPerformed(ActionEvent e){
        String errMsg="Password: ***";
        try {
            if (e.getActionCommand().equals("Confirm")) {//pressed Confirm
                String val = view.getTextField(0).getText();
                switch (typeOfChange) {
                    case 0://password
                        if (val.equals(view.getTextField(1).getText()) &&
                                !val.isEmpty()) {
                            model.changePass(val);
                            errMsg = "Success! Password changed.";
                        } else {
                            errMsg = "Error! Not the same or empty values.";
                        }
                        break;
                    case 1://mail

                        if (!val.isEmpty()) {
                            model.changeMail(val);
                            errMsg = "Success! Mail changed.";
                        } else {
                            errMsg = "Error! Empty value.";
                        }

                        break;
                    case 2://name

                        if (!val.isEmpty()) {
                            if (model.getUserType() == Model.UserType.admin) {
                                model.changeName(val);
                                errMsg = "Success! Name changed.";
                            } else {
                                model.askForChange(val, "name");
                                errMsg = "Success! Asked for name change.";
                            }
                        } else {
                            errMsg = "Error! Empty value.";
                        }

                        break;
                    case 3://studentID

                        if (!val.isEmpty() && isInteger(val)) {
                            model.askForChange(val, "student_id");
                            errMsg = "Success! Asked for student ID change.";
                        } else {
                            errMsg = "Error! Empty or not integer value.";
                        }

                        break;
                    case 4:
                    default://class
                        if (!val.isEmpty()) {
                            model.askForChange(val, "class");
                            errMsg = "Success! Asked for group change.";
                        } else {
                            errMsg = "Error! Empty value.";
                        }
                        break;
                }
                setShowingSettings(errMsg);
            }
            else if(e.getActionCommand().equals("Cancel")) {
                setShowingSettings("Action cancelled");
            }
            else {//action to change something
                for (int i = 0; i < 5; i++) {
                    if (e.getSource() == view.getButton(i)) {
                        typeOfChange = i;
                        view.getLabel(0).setText("New " + labels[i] + ": ");
                        view.getLabel(0).setVisible(true);

                        view.getTextField(0).setVisible(true);


                        view.getButton(0).setText("Confirm");
                        view.getButton(0).setVisible(true);

                        view.getButton(1).setText("Cancel");
                        view.getButton(1).setVisible(true);

                        for (int j = 2; j < 5; j++) {
                            view.getLabel(j).setVisible(false);
                            view.getTextField(j).setVisible(false);
                            view.getButton(j).setVisible(false);
                        }
                        if (i == 0) {//password
                            view.getLabel(1).setText("New password once again: ");
                            view.getTextField(1).setVisible(true);
                        } else {
                            view.getLabel(1).setVisible(false);
                            view.getTextField(1).setVisible(false);
                        }
                        break;
                    }
                }
            }//actions in state showing
        }
        catch (SQLException sqlException) {
            try {
                setShowingSettings("Failed. Connection Error!");
            }
            catch(ConnException fatalError){
                model.signOut();
                cont.signOut();
            }
        }
        catch (ConnException connException) {
            try {
                setShowingSettings("Failed. " + connException.getErrorMessage());
            }
            catch(ConnException fatalError){
                model.signOut();
                cont.signOut();
            }
        }
        catch (Exception exception) {
            model.signOut();
            cont.signOut();
        }

    }//actionPerformed

}
