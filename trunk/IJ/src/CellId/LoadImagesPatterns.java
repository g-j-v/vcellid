package CellId;

import ij.IJ;
import ij.io.PluginClassLoader;
import ij.plugin.frame.PlugInFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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

import utils.ImageNamePattern;

public class LoadImagesPatterns extends PlugInFrame implements ActionListener {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtPosition;
	private JTextField txtTime;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtBf;
	private JTextField txtFl;



	/**
	 * Create the dialog.
	 */
	public LoadImagesPatterns() {
		super("Load Images Patterns");
	}

	@Override
	public void actionPerformed(ActionEvent e) {		
		
		ImageNamePattern imageNamePattern = ImageNamePattern.getInstance();
		
		setBounds(100, 100, 500, 500);
		setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		JCheckBox chckbxPositionToken = new JCheckBox("Position token");
		chckbxPositionToken.setBounds(5, 10, 130, 30);
		contentPanel.add(chckbxPositionToken);

		JCheckBox chckbxTimeToken = new JCheckBox("Time token");
		chckbxTimeToken.setBounds(5, 35, 130, 30);
		contentPanel.add(chckbxTimeToken);

		txtPosition = new JTextField();
		txtPosition.setText(imageNamePattern.getPositionPattern());
		txtPosition.setBounds(150, 10, 100, 20);
		txtPosition.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {}
			
			@Override
			public void focusLost(FocusEvent e) {
				ImageNamePattern.getInstance().setPositionPattern(txtPosition.getText());
				textField_1.setText(prepareName());			}
		});
		contentPanel.add(txtPosition);
		txtPosition.setColumns(10);

		txtTime = new JTextField();
		txtTime.setText(imageNamePattern.getTimePattern());
		txtTime.setBounds(150, 35, 100, 20);
		txtTime.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {}
			
			@Override
			public void focusLost(FocusEvent e) {
				ImageNamePattern.getInstance().setTimePattern(txtTime.getText());
				textField_1.setText(prepareName());
			}
		});
		contentPanel.add(txtTime);
		txtTime.setColumns(10);

		JCheckBox chckbxSepCharacter = new JCheckBox("Character separator");
		chckbxSepCharacter.setBounds(5, 75, 200, 25);
		contentPanel.add(chckbxSepCharacter);

		textField = new JTextField();
		textField.setText(imageNamePattern.getSeparator());
		textField.setBounds(220, 75, 30, 20);
		textField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent arg0) {}
			
			@Override
			public void focusLost(FocusEvent arg0) {
				ImageNamePattern.getInstance().setSeparator(textField.getText());
				textField_1.setText(prepareName());
			}
		});
			
		contentPanel.add(textField);
		textField.setColumns(10);

		contentPanel.add(new JSeparator(SwingConstants.VERTICAL));

		JLabel lblExampleFluorescenceFile = new JLabel("example: fluorescence file");
		lblExampleFluorescenceFile.setBounds(260, 10, 300, 15);
		contentPanel.add(lblExampleFluorescenceFile);

		textField_1 = new JTextField();
		textField_1.setBounds(260, 35, 200, 20);
		textField_1.setText(prepareName());
		contentPanel.add(textField_1);
		textField_1.setColumns(10);

		JLabel lblToken = new JLabel("token");
		lblToken.setBounds(225, 105, 50, 15);
		contentPanel.add(lblToken);

		JLabel lblPath = new JLabel("path");
		lblPath.setBounds(300, 105, 50, 15);
		contentPanel.add(lblPath);

		JLabel lblBrightField = new JLabel("bright field");
		lblBrightField.setBounds(30, 140, 75, 15);
		contentPanel.add(lblBrightField);

		JLabel lblFluorescent = new JLabel("fluorescent");
		lblFluorescent.setBounds(30, 170, 75, 15);
		contentPanel.add(lblFluorescent);

		txtBf = new JTextField();
		txtBf.setText(imageNamePattern.getBrightfieldChannelPattern());
		txtBf.setBounds(220, 140, 45, 20);
		txtBf.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {}

			@Override
			public void focusLost(FocusEvent e) {
				ImageNamePattern.getInstance().setBrightfieldChannelPattern(txtBf.getText());
				textField_1.setText(prepareName());			}
		});
		contentPanel.add(txtBf);
		txtBf.setColumns(10);

		txtFl = new JTextField();
		txtFl.setText(imageNamePattern.getFluorChannelPattern());
		txtFl.setColumns(10);
		txtFl.setBounds(220, 170, 45, 20);
		txtFl.addFocusListener(new FocusListener() {
			
			@Override
			public void focusGained(FocusEvent e) {};
			
			@Override
			public void focusLost(FocusEvent e) {
				ImageNamePattern.getInstance().setFluorChannelPattern(txtFl.getText());
				textField_1.setText(prepareName());
			}
		});
		contentPanel.add(txtFl);
		
		JComboBox bfPath = new JComboBox();
		bfPath.setBounds(270, 140, 100, 25);
		contentPanel.add(bfPath);

		JComboBox flPath = new JComboBox();
		flPath.setBounds(270, 170, 100, 25);
		contentPanel.add(flPath);
		
		contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		JCheckBox chckbxUnevenIlluminationCorrection = new JCheckBox("<html>uneven illumination <br> correction image (basename)</html>");
		chckbxUnevenIlluminationCorrection.setBounds(10, 200, 165, 50);
		contentPanel.add(chckbxUnevenIlluminationCorrection);

		JCheckBox chckbxCameraBackgroundCorrection = new JCheckBox("<html>camera background <br>correction image (basename)</html>");
		chckbxCameraBackgroundCorrection.setBounds(10, 270, 165, 50);
		contentPanel.add(chckbxCameraBackgroundCorrection);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//ya quedan actualizados
				dispose();
			}
		});
		buttonPane.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageNamePattern.getInstance().restoreDefault();
				dispose();	
			}
		});
		buttonPane.add(cancelButton);

		setResizable(false);
		setVisible(true);
	}
	
	private String prepareName(){
		ImageNamePattern pattern = ImageNamePattern.getInstance();
		return "X"+ pattern.getFluorChannelPattern() + pattern.getSeparator()
				+ pattern.getPositionPattern() + "(d*)" + pattern.getSeparator()
				+ pattern.getTimePattern() + "(d*).tif";
	}
}
