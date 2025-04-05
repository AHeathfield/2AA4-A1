package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.Commands.MoveForwardCommand;
import ca.mcmaster.se2aa4.mazerunner.Commands.TurnLeftCommand;
import ca.mcmaster.se2aa4.mazerunner.Commands.TurnRightCommand;

/*
 * This will find the path to the maze and create the displaay option
 */
public class Path {
    private static final Logger logger = LogManager.getLogger();
    private Runner player;
    private List<Command> exploredCommandsList;
    private String rawExploredPath; // Explored Path
    // private String userPath;

    // Contructor
    public Path(Maze<Position, String> maze) {
        this.player = new MazeRunner(maze);
        this.exploredCommandsList = player.exploreMaze();
        retrievePath();
        // this.userPath = userPath;
    }


    // Retrieves all the instructions the runner did and converts it to a string
    public void retrievePath() {
        logger.info("Array size: {}", exploredCommandsList.size());
        this.rawExploredPath = convertCommandsToRawPath(exploredCommandsList);
    }


    // See's if user inputted path is valid
    public boolean isPathValid(String userPath) {
        // Using the PathFormatter
        PathFormatter formatter = new UserPathAdapter(this, userPath);
        String rawPath = formatter.getPath();
        List<Command> commands = convertRawPathToCommands(rawPath);

        return player.testPath(commands);
    }


    // public String getCanonicalPath() {
    //     if (this.path == null || this.path.isEmpty()) {
    //         return "";
    //     }
    //     
    //     StringBuilder formatted = new StringBuilder();
    //     formatted.append(this.path.charAt(0));
    //     
    //     for (int i = 1; i < this.path.length(); i++) {
    //         if (this.path.charAt(i) != this.path.charAt(i - 1)) {
    //             formatted.append(" ");
    //         }
    //         formatted.append(this.path.charAt(i));
    //     }
    //     
    //     return formatted.toString();
    // }


    // public String getFactorizedPath() {
    //     if (this.path == null || this.path.isEmpty()) {
    //         return "";
    //     }
    //     
    //     StringBuilder formatted = new StringBuilder();
    //     int count = 1;
    //     
    //     for (int i = 1; i < this.path.length(); i++) {
    //         if (this.path.charAt(i) == this.path.charAt(i - 1)) {
    //             count++;
    //         } else {
    //             if (count > 1) {
    //                 formatted.append(count);
    //             }
    //             formatted.append(this.path.charAt(i - 1)).append(" ");
    //             count = 1;
    //         }
    //     }
    //     if (count > 1) {
    //         formatted.append(count);
    //     }
    //     formatted.append(this.path.charAt(this.path.length() - 1));
    //     
    //     return formatted.toString();
    // }


    // Converts a List of commands to raw path
    private String convertCommandsToRawPath(List<Command> commands) {
        String rawPath = "";
        for (Command command : commands) {
            if (command instanceof MoveForwardCommand) {
                rawPath += "F";
            }
            else if (command instanceof TurnLeftCommand) {
                rawPath += "L";
            }
            else if (command instanceof TurnRightCommand) {
                rawPath += "R";
            }
        }
        return rawPath;
    }


    // Gets the instruciton corresponding with letter
    // private Instruction getInstruction(char letter) {
    //     switch(letter) {
    //         case 'F':
    //             return Instruction.FORWARD;
    //         case 'R':
    //             return Instruction.RIGHT;
    //         case 'L':
    //             return Instruction.LEFT;
    //         default:
    //             throw new IllegalArgumentException("Path must only consist of letters 'F', 'R', or 'L'.");
    //     }
    // }


    // Converts a path to list of instructions
    private List<Command> convertRawPathToCommands(String aRawPath) {
        List<Command> commands = new ArrayList<>();
        for (int i = 0; i < aRawPath.length(); i++) {
            switch (aRawPath.charAt(i)) {
                case 'F':
                    commands.add(new MoveForwardCommand(player));
                    break;
                case 'R':
                    commands.add(new TurnRightCommand(player));
                    break;
                case 'L':
                    commands.add(new TurnLeftCommand(player));
                    break;
            }
        }
        return commands;
    }

    // Raw is just no spaces only letters FFFRLRLRFRLFRFFFFRLLLLRRRFRFR
    public String getExploredRawPath() {
        return this.rawExploredPath;
    }

    // public String getUserPath() {
    //     return userPath;
    // }
}
