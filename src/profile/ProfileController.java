package profile;

import additional.ConnException;
import controllers.Controller;
import controllers.PersonController;
import init.InitView;
import mains.Model;
import people.Student;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class ProfileController implements ActionListener {
    private static String code = "xxxx";
    PersonController cont;
    Model model;
    ProfileView v;
    public ProfileController(PersonController controller, Model mod) throws Exception{
        cont = controller;
        model = mod;
        v = new ProfileView();
        //internal frame settings
        setShowingSettings();
        //sending frame to window
        cont.getWindow().setInternalFrame(v);

    }

    private void setShowingSettings() throws Exception{
        for(int i=0;i<4;i++){
            v.getButton(i,code).setVisible(true);
            v.getLabel(i,code).setVisible(true);
            v.getTextField(i,code).setVisible(false);
            v.getButton(i,code).addActionListener(this);
        }

        v.getLabel(0,code).setText("");//errLabel
        v.getButton(0,code).setText("Change password");

        v.getLabel(1,code).setText("Mail: "+model.getMe().getMail());
        v.getButton(1,code).setText("Change mail");

        v.getLabel(2,code).setText("Name: "+model.getMe().getName());

        switch(model.getUserType()){
            case student:
                v.getButton(2,code).setText("Ask for change of name");

                v.getLabel(3,code).setText("Student ID: "+((Student)(model.getMe())).getStudentID());
                v.getButton(3,code).setText("Ask for change of student ID");

                v.getLabel(4,code).setText("Group: "+((Student)(model.getMe())).getGroup());
                v.getButton(4,code).setText("Ask for change of group");
                break;
            case teacher:
                v.getButton(2,code).setText("Ask for change of name");

                v.getLabel(3,code).setText("Type of user: TEACHER");
                v.getButton(3,code).setVisible(false);

                v.getLabel(4,code).setVisible(false);
                v.getButton(4,code).setVisible(false);
                break;
            case admin: default:
                v.getButton(2,code).setText("Change name");

                v.getLabel(3,code).setText("Type of user: ADMIN");
                v.getButton(3,code).setVisible(false);

                v.getLabel(4,code).setVisible(false);
                v.getButton(4,code).setVisible(false);
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
                        if (e.getSource() == v.getButton(i, code)) {
                            typeOfChange = i;
                            v.getLabel(0, code).setText("New " + labels[i] + ": ");
                            v.getTextField(0, code).setVisible(true);
                            v.getButton(0, code).setText("Confirm");
                            v.getButton(1, code).setText("Cancel");
                            for (int j = 2; j < 5; j++) {
                                v.getLabel(i, code).setVisible(false);
                                v.getTextField(i, code).setVisible(false);
                                v.getButton(i, code).setVisible(false);
                            }
                            if (i == 0) {//password
                                v.getLabel(1, code).setText("New password once again: ");
                                v.getTextField(1, code).setVisible(true);
                            } else {
                                v.getLabel(1, code).setVisible(false);
                                v.getTextField(1, code).setVisible(false);
                            }
                            break;
                        }
                    }
                    state = State.changing;
                }
                break;
                case changing: {
                    if(e.getSource()==v.getButton(0,code)) {//pressed Confirm
                        String val = v.getLabel(0, code).getText();
                        try {
                            switch (typeOfChange) {
                                case 0://password
                                    if (val.equals(v.getLabel(1, code).getText()) &&
                                            !val.isEmpty()) {
                                        model.changePass(val);
                                        v.getLabel(0, code).setText("Success! Password changed.");
                                    } else {
                                        v.getLabel(0, code).setText("Error! Not the same or empty values.");
                                    }
                                    break;
                                case 1://mail

                                    if (!val.isEmpty()) {
                                        model.changeMail(val);
                                        v.getLabel(0, code).setText("Success! Mail changed.");
                                    } else {
                                        v.getLabel(0, code).setText("Error! Empty value.");
                                    }

                                    break;
                                case 2://name

                                    if (!val.isEmpty()) {
                                        if (model.getUserType() == Model.UserType.admin) {
                                            model.changeName(val);
                                            v.getLabel(0, code).setText("Success! Name changed.");
                                        } else {
                                            model.askForChange(val, "name");
                                            v.getLabel(0, code).setText("Success! Asked for name change.");
                                        }
                                    } else {
                                        v.getLabel(0, code).setText("Error! Empty value.");
                                    }

                                    break;
                                case 3://studentID

                                    if (!val.isEmpty() && isInteger(val)) {
                                        model.askForChange(val, "student_id");
                                        v.getLabel(0, code).setText("Success! Asked for student ID change.");
                                    } else {
                                        v.getLabel(0, code).setText("Error! Empty or not integer value.");
                                    }

                                    break;
                                case 4: default://class
                                    if (!val.isEmpty()) {
                                        model.askForChange(val, "class");
                                        v.getLabel(0, code).setText("Success! Asked for group change.");
                                    } else {
                                        v.getLabel(0, code).setText("Error! Empty value.");
                                    }
                                    break;
                            }
                        }
                        catch(SQLException sqlException){
                            v.getLabel(0, code).setText("Failed. Connection Error!");
                        }
                        catch (ConnException connException) {
                            v.getLabel(0, code).setText("Failed. "+connException.getErrorMessage());
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
