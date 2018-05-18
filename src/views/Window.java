package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Window extends JFrame {
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenu modulesMenu;
    private javax.swing.JMenu attendanceMenu;
    private javax.swing.JMenuItem classesButton;
    private javax.swing.JMenuItem exitButton;
    private javax.swing.JInternalFrame moduleFrame;
    private javax.swing.JMenuItem myStudentsButton;
    private javax.swing.JMenuItem myTeachersButton;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JLabel refreshInfoLabel;
    private javax.swing.JMenuItem signOutButton;
    private javax.swing.JMenuItem takeAttendanceButton;
    private javax.swing.JMenuItem timetableButton;
    private javax.swing.JMenuItem viewAttendanceButton;
    private javax.swing.JMenuItem profileButton;

    private String secretCode = "abcd";
    public void setInternalFrame(JInternalFrame internalFrame){
        moduleFrame = internalFrame;
    }

    private JMenuItem buttonArray[];


    public Window(){


        moduleFrame = new javax.swing.JInternalFrame();
        refreshInfoLabel = new javax.swing.JLabel();

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
        setBounds(0,0,(int)screenSize.getWidth()*3/4,(int)screenSize.getHeight()*3/4);
        setMinimumSize(new Dimension((int)screenSize.getWidth()*3/4,(int)screenSize.getHeight()*3/4));
        setMaximumSize(new Dimension((int)screenSize.getWidth(),(int)screenSize.getHeight()));

        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                int i=JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?");
                if(i==0)
                    System.exit(0);
            }
        });
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        moduleFrame.setVisible(true);

        javax.swing.GroupLayout moduleFrameLayout = new javax.swing.GroupLayout(moduleFrame.getContentPane());
        moduleFrame.getContentPane().setLayout(moduleFrameLayout);
        moduleFrameLayout.setHorizontalGroup(
                moduleFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 1417, Short.MAX_VALUE)
        );
        moduleFrameLayout.setVerticalGroup(
                moduleFrameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGap(0, 853, Short.MAX_VALUE)
        );

        refreshInfoLabel.setText("Last refresh: ");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(moduleFrame)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(refreshInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(moduleFrame)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(refreshInfoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
        );


        buttonArray= new JMenuItem[]{classesButton, exitButton, myStudentsButton,
                myTeachersButton, signOutButton,
                takeAttendanceButton, timetableButton, viewAttendanceButton, profileButton};
        buttonArraySize = buttonArray.length;
        setVisible(true);
    }


    public enum MenuButtons{classes,exit,myStudents,myTeachers,signOut,takeAttendance,timetable,
        viewAttendance,profile};
    public static int buttonArraySize;

    public JMenuItem getButton(MenuButtons button, String code){
        //if(!code.equals(secretCode) ) throw new Exception();
        return buttonArray[button.ordinal()];
    }
    public JMenuItem getButton(int i, String code){
        //if(!code.equals(secretCode) ) throw new Exception();
        //TODO check i
        return buttonArray[i];
    }
    public JLabel getRefreshInfoLabel(String code){
        //if(!code.equals(secretCode) ) throw new Exception();
        return refreshInfoLabel;
    }
}
