package at.ah.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Sender class from which Messages can be send to a specified chat room or as private messages.
 *
 * @author Christoph Hackenberger
 * @version 1.0
 */
public class Sender {
	
	private Session session;
	private MessageProducer producer;
	private String username;

	private String localhost;

	/**
	 * Creates a new Sender object with the specified session for the specified chat room.
	 *
	 * @param session the session for the connection
	 * @param chatroom the chat room in which this sender should send
	 * @param username the username the user should be identified with
	 * @throws JMSException when there is an internal error in the underlying middleware
	 */
	public Sender(Session session, String chatroom, String username) throws JMSException {

		this.session = session;
		this.producer = session.createProducer(session.createTopic(chatroom));
		this.username = username;

		try {

			localhost = InetAddress.getLocalHost().getHostAddress();
		} catch(UnknownHostException ex) {

			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Sends a message to the chat room
	 *
	 * @param message the message which should be send
	 * @throws JMSException when there is an internal error in the underlying middleware
	 */
	public void sendMessage(String message) throws JMSException {

		TextMessage txtMessage = session.createTextMessage(message);
		txtMessage.setStringProperty("username", username);
		txtMessage.setStringProperty("ip", localhost);
		producer.send(txtMessage);
	}
	
	/**
	 * Sends a private message to the user with the specified ip address
	 *
	 * @param address the host/ip address of the user
	 * @param message the message which should be send
	 * @throws JMSException when there is an internal error in the underlying middleware
	 */
	public void sendPrivateMessage(String address, String message) throws JMSException {

		MessageProducer sender = session.createProducer(session.createQueue(address));
		TextMessage txtMessage = session.createTextMessage(message);
		txtMessage.setStringProperty("username", username);
		txtMessage.setStringProperty("ip", localhost);
		sender.send(txtMessage);
	}
}
