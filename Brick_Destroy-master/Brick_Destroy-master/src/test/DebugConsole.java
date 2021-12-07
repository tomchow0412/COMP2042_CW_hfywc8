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
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DebugConsole extends JDialog implements WindowListener {

    private static final String TITLE = "Debug Console";


    private final JFrame owner;
    private final DebugPanel debugPanel;
    private final GameBoard gameBoard;
    private final Wall wall;


    public DebugConsole(JFrame owner, Wall wall, GameBoard gameBoard) {

        this.wall = wall;
        this.owner = owner;
        this.gameBoard = gameBoard;
        initialize();

        debugPanel = new DebugPanel(wall);
        this.add(getDebugPanel(), BorderLayout.CENTER);


        this.pack();
    }

    public static String getTITLE() {
        return TITLE;
    }

    private void initialize() {
        this.setModal(true);
        this.setTitle(getTITLE());
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);
    }


    private void setLocation() {
        int x = ((getOwner().getWidth() - this.getWidth()) / 2) + getOwner().getX();
        int y = ((getOwner().getHeight() - this.getHeight()) / 2) + getOwner().getY();
        this.setLocation(x, y);
    }


    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        getGameBoard().repaint();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {
        setLocation();
        Ball b = getWall().getBall();
        getDebugPanel().setValues(b.getSpeedX(), b.getSpeedY());
    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }

    public DebugPanel getDebugPanel() {
        return debugPanel;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public Wall getWall() {
        return wall;
    }

    @Override
    public JFrame getOwner() {
        return owner;
    }
}
