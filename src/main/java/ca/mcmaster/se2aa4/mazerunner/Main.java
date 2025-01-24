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
        // System.out.println("** Starting Maze Runner");
        logger.info("** Starting Maze Runner");
        try {
            // Creating Option (using Apache CLI)
            Options options = new Options();

            // add i option
            options.addOption("i", true, "Gets value of the command right after -i");

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            if (cmd.hasOption("i")) {
                String file = cmd.getOptionValue("i");
                // System.out.println("**** Reading the maze from file " + file);
                logger.info("**** Reading the maze from file {}", file);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;
                while ((line = reader.readLine()) != null) {
                    for (int idx = 0; idx < line.length(); idx++) {
                        if (line.charAt(idx) == '#') {
                            System.out.print("WALL ");
                        } else if (line.charAt(idx) == ' ') {
                            System.out.print("PASS ");
                        }
                    }
                    System.out.print(System.lineSeparator());
                }
            }
            
        } catch(Exception e) {
            System.err.println("/!\\ An error has occured /!\\");
        }
        // System.out.println("**** Computing path");
        // System.out.println("PATH NOT COMPUTED");
        // System.out.println("** End of MazeRunner");
        logger.info("**** Computing path");
        logger.info("PATH NOT COMPUTED");
        logger.info("** End of MazeRunner");
    }
}