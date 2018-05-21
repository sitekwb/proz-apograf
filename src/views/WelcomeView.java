package views;

import mains.controllers.PersonController;

import javax.swing.*;
import java.awt.*;

public class WelcomeView extends views.Window {

    public WelcomeView(){
        JLabel welcome = new JLabel();
        welcome.setText("Welcome to Apograf attendance checking. Choose action from menu.");
        //setSize(400,400);
        //welcome.setPreferredSize(new Dimension(400,400));
        setLayout(null);
        add(welcome);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setVisible(true);
        pack();
    }
}
