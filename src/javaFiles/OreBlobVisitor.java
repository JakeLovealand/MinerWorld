package javaFiles;

public class OreBlobVisitor extends AllFalseEntityVisitor {
    @Override
    public Boolean visit(OreBlob oreBlob) {
        return true;
    }
}
