package server.commands;

import common.exceptions.WrongAmountOfElements;
import server.utility.CollectionManager;
import server.utility.responseOutputer;

public class ExitCommand extends AbstractCommand {
	private CollectionManager collectionManager;

    public ExitCommand(CollectionManager collectionManager) {
        super("Exit","", ": Turn off the cilent and save the file");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes to exit
     * 
     * @param argument The argument passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            collectionManager.saveCollection();
            return true;            
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        }
        return false;
    }
}
