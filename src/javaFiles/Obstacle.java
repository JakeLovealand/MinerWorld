package javaFiles;

import processing.core.PImage;

import java.util.List;

public class Obstacle extends AbstractEntity{

    public Obstacle(String id, Point position, List<PImage> images, int imageIndex) {
        super(id, position, images, imageIndex);
    }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }

}
