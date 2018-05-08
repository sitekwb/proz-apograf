package initial;

import mains.*;

import javax.swing.*;
import java.awt.*;

public class InitView extends JFrame{
    Controller cont;
    public InitView(Controller cont){
        super("Logging window");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        GridBagLayout layout =new GridBagLayout();
        GridBagConstraints constraints = new GridBagConstraints();
        setLayout(layout);
        constraints.fill = GridBagConstraints.BOTH;

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int)screenSize.getWidth()/4,(int)screenSize.getHeight()/4,
                 (int)screenSize.getWidth()/2,(int)screenSize.getHeight()/2);
        setBackground(Color.YELLOW);

        JLabel title = new JLabel("APOGRAF");
        title.setBorder(BorderFactory.createLineBorder(Color.RED));
        title.setFont(new Font("Comic Sans MS", Font.ITALIC, 60));
        title.setPreferredSize(new Dimension(this.getHeight()/5,this.getWidth()));
        constraints.gridx=0;
        constraints.gridwidth=2;
        constraints.gridheight=2;
        constraints.gridy=0;
        add(title, constraints);

        JLabel mailLabel = new JLabel ("E-mail:");
        constraints.gridx=0;
        constraints.gridwidth=1;
        constraints.gridy = 3;
        constraints.gridheight =1;
        add(mailLabel, constraints);

        JLabel passLabel = new JLabel ("Password:");
        passLabel.setBounds(this.getHeight()*3/10,this.getWidth()/5,
                this.getHeight()/10, this.getWidth()/5);
        constraints.gridx = 1;
        add(passLabel,constraints);

        JTextField mailField = new JTextField();
        constraints.gridx=0;
        constraints.gridy=4;
        add(mailField, constraints);

        JTextField passField = new JTextField();
        constraints.gridx=1;
        this.add(passField, constraints);

        JButton logButton = new JButton("Log in");
        constraints.gridx=0;
        constraints.gridy=5;
        this.add(logButton, constraints);

        JButton registerButton = new JButton("Register");
        constraints.gridx=1;
        this.add(registerButton,constraints);

        setVisible(true);
    }
}
