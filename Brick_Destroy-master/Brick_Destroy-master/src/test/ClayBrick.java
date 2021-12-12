package test;

import java.awt.*;


/**
 * Clay brick which harder than normal brick but weaker than Cement brick.
 * @author hauru
 * @category Software Maintenance
 * @version 15.0.2
 * @since 12/12/2021
 */
public final class ClayBrick extends Brick {

    private static final Color DEF_INNER = new Color(178, 34, 34).darker();
    private static final Color DEF_BORDER = Color.GRAY;
    private static final int CLAY_STRENGTH = 1;


    /**
     * @param point this is a point object.
     * @param size this is a size object.
     */
    public ClayBrick(Point point, Dimension size) {
        super(point, size, getDefBorder(), getDefInner(), getClayStrength());
    }

    public static Color getDefInner() {
        return DEF_INNER;
    }

    public static Color getDefBorder() {
        return DEF_BORDER;
    }

    public static int getClayStrength() {
        return CLAY_STRENGTH;
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public Shape getBrick() {
        return super.brickFace;
    }


}
