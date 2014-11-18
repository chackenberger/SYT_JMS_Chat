package at.ah.network;

import javax.jms.Session;
import javax.jms.Connection;

public class Receiver implements Runnable {

	private String chatroom;
	private String interf;

	private Session session;

	public Receiver(Connection connection, String chatroom, String interf) {

		this.session = session;
		this.chatroom = chatroom;
		this.interf = interf;
	}

	@Override
	public void run() {


	}
}
