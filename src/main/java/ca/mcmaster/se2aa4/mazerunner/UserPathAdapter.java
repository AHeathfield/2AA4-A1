package ca.mcmaster.se2aa4.mazerunner;

public class UserPathAdapter implements PathFormatter {
    private Path path;
    private String userPath;
    

    // In this case of the adapter, it's just the format of the string
    public UserPathAdapter(Path path, String userPath) {
        this.path = path;
        this.userPath = userPath;
    }

    // From raw path to factorized
    @Override
    public String getPath() {
        if (isCanonical()) {
            return convertCanonicalToRaw();
        }
        else {
            return convertFactorizedToRaw();
        }
    }


    private boolean isCanonical() {
        for (int i = 0; i < userPath.length(); i++) {
            if (Character.isDigit(userPath.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private String convertCanonicalToRaw() {
        StringBuilder canonical = new StringBuilder(); 
        for (int i = 0; i < userPath.length(); i++) {
            Character character = userPath.charAt(i);
            if (character.equals(' ')) {
                continue;
            }
            canonical.append(character);
        }

        return canonical.toString();
    }

    private String convertFactorizedToRaw() {
        StringBuilder factorized = new StringBuilder();
        int repeatCount = 0;

        for (int i = 0; i < userPath.length(); i++) {
            Character character = userPath.charAt(i);

            for (int n = 0; n < repeatCount; n++) {
                factorized.append(character);
            }


            if (character.equals(' ')) {
                continue;
            }

            // Case with factored num
            else if (Character.isDigit(character)) {
                repeatCount = Character.getNumericValue(character);
                repeatCount--;
            }
            // Case with only 1 letter
            else {
                factorized.append(character);
                repeatCount = 0;
            }
        }

        return factorized.toString();
    }
}
