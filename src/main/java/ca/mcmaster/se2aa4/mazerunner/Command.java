package ca.mcmaster.se2aa4.mazerunner;

public abstract class Command {
    protected Runner player;

    public Command(Runner player) {
        this.player = player;
    }

    public abstract void execute();
}
