
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package elements;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * Defines a class to create the server of the program.
 * There is only one server throughout the program.
 * Functions as the mechanism where all non-received messages are stored 
 * in a first come, first served (FCFS) manner.
 * Server has finite capacity, changing currentSize and the queue contains messages.
 * @author Bahrican Yesil
 * 
 */
public class Server{
	
	/**
	 * The capacity of the server.
	 * Server has a finite capacity set at the beginning of the execution. 
	 * and its unit is the number of characters.
	 */
	private final long capacity;
	
	/**
	 * Total number of the characters of messages’ bodies in the msgs queue.
	 * it is kept updated when messages in the server are deleted or added. 
	 */
	private long currentSize;
	
	/**
	 * A queue where non received messages are stored.
	 */
	private Queue<Message> msgs;
	
	/**
	 * Constructs a server with its <code>capacity</code>.
	 * Sets the capacity field to the parameter,
	 * it also sets the currentSize field to "0", when the server is created.
	 * Creates the queue that messages are stored.
	 * @param capacity	The long representing the capacity of the server
	 */
	public Server(long capacity) {
		this.capacity = capacity;
		this.currentSize = 0;
		msgs = new LinkedList<Message>();
	}
	
	/**
	 * Prints the warnings about the capacity.
	 * Detailed explanations are inside of the method.
	 * @param printer	PrintStream object to print the warnings to the output file.
	 */
	public void checkServerLoad(PrintStream printer) {
		double fraction = getFraction(); // Gets the current server occupancy rate.
		/* Whenever the utilization of the server exceeds 50% 
		 * (i.e at least 50% of the overall server capacity gets full), 
		 * prints out a warning message. */
		if(fraction >= 50.0 && fraction < 80.0) {
			printer.println("Warning! Server is 50% full.");
		} 
		// Prints the second warning message when the server becomes 80% full.
		else if(fraction >= 80.0 && fraction < 100.0) {
			printer.println("Warning! Server is 80% full.");
		} 
		/*
		 * When no space is available in the server 
		 * and all of its capacity is allocated for the messages, 
		 * another informative message is printed out
		 * and all of the messages in the server are deleted via the flush method.
		 */
		else if(fraction >= 100.0) {
			printer.println("Server is full. Deleting all messages...");
			this.flush();
		}
	}
	
	/**
	 * Gets the currentSize of the server, a getter method for the current size field.
	 * @return	A long representing the <code>currentSize</code> of the server.
	 */
	public long getCurrentSize() {
		return this.currentSize;
	}
	
	/**
	 * Sets the currentSize of the server, a setter method for the current size field.
	 * @param currentSize	A long representing the new size of the server.
	 */
	public void setCurrentSize(long currentSize) {
		this.currentSize = currentSize;
	}
	
	/**
	 * Empties/Clear the queue of the server (msgs),
	 * removes all of the messages from the queue.
	 * Sets the currentSize field to "0" since all messages are deleted.
	 */
	public void flush() {
		this.msgs.clear();
		this.currentSize = 0;
	}
	
	/**
	 * Gets the server occupancy rate 
	 * by dividing the current size of the server by the capacity of the server,
	 * and finally multiplying by 100 to get the percentage.
	 * Firstly, sets the currentSize field.
	 * @return	A double representing the server occupancy rate.
	 */
	public double getFraction() {
		double fraction = (this.currentSize/(double)capacity)*100;
		return fraction;
	}
	
	/**
	 * Gets the messages in the server by returning the msgs field. 
	 * @return A queue representing the messages in the server.
	 */
	public Queue<Message> getMsgs() {
		return this.msgs;
	}
	
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

