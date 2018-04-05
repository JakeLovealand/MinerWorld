package javaFiles;

public class ObstacleVisitor extends AllFalseEntityVisitor {
    @Override
    public Boolean visit(Obstacle obstacle) {
        return true;
    }
}
