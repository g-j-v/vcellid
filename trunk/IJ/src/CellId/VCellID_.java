package CellId;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class VCellID_ extends ij.plugin.frame.PlugInFrame {

	// Botones del VCellId original

	JButton cellIdPathButton;
	JButton loadImagesButton;
	JButton imagesSetupButton;
	JButton segmentationButton;

	public VCellID_() {
		super("VCellId");
	}

	public void run(String arg) {

		cellIdPathButton = new JButton("Open a File...");
		cellIdPathButton.addActionListener(new LoadImages_());

		// For layout purposes, put the buttons in a separate panel
		JPanel buttonPanel = new JPanel(); // use FlowLayout
		buttonPanel.add(cellIdPathButton);

		// Add the buttons and the log to this panel.
		add(buttonPanel, BorderLayout.PAGE_START);
		setSize(500, 600);
		show();
	}
}
