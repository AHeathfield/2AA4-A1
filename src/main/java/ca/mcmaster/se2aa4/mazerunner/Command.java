package ca.mcmaster.se2aa4.mazerunner;

public abstract class Command {
    protected Runner<Direction, Instruction> player;

    public Command(Runner<Direction, Instruction> player) {
        this.player = player;
    }

    public abstract void execute();
}
