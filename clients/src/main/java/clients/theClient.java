package clients;

import clients.utility.userHandle;
import common.exceptions.ConnectionError;
import common.exceptions.NotInDeclaredLimit;
import common.interaction.request;
import common.interaction.response;
import common.interaction.responseCode;
import common.utility.outPuter;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;


public class theClient {
	private String host;
    private int port;
    private int reconnectionTimeout;
    private int reconnectionAttempts;
    private int maxReconnectionAttempts;
    private userHandle userhandle;
    private SocketChannel socketChannel;
    private ObjectOutputStream serverWriter;
    private ObjectInputStream serverReader;

    public theClient(String host, int port, int reconnectionTimeout, int maxReconnectionAttempts, userHandle userhandle) {
        this.host = host;
        this.port = port;
        this.reconnectionTimeout = reconnectionTimeout;
        this.maxReconnectionAttempts = maxReconnectionAttempts;
        this.userhandle = userhandle;

    }
    /**
     * Connecting to server.
     */
    private void connectToServer() throws ConnectionError, NotInDeclaredLimit {
    	try {
            if (reconnectionAttempts >= 1) outPuter.println("Reconnecting to the server...");
            socketChannel = SocketChannel.open(new InetSocketAddress(host, port));
            
            if (socketChannel != null) {
            	outPuter.println("Connected to server.");
            } else {
            	outPuter.println("Reconnecting to the server.");
            }
            outPuter.println("Waiting for permission to communicate...");
            outPuter.println("Permission to share data received.");
        } catch (IllegalArgumentException exception) {
        	outPuter.printerror("Server address entered incorrectly!");
            throw new NotInDeclaredLimit();
        } catch (IOException exception) {
        	outPuter.printerror("An error occurred while connecting to the server!");
            throw new ConnectionError();
        }
    }    
 /**
  * Reading response from Server   
  * @return response
  * @throws IOException
  * @throws ClassNotFoundException
  */
    private response myReadObject() throws IOException, ClassNotFoundException {
        response serverResponse = new response(responseCode.ERROR, "");                      
        int packets = upComingPackets(socketChannel);
        byte [] MainBuffer = new byte[packets*1024*6];
        int pos = 0;
        for (int i=0; i<packets; i++) {        	
        	ByteBuffer buffer = ByteBuffer.allocate(1024*6);
        	buffer.clear(); 
            int read = socketChannel.read(buffer);
            if (read < 0) return serverResponse;
            buffer.flip();
            byte [] subBuffer = buffer.array();
            for (byte elements : subBuffer) {
            	MainBuffer [pos] = elements;
            	pos ++;
            }
        }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(MainBuffer);
            serverReader = new ObjectInputStream(byteArrayInputStream);
            Object obj = serverReader.readObject();
            if (obj instanceof response) {
            	serverResponse = (response) obj;
        }           
        return serverResponse;
    }
    /**
     * Get the up coming packets number for preparing to the reading loop
     * @param socketChannel
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private int upComingPackets (SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(1024*6);
        buffer.clear();       
        socketChannel.read(buffer);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
        serverReader = new ObjectInputStream(byteArrayInputStream);
        Integer comingPackets = (Integer) serverReader.readObject();
        //System.out.println("Will be: " + comingPackets);
        return comingPackets;
    } 
    /**
     * Writing request to Server
     * @param requestToServer
     * @throws IOException
     */
    private void myWriteObject(request requestToServer) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        serverWriter = new ObjectOutputStream(byteArrayOutputStream);
        serverWriter.flush();

        serverWriter.writeObject(requestToServer);

        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        socketChannel.write(buffer);
    }     
    /**
     * Server request process.
     */
    private boolean processRequestToServer() {
        request requestToServer = null;
        response serverResponse = null;
        do {
            try {
                requestToServer = serverResponse != null ? userhandle.handle(serverResponse.getResponseCode()) :
                        userhandle.handle(null);
                if (requestToServer.isEmpty()) continue;
                // writing Request object
                myWriteObject(requestToServer);
                //serverWriter.writeObject(requestToServer);
                // reading Response object here
                serverResponse = myReadObject();
                
                outPuter.print(serverResponse.getResponseBody());
            } catch (InvalidClassException | NotSerializableException exception) {
            	outPuter.printerror(exception);
            	outPuter.printerror("An error occurred while sending data to the server!");
            } catch (ClassNotFoundException exception) {
            	outPuter.printerror("An error occurred while reading received data!");
            } catch (IOException exception) {
            	outPuter.printerror(" Server connection lost!");
                try {
                    reconnectionAttempts++;
                    connectToServer();
                } catch (ConnectionError | NotInDeclaredLimit reconnectionException) {
                    if (requestToServer.getCommandName().equals("EXIT"))
                    	outPuter.println("The command will not be registered on the server.");
                    else outPuter.println("Try repeating the command later.");
                }
            } catch (NullPointerException e) {
            	outPuter.printerror("Catched NullPointerExeption"); 
            }
        } while (!requestToServer.getCommandName().equals("EXIT"));
        return false;
    }
    /**
     * Begins client operation.
     */
    public void run() {
        try {
            boolean processingStatus = true;
            while (processingStatus) {
                try {
                    connectToServer();
                    processingStatus = processRequestToServer();
                } catch (ConnectionError exception) {
                    if (reconnectionAttempts >= maxReconnectionAttempts) {
                    	outPuter.printerror("Connection attempts exceeded!");
                        break;
                    }
                    try {
                        Thread.sleep(reconnectionTimeout);
                    } catch (IllegalArgumentException timeoutException) {
                    	outPuter.printerror("Connection timeout '" + reconnectionTimeout +
                                "' is out of range!");
                    	outPuter.println("Reconnection will be done immediately.");
                    } catch (Exception timeoutException) {
                    	outPuter.printerror("An error occurred while trying to connect!");
                    	outPuter.println("Reconnection will be done immediately.");
                    }
                }
                reconnectionAttempts++;
            }
            if (socketChannel != null) socketChannel.close();
            outPuter.println("Client job completed successfully.");
        } catch (NotInDeclaredLimit exception) {
        	outPuter.printerror("Client cannot be started!");
        } catch (IOException exception) {
        	outPuter.printerror("An error occurred while trying to terminate the connection to the server!");
        }
    }
}
