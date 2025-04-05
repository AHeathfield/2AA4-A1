package ca.mcmaster.se2aa4.mazerunner.Commands;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.mazerunner.*;

public class MoveForwardCommand extends Command {
    private final Logger logger = LogManager.getLogger();

    public MoveForwardCommand(Runner<Direction, Instruction> player) {
        super(player);
    }

    @Override
    public void execute() {
        Position playerPos = player.getPosition();
        Direction playerDir = player.getDirection();
        player.setPosition(Position.getForwardPosition(playerPos, playerDir));
        logger.info("Moved Forward!");
    }
}
