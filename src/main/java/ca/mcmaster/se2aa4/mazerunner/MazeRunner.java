package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.Commands.MoveForward;

/*
 * This simulates a person running through a maze
 */
public class MazeRunner implements Runner<Direction, Instruction> {
    private static final Logger logger = LogManager.getLogger();
    private Maze<Position, String> maze;
    private Position currentPos;
    private Direction currentDir;
    private int currentAngle;
    private List<Instruction> currentPath = new ArrayList<>();    // Maybe have the Path control the path


    // Constructor
    public MazeRunner(Maze<Position, String> maze) {
        this.maze = maze;
        setUpRunner();
    }


    // This is to test the user path to see if it's valid or not
    @Override
    public boolean testPath(List<Instruction> instructions) {
        this.currentPath.clear();
        setUpRunner();
        for (Instruction instruction : instructions) {
            if (isWallInFront() &&  instruction == Instruction.FORWARD) {
                return false;
            }
            else {
                doInstruction(instruction);
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
    public List<Instruction> exploreMaze() {
        // Explore Maze
        executeCommand(new MoveForward(this));

        this.currentAngle = Direction.convertDirToAngle(this.currentDir);
        this.currentPath.clear();
        logger.info("**** Computing path");
        while (true) {
            // First move in the maze will always be forward to enter the maze
            if (this.currentPath.isEmpty() && isWallInFront()) {
                break;
            }
            else if (this.currentPath.isEmpty()) {
                doInstruction(Instruction.FORWARD);
            }


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

            // This part is for getting through the maze
            // There's only 1 case where its 2 instructions, all other are just 1
            for (Instruction next : getNextInstructions()) {
                doInstruction(next);
            }
        }
        return this.currentPath;
    }


    // This is the logic of keeping right hand on the wall of the maze
    private ArrayList<Instruction> getNextInstructions() {
        ArrayList<Instruction> instructions = new ArrayList<>();

        if (isWallInFront()) {
            instructions.add(Instruction.LEFT);
        }
        else {
            
            if (isWallToRight()) {
                instructions.add(Instruction.FORWARD);
            }
            // Idea is you move forward and then turn RIGHT since there will be no wall
            // to your right, if we turn LEFT it gives the opportunity of just looping
            // back and forth.
            else {
                instructions.add(Instruction.FORWARD);
                instructions.add(Instruction.RIGHT);
            }
        }
        
        return instructions;
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
    public void doInstruction(Instruction instruction) {
        this.currentAngle = getNewAngle(instruction);   // Needed to update direciton
        this.currentDir = Direction.convertAngleToDir(this.currentAngle);

        // Position only changes when instruction to runner is move FORWARD
        if (instruction == Instruction.FORWARD) {
            this.currentPos = getForwardPos(this.currentPos, this.currentDir);
        }
        currentPath.add(instruction);
    }

    @Override
    public void executeCommand(Command command) {
        command.execute();
    }

     // This updates the current angle of the maze runner (which direction their facing on a normal 
    // xy plane)
    private int getNewAngle(Instruction instruction) {
        // %360 ensures it will always be between [0, 360)
        switch (instruction) {
            case LEFT:
                return (this.currentAngle + 90) % 360;
            case RIGHT:
                return (this.currentAngle + 270) % 360;
            default:
                return this.currentAngle;
        }
    }


    // Gets the position in front of the position given based on the direction given
    private Position getForwardPos(Position position, Direction direction) {
        Position pos = position.deepCopy();
        switch (direction) {
            case Direction.UP:
                pos.y -= 1;
                break;
            case Direction.DOWN:
                pos.y += 1;
                break;
            case Direction.RIGHT:
                pos.x += 1;
                break;
            case Direction.LEFT:
                pos.x -= 1;
                break;
            default:
                break;
        }
        
        return pos;
    }


    // Checks to see if there's a wall in front of runner CURRENT POS
    private boolean isWallInFront() {
        Position posInFront = getForwardPos(this.currentPos, this.currentDir);
        return this.maze.isWallAtPos(posInFront);
    }


    // Checks to see if there's a wall to the right IF THEY WERE TO MOVE FORWARD
    private boolean isWallToRight() {
        Position runnerPosForward = getForwardPos(this.currentPos, this.currentDir);

        int angleToRight = getNewAngle(Instruction.RIGHT);
        Direction playerRight = Direction.convertAngleToDir(angleToRight);
        Position posToRight = getForwardPos(runnerPosForward, playerRight);

        return this.maze.isWallAtPos(posToRight);
    }


    // Sets up runner, if we need to start from the west side, we can solve the maze normally and just flip the path
    private void setUpRunner() {
        Position entry = this.maze.getEntryPos();
        this.currentPos = entry.deepCopy();
        this.currentDir = Direction.RIGHT;
    }
}
