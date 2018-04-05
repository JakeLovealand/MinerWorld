package javaFiles;

import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Vein extends AbstractExecutable {

    private static final Random rand = new Random();
    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    private static final String QUAKE_KEY = "quake";
    private static final String ORE_KEY = "ore";

    public Vein(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod) {
        super(id, position, images, imageIndex, actionPeriod);
    }

    public void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        Optional<Point> openPt = world.findOpenAround(getPosition());

        if (openPt.isPresent())
        {
            Executable ore = (Executable)WorldModel.createOre(ORE_ID_PREFIX + getId(),
                    openPt.get(), ORE_CORRUPT_MIN +
                            rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                    imageStore.getImageList(ORE_KEY));
            world.addEntity(ore);
            ore.scheduleActions(eventScheduler, world, imageStore);
        }

        eventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), getActionPeriod());
    }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }


}
