package at.ah.network;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

/**
 * Controller for Sender and Receiver.
 *
 * @version 1.0
 * @author Klaus Ableitinger, Christoph Hackenberger
 */
public class Controller {

	private static final String password = ActiveMQConnection.DEFAULT_PASSWORD;


	private Sender sender;
	private Receiver receiver;

	public void start(String ip, String chatroom, String username) throws JMSException {

		System.out.println(ActiveMQConnection.DEFAULT_BROKER_URL);

		Connection connection;
		Session session;
		
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(username, password, "tcp://" + ip + ":61616"); // 61616
		connection = connectionFactory.createConnection();
		connection.start();

		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		this.sender = new Sender(session, chatroom, username);
		this.receiver = new Receiver(session, chatroom);
		new Thread(receiver).start();
	}

	public Receiver getReceiver() {

		return receiver;
	}

	public Sender getSender() {

		return sender;
	}
}
