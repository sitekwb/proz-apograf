package views;

import additional.ConnException;
import mains.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Wojciech Sitek
 * @since 1.0, 05/09/2018
 *
 * InitView is a front end class, part of the package views, visualising the views window
 * (it extends JFrame class),
 * which functions are:
 * <ul>
 *     <li>Asking the user for e-mail and password for logging or registration.</li>
 *     <li>Giving information about login and password to cont (class Controller), implemented in method actionPerformed
 * @link InitView#actionPerformed(ActionEvent e)
 *     </li>
 * </ul>
 *
 */
public class InitView extends JFrame implements ActionListener {
    /**
     * @param cont reference to object of class Controller from MVC model of programming in Java.
     *             Used in sending information
     * @param title JLabel with name of program - "Apograf"
     * @param mailLabel JLabel specifying aim of e-mail textField on the right, with its text "E-mail:"
     * @param passLabel JLabel specifying aim of password textField on the right, with its text "Password:"
     * @param mailField JTextField with space for entering user's email
     * @param passField JTextField with space for entering user's password
     * @param logButton button, submitting logging information to controller
     * @param registerButton button, submitting information about registration to database to controller
     * @param errLabel normally empty label, showing errors of logging or registration
     */

    private Controller cont;
    private JButton logButton;
    private JTextField mailField;
    private JLabel mailLabel;
    private JPasswordField passField;
    private JLabel passLabel;
    private JButton registerButton;
    private JLabel title;
    private JLabel errLabel;

    /**
     * Class constructor
     * @param controller reference to controller got from main class View, used later in method #actionPerformed
     */
    public InitView(Controller controller){
        super("Logging window");
        cont = controller;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int)screenSize.getWidth()/3,(int)screenSize.getHeight()/4,
                (int)screenSize.getWidth()/3,(int)screenSize.getHeight()*5/8);
        setMinimumSize(new Dimension((int)screenSize.getWidth()/3,(int)screenSize.getHeight()*5/8));
        setMaximumSize(new Dimension((int)screenSize.getWidth()/3,(int)screenSize.getHeight()*5/8));

        title = new JLabel();
        mailField = new JTextField();
        mailLabel = new JLabel();
        passLabel = new JLabel();
        registerButton = new JButton();
        logButton = new JButton();
        passField = new JPasswordField();
        errLabel = new JLabel();


        title.setBackground(Color.yellow);
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 100));
        title.setForeground(Color.blue);
        title.setText("APOGRAF");
        title.setHorizontalAlignment(JLabel.CENTER);

        mailLabel.setText("E-mail:");
        mailLabel.setHorizontalAlignment(JLabel.RIGHT);
        mailLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        passLabel.setText("Password:");
        passLabel.setHorizontalAlignment(JLabel.RIGHT);
        passLabel.setFont(new Font("Arial", Font.PLAIN, 40));

        mailField.setFont(new Font("Arial", Font.PLAIN, 40));

        passField.setFont(new Font("Arial", Font.PLAIN, 40));

        registerButton.setText("Register");
        registerButton.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        registerButton.addActionListener(this);

        logButton.setText("Log in");
        logButton.setFont(new Font("Times New Roman", Font.PLAIN, 40));
        logButton.addActionListener(this);

        errLabel.setHorizontalAlignment(JLabel.CENTER);
        errLabel.setFont(new Font("Arial", Font.PLAIN, 40));
        errLabel.setForeground(Color.RED);


        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(passLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(19, 31, Short.MAX_VALUE)
                                                .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(33, 33, 33))
                                        .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(passField)
                                                        .addComponent(mailField, javax.swing.GroupLayout.Alignment.TRAILING)))))
                        .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(errLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(mailLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(mailField, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(85, 85, 85)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(passLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE)
                                        .addComponent(passField))
                                .addGap(67, 67, 67)
                                .addComponent(errLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(registerButton, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(logButton, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );

        
        setVisible(true);
    }

    /**
     *
     * @param e event, that has been performed - either from logButton (order to log in)
     *          or from registerButton (order to register)
     */
    public void actionPerformed (ActionEvent e){
        String mail = mailField.getText();
        String pass = String.valueOf(passField.getPassword());
        if(e.getSource()==registerButton){
            try{
                cont.register(mail , pass);
                errLabel.setText("Registration success. In this instance of program you cannot register more.");
                mailField.setText("");
                passField.setText("");
                registerButton.setVisible(false);
            }
            catch(ConnException exception){
                errLabel.setText("Registration error. Try again.");
                mailField.setText("");
                passField.setText("");
            }
        }
        else if(e.getSource()==logButton){
            try{
                cont.logIn(mail, pass);
                setVisible(false);

            }
            catch(ConnException exception){
                errLabel.setText(exception.getErrorMessage()+" Try again.");
                mailField.setText("");
                passField.setText("");
            }
        }
    }
}
