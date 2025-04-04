package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// To test run: java -jar target/mazerunner.jar -i ./examples/small.maz.txt
public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        InputManager inputManager = new InputManager(args);
        logger.info("** Starting Maze Runner");
        try {

            Maze<Position, String> maze = new RectangleMaze(inputManager.getFilePath());

            Path path = new Path(maze);
            String userPath = inputManager.getUserPath();
            if (userPath == null) {
                System.out.println("Runner path: " + path.getPath());
            }
            else {
                logger.info("Before");
                if (path.isPathValid(userPath)) {
                    System.out.println("User path given solves maze!");
                }
                else {
                    System.out.println("User path given does not solve maze!");
                }
                System.out.println("Runner path: " + path.getPath());
            }
            System.out.println("Runner canoncial path: " + path.getCanonicalPath());
            System.out.println("Runner factorized path: " + path.getFactorizedPath());

        } catch(Exception e) {
            System.err.println(e);
        }
  
        logger.info("** End of MazeRunner");
    }
}
