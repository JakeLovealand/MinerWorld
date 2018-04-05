package javaFiles;

import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerFull extends AbstractMiner {

    public MinerFull(String id, Point position, List<PImage> images, int imageIndex, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        Optional<Entity> fullTarget = world.findNearest(getPosition(),
                Blacksmith.class);

        if (fullTarget.isPresent() &&
                this.moveToFull(world, (Blacksmith)fullTarget.get(), eventScheduler))
        {
            this.transformFull(world, eventScheduler, imageStore);
        }
        else
        {
            eventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore),
                    getActionPeriod());
        }
    }

    public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore)
    {
        Executable miner = (Executable)WorldModel.createMinerNotFull(getId(), resourceLimit,
                getPosition(), getActionPeriod(), animationPeriod, images);

        world.removeEntity(this);
        scheduler.unscheduleAllEvents(this);

        world.addEntity(miner);
        miner.scheduleActions(scheduler, world, imageStore);
    }

    public boolean moveToFull(WorldModel world, Blacksmith target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.position, target.getPosition()))
        {
            return true;
        }
        else
        {
            List<Point> path = strategy.computePath(this.position,target.position, p -> world.withinBounds(p) && !world.isOccupied(p), (p1,p2) -> Point.adjacent(p1,p2), PathingStrategy.CARDINAL_NEIGHBORS);
            Point nextPos = path.remove(path.size()-2);

            if (!position.equals(nextPos))
            {
                Optional<Entity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }

    protected int getRepeatCount() { return 0; }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}
