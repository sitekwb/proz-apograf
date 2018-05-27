package modules.profile;

import exceptions.ConnException;

import javax.swing.*;
import java.awt.*;

import static exceptions.ConnException.ErrorTypes.err;

public class ProfileView extends views.Window {
    private javax.swing.JButton jButton[];
    private javax.swing.JLabel jLabel[];
    private javax.swing.JTextField jTextField[];
    private javax.swing.JLabel title;
    private static String secretCode = "xxxx";
    public JButton getButton(int i) throws ConnException{
        if(i>4 || i<0) throw new ConnException(err);
        return jButton[i];
    }
    public JTextField getTextField(int i)throws ConnException{
        if(i>4 || i<0) throw new ConnException(err);
        return jTextField[i];
    }
    public JLabel getLabel(int i) throws ConnException{
        if(i>4 || i<0) throw new ConnException(err);
        return jLabel[i];
    }

    public ProfileView(){
        title = new javax.swing.JLabel();

        jLabel = new JLabel [5] ;
        jTextField = new JTextField[5];
        jButton = new JButton[5];

        GridLayout layout = new GridLayout(6,3);
        setLayout(layout);

        title.setText("PROFILE");
        title.setFont(new Font("Comic Sans MS",Font.PLAIN, 100));

        add(title);
        layout.addLayoutComponent("Title",title);
        //empty labels to manage gridLayout
        JLabel lab1 = new JLabel();
        JLabel lab2 = new JLabel();
        add(lab1);
        add(lab2);
        layout.addLayoutComponent("Empty JLabel", lab1);
        layout.addLayoutComponent("Empty JLabel", lab2);
        for(int i=0;i<5;i++){
            jLabel[i]=new JLabel();
            jLabel[i].setFont(new Font("Times New Roman", Font.PLAIN,40));
            add(jLabel[i]);
            layout.addLayoutComponent("Label "+(i+1),jLabel[i]);

            jTextField[i]=new JTextField();
            jTextField[i].setFont(new Font("Times New Roman", Font.PLAIN,40));
            add(jTextField[i]);
            layout.addLayoutComponent("TextField "+(i+1),jTextField[i]);

            jButton[i]=new JButton();
            jButton[i].setFont(new Font("Times New Roman", Font.PLAIN,40));
            add(jButton[i]);
            layout.addLayoutComponent("Button "+(i+1),jButton[i]);
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(400,400);
        setMinimumSize(new Dimension(400,400));
        setMaximumSize(new Dimension((int)screenSize.getWidth(),(int)screenSize.getHeight()));


        setVisible(true);
    }



}
