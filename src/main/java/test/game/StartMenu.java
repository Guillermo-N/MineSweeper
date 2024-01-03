package test.game;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionListener;


public class StartMenu extends JPanel{

    private JButton button10;
    private JButton button16;
    private JButton button24;
    private JButton button32;

    private static int BTN_SQR_SIZE = 180;
    
    public StartMenu(){
        this.setLayout(new GridLayout(2,2));


        button10 = new JButton("<html> <h2> 10 x 10 </h2> <br> 12 mines</html>");
        button10.setPreferredSize(new Dimension(BTN_SQR_SIZE,BTN_SQR_SIZE));
        button10.setFocusable(false);
        button10.setBackground(new Color(249, 179, 0, 234));

        button16 = new JButton("<html> <h2> 16 x 16 </h2> <br> 20 mines</html>");
        button16.setPreferredSize(new Dimension(BTN_SQR_SIZE, BTN_SQR_SIZE));
        button16.setFocusable(false);
        button16.setBackground(new Color(249, 179, 0, 234));

        button24 = new JButton("<html> <h2> 24 x 24 </h2> <br> 30 mines</html>");
        button24.setPreferredSize(new Dimension(BTN_SQR_SIZE, BTN_SQR_SIZE));
        button24.setFocusable(false);
        button24.setBackground(new Color(249, 179, 0, 234));

        button32 = new JButton("<html> <h2> 32 x 32 </h2> <br> 50 mines</html>");
        button32.setPreferredSize(new Dimension(BTN_SQR_SIZE, BTN_SQR_SIZE));
        button32.setFocusable(false);
        button32.setBackground(new Color(249, 179, 0, 234));


        add(button10);
        add(button16);
        add(button24);
        add(button32);

        
    }

    public void addActionListener(ActionListener action){
        button10.addActionListener(action);
        button16.addActionListener(action);
        button24.addActionListener(action);
        button32.addActionListener(action);
    }
}
