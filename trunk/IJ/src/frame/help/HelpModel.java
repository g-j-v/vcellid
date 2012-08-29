package frame.help;

import ij.plugin.frame.PlugInFrame;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class HelpModel extends PlugInFrame {

	private JPanel contentPane;

//	/**
//	 * Launch the application.
//	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					HelpModel frame = new HelpModel();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 * @throws URISyntaxException 
	 */
	public HelpModel() throws URISyntaxException {
		super("Help");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblForMoreHelp = new JLabel(
				"For more help on CellID options visit:");
		lblForMoreHelp.setBounds(10, 193, 422, 14);
		contentPane.add(lblForMoreHelp);

		JButton button = new JButton("Link");
		button.setForeground(Color.BLUE);
		button.setBounds(123, 218, 165, 23);
		final URI uri = new URI("http://www.roseindia.net");
		button.setText("www.roseindia.net");
		button.setHorizontalAlignment(SwingConstants.LEFT);
		button.setBorderPainted(false);
		button.setOpaque(false);
		button.setBackground(Color.lightGray);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Desktop.isDesktopSupported()) {
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(uri);
					} catch (Exception ex) {
					}
				} else {
				}
			}
		});
		contentPane.add(button);
	}
}
