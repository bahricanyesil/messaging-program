
//DO_NOT_EDIT_ANYTHING_ABOVE_THIS_LINE

package elements;

/**
 * 
 * Defines a class to create messages.
 * All messages in this program are instances of this class.
 * Each message has an ID, a body (text), a sender, a receiver and various time stamps.
 * @author Bahrican Yesil
 * 
 */
public class Message implements Comparable<Message>{
	
	/**
	 * The number of total messages in the program.
	 */
	private static int numOfMessages = 0;
	
	/**
	 * The unique identity of the messages.
	 */
	private final int id;
	
	/**
	 * The text/content of the messages.
	 */
	private final String body;
	
	/**
	 * The user object representing who sent the message.
	 */
	public final User sender;
	
	/**
	 * The user object representing who should receive the message.
	 */
	public final User receiver;
	
	/**
	 * Time information when the message was sent by the sender.
	 */
	private int timeStampSent;
	
	/**
	 * Time information when the message was read by the receiver.
	 */
	private int timeStampRead;
	
	/**
	 * Time information when the message was received by the receiver.
	 */
	private int timeStampReceived;
	
	/**
	 * Constructs a message with his/her unique <code>id</code>, <code>sender</code>, <code>receiver</code>, 
	 * <code>body</code>, <code>timeStampSent</code>, <code>timeStampRead</code> and <code>timeStampReceived</code>.
	 * Adds the message to the server, queue of the server.
	 * Sets sender, receiver and body fields to the parameters.
	 * The IDs of the messages are determined by the order they are created 
	 * as consecutive integer values simply by equaling them to numOfMessages.
	 * Also increases the total number of messages by 1.
	 * @param sender	User object representing who sent the message.
	 * @param receiver	User object representing who should receive the message.
	 * @param body		String representing the text/content of the messages.
	 * @param server	Server object representing the server of the program.
	 * @param time		An integer representing the current time of the program.
	 */
	public Message(User sender , User receiver , String body , Server server , int time){
		this.id = numOfMessages;
		this.sender = sender;
		this.receiver = receiver;
		this.body = body;
		this.timeStampSent = time;
		this.timeStampRead = -1;
		this.timeStampReceived = -1;
		server.getMsgs().add(this);
		server.setCurrentSize(server.getCurrentSize()+this.body.length());
		numOfMessages = numOfMessages + 1;
	}
	
	/**
	 * Sets the timeStampSent field to the time given as parameter.
	 * @param timeStampSent	An integer representing when the message was sent by the sender.
	 */
	public void setTimeStampSent(int timeStampSent) {
		this.timeStampSent = timeStampSent;
	}
	
	/**
	 * Sets the timeStampRead field to the time given as parameter.
	 * @param timeStampRead An integer representing when the message was read by the receiver.
	 */
	public void setTimeStampRead(int timeStampRead) {
		this.timeStampRead = timeStampRead;
	}

	/**
	 * Sets the timeStampReceived field to the time given as parameter.
	 * @param timeStampReceived An integer representing when the message was received by the receiver.
	 */
	public void setTimeStampReceived(int timeStampReceived) {
		this.timeStampReceived = timeStampReceived;
	}

	/**
	 * Gets the <code>id</code> of the message, the getter method of the id field.
	 * @return An integer representing the unique <code>id</code> of the message.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets the <code>body</code> of the message, the getter method of the body field.
	 * @return The string representing the text/content of the message.
	 */
	public String getBody() {
		return this.body;
	}
	
	/**
	 * Returns "1" (positive number) if this message is longer than the other message,
	 * else if the other message is longer, returns "-1" (negative number). 
	 * Returns "0" if both messages have the same number of characters.
	 * @param o	The message object that should be compared with this message.
	 * @return	An integer representing the which message is longer.
	 */
	@Override
	public int compareTo(Message o) {
		if(this.body.length() > o.getBody().length()) {
			return 1;
		} else if(this.body.length() < o.getBody().length()) {
			return -1;
		} else {
			return 0;
		}
	}

	/**
	 * Control whether this message and "o" given as parameter are equals or not
	 * by simply looking at their ids.
	 * @param o	The message object that should be controlled whether it is equal to this message.
	 * @return	A boolean representing the equality of the messages.
	 */
	@Override
	public boolean equals(Object o) {
		if(o instanceof Message) {
			if(this.id == ((Message) o).getId()) {
				return true;
			} else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	
	/**
	 * Returns a String in a specific form with the id of the sender and receiver,
	 * time stamps and the body of the messages.
	 * @return String representing the message object with its features in a specific form.
	 */
	@Override
	public String toString() {
		String stringForm = "";
		stringForm = stringForm + "\t" + "From: " + sender.getId() + " To: " + receiver.getId() + "\n";
		if(this.timeStampRead == -1 && this.timeStampReceived == -1) {
			stringForm = stringForm + "\t" + "Received: " + " Read: " + "\n";
		} else if(this.timeStampRead == -1) {
			stringForm = stringForm + "\t" + "Received: " + this.timeStampReceived + " Read: " + "\n";
		} else if(this.timeStampReceived == -1) {
			stringForm = stringForm + "\t" + "Received: " + " Read: " + this.timeStampRead + "\n";
		} else {
			stringForm = stringForm + "\t" + "Received: " + this.timeStampReceived + " Read: " + this.timeStampRead + "\n";
		}
		stringForm = stringForm + "\t" + this.body;
		return stringForm;
	}
	
}

//DO_NOT_EDIT_ANYTHING_BELOW_THIS_LINE

