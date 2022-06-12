package server.commands;

import server.utility.CollectionManager;
import server.utility.responseOutputer;

import java.util.Collections;

import common.exceptions.CollectionIsEmpty;
import common.exceptions.NotInDeclaredLimit;
import common.exceptions.WrongAmountOfElements;

public class PrintDescending extends AbstractCommand {
	private CollectionManager collectionManager;
	
	public PrintDescending(CollectionManager collectionManager) {
		super ("Print_Descending","", ": Display the elements of the collection in descending order");
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
            Collections.reverse( collectionManager.getCollection());
            responseOutputer.appendln(collectionManager);
            Collections.reverse( collectionManager.getCollection());
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appendln("The collection is empty, cannot be print in descending order!");
        } catch (NotInDeclaredLimit exception) {
        	responseOutputer.appendln("The collection only have 1 element, so it cannot be print in descending order!");
        }
        return false;
    }
}
