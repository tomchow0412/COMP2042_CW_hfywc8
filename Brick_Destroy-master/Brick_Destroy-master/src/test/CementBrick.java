package test;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class CementBrick extends Brick {


    /**
     * DEF_INNER is the brick between the outer brick.
     * CementBrick is a brick thicker than normal brick which requires
     * few collisions to break it.
     */
    private static final Color DEF_INNER = new Color(147, 147, 147);
    private static final Color DEF_BORDER = new Color(217, 199, 175);
    private static final int CEMENT_STRENGTH = 2;

    private final Crack crack;
    private Shape brickFace;


    public CementBrick(Point point, Dimension size) {
        super(point, size, getDefBorder(), getDefInner(), getCementStrength());
        crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS);
        setBrickFace(super.brickFace);
    }

    public static Color getDefInner() {
        return DEF_INNER;
    }

    public static Color getDefBorder() {
        return DEF_BORDER;
    }

    public static int getCementStrength() {
        return CEMENT_STRENGTH;
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {
        if (!super.isBroken())
            return false;
        super.impact();
        if (super.isBroken()) {
            getCrack().makeCrack(point, dir);
            updateBrick();
            return false;
        }
        return true;
    }


    @Override
    public Shape getBrick() {
        return getBrickFace();
    }

    private void updateBrick() {
        if (super.isBroken()) {
            GeneralPath gp = getCrack().draw();
            gp.append(super.brickFace, false);
            setBrickFace(gp);
        }
    }

    public void repair() {
        super.repair();
        getCrack().reset();
        setBrickFace(super.brickFace);
    }

    public Crack getCrack() {
        return crack;
    }

    public Shape getBrickFace() {
        return brickFace;
    }

    public void setBrickFace(Shape brickFace) {
        this.brickFace = brickFace;
    }
}
