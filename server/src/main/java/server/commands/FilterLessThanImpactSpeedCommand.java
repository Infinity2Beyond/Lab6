package server.commands;

import common.exceptions.CollectionIsEmpty;
import common.exceptions.WrongAmountOfElements;
import server.utility.responseOutputer;
import server.utility.CollectionManager;

public class FilterLessThanImpactSpeedCommand extends AbstractCommand {
	private CollectionManager collectionManager;
	
	public FilterLessThanImpactSpeedCommand (CollectionManager collectionManager) {
		super ("Filter_Less_Than_Impact_Speed","", 
				": Display all elements whose Impact Speed field value is less than the specified one");
		this.collectionManager = collectionManager ;
	}
	/**
     * Executes the command.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();
            if (collectionManager.collectionSize() == 0) throw new CollectionIsEmpty();
            Double impactSpeed = Double.parseDouble(argument);
            String filteredInfo = collectionManager.FilterLessByImpactSpeed(impactSpeed);
            if (!filteredInfo.isEmpty()) {
            	responseOutputer.appendln(filteredInfo);
                return true;
            } else responseOutputer.appendln("There are no elements whose ImpactSpeed field value is smaller than the specified one");
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        } catch (CollectionIsEmpty exception) {
        	responseOutputer.appenderror("Collection is empty");
        } 
        return false;
    }
	

}
