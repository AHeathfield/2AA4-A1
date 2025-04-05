package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

/*
 * The runner interface defines methods that all runners must have.
 * TODO: I think I can remove the setters and getters completely
 */
public interface Runner {
    public Direction getDirection();
    public void setDirection(Direction direction);

    public Position getPosition();
    public void setPosition(Position position);

    // public boolean isWallInFront();
    // public boolean isWallInFrontRight();

    public void executeCommand(Command command);

    public List<Command> exploreMaze();
    public boolean testPath(List<Command> commands);
}
