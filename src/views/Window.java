package views;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private javax.swing.JMenuBar MenuBar;
    private javax.swing.JMenu ModulesMenu;
    private javax.swing.JMenu attendanceMenu;
    private javax.swing.JMenuItem chPassButton;
    private javax.swing.JMenuItem classesButton;
    private javax.swing.JMenuItem exitButton;
    private javax.swing.JInternalFrame moduleFrame;
    private javax.swing.JMenuItem myStudentsButton;
    private javax.swing.JMenuItem myTeachersButton;
    private javax.swing.JMenu optionsMenu;
    private javax.swing.JMenu profileMenu;
    private javax.swing.JMenuItem refreshButton;
    private javax.swing.JLabel refreshInfoLabel;
    private javax.swing.JMenuItem signOutButton;
    private javax.swing.JMenuItem takeAttendanceButton;
    private javax.swing.JMenuItem timetableButton;
    private javax.swing.JMenuItem viewAttendanceButton;
    private javax.swing.JMenuItem viewProfileButton;

    public void setInternalFrame(JInternalFrame internalFrame){
        moduleFrame = internalFrame;
    }

    public Window(){

        moduleFrame = new javax.swing.JInternalFrame();
        refreshInfoLabel = new javax.swing.JLabel();
        MenuBar = new javax.swing.JMenuBar();
        optionsMenu = new javax.swing.JMenu();
        refreshButton = new javax.swing.JMenuItem();
        signOutButton = new javax.swing.JMenuItem();
        exitButton = new javax.swing.JMenuItem();
        ModulesMenu = new javax.swing.JMenu();
        classesButton = new javax.swing.JMenuItem();
        timetableButton = new javax.swing.JMenuItem();
        attendanceMenu = new javax.swing.JMenu();
        takeAttendanceButton = new javax.swing.JMenuItem();
        viewAttendanceButton = new javax.swing.JMenuItem();
        myStudentsButton = new javax.swing.JMenuItem();
        myTeachersButton = new javax.swing.JMenuItem();
        profileMenu = new javax.swing.JMenu();
        viewProfileButton = new javax.swing.JMenuItem();
        chPassButton = new javax.swing.JMenuItem();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0,0,(int)screenSize.getWidth()*3/4,(int)screenSize.getHeight()*3/4);
        setMinimumSize(new Dimension((int)screenSize.getWidth()*3/4,(int)screenSize.getHeight()*3/4));
        setMaximumSize(new Dimension((int)screenSize.getWidth()*3/4,(int)screenSize.getHeight()*3/4));


        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        refreshButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        refreshButton.setText("REFRESH");
        optionsMenu.add(refreshButton);

        signOutButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        signOutButton.setText("Sign out");
        optionsMenu.add(signOutButton);

        exitButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitButton.setText("Exit program");
        optionsMenu.add(exitButton);

        MenuBar.add(optionsMenu);

        ModulesMenu.setText("Modules");

        classesButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        classesButton.setText("List of classes");
        classesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //TODO classesButtonActionPerformed(evt);
            }
        });
        ModulesMenu.add(classesButton);

        timetableButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        timetableButton.setText("My Timetable");
        ModulesMenu.add(timetableButton);

        attendanceMenu.setText("Attendance");

        takeAttendanceButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        takeAttendanceButton.setText("Take attendance");
        attendanceMenu.add(takeAttendanceButton);

        viewAttendanceButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        viewAttendanceButton.setText("View/change attendance");
        attendanceMenu.add(viewAttendanceButton);

        ModulesMenu.add(attendanceMenu);

        myStudentsButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        myStudentsButton.setText("My Students");
        myStudentsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                //TODO myStudentsButtonActionPerformed(evt);
            }
        });
        ModulesMenu.add(myStudentsButton);

        myTeachersButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        myTeachersButton.setText("My Teachers");
        ModulesMenu.add(myTeachersButton);

        MenuBar.add(ModulesMenu);

        profileMenu.setText("Profile");

        viewProfileButton.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        viewProfileButton.setText("View my data");
        profileMenu.add(viewProfileButton);

        chPassButton.setText("Change password");
        profileMenu.add(chPassButton);

        MenuBar.add(profileMenu);

        setJMenuBar(MenuBar);

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
        setVisible(true);
    }
}
