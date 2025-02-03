package ca.mcmaster.se2aa4.mazerunner;


/*
 * The runner interface defines methods that all runners must have.
 * It must include a "Direction" and "Instruction" type, (implementations may vary)
 */
interface Runner<D, I> {
    public D getCurrentDirection();
    public void setCurrentDirection(D direction);

    public Position getCurrentPosition();
    public void setCurrentPosition(Position position);

    public void doInstruction(I instruciton);
}
