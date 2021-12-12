package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This class is for creating the collision of brick in the game.
 * @author Chow Wen Jun
 *  @category Software Maintenance
 *  @version 15.0.2
 *  @since 12/12/2021
 */
abstract class Brick {

    public static final int DEF_CRACK_DEPTH = 1, DEF_STEPS = 35;

    public static final int UP_IMPACT = 100, DOWN_IMPACT = 200, LEFT_IMPACT = 300, RIGHT_IMPACT = 400;


    public class Crack {

        private static final int CRACK_SECTIONS = 3;
        private static final double JUMP_PROBABILITY = 0.7;

        public static final int LEFT = 10, RIGHT = 20, UP = 30, DOWN = 40, VERTICAL = 100, HORIZONTAL = 200;


        private final GeneralPath crack;

        private final int crackDepth, steps;


        public Crack(int crackDepth, int steps) {

            crack = new GeneralPath();
            this.crackDepth = crackDepth;
            this.steps = steps;

        }


        public GeneralPath draw() {

            return crack;
        }

        public void reset() {
            crack.reset();
        }

        protected void makeCrack(Point2D point, int direction) {
            Rectangle bounds = Brick.this.brickFace.getBounds();

            Point impact = new Point((int) point.getX(), (int) point.getY());
            Point start = new Point();
            Point end = new Point();


            switch (direction) {
                case LEFT:
                    start.setLocation(bounds.x + bounds.width, bounds.y);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    Point tmp = makeRandomPoint(start, end, VERTICAL);
                    makeCrack(impact, tmp);

                    break;
                case RIGHT:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start, end, VERTICAL);
                    makeCrack(impact, tmp);

                    break;
                case UP:
                    start.setLocation(bounds.x, bounds.y + bounds.height);
                    end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                    tmp = makeRandomPoint(start, end, HORIZONTAL);
                    makeCrack(impact, tmp);
                    break;
                case DOWN:
                    start.setLocation(bounds.getLocation());
                    end.setLocation(bounds.x + bounds.width, bounds.y);
                    tmp = makeRandomPoint(start, end, HORIZONTAL);
                    makeCrack(impact, tmp);
                    break;

            }
        }

        protected void makeCrack(Point start, Point end) {

            GeneralPath path = new GeneralPath();


            path.moveTo(start.x, start.y);

            double w = (end.x - start.x) / (double) steps;
            double h = (end.y - start.y) / (double) steps;

            int bound = crackDepth;
            int jump = bound * 5;

            double x, y;

            for (int i = 1; i < steps; i++) {

                x = (i * w) + start.x;
                y = (i * h) + start.y + randomInBounds(bound);

                if (inMiddle(i, steps))
                    y += jumps(jump);

                path.lineTo(x, y);

            }

            path.lineTo(end.x, end.y);
            crack.append(path, true);
        }

        private int randomInBounds(int bound) {
            int n = (bound * 2) + 1;
            return rnd.nextInt(n) - bound;
        }

        private boolean inMiddle(int i, int divisions) {
            int low = (Crack.CRACK_SECTIONS / divisions);
            int up = low * (divisions - 1);

            return (i > low) && (i < up);
        }

        private int jumps(int bound) {

            if (rnd.nextDouble() > Crack.JUMP_PROBABILITY)
                return randomInBounds(bound);
            return 0;

        }

        private Point makeRandomPoint(Point from, Point to, int direction) {

            Point out = new Point();
            int pos;

            switch (direction) {
                case HORIZONTAL -> {
                    pos = rnd.nextInt(to.x - from.x) + from.x;
                    out.setLocation(pos, to.y);
                }
                case VERTICAL -> {
                    pos = rnd.nextInt(to.y - from.y) + from.y;
                    out.setLocation(to.x, pos);
                }
            }
            return out;
        }

    }

    private static Random rnd;

    Shape brickFace;

    private final Color border;
    private final Color inner;

    private final int fullStrength;
    private int strength;

    private boolean broken;


    public Brick(Point pos, Dimension size, Color border, Color inner, int strength) {
        rnd = new Random();
        broken = false;
        brickFace = makeBrickFace(pos, size);
        this.border = border;
        this.inner = inner;
        this.fullStrength = this.strength = strength;

    }

    protected abstract Shape makeBrickFace(Point pos, Dimension size);

    public boolean setImpact(Point2D point, int dir) {
        if (broken)
            return false;
        impact();
        return broken;
    }

    public abstract Shape getBrick();


    public Color getBorderColor() {
        return border;
    }

    public Color getInnerColor() {
        return inner;
    }


    public final int findImpact(Ball b) {
        if (broken)
            return 0;
        var out = new AtomicInteger();
        if (brickFace.contains(b.getRight()))
            out.set(LEFT_IMPACT);
        else if (brickFace.contains(b.getLeft()))
            out.set(RIGHT_IMPACT);
        else if (brickFace.contains(b.getUp()))
            out.set(DOWN_IMPACT);
        else if (brickFace.contains(b.getDown()))
            out.set(UP_IMPACT);
        else
            return -1;
        return out.get();
    }

    public final boolean isBroken() {
        return !broken;
    }

    public void repair() {
        broken = false;
        strength = fullStrength;
    }

    public void impact() {
        strength--;
        broken = (strength == 0);
    }
}





