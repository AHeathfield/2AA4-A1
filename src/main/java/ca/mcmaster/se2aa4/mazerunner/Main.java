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

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        IOManager ioManager = new IOManager(args);
        // System.out.println("** Starting Maze Runner");
        logger.info("** Starting Maze Runner");
        try {
            System.out.println(ioManager.getFilePath());
            System.out.println(ioManager.getUserPath());

            Maze maze = new Maze(ioManager.getFilePath());
            maze.testDisplay();
            System.out.println("Entry: " + maze.getEntryPos());
            System.out.println("Exit: " + maze.getExitPos());
            
        } catch(Exception e) {
            System.err.println("/!\\ An error has occured /!\\");
        }
  
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}