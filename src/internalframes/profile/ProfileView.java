package internalframes.profile;

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
        add(title);
        layout.addLayoutComponent("Title",title);
        for(int i=0;i<5;i++){
            jLabel[i]=new JLabel();
            add(jLabel[i]);
            layout.addLayoutComponent("Label "+(i+1),jLabel[i]);

            jTextField[i]=new JTextField();
            add(jTextField[i]);
            layout.addLayoutComponent("TextField "+(i+1),jTextField[i]);

            jButton[i]=new JButton();
            add(jButton[i]);
            layout.addLayoutComponent("Button "+(i+1),jButton[i]);
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(400,400);
        setMinimumSize(new Dimension(400,400));
        setMaximumSize(new Dimension((int)screenSize.getWidth(),(int)screenSize.getHeight()));


/*
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 1561, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel[0], javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField[0], javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36)
                                                .addComponent(jButton[0], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel[1], javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField[1], javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36)
                                                .addComponent(jButton[1], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel[2], javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField[2], javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36)
                                                .addComponent(jButton[2], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addComponent(jLabel[3], javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField[3], javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36)
                                                .addComponent(jButton[3], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel[4], javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jTextField[4], javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(36, 36, 36)
                                                .addComponent(jButton[4], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel[0], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField[0], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton[0], javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel[1], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField[1], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton[1], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel[2], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField[2], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton[2], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(57, 57, 57)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel[3], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField[3], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton[3], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(55, 55, 55)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel[4], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jTextField[4], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(jButton[4], javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(198, Short.MAX_VALUE))
        );
        */
        setVisible(true);
    }



}
