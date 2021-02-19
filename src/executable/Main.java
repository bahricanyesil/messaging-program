
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package executable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import elements.Message;
import elements.Server;
import elements.User;

/**
 * Defines the <code>main</code> class to analyze the incoming requests 
 * provided as a list of input in the input file, 
 * and carrying out the necessary actions with the help of the other classes. 
 * After, writes to the output file.
 * @author Bahrican Yesil
 */
public class Main{
	
	/**
	 * Reads input file step by step, does required operations and then prints to the output file.
	 * it takes two inputs as arguments (input.txt, output.txt) 
	 * @param args 					 Command-line arguments
	 * @throws FileNotFoundException Throws in case of files with the specified pathnames do not exist or exist but inaccessible for some reason
	 */
	public static void main(String[] args) throws FileNotFoundException {
		
		// Scanner object to parse the input file composed of the sequential messaging (reading, receiving, etc.) operations. 
		Scanner input = new Scanner(new File(args[0]));
		// PrintStream object to print the required outputs to an output file.
		PrintStream output = new PrintStream(new File(args[1]));
		
		// The first row of the input file specifies the number of users, number of queries, and capacity of the server
		int numOfUser = input.nextInt(); 	// Total number of users in the program
		int numOfQueries = input.nextInt(); // Total number of operations that should be done in the program, number of rows that indicate actions.
		int capacity = input.nextInt(); 	// The capacity of the server.
		
		// An array list that composed of the users with their sequential unique IDs from "0" to the " total number of user - 1".
		ArrayList<User> users = new ArrayList<User>(numOfUser);
		for(int i=0; i<numOfUser; i++) {
			users.add(i, new User(i));
		}
		
		// There is only one server throughout the program and it is created here.
		Server server = new Server(capacity);
		
		// Before the program starts, set the timer that will increase with each action to 0.
		int time = 0;
		
		// Main for loop to take all of the actions respectively by reading line-by-line
		for(int i=0; i<numOfQueries; i++) {
			int query = input.nextInt(); // the action type
			// Action 0 sends a message from a user to another user.
			if(query == 0) {
				int senderId = input.nextInt();			 // id of the user who sent the message.
				int receiverId = input.nextInt();		 // id of the user who will receive the message.
				String msgBody = input.nextLine();	 	 // the text/content of the message. 
				msgBody = msgBody.substring(1);			 // to get rid of the extra space at the beginning of the text.
				double fraction1 = server.getFraction(); // Server occupancy rate before the action.
				// Sends the message from a user to another, does the required operations via 'sendMessage' method.
				users.get(senderId).sendMessage(users.get(receiverId), msgBody, time, server);
				double fraction2 = server.getFraction(); // Server occupancy rate after the action.
				// Following if statements check the server load and prints the warning message if necessary.
				if(fraction1<50&&fraction2>=50) { // e.g., from 49% to 50%
					server.checkServerLoad(output);
				} else if(fraction1>=50&&fraction1<80&&fraction2>=80) { // e.g., from 76% to 83%
					server.checkServerLoad(output);
				} else if(fraction1>=80&&fraction2<80&&fraction2>=50) { // e.g., from 83% to 65%
					server.checkServerLoad(output);
				} else if(fraction1>=80&&fraction2>=100) { // e.g., from 80% to 100%
					server.checkServerLoad(output);
				} else {
					
				}
				time = time + 1; // increase the time because of the processing time of the action.
			} 
			// Action 1 receive messages of the receiver in the server.
			else if(query == 1) {
				int receiverId = input.nextInt(); // id of the user who will receive the messages.
				double fraction1 = server.getFraction(); // Server occupancy rate before the action.
				// Receive the messages of a user from the server, does the required operations via 'receiveMessages' method.
				users.get(receiverId).inbox.receiveMessages(server, time);
				double fraction2 = server.getFraction(); // Server occupancy rate after the action.
				// Following if statements check the server load and prints the warning message if necessary.
				if(fraction1<50&&fraction2>=50) { // e.g., from 49% to 50%
					server.checkServerLoad(output);
				} else if(fraction1>=50&&fraction1<80&&fraction2>=80) { // e.g., from 76% to 83%
					server.checkServerLoad(output);
				} else if(fraction1>=80&&fraction2<80&&fraction2>=50) { // e.g., from 83% to 65%
					server.checkServerLoad(output);
				} else if(fraction1>=80&&fraction2>=100) { // e.g., from 80% to 100%
					server.checkServerLoad(output);
				} else {
					
				}
				time = time + 1; // increase the time because of the processing time of the action.
			} 
			// Action 2 reads a certain amount of messages of the receiver.
			else if(query == 2) {
				int receiverId = input.nextInt(); 	 // id of the user who will receive the messages.
				int numOfMessages = input.nextInt(); // number of messages that should be read.
				// Reads messages via 'readMessages' method, increase the time with the returning value because of the processing time of the action.
				time = time + users.get(receiverId).inbox.readMessages(numOfMessages, time);
			} 
			// Action 21 reads all the messages that are sent to receiver from 'a sender'.
			else if(query == 21) {
				int receiverId = input.nextInt();	// id of the user who will receive the messages.
				int senderId = input.nextInt();		// id of the user who sent the messages.
				// Reads messages via 'readMessages' method with different parameters, increase the time with the returning value because of the processing time of the action.
				time = time + users.get(receiverId).inbox.readMessages(users.get(senderId), time);
			} 
			// Action 22 reads a specific message with its unique id.
			else if(query == 22) {
				int receiverId = input.nextInt();	// id of the user who will receive the message.
				int messageId = input.nextInt();	// id of the message that should be read.
				// Reads the message via 'readMessages' method with different parameters.
				users.get(receiverId).inbox.readMessage(messageId, time);
				time = time + 1; // increase the time because of the processing time of the action.
				// read a specific message
			} 
			// Action 3 makes two user friends, adds each other to their friends lists.
			else if(query == 3) {
				int user1 = input.nextInt(); // the first user who should be friend of the other.
				int user2 = input.nextInt(); // the other user who should be friend of the first one.
				// Makes the first and the other user friend via 'addFriend' method, does required operations.
				users.get(user1).addFriend(users.get(user2));
				time = time + 1; // increase the time because of the processing time of the action.
			} 
			// Action 4 ends the friendship of two user, remove each other from their friends lists.
			else if(query == 4) {
				int user1 = input.nextInt(); // the first user who should be friend of the other.
				int user2 = input.nextInt(); // the other user who should be friend of the first one.
				// Ends the friendship of the first and other user via 'removeFriend' method, does required operations.
				users.get(user1).removeFriend(users.get(user2));
				time = time + 1; // increase the time because of the processing time of the action.
			} 
			// Action 5 flushes server, deletes all messages from the queue of the server.
			else if(query == 5) {
				server.flush(); // flushes the server, does required operations.
				time = time + 1; // increase the time because of the processing time of the action.
			} 
			// Action 6 prints the current size of the server.
			else if(query == 6) {
				output.println("Current load of the server is " + server.getCurrentSize() + " characters.");
				time = time + 1; // increase the time because of the processing time of the action.
			} 
			// Action 61 prints the last message a user has read.
			else if(query == 61) {
				int userId = input.nextInt(); // the id of the user whose last message will be printed.
				Message message = users.get(userId).inbox.getLastReadMessage(); // The message that will be printed.
				if(message != null) {
					// prints the message with the asked form via the 'toString' method.
					output.println(message.toString());
				}
				time = time + 1; // increase the time because of the processing time of the action.
			} else {
				
			}
		}
		input.close();
	}
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

