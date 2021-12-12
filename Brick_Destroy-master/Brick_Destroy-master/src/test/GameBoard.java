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
import java.awt.event.*;
import java.awt.font.FontRenderContext;


public class GameBoard extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final String CONTINUE = "Continue";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Exit";
    private static final String PAUSE = "Pause Menu";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(0, 255, 0);


    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = Color.WHITE;

    private Timer gameTimer;

    private final Wall wall;

    private String message;

    private boolean showPauseMenu;

    private final Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle exitButtonRect;
    private Rectangle restartButtonRect;
    private int strLen;

    private final DebugConsole debugConsole;


    public GameBoard(JFrame owner) {
        super();

        setStrLen(0);
        setShowPauseMenu(false);


        menuFont = new Font("Monospaced", Font.PLAIN, getTextSize());


        this.initialize();
        setMessage("");
        wall = new Wall(new Rectangle(0, 0, getDefWidth(), getDefHeight()), 30, 3, 3, new Point(300, 430));

        debugConsole = new DebugConsole(owner, getWall(), this);
        //initialize the first level
        getWall().nextLevel();

        setGameTimer(new Timer(10, e -> {
            getWall().move();
            getWall().findImpacts();
            setMessage(String.format("Bricks: %d Balls %d", getWall().getBrickCount(), getWall().getBallCount()));
            if (getWall().isBallLost()) {
                if (getWall().ballEnd()) {
                    getWall().wallReset();
                    setMessage("Game over");
                }
                getWall().ballReset();
                getGameTimer().stop();
            } else if (getWall().isDone()) {
                if (getWall().hasLevel()) {
                    setMessage("Go to Next Level");
                    getGameTimer().stop();
                    getWall().ballReset();
                    getWall().wallReset();
                    getWall().nextLevel();
                } else {
                    setMessage("ALL WALLS DESTROYED");
                    getGameTimer().stop();
                }
            }

            repaint();
        }));

    }

    public static String getCONTINUE() {
        return CONTINUE;
    }

    public static String getRESTART() {
        return RESTART;
    }

    public static String getEXIT() {
        return EXIT;
    }

    public static String getPAUSE() {
        return PAUSE;
    }

    public static int getTextSize() {
        return TEXT_SIZE;
    }

    public static Color getMenuColor() {
        return MENU_COLOR;
    }

    public static int getDefWidth() {
        return DEF_WIDTH;
    }

    public static int getDefHeight() {
        return DEF_HEIGHT;
    }

    public static Color getBgColor() {
        return BG_COLOR;
    }


    private void initialize() {
        this.setPreferredSize(new Dimension(getDefWidth(), getDefHeight()));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }


    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.GREEN);
        g2d.drawString(getMessage(), 250, 225);

        drawBall(getWall().getBall(), g2d);

        for (Brick b : getWall().getBricks())
            if (b.isBroken())
                drawBrick(b, g2d);

        drawPlayer(getWall().getPlayer(), g2d);

        if (isShowPauseMenu())
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();
    }

    private void clear(Graphics2D g2d) {
        Color tmp = g2d.getColor();
        g2d.setColor(getBgColor());
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(tmp);
    }

    private void drawBrick(Brick brick, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());


        g2d.setColor(tmp);
    }

    private void drawBall(Ball ball, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawPlayer(Player p, Graphics2D g2d) {
        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.getInnerColor());
        g2d.fill(s);

        g2d.setColor(Player.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);
    }

    private void drawMenu(Graphics2D g2d) {
        obscureGameBoard(g2d);
        drawPauseMenu(g2d);
    }

    private void obscureGameBoard(Graphics2D g2d) {

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getDefWidth(), getDefHeight());

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);
    }

    private void drawPauseMenu(Graphics2D g2d) {
        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();


        g2d.setFont(getMenuFont());
        g2d.setColor(getMenuColor());

        if (getStrLen() == 0) {
            FontRenderContext frc = g2d.getFontRenderContext();
            setStrLen(getMenuFont().getStringBounds(getPAUSE(), frc).getBounds().width);
        }

        int x = (this.getWidth() - getStrLen()) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(getPAUSE(), x, y);

        x = this.getWidth() / 4;
        y = this.getHeight() / 4;


        if (contButton()) {
            FontRenderContext frc = g2d.getFontRenderContext();
            setContinueButtonRect(getMenuFont().getStringBounds(getCONTINUE(), frc).getBounds());
            getContinueButtonRect().setLocation(x, y - getContinueButtonRect().height);
        }

        g2d.drawString(getCONTINUE(), x, y);

        y *= 2;

        if (restartButton()) {
            setRestartButtonRect((Rectangle) getContinueButtonRect().clone());
            getRestartButtonRect().setLocation(x, y - getRestartButtonRect().height);
        }

        g2d.drawString(getRESTART(), x, y);

        y *= 3.0 / 2;

        if (exitButton()) {
            setExitButtonRect((Rectangle) getContinueButtonRect().clone());
            getExitButtonRect().setLocation(x, y - getExitButtonRect().height);
        }

        g2d.drawString(getEXIT(), x, y);


        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);
    }

    private boolean contButton() {
        return getContinueButtonRect() == null;
    }

    private boolean restartButton() {
        return getRestartButtonRect() == null;
    }

    private boolean exitButton() {
        return getExitButtonRect() == null;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {
    }

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_A:
                getWall().getPlayer().moveLeft();
                break;
            case KeyEvent.VK_D:
                getWall().getPlayer().movRight();
                break;
            case KeyEvent.VK_ESCAPE:
                setShowPauseMenu(!isShowPauseMenu());
                repaint();
                getGameTimer().stop();
                break;
            case KeyEvent.VK_SPACE:
                if (!isShowPauseMenu())
                    if (getGameTimer().isRunning())
                        getGameTimer().stop();
                    else
                        getGameTimer().start();
                break;
            case KeyEvent.VK_F1:
                if (keyEvent.isAltDown() && keyEvent.isShiftDown())
                    getDebugConsole().setVisible(true);
            default:
                getWall().getPlayer().stop();
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        getWall().getPlayer().stop();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        if (!isShowPauseMenu())
            return;
        if (getContinueButtonRect().contains(p)) {
            setShowPauseMenu(false);
            repaint();
        } else if (getRestartButtonRect().contains(p)) {
            setMessage("Restarting Game...");
            getWall().ballReset();
            getWall().wallReset();
            setShowPauseMenu(false);
            repaint();
        } else if (getExitButtonRect().contains(p)) {
            System.exit(0);
        }

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

        if (ShowPauseMenu()) {
            if (ButtonsRect(mouseEvent)) {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            } else
                this.setCursor(Cursor.getDefaultCursor());
        } else {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private boolean ShowPauseMenu() {
        return getExitButtonRect() != null && isShowPauseMenu();
    }

    private boolean ButtonsRect(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        return getExitButtonRect().contains(p) || getContinueButtonRect().contains(p) || getRestartButtonRect().contains(p);
    }

    public void onLostFocus() {
        getGameTimer().stop();
        setMessage("Focus Lost");
        repaint();
    }

    public Timer getGameTimer() {
        return gameTimer;
    }

    public void setGameTimer(Timer gameTimer) {
        this.gameTimer = gameTimer;
    }

    public Wall getWall() {
        return wall;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isShowPauseMenu() {
        return showPauseMenu;
    }

    public void setShowPauseMenu(boolean showPauseMenu) {
        this.showPauseMenu = showPauseMenu;
    }

    public Font getMenuFont() {
        return menuFont;
    }

    public Rectangle getContinueButtonRect() {
        return continueButtonRect;
    }

    public void setContinueButtonRect(Rectangle continueButtonRect) {
        this.continueButtonRect = continueButtonRect;
    }

    public Rectangle getExitButtonRect() {
        return exitButtonRect;
    }

    public void setExitButtonRect(Rectangle exitButtonRect) {
        this.exitButtonRect = exitButtonRect;
    }

    public Rectangle getRestartButtonRect() {
        return restartButtonRect;
    }

    public void setRestartButtonRect(Rectangle restartButtonRect) {
        this.restartButtonRect = restartButtonRect;
    }

    public int getStrLen() {
        return strLen;
    }

    public void setStrLen(int strLen) {
        this.strLen = strLen;
    }

    public DebugConsole getDebugConsole() {
        return debugConsole;
    }
}
