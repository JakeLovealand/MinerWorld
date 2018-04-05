package javaFiles;

import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class MinerNotFull extends AbstractMiner {


    public MinerNotFull(String id, Point position, List<PImage> images, int imageIndex, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        Optional<Entity> notFullTarget = world.findNearest(getPosition(),
                Ore.class);

        if (!notFullTarget.isPresent() ||
                !this.moveToNotFull(world, (Ore)notFullTarget.get(), eventScheduler) ||
                !this.transformNotFull(world, eventScheduler, imageStore))
        {
            eventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), getActionPeriod());
        }
    }

    public boolean moveToNotFull(WorldModel world, Ore target, EventScheduler scheduler)
    {
        if (Point.adjacent(this.position, target.getPosition()))
        {
            this.resourceCount += 1;
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);

            return true;
        }
        else
        {
            List<Point> path = strategy.computePath(this.position,target.position, p -> world.withinBounds(p) && !world.isOccupied(p), (p1,p2) -> Point.adjacent(p1,p2), PathingStrategy.CARDINAL_NEIGHBORS);
            Point nextPos = path.remove(path.size()-2);

            if (!this.position.equals(nextPos))
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

    public boolean transformNotFull(WorldModel world,
                                    EventScheduler scheduler, ImageStore imageStore)
    {
        if (resourceCount >= resourceLimit)
        {
            Executable miner = (Executable)WorldModel.createMinerFull(id, resourceLimit,
                    position, actionPeriod, animationPeriod, images);

            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            miner.scheduleActions(scheduler, world, imageStore);

            return true;
        }

        return false;
    }

    @Override
    protected int getRepeatCount() {return 0;}

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }

}
