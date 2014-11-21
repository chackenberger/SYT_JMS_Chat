package at.ah.gui;

import at.ah.network.Controller;

import javax.swing.*;
import java.awt.*;

/**
 * GUI main window.
 *
 * @version 1.0
 * @author Klaus Ableitinger
 */
public class MainFrame extends JFrame {

	Controller controller;

	JPanel mainPanel;
	JPanel southPanel;
	JTextArea chatArea;
	JTextField inputField;
	JButton sendButton;

	GuiListener listener;

	/**
	 * Creates a new MainFrame Object.
	 *
	 * @param controller the controller of the frame
	 */
	public MainFrame(Controller controller) {

		this.controller = controller;

		mainPanel = new JPanel(new BorderLayout());
		southPanel = new JPanel();

		listener = new GuiListener(this);

		chatArea = new JTextArea("Welcome to SYT VSDB-Chat\n");
		chatArea.setEditable(false);

		inputField = new JTextField(30);
		inputField.addKeyListener(listener);

		sendButton = new JButton("Send");
		sendButton.addActionListener(listener);
		sendButton.addKeyListener(listener);

		mainPanel.add(chatArea);
		mainPanel.add(southPanel, BorderLayout.SOUTH);

		southPanel.add(inputField);
		southPanel.add(sendButton);

		this.add(mainPanel);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	/**
	 * Prints a error message to this frame.
	 *
	 * @param message the message to print
	 */
	public void printErrorMessage(String message) {

		chatArea.append("### " + message + "\n");
	}

	/**
	 * Returns this MainFrame's listener Object.
	 *
	 * @return this MainFrame's listener Object.
	 */
	public GuiListener getListener() {

		return listener;
	}
}
