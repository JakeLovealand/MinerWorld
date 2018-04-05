package javaFiles;

import processing.core.PImage;

import java.util.List;

public abstract class AbstractMiner extends AbstractAnimaton implements Animaton{

    protected int resourceLimit;
    protected int resourceCount;

    public AbstractMiner(String id, Point position, List<PImage> images, int imageIndex, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
        this.resourceLimit = resourceLimit;
        this.resourceCount = resourceCount;
    }

    public Point nextPositionMiner(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - getPosition().x);
        Point newPos = new Point(getPosition().x + horiz,
                getPosition().y);

        if (horiz == 0 || world.isOccupied(newPos))
        {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x,
                    getPosition().y + vert);

            if (vert == 0 || world.isOccupied(newPos))
            {
                newPos = getPosition();
            }
        }

        return newPos;
    }
}
