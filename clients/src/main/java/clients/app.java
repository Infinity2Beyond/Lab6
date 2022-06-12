package clients;

import clients.utility.userHandle;
import common.exceptions.NotInDeclaredLimit;
import common.exceptions.WrongAmountOfElements;
import common.utility.outPuter;

import java.util.Scanner;


public class app {
	public static final String PS1 = "$ ";
    public static final String PS2 = "> ";

    private static final int RECONNECTION_TIMEOUT = 5 * 1000;
    private static final int MAX_RECONNECTION_ATTEMPTS = 5;

    private static String host;
    private static int port;

    private static boolean initializeConnectionAddress(String[] hostAndPortArgs) {
        try {
            if (hostAndPortArgs.length != 2) throw new WrongAmountOfElements();
            host = hostAndPortArgs[0];
            port = Integer.parseInt(hostAndPortArgs[1]);
            if (port < 0) throw new NotInDeclaredLimit();
            return true;
        } catch (WrongAmountOfElements exception) {
            String jarName = new java.io.File(app.class.getProtectionDomain()
                    .getCodeSource()
                    .getLocation()
                    .getPath())
                    .getName();
            outPuter.println("Usage: 'java -jar " + jarName + " <host> <port>'");
        } catch (NumberFormatException exception) {
        	outPuter.printerror("The port must be represented by a integer number!");
        } catch (NotInDeclaredLimit exception) {
        	outPuter.printerror("Port cannot be negative!");
        }
        return false;
    }

    public static void main(String[] args) {
        if (!initializeConnectionAddress(args)) return;
        Scanner userScanner = new Scanner(System.in);
        userHandle userHandler = new userHandle(userScanner);
        theClient client = new theClient(host, port, RECONNECTION_TIMEOUT, MAX_RECONNECTION_ATTEMPTS, userHandler);
        client.run();
        userScanner.close();
    }
    
}
