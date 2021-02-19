
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package boxes;

import java.util.LinkedList;
import java.util.Queue;

import elements.Message;
import elements.User;

/**
 * 
 * Defines a class to create an outbox.
 * This class is a child class of Box class.
 * it stores all messages a user has sent.
 * @author Bahrican Yesil
 * 
 */
public class Outbox extends Box {
	
	/**
	 * A queue where stores all messages constructed with the sender field as the owner of this outbox.
	 */
	private Queue<Message> sent;
	
	/**
	 * Constructs an outbox with its <code>owner</code>,
	 * also initializes the queue that messages are stored.
	 * @param owner The user object representing whose box this is.
	 */
	public Outbox(User owner) {
		super.owner = owner;
		sent = new LinkedList<Message>();
	}
	
	/**
	 * Adds the message given as parameter to the sent queue of the user's outbox. 
	 * Even if a message is never added to the server’s queue due to capacity overload,
	 * adds that message to the sent queue.
	 * @param message Message object representing the message that is sent from a user.
	 */
	public void addSent(Message message) {
		this.sent.add(message);
	}
	
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

