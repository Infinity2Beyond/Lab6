package server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.commands.*;
import server.utility.FileManager;
import server.utility.CollectionManager;
import server.utility.CommandManager;
import server.utility.requestHandle;

public class app {
	public static final int PORT = 2309;
    public static final int CONNECTION_TIMEOUT = 60 * 1000;
    public static final Logger logger = LoggerFactory.getLogger("ServerLogger");

    public static void main(String[] args) {
        FileManager fileManager = new FileManager(args[0]);
        //int Port = Integer.parseInt(args[1]);
        CollectionManager collectionManager = new CollectionManager(fileManager);
        CommandManager commandManager = new CommandManager(
        		 new HelpCommand(),
        		 new InfoCommand(collectionManager),
                 new ShowCommand(collectionManager),
                 new AddCommand(collectionManager),
                 new UpdateIdCommand(collectionManager),
                 new RemoveByIdCommand(collectionManager),
                 new ClearCommand(collectionManager),
                 /*new SaveCommand(collectionManager),*/
                 new ExitCommand(collectionManager),
                 new ExecuteScriptFileCommand(),
                 new FilterLessThanImpactSpeedCommand(collectionManager),
                 new PrintDescending(collectionManager),
                 new HistoryCommand(),
                 new ShuffleCommand(collectionManager),
                 new ReorderCommand(collectionManager),
                 new MinByImpactSpeedCommand(collectionManager),
                 new serverExitCommand(collectionManager)
                 );
        requestHandle requestHandle = new requestHandle(commandManager);
        theServer server = new theServer(PORT, CONNECTION_TIMEOUT, requestHandle);
        server.run();
    }

}
