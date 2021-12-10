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
import java.awt.event.WindowFocusListener;


public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    private final GameBoard gameBoard;
    private final HomeMenu homeMenu;

    private boolean gaming;

    public GameFrame() {
        super();

        setGaming(false);

        this.setLayout(new BorderLayout());

        gameBoard = new GameBoard(this);

        homeMenu = new HomeMenu(this, new Dimension(550, 400));

        this.add(getHomeMenu(), BorderLayout.CENTER);

        this.setUndecorated(true);


    }

    public static String getDefTitle() {
        return DEF_TITLE;
    }

    public void initialize() {
        this.setTitle(getDefTitle());
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);
    }

    public void enableGameBoard() {
        this.dispose();
        this.remove(getHomeMenu());
        this.add(getGameBoard(), BorderLayout.CENTER);
        this.setUndecorated(false);
        initialize();
        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);

    }

    private void autoLocate() {
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x, y);
    }


    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        setGaming(true);
    }

    @Override
    public void windowLostFocus(WindowEvent windowEvent) {
        if (isGaming())
            getGameBoard().onLostFocus();

    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public HomeMenu getHomeMenu() {
        return homeMenu;
    }

    public boolean isGaming() {
        return gaming;
    }

    public void setGaming(boolean gaming) {
        this.gaming = gaming;
    }
}
