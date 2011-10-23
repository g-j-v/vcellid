/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package CellId;


/**
 *
 * @author alejandropetit
 */
public class LoadImages_ extends ij.plugin.frame.PlugInFrame{
    
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree jTree1;

    public LoadImages_(){
        super("LoadImages");
        jTree1 = new javax.swing.JTree();
        jScrollPane1 = new javax.swing.JScrollPane();
        

    }

    public void run(String arg){


        
        jTree1.setName("jTree1"); // NOI18N
        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jScrollPane1.setViewportView(jTree1);

       add(jScrollPane1);
       show();
    }
}
