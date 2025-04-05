package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

/*
 * The runner interface defines methods that all runners must have.
 * It must include a "Direction" and "Instruction" type, (implementations may vary)
 */
public interface Runner<D, I> {
    public D getDirection();
    public void setDirection(D direction);

    public Position getPosition();
    public void setPosition(Position position);

    public void doInstruction(I instruciton); // Plan to remove this
    public void executeCommand(Command command, I instruction);

    public List<I> exploreMaze();
    public boolean testPath(List<I> instructions);
}
