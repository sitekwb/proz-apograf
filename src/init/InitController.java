package init;

import exceptions.ConnException;
import mains.controllers.Controller;
import mains.Model;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * Class controlling initial logging actions - having {@link Model} as a parameter for sending orders
 * and {@link InitView} view to control.
 * @author Wojciech Sitek
 * @version 1.0
 * @since 2018-06-03
 * @see ActionListener
 */
public class InitController implements ActionListener {

    //-------------------PARAMETERS------------------------

    /**
     * Reference to main controller, used to pass further actions after this class functioning.
     */
    private Controller cont;
    /**
     * Reference to model, handling SQL connection, enables checking type of user or registering new user.
     */
    private Model model;
    /**
     * View, initialised in constructor, showed as a visible window and handled by ActionListener
     */
    private InitView v;

    //---------------METHODS-----------

    /**
     * Class constructor, copying parameters and adding action listener to {@link InitView#registerButton}
     * and {@link InitView#logButton}.
     * @param controller Parameter passing reference to controller.
     * @param mod Parameter passing reference to model.
     */
    public InitController(Controller controller, Model mod) {
        cont = controller;
        model = mod;
        v = new InitView();
        v.getRegisterButton().addActionListener(this);
        v.getLogButton().addActionListener(this);

    }

    /**
     * After signing out, this method enables {@link Controller} to make {@link InitView} visible again, to sign in again.
     */
    public void startViewAgain(){
        v.setVisible(true);
    }

    /**
     * Method actionPerformed - manages actions made by user in InitView - order to log in or to register.
     *
     * @param e event, that has been performed - either from logButton (order to log in)
     *          or from registerButton (order to register)
     */
    public void actionPerformed (ActionEvent e){
        try {
            String mail = v.getMailField().getText();
            String pass = String.valueOf(v.getPasswordField().getPassword());

            if (e.getSource() == v.getRegisterButton()) {
                try {
                    model.register(mail, pass);
                    v.getErrLabel().setText("Registration success. In this instance you can't register more.");
                    v.getPasswordField().setText("");
                    v.getRegisterButton().setVisible(false);
                } catch (ConnException exception) {
                    model.closeConnection();
                    v.getErrLabel().setText(exception.getErrorMessage() + " Try again.");
                    v.getMailField().setText("");
                    v.getPasswordField().setText("");
                }
            }
            else if (e.getSource() == v.getLogButton()) {
                try {
                    model.logIn(mail, pass);
                    v.setVisible(false);
                    v.getMailField().setText("");
                    v.getPasswordField().setText("");
                    //starts new controller, which envokes next windows and functions of application
                    cont.startController();
                } catch (ConnException exception) {
                    model.closeConnection(); //throws SQLException
                    v.getErrLabel().setText(exception.getErrorMessage() + " Try again.");
                    v.getPasswordField().setText("");
                }
            }
        }
        catch (SQLException sqlException){
            v.getErrLabel().setText("Problems with database connection. Try again");
        }
        catch (Exception wrongCodeException){
            v.getErrLabel().setText("Critical error. Try restarting application");
        }
        //catch(Exception wrongCodeException){
            //v.getErrLabel().setText("Critical error. Try restarting application");
        //}
    }

}
