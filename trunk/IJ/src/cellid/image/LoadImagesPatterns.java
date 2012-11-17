package cellid.image;

import ij.plugin.frame.PlugInFrame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cellid.error.ErrorWindow;

import utils.ImageLoadingPaths;
import utils.ImageNamePattern;

public class LoadImagesPatterns extends PlugInFrame implements ActionListener{

	private JPanel contentPanel;
	private JTextField txtPosition;
	private JCheckBox chckbxTimeToken;
	private JTextField txtTime;
	private JTextField txtSeparator;
	private JTextField txtPattern;
	private JTextField txtBf;
	private JTextField txtFl;
	private JTextField txtUnevenIllumination;
	private JTextField txtCameraBackground;
	private JTextField bfPath;
	private File bfDir;
	private JTextField fpPath;
	private File fpDir;
	private JTextField uiPath;
	private File uiDir;
	private JTextField cbPath;
	private File cbDir;
	private JCheckBox chckbxForceSamePath;
	private JCheckBox chckbxUnevenIlluminationCorrection;
	private JCheckBox chckbxCameraBackgroundCorrection;

	/**
	 * Create the dialog.
	 */
	public LoadImagesPatterns() {
		super("Load Images Patterns");
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		removeAll();

		/**
		 * Initialization
		 */
		txtPosition = new JTextField();
		txtTime = new JTextField();
		txtSeparator = new JTextField();
		txtPattern = new JTextField();
		txtBf = new JTextField();
		txtFl = new JTextField();
		txtUnevenIllumination = new JTextField();
		txtCameraBackground = new JTextField();

		ImageNamePattern imageNamePattern = ImageNamePattern.getInstance();
		setBounds(100, 100, 500, 500);
		setLayout(new BorderLayout());

		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		JLabel labelPositionToken = new JLabel("Position token");
		labelPositionToken.setBounds(25, 10, 130, 30);
		contentPanel.add(labelPositionToken);

		chckbxTimeToken = new JCheckBox("Time token");
		chckbxTimeToken.setBounds(5, 35, 130, 30);
		chckbxTimeToken.setSelected(imageNamePattern.isTimeFlag());
		chckbxTimeToken.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if (arg0.getStateChange() == ItemEvent.DESELECTED) {
					txtTime.setEnabled(false);
					txtPattern.setText(prepareName());
				} else if (arg0.getStateChange() == ItemEvent.SELECTED) {
					txtTime.setEnabled(true);
					txtPattern.setText(prepareName());
				}
			}
		});
		contentPanel.add(chckbxTimeToken);

		txtPosition.setText(imageNamePattern.getPositionPattern());
		txtPosition.setBounds(150, 10, 100, 20);
		txtPosition.setEnabled(true);
		txtPosition.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("Entro en position");
				txtPattern.setText(prepareName());
			};

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Salio de position");
				txtPattern.setText(prepareName());
			}
		});
		contentPanel.add(txtPosition);
		txtPosition.setColumns(10);

		txtTime.setText(imageNamePattern.getTimePattern());
		txtTime.setBounds(150, 35, 100, 20);
		txtTime.setEnabled(chckbxTimeToken.isSelected());
		txtTime.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				System.out.println("Entro en time");
				txtPattern.setText(prepareName());
			};

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Salio de time");
				txtPattern.setText(prepareName());
			}
		});
		contentPanel.add(txtTime);
		txtTime.setColumns(10);

		final JLabel labelSepCharacter = new JLabel("Character separator");
		labelSepCharacter.setBounds(25, 75, 180, 25);
		contentPanel.add(labelSepCharacter);

		txtSeparator.setText(imageNamePattern.getSeparator());
		txtSeparator.setBounds(220, 75, 30, 20);
		txtSeparator.setEditable(true);
		txtSeparator.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent arg0) {
				System.out.println("Entro en separador");
				txtPattern.setText(prepareName());
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				System.out.println("Salio de separador");
				txtPattern.setText(prepareName());
				ImageNamePattern.getInstance().setSeparator(
						txtSeparator.getText());
				txtPattern.setText(prepareName());
			}
		});

		contentPanel.add(txtSeparator);
		txtSeparator.setColumns(10);

		contentPanel.add(new JSeparator(SwingConstants.VERTICAL));

		JLabel lblExampleFluorescenceFile = new JLabel(
				"example: fluorescence file");
		lblExampleFluorescenceFile.setBounds(260, 10, 300, 15);
		contentPanel.add(lblExampleFluorescenceFile);

		txtPattern = new JTextField();
		txtPattern.setBounds(260, 35, 200, 20);
		txtPattern.setText(prepareName());
		txtPattern.setEditable(false);
		txtPattern.setColumns(10);
		contentPanel.add(txtPattern);

		JLabel lblToken = new JLabel("token");
		lblToken.setBounds(175, 115, 50, 15);
		contentPanel.add(lblToken);

		JLabel lblPath = new JLabel("path");
		lblPath.setBounds(280, 115, 50, 15);
		contentPanel.add(lblPath);

		JLabel lblBrightField = new JLabel("bright field");
		lblBrightField.setBounds(30, 140, 75, 15);
		contentPanel.add(lblBrightField);

		JLabel lblFluorescent = new JLabel("fluorescent");
		lblFluorescent.setBounds(30, 170, 75, 15);
		contentPanel.add(lblFluorescent);

		txtBf.setText(imageNamePattern.getBrightfieldChannelPattern());
		txtBf.setBounds(170, 140, 45, 20);
		txtBf.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("Entro en BF");
				txtPattern.setText(prepareName());
			}

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Salio de BF");
				txtPattern.setText(prepareName());
			}
		});
		contentPanel.add(txtBf);
		txtBf.setColumns(10);

		txtFl.setText(imageNamePattern.getFluorChannelPattern());
		txtFl.setColumns(10);
		txtFl.setBounds(170, 170, 45, 20);
		txtFl.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				System.out.println("Entro en FL");
				txtPattern.setText(prepareName());
			}

			@Override
			public void focusLost(FocusEvent e) {
				System.out.println("Salio de FL");
				txtPattern.setText(prepareName());
			}
		});
		contentPanel.add(txtFl);

		txtPattern.setText(prepareName());

		bfPath = new JTextField();
		bfPath.setText(ImageLoadingPaths.getInstance().getBfPath());
		bfPath.setBounds(250, 140, 120, 20);
		bfPath.addFocusListener(new FocusListener(){
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				bfDir = validatePath(bfPath.getText());
			}
		});
		contentPanel.add(bfPath);
		final JButton bfSelect = new JButton("Open...");
		bfSelect.setBounds(380, 140, 75, 20);
		bfDir = ImageLoadingPaths.getInstance().getBfDir();
		bfSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				bfDir = ImageLoadingPaths.getInstance().getBfDir();
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(bfDir);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(LoadImagesPatterns.this);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				}

				bfDir = fc.getSelectedFile();
				bfPath.setText(bfDir.getAbsolutePath());
			}
		});
		contentPanel.add(bfSelect);

		fpPath = new JTextField();
		fpPath.setText(ImageLoadingPaths.getInstance().getFpPath());
		fpPath.setBounds(250, 170, 120, 20);
		fpPath.addFocusListener(new FocusListener(){
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				fpDir = validatePath(fpPath.getText());
			}
		});
		contentPanel.add(fpPath);
		final JButton fpSelect = new JButton("Open...");
		fpSelect.setBounds(380, 170, 75, 20);
		fpDir = ImageLoadingPaths.getInstance().getFpDir();
		fpSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				fpDir = ImageLoadingPaths.getInstance().getFpDir();
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(fpDir);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(LoadImagesPatterns.this);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				}

				fpDir = fc.getSelectedFile();
				fpPath.setText(fpDir.getAbsolutePath());

			}
		});
		contentPanel.add(fpSelect);
		contentPanel.add(new JSeparator(SwingConstants.HORIZONTAL));

		txtUnevenIllumination.setBounds(170, 235, 65, 20);
		txtUnevenIllumination.setText(ImageLoadingPaths.getInstance()
				.getUiBasename());
		txtUnevenIllumination.setColumns(50);
		contentPanel.add(txtUnevenIllumination);

		uiPath = new JTextField();
		uiPath.setText(ImageLoadingPaths.getInstance().getUiPath());
		uiPath.setBounds(250, 235, 120, 20);
		uiPath.addFocusListener(new FocusListener(){
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				uiDir = validatePath(uiPath.getText());
			}
		});
		contentPanel.add(uiPath);
		uiPath.setColumns(10);

		final JButton uiSelect = new JButton("Open...");
		uiSelect.setBounds(380, 235, 75, 20);
		uiDir = ImageLoadingPaths.getInstance().getUiDir();
		uiSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				uiDir = ImageLoadingPaths.getInstance().getUiDir();
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(uiDir);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(LoadImagesPatterns.this);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				}

				uiDir = fc.getSelectedFile();
				uiPath.setText(uiDir.getAbsolutePath());

			}
		});
		contentPanel.add(uiSelect);

		chckbxUnevenIlluminationCorrection = new JCheckBox(
				"<html>uneven illumination <br> correction image (basename)</html>");
		chckbxUnevenIlluminationCorrection.setBounds(10, 220, 170, 60);
		chckbxUnevenIlluminationCorrection
				.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent arg0) {
						AbstractButton abstractButton = (AbstractButton) arg0
								.getSource();
						ButtonModel buttonModel = abstractButton.getModel();

						boolean selected = buttonModel.isSelected();

						txtUnevenIllumination.setEditable(selected);

					}
				});
		chckbxUnevenIlluminationCorrection.setSelected(ImageLoadingPaths
				.getInstance().isUiCheck());
		txtUnevenIllumination.setEditable(ImageLoadingPaths.getInstance()
				.isUiCheck());
		contentPanel.add(chckbxUnevenIlluminationCorrection);

		txtCameraBackground.setBounds(170, 285, 65, 20);
		txtCameraBackground.setText(ImageLoadingPaths.getInstance()
				.getCbToken());
		txtCameraBackground.setColumns(50);
		contentPanel.add(txtCameraBackground);

		cbPath = new JTextField();
		cbPath.setText(ImageLoadingPaths.getInstance().getCbPath());
		cbPath.setBounds(250, 285, 120, 20);
		cbPath.addFocusListener(new FocusListener(){
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
			@Override
			public void focusLost(FocusEvent e) {
				cbDir = validatePath(cbPath.getText());
			}
			
		});
		contentPanel.add(cbPath);
		cbPath.setColumns(10);

		final JButton cbSelect = new JButton("Open...");
		cbSelect.setBounds(380, 285, 75, 20);
		cbSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cbDir = ImageLoadingPaths.getInstance().getCbDir();
				JFileChooser fc = new JFileChooser();
				fc.setCurrentDirectory(cbDir);
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(LoadImagesPatterns.this);
				if (returnVal != JFileChooser.APPROVE_OPTION) {
					return;
				}

				cbDir = fc.getSelectedFile();
				cbPath.setText(cbDir.getAbsolutePath());

			}
		});
		contentPanel.add(cbSelect);

		chckbxCameraBackgroundCorrection = new JCheckBox(
				"<html>camera background <br>correction image (basename)</html>");
		chckbxCameraBackgroundCorrection.setBounds(10, 290, 170, 70);
		chckbxCameraBackgroundCorrection
				.addChangeListener(new ChangeListener() {

					@Override
					public void stateChanged(ChangeEvent arg0) {
						AbstractButton abstractButton = (AbstractButton) arg0
								.getSource();
						ButtonModel buttonModel = abstractButton.getModel();

						boolean selected = buttonModel.isSelected();

						txtCameraBackground.setEditable(selected);

					}
				});
		chckbxCameraBackgroundCorrection.setSelected(ImageLoadingPaths
				.getInstance().isCbCheck());
		txtCameraBackground.setEditable(ImageLoadingPaths.getInstance()
				.isCbCheck());
		contentPanel.add(chckbxCameraBackgroundCorrection);

		chckbxForceSamePath = new JCheckBox("force same path");
		chckbxForceSamePath.setBounds(250, 330, 150, 25);
		chckbxForceSamePath.addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent arg0) {
				AbstractButton abstractButton = (AbstractButton) arg0
						.getSource();
				ButtonModel buttonModel = abstractButton.getModel();

				boolean selected = buttonModel.isSelected();

				fpPath.setEnabled(!selected);
				fpSelect.setEnabled(!selected);
				uiPath.setEnabled(!selected);
				uiSelect.setEnabled(!selected);
				cbPath.setEnabled(!selected);
				cbSelect.setEnabled(!selected);

			}
		});
		chckbxForceSamePath.setSelected(ImageLoadingPaths.getInstance()
				.isForcePath());
		contentPanel.add(chckbxForceSamePath);

		JLabel lblGroupBy = new JLabel("group images by");
		lblGroupBy.setBounds(15, 380, 150, 15);
		contentPanel.add(lblGroupBy);

		JComboBox groupBy = new JComboBox();
		groupBy.setModel(new DefaultComboBoxModel(new String[] {
				"file name pattern", "time (metamorph)" }));
		groupBy.setBounds(225, 380, 150, 20);
		contentPanel.add(groupBy);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		add(buttonPane, BorderLayout.SOUTH);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (bfPath == null || bfPath.getText().trim().isEmpty()) {
					new ErrorWindow("<html>No path selected</html>");
				} else if(chckbxCameraBackgroundCorrection.isSelected() && txtCameraBackground.getText().isEmpty()){
					new ErrorWindow("<html>No file name for Background correction, but option enabled</html>");
				}
				else  if(chckbxUnevenIlluminationCorrection.isSelected() && txtUnevenIllumination.getText().isEmpty()){
					new ErrorWindow("<html>No file name for Flat correction, but option enabled</html>");
				}
				else
				{
					ImageNamePattern.getInstance().setTimeFlag(
							chckbxTimeToken.isSelected());
					ImageNamePattern.getInstance().setPositionPattern(
							txtPosition.getText());
					ImageNamePattern.getInstance().setTimePattern(
							txtTime.getText());
					ImageNamePattern.getInstance().setSeparator(
							txtSeparator.getText());
					ImageNamePattern.getInstance()
							.setBrightfieldChannelPattern(txtBf.getText());
					ImageNamePattern.getInstance().setFluorChannelPattern(
							txtFl.getText());
					ImageLoadingPaths.getInstance().setBfPath(bfPath.getText());
					ImageLoadingPaths.getInstance().setBfDir(bfDir);
					ImageLoadingPaths.getInstance().setFpPath(fpPath.getText());
					ImageLoadingPaths.getInstance().setFpDir(fpDir);
					ImageLoadingPaths.getInstance().setUiToken(
							txtUnevenIllumination.getText());
					ImageLoadingPaths.getInstance().setUiPath(uiPath.getText());
					ImageLoadingPaths.getInstance().setUiDir(uiDir);
					ImageLoadingPaths.getInstance().setUiCheck(
							chckbxUnevenIlluminationCorrection.isSelected());
					ImageLoadingPaths.getInstance().setCbToken(
							txtCameraBackground.getText());
					ImageLoadingPaths.getInstance().setCbPath(cbPath.getText());
					ImageLoadingPaths.getInstance().setCbDir(cbDir);
					ImageLoadingPaths.getInstance().setCbCheck(
							chckbxCameraBackgroundCorrection.isSelected());
					ImageLoadingPaths.getInstance().setForcePath(
							chckbxForceSamePath.isSelected());
					new LoadImages().run();
					dispose();
				}
			}
		});
		buttonPane.add(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// ImageNamePattern.getInstance().restoreDefault();
				dispose();
			}
		});
		buttonPane.add(cancelButton);

		setResizable(false);
		setVisible(true);
	}
	
	private File validatePath(String path) {
		if(!path.isEmpty()){
			File aux = new File(path);
			if(!aux.exists()){
				new ErrorWindow("<html>Invalid Path</html>");
			}else if(!aux.isDirectory()){
				new ErrorWindow("<html>Path is not a directory</html>");
			}else{
				return aux;
			}
		}
		return null;
	}
	
	private String prepareName() {
		return "?"
				+ txtFl.getText()
				+ txtSeparator.getText()
				+ txtPosition.getText() + "(d*)"
				+ txtSeparator.getText()
				+ (chckbxTimeToken.isSelected() == true ? txtTime.getText()
						+ "(d*)" : "") + ".tif";
	}
}
