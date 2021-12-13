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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;


/**
 * Home Menu shows whenever user runs the program.
 * initializing every object that gonna used.
 */
public class HomeMenu extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to:";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "by Chow Wen Jun";
    private static final String START_TEXT = "Start";
    private static final String INSTRUCTION_TEXT = "Instruction";
    private static final String MENU_TEXT = "Exit";

    private static final Color BG_COLOR = Color.GREEN.darker();
    private static final Color BORDER_COLOR = new Color(200, 8, 21); //Venetian Red
    private static final Color DASH_BORDER_COLOR = new Color(255, 216, 0);//school bus yellow
    private static final Color TEXT_COLOR = new Color(16, 52, 166);//egyptian blue
    private static final Color CLICKED_BUTTON_COLOR = getBgColor().brighter();
    private static final Color CLICKED_TEXT = Color.WHITE;
    private static final int BORDER_SIZE = 5;
    private static final float[] DASHES = {12, 6};

    private final Rectangle menuFace;
    private final Rectangle startButton;
    private final Rectangle menuButton;
    private final Rectangle instructionButton;
    private boolean showInstructionMenu;


    private final BasicStroke borderStoke;
    private final BasicStroke borderStoke_noDashes;

    private final Font greetingsFont;
    private final Font gameTitleFont;
    private final Font creditsFont;
    private final Font buttonFont;

    private final GameFrame owner;

    private boolean startClicked;
    private boolean menuClicked;
    private boolean instructionClicked;


    /**
     * Construct a frame to Home Menu.
     * @param owner this is owner object.
     * @param area this is area object.
     */
    public HomeMenu(GameFrame owner, Dimension area) {


        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0, 0), area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        startButton = new Rectangle(btnDim);
        instructionButton = new Rectangle(btnDim);
        menuButton = new Rectangle(btnDim);

        borderStoke = new BasicStroke(getBorderSize(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, getDASHES(), 0);
        borderStoke_noDashes = new BasicStroke(getBorderSize(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Broadway", Font.PLAIN, 25);
        gameTitleFont = new Font("Castellar", Font.BOLD, 40);
        creditsFont = new Font("Brush Script MT", Font.PLAIN, 15);
        buttonFont = new Font("Winkle", Font.PLAIN, getStartButton().height - 2);



    }

    public static String getGREETINGS() {
        return GREETINGS;
    }

    public static String getGameTitle() {
        return GAME_TITLE;
    }

    public static String getCREDITS() {
        return CREDITS;
    }

    public static String getStartText() {
        return START_TEXT;
    }

    public static String getMenuText() {
        return MENU_TEXT;
    }

    public static String getInstructionText() { return INSTRUCTION_TEXT; }

    public static Color getBgColor() {
        return BG_COLOR;
    }

    public static Color getBorderColor() {
        return BORDER_COLOR;
    }

    public static Color getDashBorderColor() {
        return DASH_BORDER_COLOR;
    }

    public static Color getTextColor() {
        return TEXT_COLOR;
    }

    public static Color getClickedButtonColor() {
        return CLICKED_BUTTON_COLOR;
    }

    public static Color getClickedText() {
        return CLICKED_TEXT;
    }

    public static int getBorderSize() {
        return BORDER_SIZE;
    }

    public static float[] getDASHES() {
        return DASHES;
    }


    public void paint(Graphics g) {
        drawMenu((Graphics2D) g);
    }

    /**
     * drawing the Home Menu layout.
     * @param g2d this is g2d object.
     */
    public void drawMenu(Graphics2D g2d) {

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = getMenuFace().getX();
        double y = getMenuFace().getY();

        g2d.translate(x, y);

        //method calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x, -y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);
    }

    /**
     * drawing the container of the Home Menu.
     * @param g2d this is g2d object.
     */
    private void drawContainer(Graphics2D g2d) {
        Color prev = g2d.getColor();

        g2d.setColor(getBgColor());
        g2d.fill(getMenuFace());

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(getBorderStoke_noDashes());
        g2d.setColor(getDashBorderColor());
        g2d.draw(getMenuFace());

        g2d.setStroke(getBorderStoke());
        g2d.setColor(getBorderColor());
        g2d.draw(getMenuFace());

        g2d.setStroke(tmp);

        g2d.setColor(prev);
    }

    /**
     * Drawing the title and other text in the Home Menu
     * @param g2d this is g2d object.
     */
    private void drawText(Graphics2D g2d) {

        g2d.setColor(getTextColor());

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = getGreetingsFont().getStringBounds(getGREETINGS(), frc);
        Rectangle2D gameTitleRect = getGameTitleFont().getStringBounds(getGameTitle(), frc);
        Rectangle2D creditsRect = getCreditsFont().getStringBounds(getCREDITS(), frc);

        int sX, sY;

        sX = (int) (getMenuFace().getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int) (getMenuFace().getHeight() / 6);

        g2d.setFont(getGreetingsFont());
        g2d.drawString(getGREETINGS(), sX, sY);

        sX = (int) (getMenuFace().getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(getGameTitleFont());
        g2d.drawString(getGameTitle(), sX, sY);

        sX = (int) (getMenuFace().getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(getCreditsFont());
        g2d.drawString(getCREDITS(), sX, sY);


    }

    /**
     * draw the layout of start, instructions and exit button.
     * @param g2d this is g2d object.
     */
    private void drawButton(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = getButtonFont().getStringBounds(getStartText(), frc);
        Rectangle2D iTxtRect = getButtonFont().getStringBounds(getInstructionText(), frc);
        Rectangle2D mTxtRect = getButtonFont().getStringBounds(getMenuText(), frc);

        g2d.setFont(getButtonFont());

        int x = (getMenuFace().width - getStartButton().width) / 2;
        int y = (int) ((getMenuFace().height - getStartButton().height) * 0.6);

        getStartButton().setLocation(x, y);

        x = (int) (getStartButton().getWidth() - txtRect.getWidth()) / 2;
        y = (int) (getStartButton().getHeight() - txtRect.getHeight()) / 2;

        x += getStartButton().x;
        y += getStartButton().y + (getStartButton().height * 0.9);


        if (isStartClicked()) {
            Color tmp = g2d.getColor();
            g2d.setColor(getClickedButtonColor());
            g2d.draw(getStartButton());
            g2d.setColor(getClickedText());
            g2d.drawString(getStartText(), x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(getStartButton());
            g2d.drawString(getStartText(), x, y);
        }

        x = getStartButton().x;
        y = getStartButton().y;

        y *= 1.2;

        getInstructionButton().setLocation(x, y);

        x = (int) (getInstructionButton().getWidth() - iTxtRect.getWidth()) / 2;
        y = (int) (getInstructionButton().getHeight() - iTxtRect.getHeight()) / 2;

        x += getInstructionButton().x;
        y += getInstructionButton().y + (getStartButton().height * 0.9);

        if (isInstructionClicked()) {
            Color tmp = g2d.getColor();
            g2d.setColor(getClickedButtonColor());
            g2d.draw(getInstructionButton());
            g2d.setColor(getClickedText());
            g2d.drawString(getInstructionText(), x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(getInstructionButton());
            g2d.drawString(getInstructionText(), x, y);
        }

        x = getInstructionButton().x;
        y = getInstructionButton().y;

        y *= 1.2;

        getMenuButton().setLocation(x, y);


        x = (int) (getMenuButton().getWidth() - mTxtRect.getWidth()) / 2;
        y = (int) (getMenuButton().getHeight() - mTxtRect.getHeight()) / 2;

        x += getMenuButton().x;
        y += getMenuButton().y  + (getStartButton().height * 0.9) ;

        if (isMenuClicked()) {
            Color tmp = g2d.getColor();
            g2d.setColor(getClickedButtonColor());
            g2d.draw(getMenuButton());
            g2d.setColor(getClickedText());
            g2d.drawString(getMenuText(), x, y);
            g2d.setColor(tmp);
        } else {
            g2d.draw(getMenuButton());
            g2d.drawString(getMenuText(), x, y);
        }

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (GetStartButt(mouseEvent)) {
            getOwner().enableGameBoard();

        } else if (GetMenuButt(mouseEvent)) {
            System.out.println("Goodbye " + System.getProperty("user.name"));
            System.exit(0);
        } else if (GetInstructionButt(mouseEvent)) {
            new Instructions();
        }
    }

    public boolean isShowInstructionMenu() {
        return showInstructionMenu;
    }

    public void setShowInstructionMenu(boolean showInstructionMenu) {
        this.showInstructionMenu = showInstructionMenu;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        if (GetStartButt(mouseEvent)) {
            setStartClicked(true);
            repaint(getStartButton().x, getStartButton().y, getStartButton().width + 1, getStartButton().height + 1);

        } else if (GetMenuButt(mouseEvent)) {
            setMenuClicked(true);
            repaint(getMenuButton().x, getMenuButton().y, getMenuButton().width + 1, getMenuButton().height + 1);
        } else if (GetInstructionButt(mouseEvent)) {
            setInstructionClicked(true);
            repaint(getInstructionButton().x, getInstructionButton().y, getInstructionButton().width + 1, getInstructionButton().height + 1);
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (isStartClicked()) {
            setStartClicked(false);
            repaint(getStartButton().x, getStartButton().y, getStartButton().width + 1, getStartButton().height + 1);
        } else if (isMenuClicked()) {
            setMenuClicked(false);
            repaint(getMenuButton().x, getMenuButton().y, getMenuButton().width + 1, getMenuButton().height + 1);
        } else if (isInstructionClicked()) {
            setInstructionClicked(false);
            repaint(getInstructionButton().x, getInstructionButton().y, getInstructionButton().width +1, getInstructionButton().height +1);
        }
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

        if (GetAllButt(mouseEvent))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }

    private boolean GetStartButt(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        return getStartButton().contains(p);
    }

    private boolean GetMenuButt(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        return getMenuButton().contains(p);
    }

    private boolean GetInstructionButt(MouseEvent mouseEvent){
        Point p = mouseEvent.getPoint();
        return getInstructionButton().contains(p);
    }

    private boolean GetAllButt(MouseEvent mouseEvent) {
        Point p = mouseEvent.getPoint();
        return getStartButton().contains(p) || getMenuButton().contains(p) || getInstructionButton().contains(p);

    }

    public Rectangle getMenuFace() {
        return menuFace;
    }

    public Rectangle getStartButton() {
        return startButton;
    }

    public Rectangle getMenuButton() {
        return menuButton;
    }

    public Rectangle getInstructionButton() {
        return instructionButton;
    }

    public BasicStroke getBorderStoke() {
        return borderStoke;
    }

    public BasicStroke getBorderStoke_noDashes() {
        return borderStoke_noDashes;
    }

    public Font getGreetingsFont() {
        return greetingsFont;
    }

    public Font getGameTitleFont() {
        return gameTitleFont;
    }

    public Font getCreditsFont() {
        return creditsFont;
    }

    public Font getButtonFont() {
        return buttonFont;
    }

    public GameFrame getOwner() {
        return owner;
    }

    public boolean isStartClicked() {
        return startClicked;
    }

    public void setStartClicked(boolean startClicked) {
        this.startClicked = startClicked;
    }

    public boolean isInstructionClicked() {
        setShowInstructionMenu(!isShowInstructionMenu());
        repaint();
        return instructionClicked;
    }

    public void setInstructionClicked(boolean instructionClicked) {
        this.instructionClicked= instructionClicked;
    }

    public boolean isMenuClicked() {
        return menuClicked;
    }

    public void setMenuClicked(boolean menuClicked) {
        this.menuClicked = menuClicked;
    }
}
