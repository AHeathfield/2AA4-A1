package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * This class handles all user input and all outputs
 */
public class IOManager {
    private static final Logger logger = LogManager.getLogger();
    private String filePath = "null";
    private String userPath = "null";

    // Constructor
    public IOManager(String[] args)
    {
        setUp(args);
    }

    /*
     *  Setups the mangager by reading the command line arguements
     */ 
    private void setUp(String[] args) {
        try {
            logger.info("**** Parsing command line arguements.");

            // Creating Option (using Apache CLI)
            Options options = new Options();

            // add i option
            options.addOption("i", true, "Gets file path to maze.");
            options.addOption("p", true, "Gets end user path to check.");

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(options, args);

            // Checks flags
            if (cmd.hasOption("i")) {
                this.filePath = cmd.getOptionValue("i");
                logger.info("File path: {}", this.filePath);
            }
            if (cmd.hasOption("p")) {
                this.userPath = cmd.getOptionValue("p");
                logger.info("User path: {}", this.userPath);
            }

            logger.info("**** Successfully parsed command line.");

        } catch(Exception e) {
            System.err.println("/!\\ An error has occured /!\\");
        }
    }


    public String getFilePath() {
        return this.filePath;
    }

    public String getUserPath() {
        return this.userPath;
    }
}
