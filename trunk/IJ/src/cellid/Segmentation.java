
package cellid;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import utils.SegmentationValues;
import utils.run.Output;

/**
 * Class to define the parameters for running CellID
 * @author Gisela
 */
public class Segmentation extends ij.plugin.frame.PlugInFrame{
	
	/**
	 * Variables
	 */
	private static final ButtonGroup frameAlignmentButtonGroup = new ButtonGroup();
	private static final ButtonGroup cellAlignmentButtonGroup = new ButtonGroup();
	
	private static JSpinner maxDistSpinner = new JSpinner();
	private static JSpinner maxSplitSpinner = new JSpinner();
	private static JSpinner minPixelsSpinner = new JSpinner();
	private static JSpinner maxPixelsSpinner = new JSpinner();
	private static JSpinner backgroundRejectSpinner = new JSpinner();
	private static JSpinner trackingComparisonSpinner = new JSpinner();
	private static JTextField parameters = new JTextField();
	private static JCheckBox includeParameters = new JCheckBox("Add");

	private JRadioButton noFrameAlignmentRadioButton;
	private JRadioButton alignToFirstRadioButton;
	private JRadioButton alignToBFRadioButton;
	private	JRadioButton noCellAlignmentRadioButton;
	private JRadioButton alignIndividualRadioButton;

	
	private final JTree jtree;
	private final File directory;
	
	/**
	 * Constructor
	 * @param tree where to run, depending on selected node.
	 * @param file directory where the images can be found.
	 * @param BfType indicates if its a normal run or a BF run.
	 * @param test indicates if it is a test.
	 */
	public Segmentation(JTree tree, File file, final List<String> fluorChannels , final boolean BfType, final boolean test) {
				
		super("Segmentation");
		
		this.jtree = tree;
		this.directory = file;
		
		//Singleton to store the segmentation values
		SegmentationValues segmentationValues = SegmentationValues.getInstance();
		
		setTitle("Segmentation");
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 40, 352, 243);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(Color.LIGHT_GRAY);
		add(panel);
		panel.setLayout(null);
		
		maxDistSpinner.setBounds(233, 11, 80, 18);
		maxDistSpinner.setModel(new SpinnerNumberModel(50.0,0.0,100.0,1.0));
		maxDistSpinner.setValue(segmentationValues.getMaxDistValue());
		panel.add(maxDistSpinner);
		
		maxSplitSpinner.setBounds(233, 40, 80, 18);
		maxSplitSpinner.setModel(new SpinnerNumberModel(5.0,0.0,10.0,0.5));
		maxSplitSpinner.setValue(segmentationValues.getMaxSplitValue());
		panel.add(maxSplitSpinner);
		
		minPixelsSpinner.setBounds(233, 89, 80, 18);
		minPixelsSpinner.setModel(new SpinnerNumberModel(100.0,0,9999,1));
		minPixelsSpinner.setValue(segmentationValues.getMinPixelsValue());
		panel.add(minPixelsSpinner);
		
		maxPixelsSpinner.setBounds(233, 118, 80, 18);
		maxPixelsSpinner.setModel(new SpinnerNumberModel(100.0,0,9999,1));
		maxPixelsSpinner.setValue(segmentationValues.getMaxPixelsValue());
		panel.add(maxPixelsSpinner);
		
		backgroundRejectSpinner.setBounds(233, 174, 80, 18);
		backgroundRejectSpinner.setModel(new SpinnerNumberModel(5.0,0.10,10.0,0.1));
		backgroundRejectSpinner.setValue(segmentationValues.getBackgroundRejectValue());
		panel.add(backgroundRejectSpinner);
		
		trackingComparisonSpinner.setBounds(233, 203, 80, 18);
		trackingComparisonSpinner.setModel(new SpinnerNumberModel(5.0, 0.0, 10.0, 0.1));
		trackingComparisonSpinner.setValue(segmentationValues.getTrackingComparisonValue());
		panel.add(trackingComparisonSpinner);
		
		JLabel maxDistLabel = new JLabel("max dist over waist");
		maxDistLabel.setBounds(30, 13, 180, 14);
		panel.add(maxDistLabel);
		
		JLabel maxSplitLabel = new JLabel("max split over minor axis");
		maxSplitLabel.setBounds(30, 42, 180, 14);
		panel.add(maxSplitLabel);
		
		JLabel minPixelsLabel = new JLabel("min pixels per cell");
		minPixelsLabel.setBounds(30, 91, 157, 14);
		panel.add(minPixelsLabel);
		
		JLabel maxPixelsLabel = new JLabel("max pixels per cell");
		maxPixelsLabel.setBounds(30, 120, 157, 14);
		panel.add(maxPixelsLabel);
		
		JLabel backgroundRejectLabel = new JLabel("background reject factor");
		backgroundRejectLabel.setBounds(30, 176, 157, 14);
		panel.add(backgroundRejectLabel);
		
		JLabel trackingComparisonLabel = new JLabel("tracking comparison");
		trackingComparisonLabel.setBounds(30, 205, 157, 14);
		panel.add(trackingComparisonLabel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setBounds(10, 285, 352, 197);
		panel_1.setBackground(Color.LIGHT_GRAY);
		add(panel_1);
		panel_1.setLayout(null);
		
		JLabel frameAlignmentLabel = new JLabel("Frame alignment");
		frameAlignmentLabel.setBounds(10, 11, 92, 14);
		panel_1.add(frameAlignmentLabel);
				
		noFrameAlignmentRadioButton = new JRadioButton("no frame alignment");
		noFrameAlignmentRadioButton.setMnemonic('N');
		frameAlignmentButtonGroup.add(noFrameAlignmentRadioButton);
		noFrameAlignmentRadioButton.setBounds(46, 32, 160, 23);
		noFrameAlignmentRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(noFrameAlignmentRadioButton);
		
		alignToFirstRadioButton = new JRadioButton("align FL to first");
		alignToFirstRadioButton.setMnemonic('F');
		frameAlignmentButtonGroup.add(alignToFirstRadioButton);
		alignToFirstRadioButton.setBounds(46, 58,160, 23);
		alignToFirstRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(alignToFirstRadioButton);
		
		alignToBFRadioButton = new JRadioButton("align FL to BF");
		alignToBFRadioButton.setMnemonic('B');
		frameAlignmentButtonGroup.add(alignToBFRadioButton);
		alignToBFRadioButton.setBounds(46, 84, 160, 23);
		alignToBFRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(alignToBFRadioButton);
		
		JLabel cellAlignmentLabel = new JLabel("Cell alignment");
		cellAlignmentLabel.setBounds(10, 114, 81, 14);
		panel_1.add(cellAlignmentLabel);
		
		noCellAlignmentRadioButton = new JRadioButton("no cell alignment");
		noCellAlignmentRadioButton.setMnemonic('N');
		cellAlignmentButtonGroup.add(noCellAlignmentRadioButton);
		noCellAlignmentRadioButton.setBounds(46, 135, 160, 23);
		noCellAlignmentRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(noCellAlignmentRadioButton);
		
		alignIndividualRadioButton = new JRadioButton("align individual cells");
		alignIndividualRadioButton.setMnemonic('I');
		cellAlignmentButtonGroup.add(alignIndividualRadioButton);
		alignIndividualRadioButton.setBounds(46, 161, 180, 23);
		alignIndividualRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(alignIndividualRadioButton);
		
		switch(segmentationValues.getCellAlignmentButtonSelected()){
		case 1:
			frameAlignmentButtonGroup.setSelected(noFrameAlignmentRadioButton.getModel(), true);
			break;
		case 2:
			frameAlignmentButtonGroup.setSelected(alignToFirstRadioButton.getModel(), true);
			break;
		case 3:
			frameAlignmentButtonGroup.setSelected(alignToBFRadioButton.getModel(), true);
			break;
		default:
			frameAlignmentButtonGroup.setSelected(noFrameAlignmentRadioButton.getModel(), true);
		}
		
		switch(segmentationValues.getFrameAlignmentButtonSelected()){
		case 1:
			cellAlignmentButtonGroup.setSelected(noCellAlignmentRadioButton.getModel(), true);
			break;
		case 2:
			cellAlignmentButtonGroup.setSelected(alignIndividualRadioButton.getModel(), true);			
			break;
		default:
			cellAlignmentButtonGroup.setSelected(noCellAlignmentRadioButton.getModel(), true);			
		}


		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 500, 350, 100);
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBackground(Color.LIGHT_GRAY);
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel parametersLabel = new JLabel("Advanced Parameters...");
		parametersLabel.setBounds(20, 10, 150, 14);
		panel_2.add(parametersLabel);
		
		//Parameters field for running
		parameters.setEnabled(segmentationValues.isAdvancedParametersEnabled());
		parameters.setBounds(100, 40 , 200, 20);
		parameters.setText(segmentationValues.getAdvancedParameters());
		parameters.setToolTipText("Do not use, unless you know what you are doing");
		panel_2.add(parameters);

		//checkBox for including parameters
		includeParameters.setBackground(Color.lightGray);
		includeParameters.setBounds(20, 30, 80, 40);
		includeParameters.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				if(arg0.getStateChange() == ItemEvent.DESELECTED){
					parameters.setEnabled(false);
				}else if(arg0.getStateChange() == ItemEvent.SELECTED){
					parameters.setEnabled(true);
				}
			}
		});
		panel_2.add(includeParameters);
				
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(10, 650, 91, 23);
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
						
				Output output = new Output(jtree, directory, fluorChannels);
				if(BfType){
					output.generateBF(test);
				}else{
					output.generateRun();
				}
				output.run();
				dispose();
				
			}
		});
		okButton.setBounds(271, 650, 91, 23);
		add(okButton);
		
		JButton applyButton = new JButton("Apply");
		applyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				saveValues();
				dispose();
			}
		});
		applyButton.setBounds(170, 650, 91, 23);
		add(applyButton);
		setSize(400, 700);
		setResizable(false);
	}
	
	public static double getMaxSplitOverMinor(){
		return ((SpinnerNumberModel)maxSplitSpinner.getModel()).getNumber().doubleValue();
	}
	
	 public static double maxDistOverWaist(){
		 return ((SpinnerNumberModel)maxDistSpinner.getModel()).getNumber().doubleValue();
	 }
	 
	 public static int maxPixelsPerCell (){
		 return ((SpinnerNumberModel)maxPixelsSpinner.getModel()).getNumber().intValue();
	 }
	 
	 
	 public static int minPixelsPerCell(){
		 return ((SpinnerNumberModel)minPixelsSpinner.getModel()).getNumber().intValue();
	 }
	 
	 public static double backgroundRejectFactor (){
		 return ((SpinnerNumberModel)backgroundRejectSpinner.getModel()).getNumber().doubleValue();
	 }
	 
	 public static double trackingComparison (){
		 return ((SpinnerNumberModel)trackingComparisonSpinner.getModel()).getNumber().doubleValue();
	 }
	 
	 public static boolean isParameterCheckBoxSelected(){
		 return includeParameters.isSelected();
	 }
	 
	 public static String getParameters(){
		 return parameters.getText();
	 }
	 
	 public static String cellAlignment(){
		 ButtonModel selected = cellAlignmentButtonGroup.getSelection();
		 if(selected == null){
			 return "";
		 }
		 int option  = selected.getMnemonic() ; 
		 if(option == 'N'){
			 return "no_cell_alignment";
		 } else if (option == 'I'){
			 return "align_individual_cells";
		 }
		 return null;
	 }
	 
	 public static String frameAlignment(){
		 ButtonModel selected = cellAlignmentButtonGroup.getSelection();
		 if(selected == null){
			 return "";
		 }
		 int option  = selected.getMnemonic() ; 
		 if(option == 'N'){
			 return "no_frame_alignment";
		 } else if (option == 'F'){
			 return "align_fl_to_first";
		 } else if (option == 'B'){
			 return "align_fl_to_bf";
		 }
		 return null;
	 }
	 
	 /**
	  * Saves the new values in the singleton
	  */
	 private void saveValues(){
		 SegmentationValues segmentationValues = SegmentationValues.getInstance();
		 segmentationValues.setAdvancedParameters(parameters.getText());
		 segmentationValues.setAdvancedParametersEnabled(includeParameters.isSelected());
		 
		 if(frameAlignmentButtonGroup.getSelection().equals(noFrameAlignmentRadioButton.getModel())){
				segmentationValues.setFrameAlignmentButtonSelected(1);
		 }else if(frameAlignmentButtonGroup.getSelection().equals(alignToFirstRadioButton.getModel())){
				segmentationValues.setFrameAlignmentButtonSelected(2);
		 }else if(frameAlignmentButtonGroup.getSelection().equals(alignToBFRadioButton.getModel())){
				segmentationValues.setFrameAlignmentButtonSelected(3);
		 }
		 if(cellAlignmentButtonGroup.getSelection().equals(noCellAlignmentRadioButton.getModel())){
			segmentationValues.setCellAlignmentButtonSelected(1);
		 }else if(cellAlignmentButtonGroup.getSelection().equals(alignIndividualRadioButton.getModel())){
				segmentationValues.setCellAlignmentButtonSelected(2);
		 }
			
		 segmentationValues.setMaxDistValue((Double)maxDistSpinner.getValue());
		 segmentationValues.setMaxSplitValue((Double)maxSplitSpinner.getValue());
		 segmentationValues.setMinPixelsValue((Double)minPixelsSpinner.getValue());
		 segmentationValues.setMaxPixelsValue((Double)maxPixelsSpinner.getValue());
		 segmentationValues.setTrackingComparisonValue((Double)trackingComparisonSpinner.getValue());
		 segmentationValues.setBackgroundRejectValue((Double)backgroundRejectSpinner.getValue());
	 }
	
}
