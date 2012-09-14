package cellid.image;

import java.awt.EventQueue;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;

public class ImagesSetupModel extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ImagesSetupModel frame = new ImagesSetupModel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ImagesSetupModel() {
		super("ImagesSetup");
		setTitle("Image Setup");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 387, 330);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblImageTypeBrightfield = new JLabel("Image type: brightfield");
		lblImageTypeBrightfield.setBounds(10, 30, 125, 14);
		contentPane.add(lblImageTypeBrightfield);
		
		JCheckBox chckbxBfAsFl = new JCheckBox("bf as fl");
		chckbxBfAsFl.setBounds(57, 51, 97, 23);
		contentPane.add(chckbxBfAsFl);
		
		JCheckBox chckbxNucleusFromChannel = new JCheckBox("nucleus from channel");
		chckbxNucleusFromChannel.setBounds(57, 85, 135, 23);
		contentPane.add(chckbxNucleusFromChannel);
		
		textField = new JTextField();
		textField.setBounds(213, 86, 86, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.setBounds(57, 129, 242, 91);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JCheckBox chckbxSplittedFretImage = new JCheckBox("splitted fret image");
		chckbxSplittedFretImage.setBounds(42, 5, 113, 23);
		panel.add(chckbxSplittedFretImage);
		
		JLabel lblNucleus = new JLabel("nucleus:");
		lblNucleus.setBounds(42, 35, 40, 14);
		panel.add(lblNucleus);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"top", "bottom"}));
		comboBox.setBounds(109, 31, 123, 22);
		panel.add(comboBox);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(278, 269, 91, 23);
		contentPane.add(btnAceptar);
		
		JButton btnAplicar = new JButton("Aplicar");
		btnAplicar.setBounds(177, 269, 91, 23);
		contentPane.add(btnAplicar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setBounds(77, 269, 91, 23);
		contentPane.add(btnCancelar);
	}
}
