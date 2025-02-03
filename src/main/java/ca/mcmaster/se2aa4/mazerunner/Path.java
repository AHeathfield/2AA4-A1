package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * This will find the path to the maze and create the displaay option
 */
public class Path {
    // private enum Instruction {
    //     FORWARD,
    //     RIGHT,
    //     LEFT
    // }
    // private enum Direction {
    //     UP,
    //     RIGHT,
    //     LEFT,
    //     DOWN
    // }
    private static final Logger logger = LogManager.getLogger();
    private int currentAngle = 0;   // This is used to calculate the currentDir
    private Direction currentDir;
    private String currentPos;
    private ArrayList<Instruction> instructHistory = new ArrayList<>();
    private Maze maze;
    private boolean possible = true;
    private String path = "";
    private int moves = 0;

    // Contructor
    public Path(Maze maze) {
        this.maze = maze;
        this.currentPos = maze.getEntryPos();   // "x,y"
        this.setupStartDirection();
        // These are dummies
        // this.instructHistory.add(Instruction.FORWARD);
        // this.instructHistory.add(Instruction.FORWARD);
        // this.instructHistory.add(Instruction.FORWARD);
        // this.instructHistory.add(Instruction.FORWARD);
        findPath();
    }

    public String getPath() {
        return this.path;
    }

    // Path finding algorithm, this will find a path if it exists
    private void findPath() {
        boolean pathNotFound = true;
        logger.info("**** Computing path");
        while (this.possible && pathNotFound) {
            // logger.info("Pos: {}", this.currentPos);
            
            // First move in the maze will always be forward to enter the maze
            if (this.moves == 0 && isWallInFront()) {
                this.possible = false;
                continue; // Could just break
            }
            else if (this.moves == 0) {
                doInstruction(Instruction.FORWARD);
                continue;
            }

            // This checks to see if the maze is possible or if the maze has been solved
            if (this.currentPos.equals(maze.getEntryPos())) {
                // logger.info("TEST3");
                this.possible = false;
                continue; // Could just break
            }
            // SOLVED!!!
            else if (this.currentPos.equals(maze.getExitPos())) {
                logger.info("Final position (x,y): {}", this.currentPos);
                pathNotFound = false;
                continue; // Could just break
            }

            // This part is for getting through the maze
            // There's only 1 case where its 2 instructions, all other are just 1
            for (Instruction next : getNextInstructions()) {
                doInstruction(next);
            }
            
        }

        // By this point the path should be updated and same with the boolean saying if its possible
        // Uncomment when working
        if (!this.possible) {
            logger.info("No path found");
            this.path = "Not possible"; // Because there is no path
        }
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
    

    // This 
    private void doInstruction(Instruction instruction) {
        // logger.info("doInstruction");
        this.instructHistory.add(instruction);
        // logger.info("doInstruction");
        this.moves++;
        switch (instruction) {
            case FORWARD:
                // logger.info("FORWARD");
                this.path += "F";
                break;
            case LEFT:
                this.path += "L";
                break;
            case RIGHT:
                this.path += "R";
                break;
            default:
                break;
        }

        // This actually applys the instruction to the runner
        // logger.info("doInstruction BEFORE");
        updateRunner(instruction);
        // logger.info("doInstruction AFTER");
    }

    // Updates runner values (pos, dir)
    private void updateRunner(Instruction instruction) {
        this.currentAngle = getNewAngle(instruction);   // Needed to update direciton
        this.currentDir = Direction.convertAngleToDir(this.currentAngle);

        // Position only changes when instruction to runner is move FORWARD
        if (instruction == Instruction.FORWARD) {
            this.currentPos = getNewPosition(this.currentPos, this.currentDir);
        }
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

    // This returns a Direction based on an angle
    // private Direction getNewDirection(int angle) {
    //     switch (angle) {
    //         case 0:
    //             return Direction.RIGHT;
    //         case 90:
    //             return Direction.UP;
    //         case 180:
    //             return Direction.LEFT;
    //         case 270:
    //             return Direction.DOWN;
    //         default:
    //             return this.currentDir;
    //     }
    // }

    // Moves the currentPos of the runner based on the direction they were facing
    // Called when the Instruction is FORWARD
    private String getNewPosition(String position, Direction direction) {
        String[] pos = position.split("[,]");
        int posX = Integer.parseInt(pos[0]);
        int posY = Integer.parseInt(pos[1]);

        switch (direction) {
            case UP:
                posY -= 1;
                break;
            case DOWN:
                posY += 1;
                break;
            case RIGHT:
                posX += 1;
                break;
            case LEFT:
                posX -= 1;
                break;
            default:
                break;
        }

        return posX + "," + posY;    // Updates the currentPos after moving forward
    }

    
    // Checks to see if there's a wall to the right IF THEY WERE TO MOVE FORWARD
    private boolean isWallToRight() {
        String runnerPosForward = getNewPosition(this.currentPos, this.currentDir);

        int angleToRight = getNewAngle(Instruction.RIGHT);
        Direction playerRight = Direction.convertAngleToDir(angleToRight);
        String posToRight = getNewPosition(runnerPosForward, playerRight);

        return this.maze.isWallAtPosition(posToRight);
    }

    // Checks to see if there's a wall in front of runner CURRENT POS
    private boolean isWallInFront() {
        String posInFront = getNewPosition(this.currentPos, this.currentDir);
        // logger.info("Front Pos: {}", posInFront);
        return this.maze.isWallAtPosition(posInFront);
    }


    // Determines starting direction (depends if starting on west or east side)
    private void setupStartDirection() {
        if (this.currentPos.equals(this.maze.getEntryPos())) {
            logger.info("Starting at EAST SIDE");
            this.currentDir = Direction.RIGHT;
        }
        else {
            logger.info("Starting at WEST SIDE");
            this.currentDir = Direction.LEFT;
        }
    }
}
