package ca.mcmaster.se2aa4.mazerunner;

import ca.mcmaster.se2aa4.mazerunner.Commands.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RunnerTests {
    @Test
    public void runnerStartingPositionForRegularMazeTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner mazeRunner = new MazeRunner(regularMaze);
        Position expectedStartingPos = new Position(0, 33);
        // Starting position should be at entrance
        assertEquals(expectedStartingPos, mazeRunner.getPosition());
    }
    
    @Test
    public void runnerStartingDirectionTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner mazeRunner = new MazeRunner(regularMaze);
        // Should always start to facing the right
        Direction expectedStartingDir = Direction.RIGHT;
        assertEquals(expectedStartingDir, mazeRunner.getDirection());
    }

    @Test
    public void runnerTurnRightInstructionTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner mazeRunner = new MazeRunner(regularMaze);

        // Should always start to facing the right, so turning right will face downwards
        mazeRunner.executeCommand(new TurnRightCommand(mazeRunner));
        Direction expectedDir = Direction.DOWN;
        assertEquals(expectedDir, mazeRunner.getDirection());
    }

    @Test
    public void runnerTurnLeftInstructionTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner mazeRunner = new MazeRunner(regularMaze);

        // Should always start to facing the right, so turning left will face upwards
        mazeRunner.executeCommand(new TurnLeftCommand(mazeRunner));
Direction expectedDir = Direction.UP;
        assertEquals(expectedDir, mazeRunner.getDirection());
    }


    @Test
    public void runnerForwardInstructionTest() {
        Maze<Position, String> regularMaze = getMaze("regular"); 
        Runner mazeRunner = new MazeRunner(regularMaze);

        // Should always start to facing the right, so turning left will face upwards
        mazeRunner.executeCommand(new MoveForwardCommand(mazeRunner));
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
        Runner mazeRunner = new MazeRunner(straightMaze);

        List<Command> expectedPath = new ArrayList<>();
        expectedPath.add(new MoveForwardCommand(mazeRunner));
        expectedPath.add(new MoveForwardCommand(mazeRunner));
        expectedPath.add(new MoveForwardCommand(mazeRunner));
        expectedPath.add(new MoveForwardCommand(mazeRunner));

        List<Command> runnerPathCommandsList = mazeRunner.exploreMaze();        

        assertTrue(areCommandListsEqual(expectedPath, runnerPathCommandsList));
    }


    @Test
    public void checkCorrectGivenPathTest() {
        Maze<Position, String> smallMaze = getMaze("small"); 
        Runner mazeRunner = new MazeRunner(smallMaze);
        
        List<Command> givenWorkingPath = new ArrayList<>();
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnLeftCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnRightCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnLeftCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnRightCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnRightCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnLeftCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnRightCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnLeftCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        
        assertTrue(mazeRunner.testPath(givenWorkingPath));
    }

    @Test
    public void checkIncorrectGivenPathTest() {
        Maze<Position, String> smallMaze = getMaze("small"); 
        Runner mazeRunner = new MazeRunner(smallMaze);
        
        List<Command> givenWorkingPath = new ArrayList<>();
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new TurnLeftCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        givenWorkingPath.add(new MoveForwardCommand(mazeRunner));
        assertFalse(mazeRunner.testPath(givenWorkingPath));
    }

    // Helper method
    private Maze<Position, String> getMaze(String mazeName) {
        String filePath = "examples/" + mazeName + ".maz.txt";
        Maze<Position, String> maze = new RectangleMaze(filePath);
        return maze;
    }

    private boolean areCommandListsEqual(List<Command> list1, List<Command> list2) {
        boolean isCommandsSame = true;

        if (list1.size() == list2.size()) {
            for (int i = 0; i < list1.size(); i++) {
                Command list1Command = list1.get(i);
                Command list2Command = list2.get(i);
                if (!list1Command.getClass().isInstance(list2Command)) {
                    isCommandsSame = false;
                    break;
                }
            }
        }
        else {
            isCommandsSame = false;
        }
        
        return isCommandsSame;
    }
}
