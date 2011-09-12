/*
 * VCellId_v1View.java
 */

package vcellid_v1;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * The application's main frame.
 */
public class VCellId_v1View extends FrameView {

    public VCellId_v1View(SingleFrameApplication app) {
        super(app);

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = VCellId_v1App.getApplication().getMainFrame();
            aboutBox = new VCellId_v1AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        VCellId_v1App.getApplication().show(aboutBox);
    }

     @Action
    public void showCellIdChooser() {
        if (cellIdChooser == null) {
            JFrame mainFrame = VCellId_v1App.getApplication().getMainFrame();
            cellIdChooser = new VCellId_v1CellIdPath(mainFrame,true);
            cellIdChooser.setLocationRelativeTo(mainFrame);
        }
        VCellId_v1App.getApplication().show(cellIdChooser);
    }
     
    @Action
    public void showImageSetup() {
        if (imageSetup == null) {
            JFrame mainFrame = VCellId_v1App.getApplication().getMainFrame();
            imageSetup = new ImageSetup(mainFrame,true);
            imageSetup.setLocationRelativeTo(mainFrame);
        }
        VCellId_v1App.getApplication().show(imageSetup);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        mainToolbar = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        treePanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        imagePanel = new javax.swing.JPanel();
        javax.swing.JLabel appTitleLabel = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        cellIdPathMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        setupMenu = new javax.swing.JMenu();
        loadImageMenuItem = new javax.swing.JMenuItem();
        imagesSetupMenuItem = new javax.swing.JMenuItem();
        segmentationMenuItem = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        jDialog1 = new javax.swing.JDialog();
        jSplitPane1 = new javax.swing.JSplitPane();

        mainPanel.setName("mainPanel"); // NOI18N

        mainToolbar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        mainToolbar.setRollover(true);
        mainToolbar.setName("mainToolbar"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(vcellid_v1.VCellId_v1App.class).getContext().getResourceMap(VCellId_v1View.class);
        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolbar.add(jButton1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(vcellid_v1.VCellId_v1App.class).getContext().getActionMap(VCellId_v1View.class, this);
        jButton2.setAction(actionMap.get("showImageSetup")); // NOI18N
        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolbar.add(jButton2);

        jButton3.setAction(actionMap.get("showSegmentationSetup")); // NOI18N
        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        mainToolbar.add(jButton3);

        treePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("treePanel.border.title"))); // NOI18N
        treePanel.setName("treePanel"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTree1.setName("jTree1"); // NOI18N
        jScrollPane1.setViewportView(jTree1);

        org.jdesktop.layout.GroupLayout treePanelLayout = new org.jdesktop.layout.GroupLayout(treePanel);
        treePanel.setLayout(treePanelLayout);
        treePanelLayout.setHorizontalGroup(
            treePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(treePanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE)
                .addContainerGap())
        );
        treePanelLayout.setVerticalGroup(
            treePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, treePanelLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 395, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        imagePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("imagePanel.border.title"))); // NOI18N
        imagePanel.setName("imagePanel"); // NOI18N

        appTitleLabel.setFont(appTitleLabel.getFont().deriveFont(appTitleLabel.getFont().getSize()+4f));
        appTitleLabel.setIcon(resourceMap.getIcon("appTitleLabel.icon")); // NOI18N
        appTitleLabel.setText(resourceMap.getString("appTitleLabel.text")); // NOI18N
        appTitleLabel.setName("appTitleLabel"); // NOI18N

        org.jdesktop.layout.GroupLayout imagePanelLayout = new org.jdesktop.layout.GroupLayout(imagePanel);
        imagePanel.setLayout(imagePanelLayout);
        imagePanelLayout.setHorizontalGroup(
            imagePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 517, Short.MAX_VALUE)
            .add(imagePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(imagePanelLayout.createSequentialGroup()
                    .add(15, 15, 15)
                    .add(appTitleLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        imagePanelLayout.setVerticalGroup(
            imagePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 421, Short.MAX_VALUE)
            .add(imagePanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(imagePanelLayout.createSequentialGroup()
                    .add(11, 11, 11)
                    .add(appTitleLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainToolbar, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 834, Short.MAX_VALUE)
            .add(mainPanelLayout.createSequentialGroup()
                .add(8, 8, 8)
                .add(treePanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(imagePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(mainPanelLayout.createSequentialGroup()
                .add(mainToolbar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 25, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(imagePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(treePanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        cellIdPathMenuItem.setAction(actionMap.get("showCellIdChooser")); // NOI18N
        cellIdPathMenuItem.setText(resourceMap.getString("cellIdPathMenuItem.text")); // NOI18N
        cellIdPathMenuItem.setName("cellIdPathMenuItem"); // NOI18N
        fileMenu.add(cellIdPathMenuItem);

        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        setupMenu.setText(resourceMap.getString("setupMenu.text")); // NOI18N
        setupMenu.setName("setupMenu"); // NOI18N

        loadImageMenuItem.setText(resourceMap.getString("loadImageMenuItem.text")); // NOI18N
        loadImageMenuItem.setActionCommand(resourceMap.getString("loadImageMenuItem.actionCommand")); // NOI18N
        loadImageMenuItem.setName("loadImageMenuItem"); // NOI18N
        setupMenu.add(loadImageMenuItem);

        imagesSetupMenuItem.setAction(actionMap.get("showImageSetup")); // NOI18N
        imagesSetupMenuItem.setText(resourceMap.getString("imagesSetupMenuItem.text")); // NOI18N
        imagesSetupMenuItem.setName("imagesSetupMenuItem"); // NOI18N
        imagesSetupMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imagesSetupMenuItemActionPerformed(evt);
            }
        });
        setupMenu.add(imagesSetupMenuItem);

        segmentationMenuItem.setAction(actionMap.get("showSegmentationSetup")); // NOI18N
        segmentationMenuItem.setText(resourceMap.getString("segmentationMenuItem.text")); // NOI18N
        segmentationMenuItem.setName("segmentationMenuItem"); // NOI18N
        setupMenu.add(segmentationMenuItem);

        menuBar.add(setupMenu);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        jDialog1.setName("jDialog1"); // NOI18N

        org.jdesktop.layout.GroupLayout jDialog1Layout = new org.jdesktop.layout.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 300, Short.MAX_VALUE)
        );

        jSplitPane1.setName("jSplitPane1"); // NOI18N

        setComponent(mainPanel);
        setMenuBar(menuBar);
    }// </editor-fold>//GEN-END:initComponents

    @Action
    public void showSegmentationSetup() {
        if (segmentationSetup == null) {
            JFrame mainFrame = VCellId_v1App.getApplication().getMainFrame();
            segmentationSetup = new SegmentationSetup(mainFrame,true);
            segmentationSetup.setLocationRelativeTo(mainFrame);
        }
        VCellId_v1App.getApplication().show(segmentationSetup);
    }

    
private void imagesSetupMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imagesSetupMenuItemActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_imagesSetupMenuItemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem cellIdPathMenuItem;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JMenuItem imagesSetupMenuItem;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTree jTree1;
    private javax.swing.JMenuItem loadImageMenuItem;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JToolBar mainToolbar;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem segmentationMenuItem;
    private javax.swing.JMenu setupMenu;
    private javax.swing.JPanel treePanel;
    // End of variables declaration//GEN-END:variables


    private JDialog aboutBox;
    private JDialog cellIdChooser;
    private JDialog imageSetup;
    private JDialog segmentationSetup;
}
