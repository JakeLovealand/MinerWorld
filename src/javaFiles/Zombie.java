package javaFiles;

import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Zombie extends AbstractAnimaton {

    PathingStrategy strategy = new AStar();

    public Zombie(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(id, position, images, imageIndex, actionPeriod, animationPeriod);
    }

    public void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        Optional<Entity> zombTarget = world.findNearest(getPosition(), MinerNotFull.class);
        long nextPeriod = getActionPeriod();

        if (zombTarget.isPresent())
        {
            MinerNotFull tgt = (MinerNotFull) zombTarget.get();
            Point tgtPos = tgt.getPosition();
            if (this.moveToZombie(world, tgt, eventScheduler))
            {
                Executable zombie = (Executable)WorldModel.createZombie( tgtPos,
                        imageStore.getImageList("zombie"));

                world.addEntity(zombie);
                nextPeriod += getActionPeriod();
                zombie.scheduleActions(eventScheduler, world, imageStore);
            }
        }

        eventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), nextPeriod);
    }

    public boolean moveToZombie(WorldModel world, MinerNotFull target, EventScheduler scheduler)
    {
        if (Point.adjacent(getPosition(), target.getPosition()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {

            List<Point> path = strategy.computePath(this.position,target.position, p -> world.withinBounds(p) && !world.isOccupied(p), (p1,p2) -> Point.adjacent(p1,p2), PathingStrategy.CARDINAL_NEIGHBORS);
            Point nextPos = path.remove(path.size()-2);

            if (!getPosition().equals(nextPos))
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

    @Override
    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}
