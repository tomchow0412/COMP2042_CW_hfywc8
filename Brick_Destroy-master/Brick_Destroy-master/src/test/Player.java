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

import java.awt.*;


class Player {


    private static final Color BORDER_COLOR = Color.GREEN.darker().darker();
    private static final Color INNER_COLOR = Color.GREEN;

    private static final int DEF_MOVE_AMOUNT = 5;

    private final Rectangle playerFace;
    private final Point ballPoint;
    private int moveAmount;
    private final int min;
    private final int max;


    public Player(Point ballPoint, int width, int height, Rectangle container) {
        this.ballPoint = ballPoint;
        setMoveAmount(0);
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = getMin() + container.width - width;

    }

    public static Color getBorderColor() {
        return BORDER_COLOR;
    }

    public static Color getInnerColor() {
        return INNER_COLOR;
    }

    public static int getDefMoveAmount() {
        return DEF_MOVE_AMOUNT;
    }

    private Rectangle makeRectangle(int width, int height) {
        Point p = new Point((int) (getBallPoint().getX() - (width / 2)), (int) getBallPoint().getY());
        return new Rectangle(p, new Dimension(width, height));
    }

    public boolean impact(Ball b) {
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.getDown());
    }

    public void move() {
        double x = getBallPoint().getX() + getMoveAmount();
        if (x < getMin() || x > getMax())
            return;
        getBallPoint().setLocation(x, getBallPoint().getY());
        playerFace.setLocation(getBallPoint().x - (int) playerFace.getWidth() / 2, getBallPoint().y);
    }

    public void moveLeft() {
        setMoveAmount(-getDefMoveAmount());
    }

    public void movRight() {
        setMoveAmount(getDefMoveAmount());
    }

    public void stop() {
        setMoveAmount(0);
    }

    public Shape getPlayerFace() {
        return playerFace;
    }

    public void moveTo(Point p) {
        getBallPoint().setLocation(p);
        playerFace.setLocation(getBallPoint().x - (int) playerFace.getWidth() / 2, getBallPoint().y);
    }

    public Point getBallPoint() {
        return ballPoint;
    }

    public int getMoveAmount() {
        return moveAmount;
    }

    public void setMoveAmount(int moveAmount) {
        this.moveAmount = moveAmount;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }
}
