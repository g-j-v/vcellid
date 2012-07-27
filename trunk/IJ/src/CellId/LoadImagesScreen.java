package CellId;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JComboBox;

public class LoadImagesScreen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPosition;
	private JTextField txtTime;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtBf;
	private JTextField txtFL;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			LoadImagesScreen dialog = new LoadImagesScreen();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public LoadImagesScreen() {
		setBounds(100, 100, 450, 390);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(10, 328, 422, 2);
			contentPanel.add(separator);
		}
		{
			JSeparator separator = new JSeparator();
			separator.setBounds(10, 190, 422, 2);
			contentPanel.add(separator);
		}
		
		JCheckBox chckbxPositionToken = new JCheckBox("position token");
		chckbxPositionToken.setBounds(6, 7, 97, 23);
		contentPanel.add(chckbxPositionToken);
		
		JCheckBox chckbxTimeToken = new JCheckBox("time token");
		chckbxTimeToken.setBounds(6, 33, 97, 23);
		contentPanel.add(chckbxTimeToken);
		
		txtPosition = new JTextField();
		txtPosition.setText("position");
		txtPosition.setBounds(117, 8, 86, 20);
		contentPanel.add(txtPosition);
		txtPosition.setColumns(10);
		
		txtTime = new JTextField();
		txtTime.setText("time");
		txtTime.setBounds(117, 34, 86, 20);
		contentPanel.add(txtTime);
		txtTime.setColumns(10);
		
		JCheckBox chckbxSepCharacter = new JCheckBox("sep. character");
		chckbxSepCharacter.setBounds(6, 73, 97, 23);
		contentPanel.add(chckbxSepCharacter);
		
		textField = new JTextField();
		textField.setText("_");
		textField.setBounds(144, 74, 31, 20);
		contentPanel.add(textField);
		textField.setColumns(10);
		
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(213, 7, 2, 89);
		contentPanel.add(separator);
		
		JLabel lblExampleFluorescenceFile = new JLabel("example: fluorescence file");
		lblExampleFluorescenceFile.setBounds(225, 11, 155, 14);
		contentPanel.add(lblExampleFluorescenceFile);
		
		textField_1 = new JTextField();
		textField_1.setBounds(225, 34, 127, 20);
		contentPanel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblToken = new JLabel("token");
		lblToken.setBounds(144, 105, 46, 14);
		contentPanel.add(lblToken);
		
		JLabel lblPath = new JLabel("path");
		lblPath.setBounds(253, 105, 46, 14);
		contentPanel.add(lblPath);
		
		JLabel lblBrightField = new JLabel("bright field");
		lblBrightField.setBounds(29, 128, 74, 14);
		contentPanel.add(lblBrightField);
		
		JLabel lblFluorescent = new JLabel("fluorescent");
		lblFluorescent.setBounds(29, 153, 74, 14);
		contentPanel.add(lblFluorescent);
		
		txtBf = new JTextField();
		txtBf.setText("BF");
		txtBf.setBounds(144, 125, 46, 20);
		contentPanel.add(txtBf);
		txtBf.setColumns(10);
		
		txtFL = new JTextField();
		txtFL.setText("FL");
		txtFL.setColumns(10);
		txtFL.setBounds(144, 150, 46, 20);
		contentPanel.add(txtFL);
		
		JComboBox bfPath = new JComboBox();
		bfPath.setBounds(253, 124, 99, 22);
		contentPanel.add(bfPath);
		
		JComboBox flPath = new JComboBox();
		flPath.setBounds(253, 149, 99, 22);
		contentPanel.add(flPath);
		
		JCheckBox chckbxUnevenIlluminationCorrection = new JCheckBox("<html>uneven illumination <br> correction image (basename)</html>");
		chckbxUnevenIlluminationCorrection.setBounds(10, 199, 165, 51);
		contentPanel.add(chckbxUnevenIlluminationCorrection);
		
		JCheckBox chckbxCameraBackgroundCorrection = new JCheckBox("<html>camera background <br>correction image (basename)</html>");
		chckbxCameraBackgroundCorrection.setBounds(10, 246, 97, 23);
		contentPanel.add(chckbxCameraBackgroundCorrection);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
