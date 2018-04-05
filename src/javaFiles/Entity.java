package javaFiles;

import processing.core.PImage;

interface Entity{
   PImage getCurrentImage();
   void nextImage();
   Point getPosition();
   void setPosition(Point position);
   <R> R accept(EntityVisitor<R> visitor);

}
