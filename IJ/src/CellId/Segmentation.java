/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.font.NumericShaper;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.Action;
import javax.swing.BorderFactory;
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
import javax.swing.ProgressMonitor;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.BevelBorder;

import utils.Output;

/**
 * 
 * @author Gisela
 */
public class Segmentation extends ij.plugin.frame.PlugInFrame{
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
	
	private final JTree jtree;
	private final File directory;
	
	public Segmentation(JTree tree, File file, final boolean BfType, final boolean test) {
		
		super("Segmentation");
		
		this.jtree = tree;
		this.directory = file;
		
		setTitle("Segmentation");
		setBackground(Color.LIGHT_GRAY);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 40, 352, 243);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBackground(Color.LIGHT_GRAY);
		add(panel);
		panel.setLayout(null);
		
		maxDistSpinner.setBounds(233, 11, 53, 18);
		maxDistSpinner.setModel(new SpinnerNumberModel(50.0,0.0,100.0,1.0));
		panel.add(maxDistSpinner);
		
		maxSplitSpinner.setBounds(233, 40, 53, 18);
		maxSplitSpinner.setModel(new SpinnerNumberModel(5.0,0.0,10.0,0.5));
		panel.add(maxSplitSpinner);
		
		minPixelsSpinner.setBounds(233, 89, 53, 18);
		minPixelsSpinner.setModel(new SpinnerNumberModel(100,0,9999,1));
		panel.add(minPixelsSpinner);
		
		maxPixelsSpinner.setBounds(233, 118, 53, 18);
		maxPixelsSpinner.setModel(new SpinnerNumberModel(100,0,9999,1));
		panel.add(maxPixelsSpinner);
		
		backgroundRejectSpinner.setBounds(233, 174, 53, 18);
		backgroundRejectSpinner.setModel(new SpinnerNumberModel(5.0,0.10,10.0,0.1));
		panel.add(backgroundRejectSpinner);
		
		trackingComparisonSpinner.setBounds(233, 203, 53, 18);
		trackingComparisonSpinner.setModel(new SpinnerNumberModel(5.0, 0.0, 10.0, 0.1));
		panel.add(trackingComparisonSpinner);
		
		JLabel maxDistLabel = new JLabel("max dist over waist");
		maxDistLabel.setBounds(30, 13, 157, 14);
		panel.add(maxDistLabel);
		
		JLabel maxSplitLabel = new JLabel("max split over minor axis");
		maxSplitLabel.setBounds(30, 42, 157, 14);
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
		
		JRadioButton noFrameAlignmentRadioButton = new JRadioButton("no frame alignment");
		noFrameAlignmentRadioButton.setMnemonic('N');
		frameAlignmentButtonGroup.add(noFrameAlignmentRadioButton);
		noFrameAlignmentRadioButton.setBounds(46, 32, 160, 23);
		noFrameAlignmentRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(noFrameAlignmentRadioButton);
		
		JRadioButton alignToFirstRadioButton = new JRadioButton("align FL to first");
		alignToFirstRadioButton.setMnemonic('F');
		frameAlignmentButtonGroup.add(alignToFirstRadioButton);
		alignToFirstRadioButton.setBounds(46, 58, 109, 23);
		alignToFirstRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(alignToFirstRadioButton);
		
		JRadioButton alignToBFRadioButton = new JRadioButton("align FL to BF");
		alignToBFRadioButton.setMnemonic('B');
		frameAlignmentButtonGroup.add(alignToBFRadioButton);
		alignToBFRadioButton.setBounds(46, 84, 109, 23);
		alignToBFRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(alignToBFRadioButton);
		
		JLabel cellAlignmentLabel = new JLabel("Cell alignment");
		cellAlignmentLabel.setBounds(10, 114, 81, 14);
		panel_1.add(cellAlignmentLabel);
		
		JRadioButton noCellAlignmentRadioButton = new JRadioButton("no cell alignment");
		noCellAlignmentRadioButton.setMnemonic('N');
		cellAlignmentButtonGroup.add(noCellAlignmentRadioButton);
		noCellAlignmentRadioButton.setBounds(46, 135, 160, 23);
		noCellAlignmentRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(noCellAlignmentRadioButton);
		
		JRadioButton alignIndividualRadioButton = new JRadioButton("align individual cells");
		alignIndividualRadioButton.setMnemonic('I');
		cellAlignmentButtonGroup.add(alignIndividualRadioButton);
		alignIndividualRadioButton.setBounds(46, 161, 160, 23);
		alignIndividualRadioButton.setBackground(Color.LIGHT_GRAY);
		panel_1.add(alignIndividualRadioButton);
		
		cellAlignmentButtonGroup.setSelected(noCellAlignmentRadioButton.getModel(), true);
		frameAlignmentButtonGroup.setSelected(noFrameAlignmentRadioButton.getModel(), true);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(10, 500, 350, 100);
		panel_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_2.setBackground(Color.LIGHT_GRAY);
		add(panel_2);
		panel_2.setLayout(null);
		
		JLabel parametersLabel = new JLabel("Parameters...");
		parametersLabel.setBounds(20, 10, 150, 14);
		panel_2.add(parametersLabel);
		
		//Parameters field for running
		parameters.setEnabled(false);
		parameters.setBounds(100, 40 , 200, 20);
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
				//TODO: Do not save changes. Or restore default values.
				dispose();
			}
		});
		add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
								
				Output output = new Output(jtree, directory);
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
	
}
