/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cellid.help;

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
import javax.swing.JTextPane;
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
		setResizable(false);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		contentPane.setLayout(null);
		
		JLabel credits = new JLabel();
		credits.setBounds(25, 25, 400, 170);
		credits.setText("<html>VCellID for ImageJ<br/>Version 1.0<br/>by Alejandro Petit and Gisela de la Villa</html>");
		credits.setVerticalAlignment(SwingConstants.CENTER);
		credits.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(credits);
		

		JLabel lblForMoreHelp = new JLabel(
				"For more help on CellID options visit:");
		lblForMoreHelp.setBounds(10, 193, 422, 14);
		contentPane.add(lblForMoreHelp);

		JButton button = new JButton("Link");
		button.setBounds(50, 218, 350, 23);
		final URI uri;
		try {
			uri = new URI("http://ip138.qb.fcen.uba.ar/embnet/Cell-ID-Rcell/Cell-ID-main.htm");
			button.setText("Cell-ID Online Help");
			button.setHorizontalAlignment(SwingConstants.CENTER);
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

		setVisible(true);
	}
}
