package server.utility;

import common.interaction.request;
import common.interaction.response;
import common.interaction.responseCode;

public class requestHandle {
	private CommandManager commandManager;
	
	public requestHandle(CommandManager commandManager) {
		this.commandManager = commandManager;
	}
	/**
     * Handles requests.
     *
     * @param request Request to be processed.
     * @return Response to request.
     */
    public response handle(request request) {
        commandManager.addToHistory(request.getCommandName());
        responseCode responseCode = executeCommand(request.getCommandName(), request.getCommandStringArgument(),
                request.getCommandObjectArgument());
        /**String [] respon = responseOutputer.getAndClear();
        response[] responSet = new response[respon.length];
        for (int i =0; i<respon.length;i++) {
        	responSet[i] = new response(responseCode, respon[i]);
        }**/
        return new response(responseCode, responseOutputer.getAndClear());
    }

    /**
     * Executes a command from a request.
     *
     * @param command               Name of command.
     * @param commandStringArgument String argument for command.
     * @param commandObjectArgument Object argument for command.
     * @return Command execute status.
     */
    private responseCode executeCommand(String command, String commandStringArgument,
            Object commandObjectArgument) {
    	switch (command) {
    	case "":
            break;
        case "HELP":
            if (!commandManager.Help(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "INFO":
            if (!commandManager.Info(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "SHOW":
            if (!commandManager.Show(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "ADD":
            if (!commandManager.Add(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "UPDATE_BY_ID":
            if (!commandManager.Update(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "REMOVE_BY_ID":
            if (!commandManager.RemoveById(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "CLEAR":
            if (!commandManager.Clear(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        /*case "SAVE":
            if (!commandManager.Save(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;*/
        case "EXECUTE_SCRIPT":
            if (!commandManager.ExecuteScript(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "FILTER_LESS_THAN_IMPACT_SPEED":
            if (!commandManager.FilterLessThanImpactSpeed(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "PRINT_DESCENDING":
            if (!commandManager.PrintDescending(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "HISTORY":
            if (!commandManager.History(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "SHUFFLE":
            if (!commandManager.Shuffle(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "REORDER":
            if (!commandManager.Reorder(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "MIN_BY_IMPACT_SPEED":
            if (!commandManager.MinByImpactSpeed(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;          
        case "EXIT":
            if (!commandManager.Exit(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            break;
        case "SERVER_EXIT":
            if (!commandManager.serverExit(commandStringArgument, commandObjectArgument))
                return responseCode.ERROR;
            return responseCode.SERVER_EXIT;
        default:
        	responseOutputer.appendln("Command  '" + command + "' not found. Type 'Help' for help.");
            return responseCode.ERROR;
    	
    }
    	return responseCode.OK;
    }

}
