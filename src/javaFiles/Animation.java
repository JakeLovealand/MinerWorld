package javaFiles;

public class Animation implements Action {

    private AbstractAnimaton entity;
    private int repeatCount;

    public Animation(AbstractAnimaton entity, int repeatCount) {
        this.entity = entity;
        this.repeatCount = repeatCount;
    }

    public void executeAction(EventScheduler eventScheduler)
    {
        this.entity.nextImage();

        if (this.repeatCount != 1)
        {
            eventScheduler.scheduleEvent(this.entity,
                    createAnimationAction(this.entity,
                            Math.max(this.repeatCount - 1, 0)),
                    this.entity.getAnimationPeriod());
        }
    }

    public static Animation createAnimationAction(AbstractAnimaton entity, int repeatCount)
    {
        return new Animation(entity, repeatCount);
    }


}
