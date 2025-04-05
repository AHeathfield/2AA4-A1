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
            
            //Using Adapter Pattern to get different path formats
            Path path = new Path(maze);
            PathFormatter canonicalPath = new CanonicalPathAdapter(path);
            PathFormatter factorizedPath = new FactorizedPathAdapter(path);

            String userPath = inputManager.getUserPath();

            if (userPath != null) {
                logger.info("Before");
                if (path.isPathValid(userPath)) {
                    System.out.println("User path given solves maze!");
                }
                else {
                    System.out.println("User path given does not solve maze!");
                }
            }

            System.out.println("Runner canoncial path: " + canonicalPath.getPath());
            System.out.println("Runner factorized path: " + factorizedPath.getPath());

        } catch(Exception e) {
            System.err.println(e);
        }
  
        logger.info("** End of MazeRunner");
    }
}
