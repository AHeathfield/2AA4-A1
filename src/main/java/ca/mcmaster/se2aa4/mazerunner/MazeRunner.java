package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * This simulates a person running through a maze
 */
public class MazeRunner implements Runner<Direction, Instruction> {
    private static final Logger logger = LogManager.getLogger();
    private Position currentPos;
    private Direction currentDir;
    private int currentAngle;
    private ArrayList<Instruction> currentPath = new ArrayList<>();    // Maybe have the Path control the path


    // Constructor
    public MazeRunner(Position position, Direction direction) {
        this.currentPos = position.deepCopy();
        this.currentDir = direction;
    }


    // Runner explores Maze and reports back the exact path they took
    public ArrayList<Instruction> exploreMaze(Maze<Position, String> maze) {
        this.currentAngle = Direction.convertDirToAngle(this.currentDir);
        this.currentPath.clear();
        //logger.info("**** Computing path");

        while (true) {
            // logger.info("Pos: ({}, {})", this.currentPos.x, this.currentPos.y);
            // logger.info("Current Angle: {}", this.currentAngle);
            // First move in the maze will always be forward to enter the maze
            if (this.currentPath.isEmpty() && isWallInFront(maze)) {
                break;
            }
            else if (this.currentPath.isEmpty()) {
                doInstruction(Instruction.FORWARD);
                if (currentDir == Direction.RIGHT) {
                    logger.info("FACING RIGHT");
                }
                logger.info("Pos: ({}, {})", this.currentPos.x, this.currentPos.y);
            }


            // This checks to see if the maze is possible or if the maze has been solved
            if (this.currentPos.equals(maze.getEntryPos())) {
                logger.info("TEST");
                logger.info("Pos: ({}, {})", this.currentPos.x, this.currentPos.y);
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
            for (Instruction next : getNextInstructions(maze)) {
                doInstruction(next);
            }
        }
        return this.currentPath;
    }


    // This is the logic of keeping right hand on the wall of the maze
    private ArrayList<Instruction> getNextInstructions(Maze<Position, String> maze) {
        ArrayList<Instruction> instructions = new ArrayList<>();

        if (isWallInFront(maze)) {
            // logger.info("There's a wall in front of pos: {},{} angle: {}", currentPos.x, currentPos.y, currentAngle);
            instructions.add(Instruction.LEFT);
        }
        else {
            
            if (isWallToRight(maze)) {
                // logger.info("Should move forward.");
                instructions.add(Instruction.FORWARD);
            }
            // Idea is you move forward and then turn RIGHT since there will be no wall
            // to your right, if we turn LEFT it gives the opportunity of just looping
            // back and forth.
            else {
                // //logger.info("Should move forward then turn right.");
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
        // this.currentDir = getNewDirection(this.currentAngle);
        this.currentDir = Direction.convertAngleToDir(this.currentAngle);

        // Position only changes when instruction to runner is move FORWARD
        if (instruction == Instruction.FORWARD) {
            this.currentPos = getForwardPos(this.currentPos, this.currentDir);
        }
        currentPath.add(instruction);
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
    private boolean isWallInFront(Maze<Position, String> maze) {
        Position posInFront = getForwardPos(this.currentPos, this.currentDir);
        return maze.isWallAtPos(posInFront);
    }


    // Checks to see if there's a wall to the right IF THEY WERE TO MOVE FORWARD
    private boolean isWallToRight(Maze<Position, String> maze) {
        Position runnerPosForward = getForwardPos(this.currentPos, this.currentDir);

        int angleToRight = getNewAngle(Instruction.RIGHT);
        Direction playerRight = Direction.convertAngleToDir(angleToRight);
        Position posToRight = getForwardPos(runnerPosForward, playerRight);

        return maze.isWallAtPos(posToRight);
    }
}
