package at.ah.network;

import at.ah.notification.MessageListener;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Receiver class used to receive messages from a chat room, or private messages.
 *
 * @version 1.0
 * @author Klaus Ableitinger
 */
public class Receiver implements Runnable {

	private boolean run;

	private String chatroom;
	private Session session;

	private List<MessageListener> listenerList;

	private String localhost;

	private Queue privateMessageQueue;

	/**
	 * Creates a new Receiver.
	 *
	 * @param session   the session where this Receiver should listen for messages
	 * @param chatroom  the chatroom where this Receiver should listen for messages
	 * @throws JMSException when something goes wrong during private message queue initialization
	 */
	public Receiver(Session session, String chatroom) throws JMSException {

		this.run = true;

		this.listenerList = new ArrayList<>();
		this.session = session;
		this.chatroom = chatroom;

		try {

			localhost = InetAddress.getLocalHost().getHostAddress();
			privateMessageQueue = session.createQueue(localhost);
		} catch(UnknownHostException ex) {

			throw new RuntimeException(ex);
		}
	}

	@Override
	public void run() {

		try {

			MessageConsumer consumer = session.createConsumer(session.createTopic(chatroom));

			while(run) {

				TextMessage message = (TextMessage) consumer.receive();

				for(MessageListener listener : listenerList)
					listener.newMessage(message);
			}
		} catch(JMSException ex) {

			throw new RuntimeException(ex);
		}
	}

	/**
	 * registers a message listener, which will be notified when new messages arrive.
	 *
	 * @param listener the listener to register
	 */
	public void registerMessageListener(MessageListener listener) {

		listenerList.add(listener);
	}

	/**
	 * Retrieves and returns all private messages currently sent to this host.
	 *
	 * @return all private messages sent to this host
	 * @throws JMSException if something goes wrong during receiving
	 */
	public List<TextMessage> getPrivateMessages() throws JMSException {

		List<TextMessage> messages = new ArrayList<>();

		MessageConsumer consumer = session.createConsumer(privateMessageQueue);

		TextMessage message;
		do {

			message = (TextMessage) consumer.receive(100);
			if(message != null) messages.add(message);
		} while(message != null);

		consumer.close();
		return messages;
	}

	/**
	 * Stops the receiving of messages.
	 */
	public void stopReceiving() {

		run = false;
	}
}
