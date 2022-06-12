package server.commands;

import server.utility.responseOutputer;
import common.exceptions.WrongAmountOfElements;

public class ExecuteScriptFileCommand extends AbstractCommand {
	public ExecuteScriptFileCommand () {
		super ("Execute_Script <file_path>","", ": Execute script from specified file");
	}
	/**
     * Execute a script
     * 
     * @param argument The argument is the name of the script file.
     * @return Command exit status.
     */
    @Override
    public boolean execute(String argument, Object objectArgument) {
        try {
            if (argument.isEmpty() || objectArgument != null) throw new WrongAmountOfElements();

            responseOutputer.appendln("Executing command from the script '" + argument + "'...");
            return true;
        } catch (WrongAmountOfElements exception) {
        	responseOutputer.appendln("Wrong Syntax! Type '" + getName() + "' to use this command");
        }
        return false;
    }

}
