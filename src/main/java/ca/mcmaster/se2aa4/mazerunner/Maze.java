package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/*
 * This class contains the Maze
 */
public class Maze {
    private static final Logger logger = LogManager.getLogger();
    private HashMap<String, String> maze = new HashMap<>();
    private String entryPos;    // (column,row) = (x,y)
    private String exitPos;
    private int totalRows;
    private int totalCols;
    
    

    public Maze(String filePath) {
        setUp(filePath);
    }

    private void setUp(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            logger.info("**** Reading the maze from file {}", filePath);

            // Reads file and Hashmap
            int row = 0;
            String pos;
            while ((line = reader.readLine()) != null) {
                for (int col = 0; col < line.length(); col++) {
                    pos = col + "," + row;
                    if (line.charAt(col) == '#') {
                        this.maze.put(pos, "#");
                    } 
                    else if (line.charAt(col) == ' ') {
                        if (col == 0) {
                            logger.info("**** Entrance (EAST) position (x,y): {}", pos);
                            this.entryPos = pos;
                        }
                        else if( col == line.length() - 1) {
                            logger.info("**** Exit (WEST) position (x,y): {}", pos);
                            this.exitPos = pos;
                        }
                        this.maze.put(pos, " ");
                    }
                }
                row++;
                this.totalCols = line.length();
            }
            this.totalRows = row;

            reader.close();
            logger.info("**** Successfully read maze.");

        } catch(Exception e) {
            System.err.println("/!\\ An error has occured /!\\");
        }
    }

    public HashMap<String, String> getMazeMap() {
        return this.maze;
    }

    public String getEntryPos() {
        return this.entryPos;
    }

    public String getExitPos() {
        return this.exitPos;
    }

    public int getTotalRows() {
        return this.totalRows;
    }

    public int getTotalCols() {
        return this.totalCols;
    }

    public boolean isWallAtPosition(String position) {
        return this.maze.get(position).equals("#");
    }


    public void testDisplay() {
        int row, col;
        String pos;
        for (row = 0; row < totalRows; row++) {
            for (col = 0; col < totalCols; col++) {
                pos = col + "," + row;
                System.out.print(this.maze.get(pos) + " ");
            }
            System.err.println();
        }
    }
}
