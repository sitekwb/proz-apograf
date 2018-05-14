package controllers;

import additional.ConnException;
import mains.Model;
import views.InitView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class InitController implements ActionListener {
    private static String code = "abcd";
    Controller cont;
    Model model;
    InitView v;
    public InitController(Controller controller, Model mod){
        cont = controller;
        model = mod;
        v= new InitView();
        try {
            v.getRegisterButton(code).addActionListener(this);
            v.getLogButton(code).addActionListener(this);
        }
        catch (Exception wrongCodeException){
            v.getErrLabel().setText("Critical error. Try restarting application");
        }
    }

    /**
     * Method actionPerformed - manages actions made by user in InitView - order to log in or to register
     *
     * @param e event, that has been performed - either from logButton (order to log in)
     *          or from registerButton (order to register)
     */
    public void actionPerformed (ActionEvent e){
        try {
            String mail = v.getMailField(code).getText();
            String pass = String.valueOf(v.getPasswordField(code).getPassword());

            if (e.getSource() == v.getRegisterButton(code)) {
                try {
                    model.register(mail, pass);
                    v.getErrLabel().setText("Registration success. Wait for admin approval. In this instance of program you cannot register more.");
                    v.getMailField(code).setText("");
                    v.getPasswordField(code).setText("");
                    v.getRegisterButton(code).setVisible(false);
                } catch (ConnException exception) {
                    model.closeConnection();
                    v.getErrLabel().setText(exception.getErrorMessage() + " Try again.");
                    v.getMailField(code).setText("");
                    v.getPasswordField(code).setText("");
                }
            }
            else if (e.getSource() == v.getLogButton(code)) {
                try {
                    model.logIn(mail, pass);
                    v.setVisible(false);
                    //starts new controller, which envokes next windows and functions of application
                    cont.startController(model.getUserType());
                } catch (ConnException exception) {
                    model.closeConnection(); //throws SQLException
                    v.getErrLabel().setText(exception.getErrorMessage() + " Try again.");
                    v.getMailField(code).setText("");
                    v.getPasswordField(code).setText("");
                }
            }
        }
        catch (SQLException sqlException){
            v.getErrLabel().setText("Problems with database connection. Try again");
        }
        //catch(Exception wrongCodeException){
            //v.getErrLabel().setText("Critical error. Try restarting application");
        //}
    }

}
