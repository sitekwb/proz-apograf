package modules.attendance;

import javax.swing.*;
import java.awt.*;

public class AttendanceView extends views.Window {
    private JButton cancelButton;
    private JButton confirmButton;
    private JLabel errLabel;
    private JScrollPane jScrollPane;
    private JTable jTable;
    private JButton refreshButton;
    private JLabel classLabel;
    private JLabel title1;

    JButton getCancelButton(){
        return cancelButton;
    }
    JLabel getClassLabel(){
        return classLabel;
    }
    JButton getConfirmButton(){
        return confirmButton;
    }
    JLabel getErrLabel(){
        return errLabel;
    }
    JButton getRefreshButton(){
        return refreshButton;
    }
    JTable getTable(){ return jTable; }

    public AttendanceView(){
        refreshButton = new JButton();
        cancelButton = new JButton();
        confirmButton = new JButton();
        classLabel = new JLabel();
        errLabel = new JLabel();
        jScrollPane = new JScrollPane();
        jTable = new JTable();
        title1 = new JLabel();


        refreshButton.setText("Refresh");
        refreshButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        cancelButton.setText("Go back");
        cancelButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        confirmButton.setText("Confirm");
        confirmButton.setFont(new Font("Times New Roman", Font.PLAIN, 30));

        classLabel.setText("Class name");
        classLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));

        errLabel.setText("Attendance information:");
        errLabel.setFont(new Font("Times New Roman", Font.PLAIN, 40));


        jTable.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        jTable.setRowHeight(40);
        jTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                        "Student's name", "Date", "Present"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane.setViewportView(jTable);

        title1.setText("View attendance");
        title1.setFont(new Font("Times New Roman", Font.PLAIN, 100));

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(errLabel, GroupLayout.PREFERRED_SIZE, 1358, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 301, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(confirmButton, GroupLayout.PREFERRED_SIZE, 296, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(classLabel, GroupLayout.PREFERRED_SIZE, 1380, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(title1, GroupLayout.PREFERRED_SIZE, 905, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(1317, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addComponent(classLabel, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(errLabel, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(confirmButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(refreshButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(cancelButton, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane, GroupLayout.DEFAULT_SIZE, 833, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(title1, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
                                        .addContainerGap(945, Short.MAX_VALUE)))
        );

    }
}
