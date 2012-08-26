/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import ij.plugin.frame.PlugInFrame;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author gvilla
 */
public class Help extends PlugInFrame implements ActionListener {

	private JPanel contentPane;

	public Help() {
		super("Help");

	}

	public void run(String arg) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		contentPane.setLayout(null);

		JLabel lblForMoreHelp = new JLabel(
				"For more help on CellID options visit:");
		lblForMoreHelp.setBounds(10, 193, 422, 14);
		contentPane.add(lblForMoreHelp);

		JButton button = new JButton("Link");
		button.setBounds(123, 218, 165, 23);
		final URI uri;
		try {
			uri = new URI("http://www.roseindia.net");
			button.setText("www.roseindia.net");
			button.setHorizontalAlignment(SwingConstants.LEFT);
			button.setBorderPainted(false);
			button.setOpaque(false);
			button.setBackground(Color.lightGray);
			button.setForeground(Color.BLUE);
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
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		show();
	}
}
