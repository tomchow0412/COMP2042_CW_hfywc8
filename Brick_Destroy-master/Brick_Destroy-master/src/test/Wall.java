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


class Wall {

    private static final int LEVELS_COUNT = 4;

    private static final int CLAY = 1;
    private static final int STEEL = 2;
    private static final int CEMENT = 3;

    private final Random rnd;
    private final Rectangle area;

    private Brick[] bricks;
    private Ball ball;
    private Player player;

    private final Brick[][] levels;
    private int level;

    private final Point startPoint;
    private int brickCount;
    private int ballCount;
    private boolean ballLost;

    public Wall(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea, brickCount, lineCount, brickDimensionRatio);
        level = 0;

        setBallCount(3);
        setBallLost(false);

        rnd = new Random();

        makeBall(ballPos);
        int speedX, speedY;
        do {
            speedX = getRnd().nextInt(5) - 2;
        } while (speedX == 0);
        do {
            speedY = -getRnd().nextInt(3);
        } while (speedY == 0);

        getBall().setSpeed(speedX, speedY);

        setPlayer(new Player((Point) ballPos.clone(), 150, 10, drawArea));

        area = drawArea;


    }

    public static int getLevelsCount() {
        return LEVELS_COUNT;
    }

    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            double x = (i % brickOnLine) * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, Wall.CLAY);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = new ClayBrick(p, brickSize);
        }
        return tmp;

    }

    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickCnt, int lineCnt, double brickSizeRatio, int typeA, int typeB) {
        /*
          if brickCount is not divisible by line count,brickCount is adjusted to the biggest
          multiple of lineCount smaller then brickCount
         */
        brickCnt -= brickCnt % lineCnt;

        int brickOnLine = brickCnt / lineCnt;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickCnt += lineCnt / 2;

        Brick[] tmp = new Brick[brickCnt];

        Dimension brickSize = new Dimension((int) brickLen, (int) brickHgt);
        Point p = new Point();

        int i;
        for (i = 0; i < tmp.length; i++) {
            int line = i / brickOnLine;
            if (line == lineCnt)
                break;
            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x = (line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ? makeBrick(p, brickSize, typeA) : makeBrick(p, brickSize, typeB);
        }

        for (double y = brickHgt; i < tmp.length; i++, y += 2 * brickHgt) {
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, typeA);
        }
        return tmp;
    }

    private void makeBall(Point2D ballPos) {
        setBall(new RubberBall(ballPos));
    }

    private Brick[][] makeLevels(Rectangle drawArea, int brickCount, int lineCount, double brickDimensionRatio) {
        Brick[][] tmp = new Brick[getLevelsCount()][];
        tmp[0] = makeSingleTypeLevel(drawArea, brickCount, lineCount, brickDimensionRatio);
        tmp[1] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, CEMENT);
        tmp[2] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, CLAY, STEEL);
        tmp[3] = makeChessboardLevel(drawArea, brickCount, lineCount, brickDimensionRatio, STEEL, CEMENT);
        return tmp;
    }

    public void move() {
        getPlayer().move();
        getBall().move();
    }

    public void findImpacts() {
        if (getPlayer().impact(getBall())) {
            getBall().reverseY();
        } else if (impactWall()) {
            /*for efficiency reverse is done into method impactWall
             * because for every brick program checks for horizontal and vertical impacts
             */
            setBrickCount(getBrickCount() - 1);
        } else if (impactBorder()) {
            getBall().reverseX();
        } else if (ImpactY()) {
            getBall().reverseY();
        } else if (ImpactEmpty()) {
            setBallCount(getBallCount() - 1);
            setBallLost(true);
        }
    }

    private boolean ImpactY (){
        return getBall().getPosition().getY() < getArea().getY();
    }

    private boolean ImpactEmpty(){
        return getBall().getPosition().getY() > getArea().getY() + getArea().getHeight();
    }

    private boolean impactWall() {
        for (Brick b : getBricks()) {
            switch (b.findImpact(getBall())) {
                //Vertical Impact
                case Brick.UP_IMPACT -> {
                    getBall().reverseY();
                    return b.setImpact(getBall().getDown(), Brick.Crack.UP);
                }
                case Brick.DOWN_IMPACT -> {
                    getBall().reverseY();
                    return b.setImpact(getBall().getUp(), Brick.Crack.DOWN);
                }

                //Horizontal Impact
                case Brick.LEFT_IMPACT -> {
                    getBall().reverseX();
                    return b.setImpact(getBall().getRight(), Brick.Crack.RIGHT);
                }
                case Brick.RIGHT_IMPACT -> {
                    getBall().reverseX();
                    return b.setImpact(getBall().getLeft(), Brick.Crack.LEFT);
                }
            }
        }
        return false;
    }

    private boolean impactBorder() {
        Point2D p = getBall().getPosition();
        return ((p.getX() < getArea().getX()) || (p.getX() > (getArea().getX() + getArea().getWidth())));
    }

    public int getBrickCount() {
        return brickCount;
    }

    public int getBallCount() {
        return ballCount;
    }

    public boolean isBallLost() {
        return ballLost;
    }

    public void ballReset() {
        getPlayer().moveTo(getStartPoint());
        getBall().moveTo(getStartPoint());
        int speedX, speedY;
        do {
            speedX = getRnd().nextInt(5) - 2;
        } while (speedX == 0);
        do {
            speedY = -getRnd().nextInt(3);
        } while (speedY == 0);

        getBall().setSpeed(speedX, speedY);
        setBallLost(false);
    }

    public void wallReset() {
        for (Brick b : getBricks())
            b.repair();
        setBrickCount(getBricks().length);
        setBallCount(3);
    }

    public boolean ballEnd() {
        return getBallCount() == 0;
    }

    public boolean isDone() {
        return getBrickCount() == 0;
    }

    public void nextLevel() {
        setBricks(levels[level++]);
        this.setBrickCount(getBricks().length);
    }

    public boolean hasLevel() {
        return level < levels.length;
    }

    public void setBallXSpeed(int s) {
        getBall().setXSpeed(s);
    }

    public void setBallYSpeed(int s) {
        getBall().setYSpeed(s);
    }

    public void resetBallCount() {
        setBallCount(3);
    }

    private Brick makeBrick(Point point, Dimension size, int type) {
        return switch (type) {
            case CLAY -> new ClayBrick(point, size);
            case STEEL -> new SteelBrick(point, size);
            case CEMENT -> new CementBrick(point, size);
            default -> throw new IllegalArgumentException(String.format("Unknown Type:%d\n", type));
        };
    }

    /**
     * rnd means random.
     */
    public Random getRnd() {
        return rnd;
    }

    public Rectangle getArea() {
        return area;
    }

    public Brick[] getBricks() {
        return bricks;
    }

    public void setBricks(Brick[] bricks) {
        this.bricks = bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Point getStartPoint() {
        return startPoint;
    }

    public void setBrickCount(int brickCount) {
        this.brickCount = brickCount;
    }

    public void setBallCount(int ballCount) {
        this.ballCount = ballCount;
    }

    public void setBallLost(boolean ballLost) {
        this.ballLost = ballLost;
    }
}
