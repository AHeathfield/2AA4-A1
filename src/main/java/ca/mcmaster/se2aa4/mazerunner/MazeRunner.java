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
    private ArrayList<Instruction> currentPath;    // Maybe have the Path control the path


    // Constructor
    public MazeRunner(Position position, Direction direction) {
        this.currentPos = position;
        this.currentDir = direction;
    }


    // Runner explores Maze and reports back the exact path they took
    public ArrayList<Instruction> exploreMaze(Maze<Position, String> maze) {
        this.currentPath.clear();
        int moves = 0;
        boolean pathNotFound = true;
        logger.info("**** Computing path");

        while (pathNotFound) {
            // First move in the maze will always be forward to enter the maze
            if (moves == 0 && isWallInFront(maze)) {
                return this.currentPath;
            }
            else if (moves == 0) {
                doInstruction(Instruction.FORWARD);
            }


            // This checks to see if the maze is possible or if the maze has been solved
            if (this.currentPos.equals(maze.getEntryPos())) {
                this.currentPath.clear();   // Empty path indicates impossible maze
                return this.currentPath;
            }
            // SOLVED!!!
            else if (this.currentPos.equals(maze.getExitPos())) {
                logger.info("Final position (x,y): ({}, {})", this.currentPos.x, this.currentPos.y);
                pathNotFound = false;
                continue; // Could just break
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
            instructions.add(Instruction.LEFT);
        }
        else {
            if (isWallToRight(maze)) {
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
    public Direction getCurrentDirection() {
        return currentDir;
    }


    @Override
    public void setCurrentDirection(Direction direction) {
        currentDir = direction;
    }


    @Override
    public Position getCurrentPosition() {
        return currentPos;
    }


    @Override
    public void setCurrentPosition(Position position) {
        currentPos = position;
    }


    @Override
    public void doInstruction(Instruction instruction) {
        // Position only changes when instruction to runner is move FORWARD
        if (instruction == Instruction.FORWARD) {
            this.currentPos = getForwardPos(this.currentPos, this.currentDir);
        }
        else {
            this.currentDir = getUpdatedDirection(instruction);
        }

        // Adds the instruction to the currentPath
        currentPath.add(instruction);
    }


    private Direction getUpdatedDirection(Instruction instruction) {
        // %360 ensures it will always be between [0, 360)
        switch (instruction) {
            case Instruction.LEFT:
                this.currentAngle += 90;
                this.currentAngle %= 360;
                break;
            case Instruction.RIGHT:
                this.currentAngle += 270;
                this.currentAngle %= 360;
                break;
            default:
                break;
        }

        return Direction.convertAngleToDir(this.currentAngle);
    }


    // I want t
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
        Position posInFront = getForwardPos(this.currentPos, this.currentDir);

        Direction playerRight = getUpdatedDirection(Instruction.RIGHT);
        Position posToRight = getForwardPos(posInFront, playerRight);

        return maze.isWallAtPos(posToRight);
    }
}
