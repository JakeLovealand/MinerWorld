package javaFiles;

public class MinerVisitor extends AllFalseEntityVisitor {
    @Override
    public Boolean visit(MinerFull minerFull) {
        return true;
    }
}
