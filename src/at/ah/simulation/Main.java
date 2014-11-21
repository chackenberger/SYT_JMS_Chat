package at.ah.simulation;

import at.ah.gui.MainFrame;
import at.ah.network.Controller;

import javax.jms.JMSException;

/**
 * JMS Chat main class.
 *
 * @version 1.0
 * @author Klaus Ableitinger, Christoph Hackenberger
 */
public class Main {

	/**
	 * Main method.
	 *
	 * @param args program arguments
	 */
	public static void main(String[] args) {

		if(args.length != 3) {

			System.out.println("Illegal number of Arguments!");
			System.out.println("vsdbchat <ip_message_broker> <benutzername> <chatroom>");
			return;
		}

		Controller controller = new Controller();

		MainFrame frame = new MainFrame(controller);
		frame.setVisible(true);

		try {

			controller.start(args[0], args[2], args[1]);
		} catch(JMSException ex) {

			ex.printStackTrace();
			frame.printErrorMessage(ex.getMessage());
		}

		controller.getReceiver().registerMessageListener(frame.getListener());
	}
}
