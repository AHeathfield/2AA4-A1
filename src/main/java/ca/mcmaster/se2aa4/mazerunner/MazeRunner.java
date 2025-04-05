package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.Commands.*;

/*
 * This simulates a person running through a maze
 */
public class MazeRunner implements Runner {
    private static final Logger logger = LogManager.getLogger();
    private Maze<Position, String> maze;
    private Position currentPos;
    private Direction currentDir;
    private int currentAngle;
    private List<Command> currentPath = new ArrayList<>();    // Maybe have the Path control the path


    // Constructor
    public MazeRunner(Maze<Position, String> maze) {
        this.maze = maze;
        setUpRunner();
    }


    // This is to test the user path to see if it's valid or not
    @Override
    public boolean testPath(List<Command> commands) {
        this.currentPath.clear();
        setUpRunner();
        for (Command command : commands) {
            if (isWallInFront() && command instanceof MoveForwardCommand) {
                return false;
            }
            else {
                executeCommand(command);
            }
        }

        if (this.currentPos.equals(this.maze.getExitPos())) {
            return true;
        }
        else {
            return false;
        }
    }


    // Runner explores Maze and reports back the exact path they took
    public List<Command> exploreMaze() {
        // Explore Maze
        this.currentPath.clear();
        logger.info("**** Computing path");
        // First move in the maze will always be forward to enter the maze
        if (this.currentPath.isEmpty() && isWallInFront()) {
            return this.currentPath;
        }
        else {
            executeCommand(new MoveForwardCommand(this));
        }
        while (true) {
            // This checks to see if the maze is possible or if the maze has been solved
            if (this.currentPos.equals(this.maze.getEntryPos())) {
                this.currentPath.clear();   // Empty path indicates impossible maze
                break;
            }
            // SOLVED!!!
            else if (this.currentPos.equals(maze.getExitPos())) {
                logger.info("Final position (x,y): ({}, {})", this.currentPos.x, this.currentPos.y);
                break;
            }
            
            // Solving Maze...
            doNextCommands();
        }

        return this.currentPath;
    }


    // This is the logic of keeping right hand on the wall of the maze
    private void doNextCommands() {
        // Might not need
        if (this.currentPos.equals(maze.getExitPos())) {
            return;
        }

        if (isWallInFront()) {
            this.executeCommand(new TurnLeftCommand(this));
        }
        else if (this.isWallInFrontRight()) {
            this.executeCommand(new MoveForwardCommand(this));
        }
        else {
            this.executeCommand(new MoveForwardCommand(this));
            this.executeCommand(new TurnRightCommand(this));
        }
    }

    @Override
    public Direction getDirection() {
        return currentDir;
    }


    @Override
    public void setDirection(Direction direction) {
        currentDir = direction;
    }


    @Override
    public Position getPosition() {
        return currentPos;
    }


    @Override
    public void setPosition(Position position) {
        currentPos = position;
    }



    @Override
    public void executeCommand(Command command) {
        command.execute();
        currentPath.add(command);
    }


    // Checks to see if there's a wall in front of runner CURRENT POS
    private boolean isWallInFront() {
        Position posInFront = Position.getForwardPosition(this.currentPos, this.currentDir);
        return this.maze.isWallAtPos(posInFront);
    }


    // Checks to see if there's a wall to the right IF THEY WERE TO MOVE FORWARD
    private boolean isWallInFrontRight() {
        Position runnerPosForward = Position.getForwardPosition(this.currentPos, this.currentDir);
        Position posToFrontRight = Position.getForwardPosition(runnerPosForward, this.currentDir.getRightDirection());

        return this.maze.isWallAtPos(posToFrontRight);
    }


    // Sets up runner, if we need to start from the west side, we can solve the maze normally and just flip the path
    private void setUpRunner() {
        Position entry = this.maze.getEntryPos();
        this.currentPos = entry.deepCopy();
        this.currentDir = Direction.RIGHT;
    }
}
