
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package boxes;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import elements.Message;
import elements.Server;
import elements.User;

/**
 * 
 * Defines a class to create an inbox.
 * This class is a child class of Box class.
 * it stores both read and unread messages sent to a user.
 * it receives messages from the server, and reads messages.
 * @author Bahrican Yesil
 * 
 */
public class Inbox extends Box {
	
	/**
	 * A stack where received but unread messages are stored.
	 */
	private Stack<Message> unread;
	
	/**
	 * A queue where received and read messages are stored.
	 */
	private Queue<Message> read;
	
	/**
	 * Constructs an inbox with its <code>user</code>,
	 * also initializes the queue and stack that messages are stored.
	 * @param user The user object representing whose box this is.
	 */
	public Inbox(User user) {
		super.owner = user;
		unread = new Stack<Message>();
		read = new LinkedList<Message>();
	}
	
	/**
	 * Fetches from the server those messages that are sent from the owner’s friends to the owner. 
	 * Sets these messages' timeStampReceived to the time given as parameter and
	 * pushes them to the unread stack.
	 * @param server Server object representing the server of the program.
	 * @param time	 An integer representing the current time of the program.
	 */
	public void receiveMessages(Server server, int time) {
		// if server doesn't have any messages, method does not continue. 
		if(server.getMsgs().size() == 0) {
			return;
		}
		// Creates a temporary queue to store the messages that are not received to add them back to the server. 
		Queue<Message> temp = new LinkedList<Message>();
		// Traverses the msgs queue to get all messages in the server one by one and control whether they should be received.
		while(server.getMsgs().size()>0) {
			Message received = server.getMsgs().poll();
			// This if statement controls whether the messages' sender and receiver friends or not. 
			if(received.sender.isFriendsWith(received.receiver)) {
				// This if statement controls whether the receiver of the message is the same person with the owner of this inbox. 
				if(received.receiver.getId() == this.owner.getId()) {
					unread.push(received);
					received.setTimeStampReceived(time);
					server.setCurrentSize(server.getCurrentSize()-received.getBody().length());
				} else {
					temp.add(received);
				}
			} else {
				temp.add(received);
			}
		}
		
		// Traverses the temporary queue to take back the messages that are not received to the server queue, msgs.
		while(temp.size() > 0) {
			Message takeBack = temp.poll();
			server.getMsgs().add(takeBack);
		}
		
	}
	
	/**
	 * Reads a certain amount of messages from the unread stack and then pushes these messages to the read queue.
	 * if the num parameter is 0 or the number of messages in unread is less than num, all messages are read.
	 * @param num	An integer representing the number of messages that should be read.
	 * @param time	An integer representing the current time of the program. 
	 * @return		An integer representing the number of successfully read messages.
	 */
	public int readMessages(int num, int time) {
		// if there is no messages in the unread stack, there is no successfully read messages, so returns "1".
		if(unread.size() == 0) {
			return 1;
		} 
		// if the num parameter is 0 or the number of messages in unread is less than num, reads all messages.
		else if(num == 0 || unread.size() < num) {
			int numOfMsgs = unread.size(); // the total number of successfully read messages.
			// Takes the all messages in the unread stack one by one.
			while(unread.size()>0) {
				Message readed = unread.pop(); 
				read.add(readed);
				readed.setTimeStampRead(time);
				time = time + 1;
			}
			return numOfMsgs;
		} else {
			for(int i=0; i<num; i++) {
				Message readed = unread.pop();
				read.add(readed);
				readed.setTimeStampRead(time);
				time = time + 1;
			}
			return num;
		}
	}
	
	
	/**
	 * Reads a specified sender’s messages, 
	 * also sets the timeStampRead of the messages to time given as parameter.
	 * @param sender	User object representing who sent the message.
	 * @param time		An integer representing the current time of the program.
	 * @return			An integer representing the number of successfully read messages.
	 */
	public int readMessages(User sender, int time) {
		// if there is no messages in the unread stack, there is no successfully read messages, so returns "1".
		if(unread.size() == 0) {
			return 1;
		} else {
			int numOfMessages = 0; // the total number of successfully read messages.
			// Creates a temporary stack to store the messages that are not read to add them back to the unread stack. 
			Stack<Message> temp = new Stack<Message>();
			// Traverses the unread stack to get all messages one by one and control whether they are sent from the asked sender.
			while(unread.size()>0) {
				Message toRead = unread.pop();
				// This if statement controls whether the sender of the message is the same person with the sender taken as parameter.
				if(toRead.sender.getId() == sender.getId()) {
					read.add(toRead);
					toRead.setTimeStampRead(time);
					time = time + 1;
					numOfMessages++;
				} else {
					temp.push(toRead);
				}
			}
			
			// Traverses the temporary stack to take back the messages that are not read to add to unread stack.
			while(temp.size()>0) {
				Message noRead = temp.pop();
				unread.push(noRead);
			}
			
			// if no messages is read, returns "1".
			if(numOfMessages == 0) {
				return 1;
			} else {
				return numOfMessages;
			}
		}
	}
	
	
	/**
	 * Reads the message with a specific "id" if it exists in the unread stack.
	 * @param msgId	An integer representing the id of the asked message.
	 * @param time	An integer representing the current time of the program.
	 */
	public void readMessage(int msgId , int time) {
		// if there is no messages in the unread stack, this method does nothing.
		if(unread.size() == 0) {
			return;
		}
		// Creates a temporary stack to store the messages that are not read to add them back to the unread stack. 
		Stack<Message> temp = new Stack<Message>();
		// Traverses the unread stack to get all messages one by one and try to find the message with a specific 'id'.
		while(unread.size()>0) {
			Message readed = unread.pop();
			// This if statement controls whether the id of the message is the same with the asked.
			if(readed.getId() == msgId) {
				read.add(readed);
				readed.setTimeStampRead(time);
			} else {
				temp.push(readed);
			}
		}
		
		// Traverses the temporary stack to take back the messages that are not read to add to unread stack.
		while(temp.size()>0) {
			Message turnBack = temp.pop();
			unread.push(turnBack);
		}
	}
	
	/**
	 * Gets the last message the user has read,
	 * take the last element of the queue.
	 * @return	A message object representing the last message the user has read.
	 */
	public Message getLastReadMessage() {
		// if there is no messages in the read queue, this method return null.
		if(read.size() == 0) {
			return null;
		}
		// Creates a temporary queue to store the messages that are read before the last one to add them back to the read stack. 
		Queue<Message> temp = new LinkedList<Message>();
		// Traverses the read queue until there is one message.
		while(read.size()>1) {
			Message lastRead = read.poll();
			temp.add(lastRead);
		}
		
		// The last read message.
		Message willReturn = read.poll();
		temp.add(willReturn);
		
		// Traverses the temporary queue to take back the messages to add to read stack.
		while(temp.size()>0) {
			Message temporary = temp.poll();
			read.add(temporary);
		}
		
		return willReturn;
	}
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

