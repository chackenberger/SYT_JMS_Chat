package at.ah.gui;

import at.ah.notification.MessageListener;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.List;

/**
 * GUI Listener Class.
 *
 * @version 1.0
 * @author Klaus Ableitinger
 */
public class GuiListener implements ActionListener, KeyListener, MessageListener {

	MainFrame frame;

	GuiListener(MainFrame frame) {

		this.frame = frame;
	}

	@Override
	public void actionPerformed(ActionEvent ev) {

		String input = frame.inputField.getText();
		if(input == null || input.isEmpty()) return;

		String[] args = input.split(" ");

		if(args.length == 1) {

			switch(args[0]) {

				case "mailbox":
					try {

						List<TextMessage> messages = frame.controller.getReceiver().getPrivateMessages();

						if(messages.isEmpty()) {

							frame.chatArea.append("No new Mails\n");
							break;
						} else {

							frame.chatArea.append("Mailbox:-----\n");
						}

						for(TextMessage message : messages)
							frame.chatArea.append(message.getStringProperty("username") + " " +
									message.getStringProperty("ip") + ": " + message.getText() + "\n");

						frame.chatArea.append("-------------\n");
					} catch(JMSException ex) {

						frame.printErrorMessage(ex.getMessage());
					}
					break;

				case "exit":
					System.exit(0);
					break;

				default:
					try {

						frame.controller.getSender().sendMessage(String.join(" ", args));
					} catch(JMSException ex) {

						frame.printErrorMessage(ex.getMessage());
					}
					break;
			}
		} else if(args.length >= 3) {

			if(args[0].equals("mail")) {

				try {

					String message = String.join(" ", Arrays.copyOfRange(args, 2, args.length));
					frame.controller.getSender().sendPrivateMessage(args[1], message);
					frame.chatArea.append("To " + args[1] + ": " + message + "\n");
				} catch(JMSException ex) {

					frame.printErrorMessage(ex.getMessage());
				}
			} else {

				try {

					frame.controller.getSender().sendMessage(String.join(" ", args));
				} catch(JMSException ex) {

					frame.printErrorMessage(ex.getMessage());
				}
			}
		} else {

			try {

				frame.controller.getSender().sendMessage(String.join(" ", args));
			} catch(JMSException ex) {

				frame.printErrorMessage(ex.getMessage());
			}
		}
		frame.inputField.setText("");
	}

	@Override
	public void keyTyped(KeyEvent ev) { }

	@Override
	public void keyPressed(KeyEvent ev) {

		if(ev.getKeyCode() == KeyEvent.VK_ENTER)
			frame.sendButton.doClick();
	}

	@Override
	public void keyReleased(KeyEvent ev) { }

	@Override
	public void newMessage(TextMessage message) {

		try {

			frame.chatArea.append(message.getStringProperty("username") + " " +
					message.getStringProperty("ip") + ": " + message.getText() + "\n");
		} catch(JMSException ex) {

			frame.printErrorMessage(ex.getMessage());
		}
	}
}
