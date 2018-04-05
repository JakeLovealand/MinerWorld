package javaFiles;

import processing.core.PImage;
import java.util.List;

public abstract class AbstractEntity implements Entity{
    protected String id;
    protected Point position;
    protected List<PImage> images;
    protected int imageIndex;

    public AbstractEntity(String id, Point position, List<PImage> images, int imageIndex) {
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = imageIndex;
    }

    public String getId() {return id;}

    public void setPosition(Point position) {this.position = position;}

    public Point getPosition() {return position;}

    public PImage getCurrentImage()
    {
        return this.images.get(this.imageIndex);
    }

    public void nextImage()
    {
        imageIndex = (imageIndex + 1) % images.size();
    }



}
