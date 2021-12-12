package test;

import javax.swing.*;
import java.awt.*;

public class Instructions extends JFrame{

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 450;
    JLabel label;
    JLabel label2;
    JLabel label3;
    JLabel label4;
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();


    Instructions(){

        frame.setTitle("Instructions");
        frame.setSize(getFrameWidth(),getFrameHeight());
        frame.setVisible(true);
        frame.setLayout(new FlowLayout(FlowLayout.CENTER,200,0));
        frame.add(panel);

        panel.setPreferredSize(new Dimension(getFrameWidth(),getFrameHeight()));
        panel.setBackground(Color.ORANGE);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER,200,45));

        label = new JLabel ("Press 'A' = Move to the LEFT.");
        label.setFont(new Font("Jokerman", Font.PLAIN, 23));
        panel.add(label);

        label2 = new JLabel ("Press 'D' = Move to the RIGHT.");
        label2.setFont(new Font("Jokerman", Font.PLAIN, 23));
        panel.add(label2);

        label3 = new JLabel ("Press 'SpaceBar' = Start the game.");
        label3.setFont(new Font("Jokerman", Font.PLAIN, 23));
        panel.add(label3);

        label4 = new JLabel ("Press 'Alt+Shift+F1' = To Access settings.");
        label4.setFont(new Font("Jokerman", Font.PLAIN, 23));
        panel.add(label4);



    }



    public static int getFrameWidth() {
        return FRAME_WIDTH;
    }

    public static int getFrameHeight() {
        return FRAME_HEIGHT;
    }

}
