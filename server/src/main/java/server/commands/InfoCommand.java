package server.commands;

import java.time.LocalDateTime ;
import server.utility.CollectionManager;
import server.utility.responseOutputer;
import common.exceptions.WrongAmountOfElements;

public class InfoCommand extends AbstractCommand {
	private CollectionManager collectionManager ;
	
	public InfoCommand (CollectionManager collectionManager) {
		super ("Info","", ": Print out information about the collection(type, initialization date, number of elements, etc)");
		this.collectionManager = collectionManager;
	}
	/**
	    * Prints information about the collection
	    * 
	    * @param argument The argument is the string that is passed to the command.
	    * @return Command exit status.
	    */
	    @Override
	    public boolean execute(String argument, Object objectArgument) {
	        try {
	            if (!argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();

	            LocalDateTime lastInputTime = collectionManager.getLastInputTime();
	            String lastInputTimeString = (lastInputTime == null) ? "no initialization has yet taken place in this session"
	                    : lastInputTime.toLocalDate().toString() + " " + lastInputTime.toLocalTime().toString();

	            LocalDateTime lastSaveTime = collectionManager.getLastSaveTime();
	            String lastSaveTimeString = (lastSaveTime == null) ? "in this session has not yet occurred"
	                    : lastSaveTime.toLocalDate().toString() + " " + lastSaveTime.toLocalTime().toString();

	            responseOutputer.appendln("Collection Information:");
	            responseOutputer.appendln(" Type: " + collectionManager.collectionType());
	            responseOutputer.appendln(" Number of elements: " + collectionManager.collectionSize());
	            responseOutputer.appendln(" Date of the last save: " + lastSaveTimeString);
	            responseOutputer.appendln(" Date of last initialization: " + lastInputTimeString);
	            return true;
	        } catch (WrongAmountOfElements exception) {
	        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
	        }
	        return false;
	    }

}
