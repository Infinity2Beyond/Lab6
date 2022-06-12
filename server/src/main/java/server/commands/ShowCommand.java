package server.commands;

import server.utility.CollectionManager;
import server.utility.responseOutputer;
import common.exceptions.WrongAmountOfElements;

public class ShowCommand extends AbstractCommand {
	private CollectionManager collectionManager;
	public ShowCommand(CollectionManager collectionManager) {
		super ("Show","", ": Display all items in the collection");
		this.collectionManager = collectionManager;
	}
	/**
     * print out the collection
     * 
     * @param argument The argument passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            
            

            responseOutputer.appendln(collectionManager);
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        }
        return false;
    }
}
