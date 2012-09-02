/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cellid.image;

import ij.plugin.frame.PlugInFrame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 * @author gvilla
 */
public class ImagesSetup extends PlugInFrame implements ActionListener {

	private JPanel contentPane;
	private JTextField nucleusFromChannelTextbox;
	private JComboBox comboBox;
	private JCheckBox chckbxBfAsFl;
	private JCheckBox chckbxNucleusFromChannel;
	private JCheckBox chckbxSplittedFretImage;

	public ImagesSetup() {
		super("Images Setup");

	}

	public void run(String arg) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setTitle("Image Setup");
		setBounds(100, 100, 387, 330);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPane);
		contentPane.setLayout(null);

		JLabel lblImageTypeBrightfield = new JLabel("Image type: brightfield");
		lblImageTypeBrightfield.setBounds(10, 30, 150, 14);
		contentPane.add(lblImageTypeBrightfield);

		chckbxBfAsFl = new JCheckBox("bf as fl");
		chckbxBfAsFl.setBounds(57, 51, 97, 23);
		contentPane.add(chckbxBfAsFl);

		chckbxNucleusFromChannel = new JCheckBox(
				"nucleus from channel");
		chckbxNucleusFromChannel.setBounds(57, 85, 180, 23);
		chckbxNucleusFromChannel.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				AbstractButton abstractButton = (AbstractButton) arg0
						.getSource();
				ButtonModel buttonModel = abstractButton.getModel();

				boolean selected = buttonModel.isSelected();

				nucleusFromChannelTextbox.setEnabled(selected);
			}
		});
		contentPane.add(chckbxNucleusFromChannel);

		nucleusFromChannelTextbox = new JTextField();
		nucleusFromChannelTextbox.setBounds(240, 86, 86, 20);
		nucleusFromChannelTextbox.setEnabled(false);
		contentPane.add(nucleusFromChannelTextbox);
		nucleusFromChannelTextbox.setColumns(10);

		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null,
				null, null));
		panel.setBounds(57, 129, 242, 91);
		contentPane.add(panel);
		panel.setLayout(null);

		chckbxSplittedFretImage = new JCheckBox("splitted fret image");
		chckbxSplittedFretImage.setBounds(42, 5, 150, 23);
		chckbxSplittedFretImage.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				AbstractButton abstractButton = (AbstractButton) arg0
						.getSource();
				ButtonModel buttonModel = abstractButton.getModel();

				boolean selected = buttonModel.isSelected();

				comboBox.setEnabled(selected);
			}
		});
		panel.add(chckbxSplittedFretImage);

		JLabel lblNucleus = new JLabel("nucleus:");
		lblNucleus.setBounds(42, 35, 80, 14);
		panel.add(lblNucleus);

		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "top", "bottom" }));
		comboBox.setBounds(109, 31, 123, 22);
		comboBox.setEnabled(chckbxSplittedFretImage.isSelected());
		panel.add(comboBox);

		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(278, 269, 91, 23);
		btnAceptar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Agregar acciones de persistido de datos
				dispose();
			}
		});
		contentPane.add(btnAceptar);

		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.setBounds(177, 269, 91, 23);
		btnAplicar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: Agregar acciones de persistido de datos
			}
		});
		contentPane.add(btnAplicar);

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(77, 269, 91, 23);
		btnCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		contentPane.add(btnCancelar);

		setVisible(true);
	}
}
