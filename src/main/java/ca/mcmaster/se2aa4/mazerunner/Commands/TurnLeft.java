package ca.mcmaster.se2aa4.mazerunner.Commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.*;

public class TurnLeft extends Command {
    private final Logger logger = LogManager.getLogger();

    public TurnLeft(Runner<Direction, Instruction> player) {
        super(player);
    }

    @Override
    public void execute() {
        logger.info("Turning left!"); 
    }
}

