package at.ah.network;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

public class Controller {

	private static final String user = ActiveMQConnection.DEFAULT_USER;
	private static final String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;


	private Sender sender;
	private Receiver receiver;

	public void start(String chatroom, String interf) {

		Connection connection;

		try {

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
			connection = connectionFactory.createConnection();
			connection.start();


			connection.start();
		} catch(JMSException ex) {

			throw new RuntimeException(ex);
		}

		this.sender = new Sender(connection, chatroom);
		this.receiver = new Receiver(connection, chatroom, interf);
	}
}
