package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// To test run: java -jar target/mazerunner.jar -i ./examples/small.maz.txt
public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        IOManager ioManager = new IOManager(args);
        // System.out.println("** Starting Maze Runner");
        logger.info("** Starting Maze Runner");
        try {
            // System.out.println(ioManager.getFilePath());
            // System.out.println(ioManager.getUserPath());

            RectangleMaze maze = new RectangleMaze(ioManager.getFilePath());
            // maze.testDisplay();
            // logger.info("Entry: {}", maze.getEntryPos());
            // logger.info("Exit: {}", maze.getExitPos());

            // logger.info("**** Computing path");
            Path path = new Path(maze);
            System.out.println("PATH: " + path.getPath());
            
            
        } catch(Exception e) {
            System.err.println(e);
        }
  
        
        // logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}