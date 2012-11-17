package cellid.error;

import ij.plugin.frame.PlugInFrame;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.SwingConstants;

public class ErrorWindow extends PlugInFrame {

	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	public ErrorWindow(String message) {
		
		super("Error");

//		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 342, 186);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblErrorMessage = new JLabel(message);
		lblErrorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		lblErrorMessage.setBounds(45, 31, 239, 84);
		contentPane.add(lblErrorMessage);
		add(contentPane);
		setVisible(true);
	}
}
