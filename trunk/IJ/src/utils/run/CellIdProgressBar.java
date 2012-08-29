/**
 * Class to keep the user informed of the execution
 */

package utils.run;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.beans.*;
import java.util.Date;

public class CellIdProgressBar extends ij.plugin.frame.PlugInFrame implements PropertyChangeListener {

    private JProgressBar progressBar;
    private JTextArea taskOutput;
    private JButton closeButton;

    /**
     * Constructor
     * @param task used to modify the progress bar.
     */
    public CellIdProgressBar(Task task) {
        super("Progress");

        
        setBounds(0, 0, 400, 400);
        
        
        progressBar = new JProgressBar(0,task.getMaxPositions());
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        taskOutput = new JTextArea(5, 20);
        taskOutput.setMargin(new Insets(5,5,5,5));
        taskOutput.setEditable(false);
        
        closeButton = new JButton("Close");
        closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});

        JPanel panel = new JPanel();
        panel.setBounds(10,10,350,350);
        panel.add(progressBar);
        panel.add(closeButton);
        
        add(panel, BorderLayout.PAGE_START);
        add(new JScrollPane(taskOutput), BorderLayout.CENTER);

    }

    /**
     * Invoked when task's progress property changes.
     * @param evt the event
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer) evt.getNewValue();
        	System.out.println("Progress modified, to be updated to " + progress);
            progressBar.setValue(progress);
            taskOutput.append(String.format(
                    "Position %d finished ", progress ) +  "at " + new Date() + "\r\n");
        } 
    }
}
