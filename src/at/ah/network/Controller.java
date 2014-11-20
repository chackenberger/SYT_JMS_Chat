package at.ah.network;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;

public class Controller {

	private static final String user = ActiveMQConnection.DEFAULT_USER;
	private static final String password = ActiveMQConnection.DEFAULT_PASSWORD;
	private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;


	private Sender sender;
	private Receiver receiver;

	public void start(String chatroom, String interf) {

		Connection connection;
		Session session;
		
		try {

			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);
			connection = connectionFactory.createConnection();
			connection.start();


			connection.start();
			
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch(JMSException ex) {

			throw new RuntimeException(ex);
		}

		try {
			this.sender = new Sender(session, chatroom);
		}catch (JMSException ex) {
			//TODO: Do something
		}
		this.receiver = new Receiver(session, chatroom, interf);
	}
}
