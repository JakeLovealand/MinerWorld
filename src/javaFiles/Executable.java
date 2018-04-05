package javaFiles;

public interface Executable extends Entity {
    void executeActivity(EventScheduler eventScheduler, WorldModel world, ImageStore imageStore);
    int getActionPeriod();
    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);


}
