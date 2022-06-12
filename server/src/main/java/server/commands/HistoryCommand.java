package server.commands;

import common.exceptions.WrongAmountOfElements;
import server.utility.responseOutputer;

public class HistoryCommand extends AbstractCommand {

    public HistoryCommand() {
        super("History","", ": Display history of the last 15 used commands");
    }

    /**
     * The command shows 15 last commands.
     * 
     * @param argument The argument passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        }
        return false;
    }
}