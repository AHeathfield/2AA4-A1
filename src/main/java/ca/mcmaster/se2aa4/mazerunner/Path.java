package ca.mcmaster.se2aa4.mazerunner;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * This will find the path to the maze and create the displaay option
 */
public class Path {
    private static final Logger logger = LogManager.getLogger();
    private MazeRunner runner;
    // private RectangleMaze maze;
    private String path;

    // Contructor
    public Path(RectangleMaze maze) {
        // this.maze = maze;
        setUpRunner(maze);
        retrievePath(); // Gets path that runner took
    }


    // Retrieves all the instructions the runner did and converts it to a string
    public void retrievePath() {
        path = "";
        ArrayList<Instruction> runnerPath = this.runner.exploreMaze();
        logger.info("Array size: {}", runnerPath.size());
        for (Instruction instruction : runnerPath) {
            logInstruction(instruction);
        }
    }


    // Adds instruction to path
    private void logInstruction(Instruction instruction) {
        switch(instruction) {
            case Instruction.FORWARD:
                path += "F";
                break;
            case Instruction.RIGHT:
                path += "R";
                break;
            case Instruction.LEFT:
                path+= "L";
                break;
            default:
                break;
        }
    }


    public String getPath() {
        return path;
    }


    // Determines starting direction (depends if starting on west or east side)
    private void setUpRunner(Maze<Position, String> maze) {
        Position entry = maze.getEntryPos();

        if (entry.x == 0) {
            logger.info("Starting at EAST SIDE");
            this.runner = new MazeRunner(maze.getEntryPos(), Direction.RIGHT, maze);
        }
        else {
            logger.info("Starting at WEST SIDE");
            this.runner = new MazeRunner(maze.getEntryPos(), Direction.LEFT, maze);
        }
    }
}
