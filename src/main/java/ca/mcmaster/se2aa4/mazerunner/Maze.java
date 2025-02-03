package ca.mcmaster.se2aa4.mazerunner;

import java.util.Map;

/*
 * This is the maze interface
 */
interface Maze<K, V> {
    public Map<K, V> getMazeMap();

    public Position getEntryPos();

    public Position getExitPos();

    public boolean isWallAtPos(K position);
}
