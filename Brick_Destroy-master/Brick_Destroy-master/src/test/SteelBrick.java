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
import java.awt.geom.Point2D;
import java.util.Random;


class SteelBrick extends Brick {

    /**
     * the declaration of the brick's color.
     */
    private static final Color DEF_INNER = new Color(203, 203, 201);
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private final Random rnd;
    private final Shape brickFace;

    /**
     * @param point this is point object of Steel brick.
     * @param size this is size object of steel brick.
     */
    public SteelBrick(Point point, Dimension size) {
        super(point, size, getDefBorder(), getDefInner(), getSteelStrength());
        rnd = new Random();
        brickFace = super.brickFace;
    }

    public static Color getDefInner() {
        return DEF_INNER;
    }

    public static Color getDefBorder() {
        return DEF_BORDER;
    }

    public static int getSteelStrength() {
        return STEEL_STRENGTH;
    }

    public static double getSteelProbability() {
        return STEEL_PROBABILITY;
    }


    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public Shape getBrick() {
        return getBrickFace();
    }

    public boolean setImpact(Point2D point, int dir) {
        if (!super.isBroken())
            return false;
        impact();
        return !super.isBroken();
    }

    public void impact() {
        if (rndImpacts()) {
            super.impact();
        }
    }

    private boolean rndImpacts() {
        return getRnd().nextDouble() < getSteelProbability();
    }

    public Random getRnd() {
        return rnd;
    }

    public Shape getBrickFace() {
        return brickFace;
    }
}
