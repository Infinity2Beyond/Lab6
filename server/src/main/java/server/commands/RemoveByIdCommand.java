package server.commands;

import common.exceptions.WrongAmountOfElements;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.HumanNotFound;
import common.data.humanbeing;
import server.utility.CollectionManager;
import server.utility.responseOutputer;

public class RemoveByIdCommand extends AbstractCommand {
	private CollectionManager collectionManager ;

	public RemoveByIdCommand (CollectionManager collectionManager) {
		super("Remove_By_ID","", ": Remove items from collection by id");
		this.collectionManager = collectionManager ;
	}
	/**
     * Remove an organization from the collection
     * 
     * @param argument The argument that is passed to the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();

            if (collectionManager.collectionSize() == 0)
                throw new CollectionIsEmpty();
            long id = Long.parseLong(argument);
            humanbeing HumanBeingToRemove = collectionManager.getById(id);
            if (HumanBeingToRemove == null)
                throw new HumanNotFound();
            collectionManager.removeFromCollection(HumanBeingToRemove);
            collectionManager.regenerateID();
            responseOutputer.appendln("Humanbeing successfully removed!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appenderror("The collection is empty!");
        } catch (HumanNotFound exception) {
        	responseOutputer.appenderror("There is no humanbeing with this ID in the collection!");
        } 
        return false;
    }

}
