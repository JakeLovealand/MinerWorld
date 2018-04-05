package javaFiles;

public class MinerNotVisitor extends AllFalseEntityVisitor {
    @Override
    public Boolean visit(MinerNotFull minerNotFull) {
        return true;
    }
}
