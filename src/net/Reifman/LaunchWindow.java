package net.Reifman;

/**
 * This file will create the GUI that will retrieve information for the bot. Should collect
 * information on draft order and # of rounds in the nights draft.
 * 
 * @author Andrew Reifman-Packett
 * 
 */

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LaunchWindow {

	JFrame window;
	private static final int EXIT_ON_CLOSE = 0;
	JPanel buttons;
	JPanel running;

	ArrayList<Integer> temp;

	public LaunchWindow() {
		window = new JFrame();
		buttons = new JPanel();
		running = new JPanel();

	}

	public void createGUI() {

		window.getContentPane().add(buttons);
		JLabel lblOrder = new JLabel("Enter Draft Order");
		JLabel lblRounds = new JLabel("How many rounds in tonights draft?");
		final JRadioButton btnNormalDraft = new JRadioButton("6");
		final JRadioButton btnFirstDraft = new JRadioButton("28");

		final JTextField txtOrder = new JTextField(10);

		window.setTitle("Bbfbl Bot");
		window.setSize(300, 200);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(0);

		JButton close = new JButton("Exit");
		JButton start = new JButton("Start");
		ButtonGroup group = new ButtonGroup();
		group.add(btnFirstDraft);
		group.add(btnNormalDraft);
		buttons.setLayout(new FlowLayout());
		buttons.add(lblOrder);
		buttons.add(txtOrder);
		buttons.add(lblRounds);
		buttons.add(btnFirstDraft);
		buttons.add(btnNormalDraft);
		buttons.add(start);
		buttons.add(close);
		window.getContentPane().add(buttons);

		window.pack();

		window.setVisible(true);

		close.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (btnFirstDraft.isSelected()) {
					if (JOptionPane.showConfirmDialog(null,
							"The order inputted is: " + txtOrder.getText()
									+ ". Is that correct?", "WARNING",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						temp = pullInfo(txtOrder, 28);
					} else {
						txtOrder.setText("");
					}

				} else if (btnNormalDraft.isSelected()) {
					if (JOptionPane.showConfirmDialog(null,
							"The order inputted is: " + txtOrder.getText()
									+ ". Is that correct?", "WARNING",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						temp = pullInfo(txtOrder, 6);
					} else {
						txtOrder.setText("");
					}
				} else {
					JOptionPane.showMessageDialog(null,
							"Please ensure you choose the number of rounds");
				}
				if (temp.size() > 1) {
					window.setVisible(false);
					window.dispose();
					int rounds = temp.get(0);
					temp.remove(0);
					ArrayList<Integer> order = temp;
					window.dispose();
					window = new JFrame();
					showRunningWindow();
					connect(order, rounds);
				}
			}
		});

	}

	public ArrayList<Integer> pullInfo(JTextField txtOrder, int numRounds) {
		ArrayList<Integer> returns = new ArrayList<Integer>();
		ArrayList<Integer> temps = new ArrayList<Integer>();
		String tempOrder = txtOrder.getText();
		String[] temp = tempOrder.split("\\s+");
		try {
			returns.add(numRounds);
			for (String t : temp) {
				returns.add(Integer.parseInt(t));
				temps.add(Integer.parseInt(t));
			}
		} catch (Exception e) {
			if (txtOrder.getText().equals(""))
				JOptionPane.showMessageDialog(null,
						"Please enter a draft order");
			else
				JOptionPane.showMessageDialog(null,
						"Please enter only numbers and spaces");
			txtOrder.setText("");
		}

		return returns;
	}

	private void connect(ArrayList<Integer> order, int rounds) {

		BotClass bot = new BotClass("BBFBL_Mod", order, rounds); // create new
																	// bot and
																	// name it
		try {
			bot.setVerbose(true);
			bot.connect("irc.freenode.org"); // connect to freenode. Can be set
												// to other IRC chat
			// bot.sendMessage("nickserv", "IDENTIFY <password>"); //Once
			// username is registered, this
			// will be the password
			bot.joinChannel("#bbfbl"); // what channel to join
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void showRunningWindow() {

		JLabel lblRunning = new JLabel("Running...");
		JButton btnExit = new JButton("Exit");
		window.setTitle("Bbfbl Bot");
		window.setSize(300, 200);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(EXIT_ON_CLOSE);
		running.setLayout(new FlowLayout());
		running.add(lblRunning);
		running.add(btnExit);
		window.getContentPane().add(running);
		window.pack();
		window.setVisible(true);

		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
