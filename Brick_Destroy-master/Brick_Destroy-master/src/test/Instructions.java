package test;

import javax.swing.*;
import java.awt.*;

/**
 * Instructions Menu in the Home Menu.
 */
public class Instructions extends JFrame {

    private static final int FRAME_WIDTH = 600;
    private static final int FRAME_HEIGHT = 450;
    private JLabel label;
    private JLabel label2;
    private JLabel label3;
    private JLabel label4;
    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();


    public Instructions() {

        getFrame().setTitle("Instructions");
        getFrame().setSize(getFrameWidth(), getFrameHeight());
        getFrame().setVisible(true);
        getFrame().setLayout(new FlowLayout(FlowLayout.CENTER, 200, 0));
        getFrame().add(getPanel());

        getPanel().setPreferredSize(new Dimension(getFrameWidth(), getFrameHeight()));
        getPanel().setBackground(Color.ORANGE);
        getPanel().setLayout(new FlowLayout(FlowLayout.CENTER, 200, 45));

        setLabel(new JLabel("Press 'A' = Move to the LEFT."));
        getLabel().setFont(new Font("Jokerman", Font.PLAIN, 23));
        getPanel().add(getLabel());

        setLabel2(new JLabel("Press 'D' = Move to the RIGHT."));
        getLabel2().setFont(new Font("Jokerman", Font.PLAIN, 23));
        getPanel().add(getLabel2());

        setLabel3(new JLabel("Press 'SpaceBar' = Start the game."));
        getLabel3().setFont(new Font("Jokerman", Font.PLAIN, 23));
        getPanel().add(getLabel3());

        setLabel4(new JLabel("Press 'Alt+Shift+F1' = To Access settings."));
        getLabel4().setFont(new Font("Jokerman", Font.PLAIN, 23));
        getPanel().add(getLabel4());

    }


    public static int getFrameWidth() {
        return FRAME_WIDTH;
    }

    public static int getFrameHeight() {
        return FRAME_HEIGHT;
    }

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }

    public JLabel getLabel2() {
        return label2;
    }

    public void setLabel2(JLabel label2) {
        this.label2 = label2;
    }

    public JLabel getLabel3() {
        return label3;
    }

    public void setLabel3(JLabel label3) {
        this.label3 = label3;
    }

    public JLabel getLabel4() {
        return label4;
    }

    public void setLabel4(JLabel label4) {
        this.label4 = label4;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }
}
