package modules.profile;

import exceptions.ConnException;
import mains.controllers.PersonController;
import mains.Model;
import people.Student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ProfileController implements ActionListener {
    private static String code = "xxxx";
    PersonController cont;
    Model model;
    ProfileView view;
    public ProfileController(PersonController controller, Model mod) throws Exception{
        cont = controller;
        model = mod;
        view = new ProfileView();
        //internal frame settings
        setShowingSettings();

    }
    public ProfileView getView(){
        return view;
    }

    private void setShowingSettings() throws ConnException{
        for(int i=0;i<5;i++){
            view.getButton(i).setVisible(true);
            view.getLabel(i).setVisible(true);
            view.getTextField(i).setVisible(false);
            view.getButton(i).addActionListener(this);
        }

        view.getLabel(0).setText("");//errLabel
        view.getButton(0).setText("Change password");

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
        state=State.showing;
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

    private static String labels[] = {"mail","password", "name", "student ID", "group"};
    private int typeOfChange;
    private enum State{showing,changing};
    private State state;
    public void actionPerformed(ActionEvent e){
        try {
            switch (state) {
                case showing: {
                    for (int i = 0; i < 5; i++) {
                        if (e.getSource() == view.getButton(i)) {
                            typeOfChange = i;
                            view.getLabel(0).setText("New " + labels[i] + ": ");
                            view.getTextField(0).setVisible(true);
                            view.getButton(0).setText("Confirm");
                            view.getButton(1).setText("Cancel");
                            for (int j = 2; j < 5; j++) {
                                view.getLabel(i).setVisible(false);
                                view.getTextField(i).setVisible(false);
                                view.getButton(i).setVisible(false);
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
                    state = State.changing;
                }
                break;
                case changing: {
                    if(e.getSource()== view.getButton(0)) {//pressed Confirm
                        String val = view.getLabel(0).getText();
                        try {
                            switch (typeOfChange) {
                                case 0://password
                                    if (val.equals(view.getLabel(1).getText()) &&
                                            !val.isEmpty()) {
                                        model.changePass(val);
                                        view.getLabel(0).setText("Success! Password changed.");
                                    } else {
                                        view.getLabel(0).setText("Error! Not the same or empty values.");
                                    }
                                    break;
                                case 1://mail

                                    if (!val.isEmpty()) {
                                        model.changeMail(val);
                                        view.getLabel(0).setText("Success! Mail changed.");
                                    } else {
                                        view.getLabel(0).setText("Error! Empty value.");
                                    }

                                    break;
                                case 2://name

                                    if (!val.isEmpty()) {
                                        if (model.getUserType() == Model.UserType.admin) {
                                            model.changeName(val);
                                            view.getLabel(0).setText("Success! Name changed.");
                                        } else {
                                            model.askForChange(val, "name");
                                            view.getLabel(0).setText("Success! Asked for name change.");
                                        }
                                    } else {
                                        view.getLabel(0).setText("Error! Empty value.");
                                    }

                                    break;
                                case 3://studentID

                                    if (!val.isEmpty() && isInteger(val)) {
                                        model.askForChange(val, "student_id");
                                        view.getLabel(0).setText("Success! Asked for student ID change.");
                                    } else {
                                        view.getLabel(0).setText("Error! Empty or not integer value.");
                                    }

                                    break;
                                case 4: default://class
                                    if (!val.isEmpty()) {
                                        model.askForChange(val, "class");
                                        view.getLabel(0).setText("Success! Asked for group change.");
                                    } else {
                                        view.getLabel(0).setText("Error! Empty value.");
                                    }
                                    break;
                            }
                        }
                        catch(SQLException sqlException){
                            view.getLabel(0).setText("Failed. Connection Error!");
                        }
                        catch (ConnException connException) {
                            view.getLabel(0).setText("Failed. "+connException.getErrorMessage());
                        }
                    }
                    else{

                    }
                    setShowingSettings();
                }

            }
        }catch (Exception exception) {
            model.signOut();
            cont.signOut();
        }
    }

}
