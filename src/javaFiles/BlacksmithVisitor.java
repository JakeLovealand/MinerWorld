package javaFiles;

public class BlacksmithVisitor extends AllFalseEntityVisitor {
    @Override
    public Boolean visit(Blacksmith blacksmith) {
        return true;
    }
}
