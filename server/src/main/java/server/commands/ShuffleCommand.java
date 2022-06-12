package server.commands;

import server.utility.CollectionManager;
import server.utility.responseOutputer;

import java.util.Collections;
import java.util.List;

import common.exceptions.WrongAmountOfElements;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.NotInDeclaredLimit;

public class ShuffleCommand extends AbstractCommand {
	private CollectionManager collectionManager;
	public ShuffleCommand(CollectionManager collectionManager) {
		super ("Shuffle ","", ": Shuffle the order of elements in the collection");
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

            if (collectionManager.getCollection().isEmpty())
                throw new CollectionIsEmpty() ;
            if (collectionManager.collectionSize() == 1)
            	throw new NotInDeclaredLimit() ;
            Collections.shuffle((List<?>) collectionManager.getCollection());
            collectionManager.regenerateID();
            responseOutputer.appendln("Collection being shuffle successfully!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appendln("The collection is empty, cannot be shuffle!");
        } catch (NotInDeclaredLimit exception) {
        	responseOutputer.appendln("The collection only have 1 element, so it can not be shuffle!");
        }
        return false;
    }
}

