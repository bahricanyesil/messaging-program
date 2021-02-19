
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package elements;

import java.util.ArrayList;

import boxes.Inbox;
import boxes.Outbox;

/**
 * 
 * Defines a class to create users with their common features.
 * A user has unique id, friends list, inbox and outbox objects contains messages.
 * @author Bahrican Yesil
 * 
 */
public class User {
	
	/**
	 * The unique identity of the users.
	 */
	private final int id;
	
	/**
	 * The inbox of the user that contains the messages s/he received from the server.
	 */
	public Inbox inbox;
	
	/**
	 * The outbox of the user that contains the messages s/he sent to another user.
	 */
	private Outbox outbox;
	
	/**
	 * The lists of the friends of the user.
	 */
	private ArrayList<User> friends;
	
	/**
	 * Constructs a user with his/her unique <code>id</code>, <code>inbox</code>, <code>outbox</code> and array list of <code>friends</code>.
	 * Creates inbox, outbox objects and friends list.
	 * @param id The integer representing unique identity of the user.
	 */
	public User(int id) {
		this.id = id;
		inbox = new Inbox(this);
		outbox = new Outbox(this);
		friends = new ArrayList<User>();
	}
	
	/**
	 * Adds a friend to the friends list. 
	 * it also adds the user to the other user’s friends list.
	 * in case they are already friends, does nothing.
	 * @param other	The other user that will be friend with the user. 
	 */
	public void addFriend(User other) {
		// To control whether the users are friend or not.
		if(this.isFriendsWith(other)) {
			return;
		}
		this.friends.add(other);
		other.friends.add(this);
	}
	
	/**
	 * Removes a friend from the friends list. 
	 * it also removes the user from the other user’s friends list.
	 * in case they are not friends, does nothing.
	 * @param other	The other user that will be removed from the friends list. 
	 */
	public void removeFriend(User other) {
		if(this.isFriendsWith(other)) {
			friends.remove(other);
			other.friends.remove(this);
		}
	}
	
	/**
	 * Returns true if the user and the other user are friends, false otherwise.
	 * @param other	The other user that will be controlled his/her friendship with the user.
	 * @return 		A boolean representing whether the user is friend with the other user.
	 */
	public boolean isFriendsWith(User other) {
		if(this.friends.contains(other) && other.friends.contains(this)) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Creates a new message and
	 * adds to the user’s sent list which is in his/her outbox via 'addSent' method.
	 * @param receiver	The user object representing who will receive the message.
	 * @param body		The string representing the text/content of the message.
	 * @param time		The integer representing the time of the program.
	 * @param server	The server object representing the server itself.
	 */
	public void sendMessage(User receiver , String body , int time , Server server) {
		Message message = new Message(this, receiver, body, server, time);
		this.outbox.addSent(message);
	}
	
	/**
	 * Gets the <code>id</code> of the user, the getter method of the id field.
	 * @return An integer representing the unique <code>id</code> of the user.
	 */
	public int getId() {
		return this.id;
	}
}


//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

