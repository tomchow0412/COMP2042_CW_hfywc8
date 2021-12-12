/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2017  Filippo Ranza
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package test;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;


/**
 * Debug Panel use for the calculation in Debug Console.
 */
public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;


    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    /**
     * Adding the SKip level function and the Movement Speed of the ball.
     * @param wall this is wall object.
     */
    public DebugPanel(Wall wall) {

        initialize();

        JButton skipLevel = makeButton("Skip Level", e -> wall.nextLevel());
        JButton resetBalls = makeButton("Reset Balls", e -> wall.resetBallCount());

        setBallXSpeed(makeSlider(e -> wall.setBallXSpeed(getBallXSpeed().getValue())));
        setBallYSpeed(makeSlider(e -> wall.setBallYSpeed(getBallYSpeed().getValue())));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(getBallXSpeed());
        this.add(getBallYSpeed());

    }

    public static Color getDefBkg() {
        return DEF_BKG;
    }

    private void initialize() {
        this.setBackground(getDefBkg());
        this.setLayout(new GridLayout(2, 2));
    }

    /**
     * Makes button to get the confirmation of levels or movement of speed they want from player.
     * @param title this is title object.
     * @param e this is the action listener object.
     * @return return out if it doesn't receive anything.
     */
    private JButton makeButton(String title, ActionListener e) {
        JButton out = new JButton(title);
        out.addActionListener(e);
        return out;
    }

    /**
     * Slider to let player skip the levels and adjust the ball movement speed.
     * @param e this is the action listener object.
     * @return return out if it doesn't receive anything.
     */
    private JSlider makeSlider(ChangeListener e) {
        JSlider out = new JSlider(-4, 4);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);
        return out;
    }

    public void setValues(int x, int y) {
        getBallXSpeed().setValue(x);
        getBallYSpeed().setValue(y);
    }

    public JSlider getBallXSpeed() {
        return ballXSpeed;
    }

    public void setBallXSpeed(JSlider ballXSpeed) {
        this.ballXSpeed = ballXSpeed;
    }

    public JSlider getBallYSpeed() {
        return ballYSpeed;
    }

    public void setBallYSpeed(JSlider ballYSpeed) {
        this.ballYSpeed = ballYSpeed;
    }
}
