package server.utility;

import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.Stack;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.io.IOException;


import java.lang.reflect.Type;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParseException;

import common.data.humanbeing;
import common.utility.outPuter;
import server.app;


public class FileManager {
	private Gson gson = new Gson() ;
	private String Argument ;
	public FileManager(String Argument) {
		this.Argument = Argument ;
	}
	
    public void writeCollection(Collection<?> collection) {
	       if (Argument != null) {
	           try (OutputStreamWriter collectionFileWriter = 
	        		new OutputStreamWriter(new FileOutputStream(Argument))) {
	               collectionFileWriter.write(gson.toJson(collection));
	               responseOutputer.appendln("Collection successfully saved to file!");
	               
	           } catch (IOException exception) {
	        	   responseOutputer.appenderror("The download file is a directory/cannot be opened!");
	           }
	        }  else responseOutputer.appenderror("Boot file system Argument not found!");
    }


    public Stack<humanbeing> readCollection() {
    	 if (Argument != null) { 
    		 try (InputStreamReader collectionFileReader 
    		     = new InputStreamReader(new FileInputStream (Argument))) {
    			 Stack<humanbeing> collection;
    		
                 Type collectionType = new TypeToken<Stack<humanbeing>>() {}.getType();
                 collection = gson.fromJson(collectionFileReader, collectionType);
                 //System.out.println(collectionFileReader);
                 if (collection == null) throw new NoSuchElementException() ;                 
                 outPuter.println("Collection successfully uploaded!");
                 app.logger.info("Collection successfully uploaded!");
                 return collection;
    		 } catch (FileNotFoundException exception) {
    			 outPuter.printerror("Boot file not found!");
    			 app.logger.warn("Boot file not found!");
             } catch (NoSuchElementException exception) {
            	 outPuter.printerror("The boot file is empty!");
            	 app.logger.error("The boot file is empty!");
             } catch (JsonParseException | NullPointerException exception) {
            	 outPuter.printerror("Required collection not found in upload file!");
            	 app.logger.error("Required collection not found in upload file!");
             } catch (IOException exception) {
            	 outPuter.printerror("The download file is a directory/cannot be opened!");
            	 app.logger.error("The download file is a directory/cannot be opened!");
             } catch (IllegalStateException exception) {
            	 outPuter.printerror("Unexpected error!");
            	 app.logger.error("Unexpected error!");
                 System.exit(0);
             } 
    	 } else outPuter.printerror("Boot file system Argument not found!");
    	 return new Stack<> ();	
    }
    @Override
    public String toString() {
        String string = "FileManager (class for working with file)";
        return string;
    }
}


