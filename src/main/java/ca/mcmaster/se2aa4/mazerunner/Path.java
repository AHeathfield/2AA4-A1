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
    private String path;

    // Contructors
    public Path(RectangleMaze maze) {
        // this.maze = maze;
        // setUpRunner(maze);
        this.runner = new MazeRunner(maze);
        retrievePath(); // Gets path that runner took
    }

    public Path(RectangleMaze maze, String userPath) {
        // setUpRunner(maze);

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
}
