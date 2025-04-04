package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MazeTests {
    @Test
    public void smallMazeGetEntrancePosTest() {
        String filePath = "examples/small.maz.txt";
        Maze<Position, String> smallMaze = new RectangleMaze(filePath);
        Position expectedEntryPos = new Position(0, 8);
        assertTrue(expectedEntryPos.equals(smallMaze.getEntryPos())); 
    }

    @Test
    public void regularMazeGetExitPosTest() {
        String filePath = "examples/regular.maz.txt";
        Maze<Position, String> regularMaze = new RectangleMaze(filePath);
        Position expectedExitPos = new Position(40, 27);
        assertTrue(expectedExitPos.equals(regularMaze.getExitPos()));
    }

    @Test
    public void giantMazeWallAtPositionCheck() {
        String filePath = "examples/giant.maz.txt";
        Maze<Position, String> giantMaze = new RectangleMaze(filePath);
        Position wallPos = new Position(0, 0);
        assertTrue(giantMaze.isWallAtPos(wallPos));
    }
}
