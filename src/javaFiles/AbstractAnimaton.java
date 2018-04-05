package javaFiles;

import processing.core.PImage;

import java.util.List;

public abstract class AbstractAnimaton extends AbstractExecutable implements Executable{

    protected int animationPeriod;
    protected PathingStrategy strategy = new AStar();


    public AbstractAnimaton(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(id,position,images,imageIndex,actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public int getAnimationPeriod() {return animationPeriod;}

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore)
    {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                Animation.createAnimationAction(this, getRepeatCount()),
                this.getAnimationPeriod());
    }

    protected abstract int getRepeatCount();

}

