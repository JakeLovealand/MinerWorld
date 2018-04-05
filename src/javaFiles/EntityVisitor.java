package javaFiles;

public interface EntityVisitor<R>
{
    R visit(Ore ore);
    R visit(OreBlob oreBlob);
    R visit(Blacksmith blacksmith);
    R visit(Obstacle obstacle);
    R visit(MinerNotFull minerNotFull);
    R visit(MinerFull minerFull);
    R visit(Vein vein);
    R visit(Quake quake);
    R visit(Zombie zombie);



}


