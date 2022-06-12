package server.commands;

import common.exceptions.WrongAmountOfElements;
import server.utility.CollectionManager;
import server.utility.responseOutputer;

public class serverExitCommand extends AbstractCommand {
	private CollectionManager collectionManager ;
	public serverExitCommand(CollectionManager collectionManager) {
        super("Server_Exit", "", ": Save collection and shut down the server");
		this.collectionManager = collectionManager ;
    }

    /**
     * Executes the command.
     *
     * @return Command exit status.
     */
    @Override
    public boolean execute(String stringArgument, Object objectArgument) {
        try {
            if (!stringArgument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            collectionManager.saveCollection();
            responseOutputer.appendln("Server completed successfully!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Usage: '" + getName() + " " + getUsage() + "'");
        }
        return false;
    }

}
