package ca.mcmaster.se2aa4.mazerunner;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class InputManagerTests {
    @Test
    public void checkGiantMazeFilePath() {
        String filePath = "examples/giant.maz.txt";
        String[] testArgs = buildTestInput(filePath);
        InputManager testInputManager = new InputManager(testArgs);
        assertEquals(filePath, testInputManager.getFilePath());
    }

    @Test
    public void checkMazeFilePathParse() {
        String filePath = "examples/small.maz.txt";
        String[] testArgs = buildTestInput(filePath);
        InputManager testInputManager = new InputManager(testArgs);
        assertEquals(filePath, testInputManager.getFilePath());
    }

    @Test
    public void checkUserPathParse() {
        String filePath = "examples/small.maz.txt";
        String userPath = "F R LLLL FFFFF RR FFFF LL F L F L FF";
        String[] testArgs = buildTestInput(filePath, userPath);
        InputManager testInputManager = new InputManager(testArgs);
        assertEquals(userPath, testInputManager.getUserPath());
    }
    
    private String[] buildTestInput(String filePath) {
        String[] testArgs = new String[2];
        testArgs[0] = "-i";
        testArgs[1] = filePath;
        return testArgs;
    }
    
    private String[] buildTestInput(String filePath, String userPath) {
        String[] testArgs = new String[4];
        testArgs[0] = "-i";
        testArgs[1] = filePath;
        testArgs[2] = "-p";
        testArgs[3] = userPath;
        return testArgs;
    }
}
