package ca.mcmaster.se2aa4.mazerunner;

// Client Interface (API) in the Adapter Pattern
// Used to convert between the 3 formatted String CommandList(my own), canoncial, and factorized
public interface PathFormatter {
    public String getPath();
}
