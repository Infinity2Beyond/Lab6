package server;

import common.exceptions.ClosingSocket;
import common.exceptions.OpeningServerSocket;
import common.interaction.request;
import common.interaction.response;
import common.interaction.responseCode;
import common.utility.outPuter;
import server.utility.requestHandle;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;


public class theServer {
	private final int port;
    //private int soTimeout;
    private final requestHandle requestHandle;
    // NonBlocking IO
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    
    private request userRequest = null;
    private response responseToUser = null;
    
    public theServer(int port, int soTimeout, requestHandle requestHandle) {
        this.port = port;
        //this.soTimeout = soTimeout;
        this.requestHandle = requestHandle;
    }   
    /**
     * Open server socket.
     */
    private void openServerSocket() throws OpeningServerSocket {
        try {
            app.logger.info("Server start...");
//          serverSocket = new ServerSocket(port);
//          serverSocket.setSoTimeout(soTimeout);
            selector = Selector.open();

            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);

            serverSocketChannel.bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            

            app.logger.info("Server started successfully.");
        } catch (IllegalArgumentException exception) {
            outPuter.printerror("Port '" + port + "' is out of range!");
            app.logger.error("Port '" + port + "' is out of range!");
            throw new OpeningServerSocket();
        } catch (IOException exception) {
        	outPuter.printerror("An error occurred while trying to use the port '" + port + "'!");
        	app.logger.error("An error occurred while trying to use the port '" + port + "'!");
            throw new OpeningServerSocket();
        }
    }
    
 /**
  * Accept the require to connect from client
  * @param key
  * @param selector
  * @throws IOException
  */
    private void accept(SelectionKey key, Selector selector) throws IOException {
        serverSocketChannel = (ServerSocketChannel) key.channel();
        // Get client socket channel
        outPuter.println("Port listening '" + port + "'...");
        app.logger.info("Port listening '" + port + "'...");

        SocketChannel clientSocket = serverSocketChannel.accept();
        

        outPuter.println("Client connection successfully established.");
        app.logger.info("Client connection successfully established.");
        // Non blocking I/O
        clientSocket.configureBlocking(false);
        // Record it for read/write operations (only read here)
        clientSocket.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
    }
    /**
     * Reading request from Client
     * @param key
     * @throws IOException
     * @throws ClassNotFoundException
     */
    
    private void read(SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel clientSocket = (SocketChannel) key.channel();
        clientSocket.configureBlocking(false);
        clientSocket.register(key.selector(), SelectionKey.OP_WRITE);

        ByteBuffer buffer = ByteBuffer.allocate(1024*6);

        int readStatus = clientSocket.read(buffer);
        if (readStatus == -1) {
            key.cancel(); // Cancel the key when there are nothing to read
            return;
        }
        Object obj = null;
        if (readStatus != -1) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);

            obj = objectInputStream.readObject();
        }
        if (obj instanceof request) {
            userRequest = (request) obj;
            responseToUser = requestHandle.handle(userRequest);
            app.logger.info("Request '" + userRequest.getCommandName() + "' successfully processed.");
        }
    }  
    private void write(SelectionKey key, response responseToUser) throws IOException {        
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream clientWriter = new ObjectOutputStream(byteArrayOutputStream);
        clientWriter.flush();       
        clientWriter.writeObject(responseToUser);   
        SocketChannel clientSocket = (SocketChannel) key.channel();
        clientSocket.configureBlocking(false);
        clientSocket.register(key.selector(), SelectionKey.OP_READ);      
        int packets = byteArrayOutputStream.size()/ (1024*6) + 1;
        //sent the number of up coming Packets to CLient
        SentNumberOfPackets(packets, clientSocket);
        byte [] bufferArray = byteArrayOutputStream.toByteArray();
        //Loop to sent sub packets to client
        for (int i = 0; i< packets; i++) {
        	
        	byte[] bufferSub = copyOf(bufferArray, i*1024*6 , 1024*6);
        	ByteBuffer buffer = ByteBuffer.wrap(bufferSub);       
        	int totalwrite = 0;        
        	while (totalwrite < buffer.capacity()) {
        		int write = clientSocket.write(buffer);             
        		totalwrite += write;    
        }
        //System.out.println(totalwrite);       
        }
    }  
    /**
     * Sent to CLient the up coming number of packets
     * @param packets
     * @param clientSocket
     * @throws IOException
     */
    private void SentNumberOfPackets (int packets, SocketChannel clientSocket) throws IOException {
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream clientWriter = new ObjectOutputStream(byteArrayOutputStream);
        clientWriter.flush();       
        clientWriter.writeObject(packets);
        ByteBuffer buffer = ByteBuffer.wrap(byteArrayOutputStream.toByteArray());
        clientSocket.write(buffer);
        //System.out.println("so packet: " + packets);
        }     
    /**
     * Create a sub array from the original array
     * @param original 
     * @param copyFrom
     * @param newLength
     * @return
     */
    private byte[] copyOf(byte[] original,int copyFrom, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, copyFrom, copy, 0, Math.min(original.length - copyFrom, newLength));       
        return copy;
    }   
    /**
     * Finishes server operation.
     */
    private void stop() {
        try {
        	app.logger.info("Shutting down the server...");
            if (serverSocketChannel == null) throw new ClosingSocket();
            outPuter.println("Server completed successfully.");
            app.logger.info("Server completed successfully.");
            serverSocketChannel.close();
            System.exit(0);
        } catch (ClosingSocket exception) {
        	outPuter.printerror("Unable to shut down server not yet running!");
            app.logger.error("Unable to shut down server not yet running!");
        } catch (IOException exception) {
        	outPuter.printerror("An error occurred while shutting down the server!");
            app.logger.error("An error occurred while shutting down the server!");
        }
    }
    /**
     * Begins server operation.
     */
    public void run() {

        try {
            openServerSocket();
            boolean processingStatus = true;
            while (processingStatus) {
                int readyCount = selector.select();
                if (readyCount == 0) continue; // if there is no client connection so continue to the next loop
                // process selected keys
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                Iterator<SelectionKey> keys = readyKeys.iterator();
                if (keys.hasNext()) {
                    SelectionKey key = (SelectionKey) keys.next();
                    // Remove key from set, so we don't process it twice
                    keys.remove();
                    // Operate on the channel
                    if (key.isValid()) {

                        if (key.isAcceptable()) {
                            accept(key, selector);

                        } else if (key.isReadable()) {
                            try {
                                read(key);
                            }
                            catch (StreamCorruptedException | ClassNotFoundException e) {
                            	outPuter.printerror("An error occurred while reading received data!");
                                app.logger.error("An error occurred while reading received data!");
                            } catch (InvalidClassException | NotSerializableException exception) {
                            	outPuter.printerror("An error occurred while reading received data!");
                                app.logger.error("An error occurred while reading received data!");
                            } catch (IOException exception) {
                            	outPuter.printerror("An error occurred while trying to terminate the connection with the client!");
                                app.logger.error("An error occurred while trying to terminate the connection with the client!");
                                key.cancel();
                            }

                        }
                        else if (key.isWritable()) {
                            try {
                                write(key, responseToUser);
                                if (responseToUser.getResponseCode() == responseCode.SERVER_EXIT) break;
                            } catch (StreamCorruptedException e) {
                            	
                            } catch (InvalidClassException | NotSerializableException exception) {
                            	outPuter.printerror("An error occurred while sending data to the client!");
                                app.logger.error("An error occurred while sending data to the client!");
                            } catch (IOException exception) {
                                if (userRequest == null) {
                                	outPuter.printerror("Unexpected loss of connection with the client!");
                                    app.logger.warn("Unexpected loss of connection with the client!");
                                    key.cancel();
                                } else {
                                	outPuter.println("Client successfully disconnected from server!");
                                    app.logger.info("Client successfully disconnected from server!");
                                }
                            }
                        }                  
                    } 
                } 
            }
            stop();
        } catch (OpeningServerSocket exception) {
        	outPuter.printerror("Server cannot be started!");
            app.logger.error("Server cannot be started!");
        } catch (IOException e) {
        	outPuter.printerror("An error occurred while trying to terminate the connection with the client!");
            app.logger.error("An error occurred while trying to terminate the connection with the client!");
        }
    }	
}
