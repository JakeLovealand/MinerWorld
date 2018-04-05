package javaFiles;

import processing.core.PImage;

import java.util.List;

public abstract class AbstractExecutable extends AbstractEntity implements Executable{

    protected int actionPeriod;

    public AbstractExecutable(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod) {
        super(id, position, images, imageIndex);
        this.actionPeriod = actionPeriod;
    }

    public int getActionPeriod() {return actionPeriod;}

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
        scheduler.scheduleEvent(this,
                Activity.createActivityAction(this, world, imageStore),
                this.getActionPeriod());
    }

    public abstract void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore);

}

