package javaFiles;

import processing.core.PImage;

import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;

public class OreBlob extends AbstractAnimaton{
    private static final String QUAKE_KEY = "quake";


    public OreBlob(String id, Point position, List<PImage> images, int imageIndex, int actionPeriod, int animationPeriod) {
        super(id,position,images,imageIndex,actionPeriod,animationPeriod);
    }

    public void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore)
    {
        Optional<Entity> blobTarget = world.findNearest(getPosition(), Vein.class);
        long nextPeriod = getActionPeriod();

        if (blobTarget.isPresent())
        {
            Vein tgt = (Vein)blobTarget.get();
            Point tgtPos = tgt.getPosition();
            if (this.moveToOreBlob(world, tgt, eventScheduler))
            {
                Executable quake = (Executable)WorldModel.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += getActionPeriod();
                quake.scheduleActions(eventScheduler, world, imageStore);
            }
        }

        eventScheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), nextPeriod);
    }

    public boolean moveToOreBlob(WorldModel world, Vein target, EventScheduler scheduler)
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

    public Point nextPositionOreBlob(WorldModel world, Point destPos)
    {
        int horiz = Integer.signum(destPos.x - getPosition().x);
        Point newPos = new Point(getPosition().x + horiz,
                getPosition().y);

        Optional<Entity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().getClass().equals("Ore"))))
        {
            int vert = Integer.signum(destPos.y - getPosition().y);
            newPos = new Point(getPosition().x, getPosition().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get().getClass().equals("Ore"))))
            {
                newPos = getPosition();
            }
        }
        return newPos;
    }

    protected int getRepeatCount() { return 0; }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}
