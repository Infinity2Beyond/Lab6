package common.exceptions;

public class CommandUsage extends Exception {
    public CommandUsage() {
        super();
    }

    public CommandUsage(String message) {
        super(message);
    }
}
