package clients.utility;

import clients.app;
import common.data.*;
import common.exceptions.CommandUsage;
import common.exceptions.IncorrectInputInScript;
import common.exceptions.ScriptRecursion;
import common.interaction.humanRaw;
import common.interaction.request;
import common.interaction.responseCode;
import common.utility.outPuter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Stack;

public class userHandle {
	private final int maxRewriteAttempts = 1;

    private Scanner userScanner;
    private Stack<File> scriptStack = new Stack<>();
    private Stack<Scanner> scannerStack = new Stack<>();

    public userHandle(Scanner userScanner) {
        this.userScanner = userScanner;
    }
    /**
     * Processes the entered command.
     *
     * @return Status of code.
     */
    private processingCode processCommand(String command, String commandArgument) {
        try {
            switch (command) {
                case "":
                    return processingCode.ERROR;
                case "HELP":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "INFO":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "SHOW":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "ADD":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    return processingCode.OBJECT;
                case "UPDATE_BY_ID":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    return processingCode.UPDATE_OBJECT;
                case "REMOVE_BY_ID":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    return processingCode.REMOVE_OBJECT;
                case "CLEAR":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "EXECUTE_SCRIPT":
                    if (commandArgument.isEmpty()) throw new CommandUsage("<file_name>");
                    return processingCode.SCRIPT;
                case "EXIT":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "PRINT_DESCENDING":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "SHUFFLE":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "HISTORY":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "REORDER":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "MIN_BY_IMPACT_SPEED":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                case "FILTER_LESS_THAN_IMPACT_SPEED":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    return processingCode.SORT_SPEED;
                case "SERVER_EXIT":
                    if (!commandArgument.isEmpty()) throw new CommandUsage();
                    break;
                default:
                    outPuter.println("Command '" + command + "' not found. Type 'help' for help.");
                    return processingCode.ERROR;
            }
        } catch (CommandUsage exception) {
            if (exception.getMessage() != null) command += " " + exception.getMessage();
            outPuter.println("Usage: '" + command + "'");
            return processingCode.ERROR;
        }
        return processingCode.OK;
    }
    
    /**
     * Checks if UserHandler is in file mode now.
     *
     * @return Is UserHandler in file mode now boolean.
     */
    private boolean fileMode() {
        return !scannerStack.isEmpty();
    }
    
    /**
     * Generates human to update.
     *
     * @return human to update.
     * @throws IncorrectInputInScript When something went wrong in script.
     */
    private humanRaw generateHumanUpdate() throws IncorrectInputInScript {
        humanAskers humanAsker = new humanAskers(userScanner);
        if (fileMode()) humanAsker.setFileMode();
        String name = humanAsker.askQuestion("Do you want to change name?") ?
        		humanAsker.askName() : null;
        coordinates coordinates = humanAsker.askQuestion("Do you want to change coordinate?") ?
        		humanAsker.askCoordinates() : null;
        Boolean hero = humanAsker.askQuestion("Do you want to change the perception of this humanbeing as a true hero?") ?
        		humanAsker.askHero() : null;
        Boolean toothpick = humanAsker.askQuestion("Do you want to change the perception that this humanbeing has a toothpick ?") ?
        		humanAsker.askToothpick() : null;
        Double impactSpeed = humanAsker.askQuestion("Do you want to change this humanbeing's impact speed?") ?
        		humanAsker.askImpactSpeed() : null;
        Integer minutesOfWaiting = humanAsker.askQuestion("Do you want to change this humanbeing's minutes of waiting?") ?
        		humanAsker.askMinutesOfWaiting() : null;
        weapontype weaponType = humanAsker.askQuestion("Do you want to change this humanbeing's weapon type?") ?
        		humanAsker.askWeaponType() : null;
        mood mood = humanAsker.askQuestion("Do you want to change this humanbeing's mood?") ?
        		humanAsker.askMood() : null;
        car car = humanAsker.askQuestion("Do you want to change the perception that this humanbeing has a coolcar ?") ?
        		humanAsker.askCar() : null;
        return new humanRaw(
                name,
                coordinates,
                hero,
                toothpick,
                impactSpeed,
                minutesOfWaiting,
                weaponType,
                mood,
                car             
        );
    }
    /**
     * Generates human to add.
     *
     * @return Human to add.
     * @throws IncorrectInputInScript When something went wrong in script.
     */
    private humanRaw generateHumanAdd() throws IncorrectInputInScript {
        humanAskers Asker = new humanAskers(userScanner);
        if (fileMode()) Asker.setFileMode();
        return new humanRaw(
        		Asker.askName(),
        		Asker.askCoordinates(),
        		Asker.askHero(),
        		Asker.askToothpick(),
        		Asker.askImpactSpeed(),
        		Asker.askMinutesOfWaiting(),
        		Asker.askWeaponType(),
        		Asker.askMood(),
        		Asker.askCar()
        );
    }
    
    /**
     * Receives user input.
     *
     * @param serverResponseCode Last server's response code.
     * @return New request to server.
     */
    public request handle(responseCode serverResponseCode) {
        String userInput;
        String[] userCommand;
        humanAskers Asker = new humanAskers(userScanner);
        processingCode processingCode;
        int rewriteAttempts = 0;
        try {
            do {
                try {
                    if (fileMode() && (serverResponseCode == responseCode.ERROR ||
                            serverResponseCode == responseCode.SERVER_EXIT))
                        throw new IncorrectInputInScript();
                    while (fileMode() && !userScanner.hasNextLine()) {
                        userScanner.close();
                        userScanner = scannerStack.pop();
                        outPuter.println("Back to the script '" + scriptStack.pop().getName() + "'...");
                    }
                    if (fileMode()) {
                        userInput = userScanner.nextLine();
                        if (!userInput.isEmpty()) {
                        	outPuter.print(app.PS1);
                        	outPuter.println(userInput);
                        }
                    } else {
                    	outPuter.print(app.PS1);
                        userInput = userScanner.nextLine();
                    }
                    userCommand = (userInput.trim().toUpperCase() + " ").split(" ", 2);
                    userCommand[1] = userCommand[1].trim();
                } catch (NoSuchElementException | IllegalStateException exception) {
                	outPuter.println();
                	outPuter.printerror("An error occurred while entering the command!");
                    userCommand = new String[]{"", ""};
                    rewriteAttempts++;
                    if (rewriteAttempts >= maxRewriteAttempts) {
                    	outPuter.printerror("Exceeded number of input attempts!");
                        System.exit(0);
                    }
                }
                processingCode = processCommand(userCommand[0], userCommand[1]);
            } while (processingCode == processingCode.ERROR && !fileMode() || userCommand[0].isEmpty());
            try {
                if (fileMode() && (serverResponseCode == responseCode.ERROR || processingCode == processingCode.ERROR))
                    throw new IncorrectInputInScript();
                switch (processingCode) {
                    case OBJECT:
                        humanRaw humanAddRaw = generateHumanAdd();
                        userCommand[1] = "";
                        return new request(userCommand[0], userCommand[1], humanAddRaw);
                    case UPDATE_OBJECT:                
                    	Long id = Asker.askID() ;
                    	userCommand[1] = String.valueOf(id);
                    	humanRaw humanUpdateRaw = generateHumanUpdate();
                        return new request(userCommand[0], userCommand[1], humanUpdateRaw);
                    case REMOVE_OBJECT:                
                    	Long id_remove = Asker.askID() ;
                    	userCommand[1] = String.valueOf(id_remove);
                        return new request(userCommand[0], userCommand[1]);
                    case SORT_SPEED:                
                    	Double speed = Asker.askImpactSpeed() ;
                    	userCommand[1] = String.valueOf(speed) ;
                        return new request(userCommand[0], userCommand[1]);
                    case SCRIPT:
                        File scriptFile = new File(userCommand[1]);
                        if (!scriptFile.exists()) throw new FileNotFoundException();
                        if (!scriptStack.isEmpty() && scriptStack.search(scriptFile) != -1)
                            throw new ScriptRecursion();
                        scannerStack.push(userScanner);
                        scriptStack.push(scriptFile);
                        userScanner = new Scanner(scriptFile);
                        outPuter.println("Executing a script '" + scriptFile.getName() + "'...");
                        break;
                }
            } catch (FileNotFoundException exception) {
            	outPuter.printerror("Script file not found!");
            } catch (ScriptRecursion exception) {
            	outPuter.printerror("Scripts cannot be called recursively!");
                throw new IncorrectInputInScript();
            }
        } catch (IncorrectInputInScript exception) {
            outPuter.printerror("Script execution aborted!");
            while (!scannerStack.isEmpty()) {
                userScanner.close();
                userScanner = scannerStack.pop();
            }
            scriptStack.clear();
            return new request();
        }
        return new request(userCommand[0], userCommand[1]);
    }

}
