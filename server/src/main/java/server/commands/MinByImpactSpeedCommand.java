package server.commands;

import server.utility.CollectionManager;
import server.utility.responseOutputer;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.WrongAmountOfElements;

public class MinByImpactSpeedCommand extends AbstractCommand {
    private CollectionManager collectionManager;

    /**
     * Constructor
     * @param collectionManager instance of CollectionManager
     */
    public MinByImpactSpeedCommand(CollectionManager collectionManager) {
        super("Min_By_Impact_Speed","", ": Display any object from the collection whose impactSpeed field value is the minimum");
        this.collectionManager = collectionManager;
    }

    /**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            responseOutputer.append(collectionManager.PrintMinSpeed(collectionManager.MinByImpactSpeed()));
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appenderror("Collection is empty");
        }
        return false;
    }
}
