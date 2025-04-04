package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

/*
 * The runner interface defines methods that all runners must have.
 * It must include a "Direction" and "Instruction" type, (implementations may vary)
 */
interface Runner<D, I> {
    public D getDirection();
    public void setDirection(D direction);

    public Position getPosition();
    public void setPosition(Position position);

    public void doInstruction(I instruciton);

    public List<I> exploreMaze();
}
