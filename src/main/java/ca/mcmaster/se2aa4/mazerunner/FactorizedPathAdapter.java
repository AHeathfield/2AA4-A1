package ca.mcmaster.se2aa4.mazerunner;

public class FactorizedPathAdapter implements PathFormatter {
    private Path path;
    

    // In this case of the adapter, it's just the format of the string
    public FactorizedPathAdapter(Path path) {
        this.path = path;
    }

    // From raw path to factorized
    @Override
    public String getPath() {
        String rawPath = path.getExploredRawPath();   

        if (rawPath == null || rawPath.isEmpty()) {
            return "";
        }
        
        StringBuilder formatted = new StringBuilder();
        int count = 1;
        
        for (int i = 1; i < rawPath.length(); i++) {
            if (rawPath.charAt(i) == rawPath.charAt(i - 1)) {
                count++;
            } else {
                if (count > 1) {
                    formatted.append(count);
                }
                formatted.append(rawPath.charAt(i - 1)).append(" ");
                count = 1;
            }
        }
        if (count > 1) {
            formatted.append(count);
        }
        formatted.append(rawPath.charAt(rawPath.length() - 1));
        
        return formatted.toString();
    }
}
