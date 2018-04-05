package javaFiles;

import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Ore extends AbstractExecutable {

    private static final Random rand = new Random();
    private static final String BLOB_KEY = "blob";
    private static final String BLOB_ID_SUFFIX = " -- blob";
    private static final int BLOB_PERIOD_SCALE = 4;
    private static final int BLOB_ANIMATION_MIN = 50;
    private static final int BLOB_ANIMATION_MAX = 150;

    public Ore(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod) {
        super(id, position, images, imageIndex, actionPeriod);
    }

    public void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        Point pos = getPosition();  // store current position before removing

        world.removeEntity(this);
        eventScheduler.unscheduleAllEvents(this);

        Executable blob = (Executable)WorldModel.createOreBlob(getId() + BLOB_ID_SUFFIX,
                pos, getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN +
                        rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        blob.scheduleActions(eventScheduler, world, imageStore);
    }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }

}
