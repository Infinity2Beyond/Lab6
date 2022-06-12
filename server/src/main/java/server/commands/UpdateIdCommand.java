package server.commands;

import java.time.LocalDateTime ;
import common.data.*;
import common.exceptions.CollectionIsEmpty;
import common.exceptions.HumanNotFound;
import common.exceptions.WrongAmountOfElements;
import common.interaction.humanRaw;
import server.utility.responseOutputer;
import server.utility.CollectionManager;

public class UpdateIdCommand extends AbstractCommand {
	private CollectionManager collectionManager ;
	
	public UpdateIdCommand (CollectionManager collectionManager) {
        super("Update_By_ID","", ": Update the value of the collection element whose id is equal to the given one");
        this.collectionManager = collectionManager;
	}
	/**
     * Executes the command.
     * 
     * @return Command exit status.
     */
    /**
     * The command updates organization with the given ID 
     * 
     * @param argument the argument that the user entered.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument == null) throw new WrongAmountOfElements();

            if (collectionManager.collectionSize() == 0)
                throw new CollectionIsEmpty();

            Long id = Long.parseLong(argument);;
            humanbeing oldHumanbeing = collectionManager.getById(id);
            if (oldHumanbeing == null) throw new HumanNotFound();

            
            humanRaw humanraw = (humanRaw) objectArgument;
            String name = humanraw.getName() == null ? oldHumanbeing.getName() : humanraw.getName();
            coordinates coord = humanraw.getCoordinates() == null ? oldHumanbeing.getCoordinates() : humanraw.getCoordinates();            
            LocalDateTime creationDate = oldHumanbeing.getCreationDate();
            boolean realHero = humanraw.getHero() == null ? oldHumanbeing.getHero() : humanraw.getHero();
            Boolean Toothpick = humanraw.getToothpick() == null ? oldHumanbeing.getToothpick() : humanraw.getToothpick();
            Double Speed = humanraw.getSpeed() == null ? oldHumanbeing.getSpeed() : humanraw.getSpeed();
            Integer Minutes = humanraw.getMinutesWaiting() == null ? oldHumanbeing.getMinutesWaiting() : humanraw.getMinutesWaiting();
            weapontype weapon = humanraw.getWeaponType() == null ? oldHumanbeing.getWeaponType() : humanraw.getWeaponType();
            mood mood = humanraw.getMood() == null ? oldHumanbeing.getMood() : humanraw.getMood();
            car car = humanraw.getCar() == null ? oldHumanbeing.getCar() : humanraw.getCar();
            
            

            collectionManager.removeFromCollection(oldHumanbeing);

            collectionManager.addToCollection(new humanbeing(
            		oldHumanbeing.getId(),
                    name,
                    coord,
                    creationDate,
                    realHero,
                    Toothpick,
                    Speed,
                    Minutes,
                    weapon,
                    mood,
                    car ));
            //collectionManager.fromAtoZ();
            //collectionManager.regenerateID();
            responseOutputer.appendln("Humanbeing successfully changed!");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appenderror("Collection is empty!");
        } catch (HumanNotFound exception) {
        	responseOutputer.appenderror("There is no humanbeing with this ID in the collection!");
        } 
        return false;
    }
}
