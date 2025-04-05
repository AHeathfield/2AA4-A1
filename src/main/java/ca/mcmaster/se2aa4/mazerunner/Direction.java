package ca.mcmaster.se2aa4.mazerunner;

public enum Direction {
    UP,
    DOWN,
    RIGHT,
    LEFT;

    private Direction rightDirection;
    private Direction leftDirection;

    // Will happen when class is made to just set up some values
    static {
        UP.rightDirection = RIGHT;
        UP.leftDirection = LEFT;

        DOWN.rightDirection = LEFT;
        DOWN.leftDirection = RIGHT;

        RIGHT.leftDirection = UP;
        RIGHT.rightDirection = DOWN;

        LEFT.rightDirection = UP;
        LEFT.leftDirection = DOWN;
    }

    public Direction getRightDirection() {
        return this.rightDirection;
    }

    public Direction getLeftDirection() {
        return this.leftDirection;
    }


    // Given an angle returns the corresponding direction (similar to factory pattern but not really)
    public static Direction convertAngleToDir(int angle) {
        angle = angle % 360;
        switch (angle) {
            case 0:
                return Direction.RIGHT;
            case 90:
                return Direction.UP;
            case 180:
                return Direction.LEFT;
            case 270:
                return Direction.DOWN;
            default:
                throw new IllegalArgumentException("Angle must be on the axis (0, 90, 180, 270,...)");
        }
    }


    // Given a direction it returns it corresponding angle
    public static int convertDirToAngle(Direction dir) {
        switch (dir) {
            case RIGHT:
                return 0;
            case UP:
                return 90;
            case LEFT:
                return 180;
            case DOWN:
                return 270;
            default:
                throw new IllegalArgumentException("The direciton must be a valid direction, either UP, LEFT, RIGHT, or DOWN.");
        }
    }
}
