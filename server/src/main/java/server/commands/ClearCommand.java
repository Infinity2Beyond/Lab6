package server.commands;

import server.utility.CollectionManager;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.WrongAmountOfElements;
import server.utility.responseOutputer;

public class ClearCommand extends AbstractCommand {
	private CollectionManager collectionManager ;
	public ClearCommand(CollectionManager collectionManager) {
		super ("Clear","", ": Clear the collection") ;
		this.collectionManager = collectionManager ;
	}
	/**
     * Clear the collection
     * 
     * @param argument The argument is the string that is passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmpty();
            collectionManager.clearCollection(); 
            responseOutputer.appendln("The collection is clear!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appendln("The collection is already empty!");
		}
        return false;
    }
}
