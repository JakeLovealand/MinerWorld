package javaFiles;

public class Activity implements Action {

    private AbstractExecutable entity;
    private WorldModel world;
    private ImageStore imageStore;

    public Activity(AbstractExecutable entity, WorldModel world, ImageStore imageStore) {
        this.entity = entity;
        this.world = world;
        this.imageStore = imageStore;
    }

    public void executeAction(EventScheduler eventScheduler) {
        entity.executeActivity(eventScheduler, this.world,
                this.imageStore);
    }

    public static Activity createActivityAction(AbstractExecutable entity, WorldModel world,
                                              ImageStore imageStore)
    {
        return new Activity(entity, world, imageStore);
    }


}
