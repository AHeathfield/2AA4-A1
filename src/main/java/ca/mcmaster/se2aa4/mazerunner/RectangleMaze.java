package ca.mcmaster.se2aa4.mazerunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Map;
import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class RectangleMaze implements Maze<Position, String> {
    private static final Logger logger = LogManager.getLogger();
    private Map<Position, String> maze = new HashMap<>();
    private Position entryPos;
    private Position exitPos;
    private int sizeY;
    private int sizeX;
    

    public RectangleMaze(String filePath) {
        setUp(filePath);
    }


    private void setUp(String filePath) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;

            logger.info("**** Reading the maze from file {}", filePath);

            // Reads file and Hashmap
            int y = 0;
            int x = 0;
            while ((line = reader.readLine()) != null) {
                for (x = 0; x < line.length(); x++) {
                    Position pos = new Position(x, y);
                    if (line.charAt(x) == '#') {
                        this.maze.put(pos, "#");
                    } 
                    else if (line.charAt(x) == ' ') {
                        if (x == 0) {
                            logger.info("**** Entrance (EAST) position (x,y): ({},{})", pos.x, pos.y);
                            this.entryPos = pos;
                        }
                        else if( x == line.length() - 1) {
                            logger.info("**** Exit (WEST) position (x,y): ({},{})", pos.x, pos.y);
                            this.exitPos = pos;
                        }
                        this.maze.put(pos, " ");
                    }
                }
                y++;
                this.sizeX = line.length();
            }
            this.sizeY = y;

            reader.close();
            logger.info("**** Successfully read maze.");

        } catch(Exception e) {
            System.err.println("/!\\ An error has occured /!\\");
        }
    }


    @Override
    public Map<Position, String> getMazeMap() {
        return this.maze;
    }

    @Override
    public Position getEntryPos() {
        return this.entryPos;
    }

    @Override
    public Position getExitPos() {
        return this.exitPos;
    }


    @Override
    public boolean isWallAtPos(Position position) {      
        if (this.maze.containsKey(position)) {
            return this.maze.get(position).equals("#");
        }
        return false;
    }

    public void testDisplay() {
        int y, x;
        Position pos = new Position();
        for (y = 0; y < sizeY; y++) {
            for (x = 0; x < sizeX; x++) {
                System.out.print(this.maze.get(pos) + " ");
            }
            System.err.println();
        }
    }
}
