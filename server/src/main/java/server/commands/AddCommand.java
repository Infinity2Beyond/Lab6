package server.commands;

import java.time.LocalDateTime;
import server.utility.CollectionManager;
import common.data.humanbeing;
import common.exceptions.WrongAmountOfElements;
import common.interaction.humanRaw;
import server.utility.responseOutputer;

public class AddCommand extends AbstractCommand {
	private CollectionManager collectionManager;
	
	public AddCommand(CollectionManager collectionManager) {
		super ("Add","",": Add new element to the collection");
		this.collectionManager = collectionManager ;
	}
	/**
     * Add an organization to the collection
     * 
     * @param argument The argument is the user input.
     * @return statement of the command.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (!argument.isEmpty() || objectArgument == null) throw new WrongAmountOfElements();
            humanRaw humanraw = (humanRaw) objectArgument;
            collectionManager.addToCollection(new humanbeing(
                    collectionManager.generateNextId(),
                    humanraw.getName(),
                    humanraw.getCoordinates(),
                    LocalDateTime.now(),
                    humanraw.getHero(),
                    humanraw.getToothpick(),
                    humanraw.getSpeed(),
                    humanraw.getMinutesWaiting(),
                    humanraw.getWeaponType(),
                    humanraw.getMood(),
                    humanraw.getCar() ));
            //collectionManager.fromAtoZ();
            //collectionManager.regenerateID();
            responseOutputer.appendln("Human Being added successfully!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } /**catch (IncorrectInputInScript exception) {
        	responseOutputer.appenderror("Data in script is not correct");
        } */
        return false;
    }

}
