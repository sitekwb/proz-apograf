package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
    protected javax.swing.JMenuBar menuBar;
    protected javax.swing.JMenu modulesMenu;
    protected javax.swing.JMenu attendanceMenu;
    protected javax.swing.JMenuItem classesButton;
    protected javax.swing.JMenuItem exitButton;
    protected javax.swing.JMenuItem myStudentsButton;
    protected javax.swing.JMenuItem myTeachersButton;
    protected javax.swing.JMenu optionsMenu;
    protected javax.swing.JMenuItem signOutButton;
    protected javax.swing.JMenuItem takeAttendanceButton;
    protected javax.swing.JMenuItem timetableButton;
    protected javax.swing.JMenuItem viewAttendanceButton;
    protected javax.swing.JMenuItem profileButton;



    protected JMenuItem buttonArray[];


    public Window(){


        menuBar = new javax.swing.JMenuBar();
        menuBar.setFont(new Font("Times New Roman",Font.PLAIN,30));
        optionsMenu = new javax.swing.JMenu();
        optionsMenu.setFont(new Font("Times New Roman",Font.PLAIN,30));
        signOutButton = new javax.swing.JMenuItem();
        signOutButton.setFont(new Font("Times New Roman",Font.PLAIN,30));
        exitButton = new javax.swing.JMenuItem();
        exitButton.setFont(new Font("Times New Roman",Font.PLAIN,30));
        modulesMenu = new javax.swing.JMenu();
        modulesMenu.setFont(new Font("Times New Roman",Font.PLAIN,30));
        classesButton = new javax.swing.JMenuItem();
        classesButton.setFont(new Font("Times New Roman",Font.PLAIN,30));
        timetableButton = new javax.swing.JMenuItem();
        timetableButton.setFont(new Font("Times New Roman",Font.PLAIN,30));
        attendanceMenu = new javax.swing.JMenu();
        attendanceMenu.setFont(new Font("Times New Roman",Font.PLAIN,30));
        takeAttendanceButton = new javax.swing.JMenuItem();
        takeAttendanceButton.setFont(new Font("Times New Roman",Font.PLAIN,30));
        viewAttendanceButton = new javax.swing.JMenuItem();
        viewAttendanceButton.setFont(new Font("Times New Roman",Font.PLAIN,30));
        myStudentsButton = new javax.swing.JMenuItem();
        myStudentsButton.setFont(new Font("Times New Roman",Font.PLAIN,30));
        myTeachersButton = new javax.swing.JMenuItem();
        myTeachersButton.setFont(new Font("Times New Roman",Font.PLAIN,30));
        profileButton = new javax.swing.JMenuItem();
        profileButton.setFont(new Font("Times New Roman",Font.PLAIN,30));



        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,(int)screenSize.getWidth(),(int)screenSize.getHeight());
        setMinimumSize(new Dimension((int)screenSize.getWidth(),(int)screenSize.getHeight()));


        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
                if(i==0)
                    System.exit(0);
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);




        optionsMenu.setText("Options");


        signOutButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        signOutButton.setText("Sign out");
        optionsMenu.add(signOutButton);

        exitButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitButton.setText("Exit program");
        optionsMenu.add(exitButton);


        profileButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        profileButton.setText("View my profile");
        optionsMenu.add(profileButton);


        menuBar.add(optionsMenu);

        modulesMenu.setText("Modules");

        classesButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        classesButton.setText("List of classes");

        modulesMenu.add(classesButton);

        timetableButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        timetableButton.setText("My Timetable");
        modulesMenu.add(timetableButton);

        attendanceMenu.setText("Attendance");

        takeAttendanceButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        takeAttendanceButton.setText("Take attendance");
        attendanceMenu.add(takeAttendanceButton);

        viewAttendanceButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        viewAttendanceButton.setText("View/change attendance");
        attendanceMenu.add(viewAttendanceButton);
        modulesMenu.add(attendanceMenu);

        myStudentsButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        myStudentsButton.setText("My Students");
        modulesMenu.add(myStudentsButton);

        myTeachersButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        myTeachersButton.setText("My Teachers");
        modulesMenu.add(myTeachersButton);

        menuBar.add(modulesMenu);

        setJMenuBar(menuBar);



        buttonArray= new JMenuItem[]{classesButton, exitButton, myStudentsButton,
                myTeachersButton, signOutButton,
                takeAttendanceButton, timetableButton, viewAttendanceButton, profileButton};
        buttonArraySize = buttonArray.length;
    }


    public enum MenuButtons{classes,exit,myStudents,myTeachers,signOut,takeAttendance,timetable,
        viewAttendance,profile};
    public static int buttonArraySize;

    public JMenuItem getMenuItem(MenuButtons button){
        return buttonArray[button.ordinal()];
    }
    public JMenuItem getMenuItem(int i){
        //TODO check i
        return buttonArray[i];
    }
}
