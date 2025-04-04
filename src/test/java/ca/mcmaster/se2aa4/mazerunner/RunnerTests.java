package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunnerTests {
    @Test
    public void runnerStartingPositionForRegularMazeTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner<Direction, Instruction> mazeRunner = new MazeRunner(regularMaze);
        Position expectedStartingPos = new Position(0, 33);
        // Starting position should be at entrance
        assertEquals(expectedStartingPos, mazeRunner.getPosition());
    }
    
    @Test
    public void runnerStartingDirectionTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner<Direction, Instruction> mazeRunner = new MazeRunner(regularMaze);
        // Should always start to facing the right
        Direction expectedStartingDir = Direction.RIGHT;
        assertEquals(expectedStartingDir, mazeRunner.getDirection());
    }

    @Test
    public void runnerTurnRightInstructionTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner<Direction, Instruction> mazeRunner = new MazeRunner(regularMaze);

        // Should always start to facing the right, so turning right will face downwards
        mazeRunner.doInstruction(Instruction.RIGHT);
        Direction expectedDir = Direction.DOWN;
        assertEquals(expectedDir, mazeRunner.getDirection());
    }

    @Test
    public void runnerTurnLeftInstructionTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner<Direction, Instruction> mazeRunner = new MazeRunner(regularMaze);

        // Should always start to facing the right, so turning left will face upwards
        mazeRunner.doInstruction(Instruction.LEFT);
Direction expectedDir = Direction.UP;
        assertEquals(expectedDir, mazeRunner.getDirection());
    }


    @Test
    public void runnerForwardInstructionTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner<Direction, Instruction> mazeRunner = new MazeRunner(regularMaze);

        // Should always start to facing the right, so turning left will face upwards
        mazeRunner.doInstruction(Instruction.FORWARD);
        Direction expectedDir = Direction.RIGHT;
        Position expectedPos = new Position(1, 33);
        boolean dirsEqual = expectedDir.equals(mazeRunner.getDirection());
        boolean posEqual = expectedPos.equals(mazeRunner.getPosition());
        
        // Testing if both direction and position are equal
        assertTrue(dirsEqual && posEqual);
    }

    @Test
    public void exploreStraightMazeTest() {
        Maze<Position, String> straightMaze = getMaze("straight"); 
        Runner<Direction, Instruction> mazeRunner = new MazeRunner(straightMaze);

        List<Instruction> expectedPath = new ArrayList<>();
        expectedPath.add(Instruction.FORWARD);
        expectedPath.add(Instruction.FORWARD);
        expectedPath.add(Instruction.FORWARD);
        expectedPath.add(Instruction.FORWARD);

        assertEquals(expectedPath, mazeRunner.exploreMaze());
    }


    @Test
    public void checkCorrectGivenPathTest() {
        Maze<Position, String> smallMaze = getMaze("small"); 
        Runner<Direction, Instruction> mazeRunner = new MazeRunner(smallMaze);
        
        List<Instruction> givenWorkingPath = new ArrayList<>();
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.LEFT);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.RIGHT);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.LEFT);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.RIGHT);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.RIGHT);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.LEFT);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.RIGHT);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.LEFT);
        givenWorkingPath.add(Instruction.FORWARD);
        
        assertTrue(mazeRunner.testPath(givenWorkingPath));
    }

    @Test
    public void checkIncorrectGivenPathTest() {
        Maze<Position, String> smallMaze = getMaze("small"); 
        Runner<Direction, Instruction> mazeRunner = new MazeRunner(smallMaze);
        
        List<Instruction> givenWorkingPath = new ArrayList<>();
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.LEFT);
        givenWorkingPath.add(Instruction.FORWARD);
        givenWorkingPath.add(Instruction.FORWARD);
        assertFalse(mazeRunner.testPath(givenWorkingPath));
    }

    // Helper method
    private Maze<Position, String> getMaze(String mazeName) {
        String filePath = "examples/" + mazeName + ".maz.txt";
        Maze<Position, String> maze = new RectangleMaze(filePath);
        return maze;
    }
}
