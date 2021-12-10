package test;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * Created by filippo on 04/09/16.
 */
abstract public class Ball {

    private Shape ballFace;

    private Point2D center;

    private Point2D up, down, left, right;

    private Color border, inner;

    private int speedX, speedY;


    public Ball(Point2D center, int radiusA, int radiusB, Color inner, Color border) {
        this.setCenter(center);

        setUp(new Point2D.Double());
        setDown(new Point2D.Double());
        setLeft(new Point2D.Double());
        setRight(new Point2D.Double());

        getUp().setLocation(center.getX(),
                center.getY() - (radiusB / 2));
        getDown().setLocation(center.getX(),
                center.getY() + (radiusB / 2));

        getLeft().setLocation(center.getX() - (radiusA / 2),
                center.getY());
        getRight().setLocation(center.getX() + (radiusA / 2),
                center.getY());


        setBallFace(makeBall(center, radiusA, radiusB));
        this.setBorder(border);
        this.setInner(inner);
        setSpeedX(0);
        setSpeedY(0);
    }

    protected abstract Shape makeBall(Point2D center, int radiusA, int radiusB);

    public void move() {
        RectangularShape tmp = (RectangularShape) getBallFace();
        getCenter().setLocation((getCenter().getX() + getSpeedX()), (getCenter().getY() + getSpeedY()));
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((getCenter().getX() - (w / 2)), (getCenter().getY() - (h / 2)), w, h);
        setPoints(w, h);


        setBallFace(tmp);
    }

    public void setSpeed(int x, int y) {
        setSpeedX(x);
        setSpeedY(y);
    }

    public void setXSpeed(int s) {
        setSpeedX(s);
    }

    public void setYSpeed(int s) {
        setSpeedY(s);
    }

    public void reverseX() {
        setSpeedX(getSpeedX() * -1);
    }

    public void reverseY() {
        setSpeedY(getSpeedY() * -1);
    }

    public Color getBorderColor() {
        return getBorder();
    }

    public Color getInnerColor() {
        return getInner();
    }

    public Point2D getPosition() {
        return getCenter();
    }

    public Shape getBallFace() {
        return ballFace;
    }

    public void moveTo(Point p) {
        getCenter().setLocation(p);

        RectangularShape tmp = (RectangularShape) getBallFace();
        double w = tmp.getWidth();
        double h = tmp.getHeight();

        tmp.setFrame((getCenter().getX() - (w / 2)), (getCenter().getY() - (h / 2)), w, h);
        setBallFace(tmp);
    }

    private void setPoints(double width, double height) {
        getUp().setLocation(getCenter().getX(), getCenter().getY() - (height / 2));
        getDown().setLocation(getCenter().getX(), getCenter().getY() + (height / 2));

        getLeft().setLocation(getCenter().getX() - (width / 2), getCenter().getY());
        getRight().setLocation(getCenter().getX() + (width / 2), getCenter().getY());
    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }


    public void setBallFace(Shape ballFace) {
        this.ballFace = ballFace;
    }

    public Point2D getCenter() {
        return center;
    }

    public void setCenter(Point2D center) {
        this.center = center;
    }

    public Point2D getUp() {
        return up;
    }

    public void setUp(Point2D up) {
        this.up = up;
    }

    public Point2D getDown() {
        return down;
    }

    public void setDown(Point2D down) {
        this.down = down;
    }

    public Point2D getLeft() {
        return left;
    }

    public void setLeft(Point2D left) {
        this.left = left;
    }

    public Point2D getRight() {
        return right;
    }

    public void setRight(Point2D right) {
        this.right = right;
    }

    public Color getBorder() {
        return border;
    }

    public void setBorder(Color border) {
        this.border = border;
    }

    public Color getInner() {
        return inner;
    }

    public void setInner(Color inner) {
        this.inner = inner;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }
}
