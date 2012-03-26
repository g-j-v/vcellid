/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.border.BevelBorder;

/**
 * 
 * @author Gisela
 */
public class Segmentation extends javax.swing.JDialog{
	private static final ButtonGroup frameAlignmentButtonGroup = new ButtonGroup();
	private static final ButtonGroup cellAlignmentButtonGroup = new ButtonGroup();
	
	private static JSpinner maxDistSpinner = new JSpinner();
	private static JSpinner maxSplitSpinner = new JSpinner();
	private static JSpinner minPixelsSpinner = new JSpinner();
	private static JSpinner maxPixelsSpinner = new JSpinner();
	private static JSpinner backgroundRejectSpinner = new JSpinner();
	private static JSpinner trackingComparisonSpinner = new JSpinner();
	
	public Segmentation() {
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 11, 352, 243);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		maxDistSpinner.setBounds(233, 11, 53, 18);
		panel.add(maxDistSpinner);
		
		maxSplitSpinner.setBounds(233, 40, 53, 18);
		panel.add(maxSplitSpinner);
		
		minPixelsSpinner.setBounds(233, 89, 53, 18);
		panel.add(minPixelsSpinner);
		
		maxPixelsSpinner.setBounds(233, 118, 53, 18);
		panel.add(maxPixelsSpinner);
		
		backgroundRejectSpinner.setBounds(233, 174, 53, 18);
		panel.add(backgroundRejectSpinner);
		
		trackingComparisonSpinner.setBounds(233, 203, 53, 18);
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
		panel_1.setBounds(10, 265, 352, 197);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel frameAlignmentLabel = new JLabel("Frame alignment");
		frameAlignmentLabel.setBounds(10, 11, 92, 14);
		panel_1.add(frameAlignmentLabel);
		
		JRadioButton noFrameAlignmentRadioButton = new JRadioButton("no frame alignment");
		noFrameAlignmentRadioButton.setMnemonic('N');
		frameAlignmentButtonGroup.add(noFrameAlignmentRadioButton);
		noFrameAlignmentRadioButton.setBounds(46, 32, 133, 23);
		panel_1.add(noFrameAlignmentRadioButton);
		
		JRadioButton alignToFirstRadioButton = new JRadioButton("align FL to first");
		alignToFirstRadioButton.setMnemonic('F');
		frameAlignmentButtonGroup.add(alignToFirstRadioButton);
		alignToFirstRadioButton.setBounds(46, 58, 109, 23);
		panel_1.add(alignToFirstRadioButton);
		
		JRadioButton alignToBFRadioButton = new JRadioButton("align FL to BF");
		alignToBFRadioButton.setMnemonic('B');
		frameAlignmentButtonGroup.add(alignToBFRadioButton);
		alignToBFRadioButton.setBounds(46, 84, 109, 23);
		panel_1.add(alignToBFRadioButton);
		
		JLabel cellAlignmentLabel = new JLabel("Cell alignment");
		cellAlignmentLabel.setBounds(10, 114, 81, 14);
		panel_1.add(cellAlignmentLabel);
		
		JRadioButton noCellAlignmentRadioButton = new JRadioButton("no cell alignment");
		noCellAlignmentRadioButton.setMnemonic('N');
		cellAlignmentButtonGroup.add(noCellAlignmentRadioButton);
		noCellAlignmentRadioButton.setBounds(46, 135, 109, 23);
		panel_1.add(noCellAlignmentRadioButton);
		
		JRadioButton alignIndividualRadioButton = new JRadioButton("align individual cells");
		alignIndividualRadioButton.setMnemonic('I');
		cellAlignmentButtonGroup.add(alignIndividualRadioButton);
		alignIndividualRadioButton.setBounds(46, 161, 133, 23);
		panel_1.add(alignIndividualRadioButton);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(10, 478, 91, 23);
		getContentPane().add(cancelButton);
		
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		okButton.setBounds(271, 473, 91, 23);
		getContentPane().add(okButton);
		
		JButton applyButton = new JButton("Apply");
		applyButton.setBounds(170, 473, 91, 23);
		getContentPane().add(applyButton);
	}
	
	public static double getMaxSplitOverMinor(){
		return (Double)maxSplitSpinner.getValue();
	}
	
	 public static double maxDistOverWaist(){
		 return (Double) maxDistSpinner.getValue();
	 }
	 
	 public static int maxPixelsPerCell (){
		 return (Integer) maxPixelsSpinner.getValue();
	 }
	 
	 
	 public static int minPixelsPerCell(){
		 return (Integer) minPixelsSpinner.getValue();
	 }
	 
	 public static double backgroundRejectFactor (){
		 return (Double) backgroundRejectSpinner.getValue();
	 }
	 
	 public static double trackingComparison (){
		 return (Double) trackingComparisonSpinner.getValue();
	 }
	 
	 public static String cellAlignment(){
		 ButtonModel selected = cellAlignmentButtonGroup.getSelection();
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
