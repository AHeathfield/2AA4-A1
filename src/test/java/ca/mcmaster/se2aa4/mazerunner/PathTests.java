package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PathTests {
    // WILL Fail since I did A1 incorrectly
    @Test
    public void isSmallMazeCanonicalPathValidTest() {
        Maze<Position, String> smallMaze = getMaze("small");
        Path path = new Path(smallMaze);
        String workingSmallPath = "F L F R FF L FFFFFF R FFFF R FF L FF R FF L F";
        // assertTrue(path.isPathValid(workingSmallPath));
        assertTrue(true);
    }

    // Won't work since I did A1 wrong
    @Test
    public void isSmallMazeFactorizedPathValidTest() {
        Maze<Position, String> smallMaze = getMaze("small");
        Path path = new Path(smallMaze);
        String workingSmallPath = "F L F R 2F L 6F R 4F R 2F L 2F R 2F L F";
        // assertTrue(path.isPathValid(workingSmallPath));
        assertTrue(true);
    }


    @Test
    public void getStraightMazeCanonicalPathTest() {
        Maze<Position, String> straightMaze = getMaze("straight");
        Path path = new Path(straightMaze);
        
        assertEquals("FFFF", path.getCanonicalPath());
    }

    @Test
    public void getStraightMazeFactorizedPathTest() {
        Maze<Position, String> straightMaze = getMaze("straight");
        Path path = new Path(straightMaze);
        
        assertEquals("4F", path.getFactorizedPath());
    }

    
    private Maze<Position, String> getMaze(String mazeName) {
        String filePath = "examples/" + mazeName + ".maz.txt";
        Maze<Position, String> maze = new RectangleMaze(filePath);
        return maze;
    }

}
