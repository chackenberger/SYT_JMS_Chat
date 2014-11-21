package at.ah.notification;

import javax.jms.TextMessage;

/**
 * Message Listener Interface.
 *
 * @version 1.0
 * @author Klaus Ableitinger, Christoph Hackenberger
 */
public interface MessageListener {

	/**
	 * Called every time a new message is received in the Receiver.
	 *
	 * @param message the message received
	 */
	void newMessage(TextMessage message);
}
