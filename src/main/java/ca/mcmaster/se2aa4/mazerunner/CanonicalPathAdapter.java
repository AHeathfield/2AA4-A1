package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CanonicalPathAdapter implements PathFormatter {
    private Path path;
    private final Logger logger = LogManager.getLogger();

    // In this case of the adapter, it's just the format of the string
    public CanonicalPathAdapter(Path path) {
        this.path = path;
    }

    // From raw path to canonical
    @Override
    public String getPath() {
        String rawPath = path.getExploredRawPath();   
        logger.info(rawPath);

        if (rawPath == null || rawPath.isEmpty()) {
            return "";
        }
        
        StringBuilder formatted = new StringBuilder();
        formatted.append(rawPath.charAt(0));
        
        for (int i = 1; i < rawPath.length(); i++) {
            if (rawPath.charAt(i) != rawPath.charAt(i - 1)) {
                formatted.append(" ");
            }
            formatted.append(rawPath.charAt(i));
        }
         
        return formatted.toString();
    }
}
