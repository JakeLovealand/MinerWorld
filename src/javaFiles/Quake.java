package javaFiles;

import processing.core.PImage;

import java.util.List;

public class Quake extends AbstractAnimaton {

    private PathingStrategy strategy = null;

    public Quake(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
    }

    public void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        eventScheduler.unscheduleAllEvents(this);
        world.removeEntity(this);
    }

    protected int getRepeatCount() {return 10;}

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}
