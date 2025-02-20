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

    // Contructor
    public Path(RectangleMaze maze) {
        this.runner = new MazeRunner(maze);
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


    // See's if user inputted path is valid
    public boolean isPathValid(String aPath) {
        ArrayList<Instruction> instructions = convertPathToInstructions(aPath);
        logger.info("path converted");
        boolean valid = this.runner.testPath(instructions);
        logger.info("path validity checked");
        return valid;
    }


    public String getCanonicalPath() {
        if (this.path == null || this.path.isEmpty()) {
            return "";
        }
        
        StringBuilder formatted = new StringBuilder();
        formatted.append(this.path.charAt(0));
        
        for (int i = 1; i < this.path.length(); i++) {
            if (this.path.charAt(i) != this.path.charAt(i - 1)) {
                formatted.append(" ");
            }
            formatted.append(this.path.charAt(i));
        }
        
        return formatted.toString();
    }


    public String getFactorizedPath() {
        if (this.path == null || this.path.isEmpty()) {
            return "";
        }
        
        StringBuilder formatted = new StringBuilder();
        int count = 1;
        
        for (int i = 1; i < this.path.length(); i++) {
            if (this.path.charAt(i) == this.path.charAt(i - 1)) {
                count++;
            } else {
                if (count > 1) {
                    formatted.append(count);
                }
                formatted.append(this.path.charAt(i - 1)).append(" ");
                count = 1;
            }
        }
        if (count > 1) {
            formatted.append(count);
        }
        formatted.append(this.path.charAt(this.path.length() - 1));
        
        return formatted.toString();
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


    // Gets the instruciton corresponding with letter
    private Instruction getInstruction(char letter) {
        switch(letter) {
            case 'F':
                return Instruction.FORWARD;
            case 'R':
                return Instruction.RIGHT;
            case 'L':
                return Instruction.LEFT;
            default:
                throw new IllegalArgumentException("Path must only consist of letters 'F', 'R', or 'L'.");
        }
    }


    // Converts a path to list of instructions
    private ArrayList<Instruction> convertPathToInstructions(String aPath) {
        ArrayList<Instruction> userInstructions = new ArrayList<>();
        for (int i = 0; i < aPath.length(); i++) {
            userInstructions.add(getInstruction(aPath.charAt(i)));
        }
        return userInstructions;
    }


    public String getPath() {
        return path;
    }
}
