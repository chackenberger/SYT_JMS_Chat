package at.ah.network;

import java.net.InetAddress;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

/**
 * Sender class from which Messages can be send to a specified chatroom or as private messages
 * @author Christoph Hackeneberger
 * @version 1.0
 */
public class Sender {
	
	private Session session;
	private MessageProducer producer;

	/**
	 * Creates a new Sender object with the specified session for the specified chatroom
	 * @param session the session for the connection
	 * @param chatroom the chatroom in which this sender should send
	 * @throws JMSException when there is an internal error in the underlying middleware
	 */
	public Sender(Session session, String chatroom) throws JMSException {
		this.session = session;
		
		this.producer = session.createProducer(session.createTopic(chatroom));
	}
	
	/**
	 * Sends a message to the chatroom
	 * @param message the message which should be send
	 * @throws JMSException when there is an internal error in the underlying middleware
	 */
	public void sendMessage(String message) throws JMSException {
		producer.send(session.createTextMessage(message));
	}
	
	/**
	 * Sends a private message to the user with the specified ip address
	 * @param address the host/ip adress of the user
	 * @param message the message which should be send
	 * @throws JMSException when there is an internal error in the underlying middleware
	 */
	public void sendPrivateMessage(InetAddress address, String message) throws JMSException {
		
		MessageProducer sender = session.createProducer(session.createQueue(address.getHostAddress()));
		sender.send(session.createTextMessage(message));
	}
}
