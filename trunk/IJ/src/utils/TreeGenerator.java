package utils;

import ij.IJ;

import java.io.File;
import java.io.FilenameFilter;
import java.util.List;

import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;

public class TreeGenerator {
	
	private Finder finder;
	private File directory;
	private List<String> fileNames;	
	
	public TreeGenerator(Finder finder, File directory){
		this.finder = finder;
		this.directory = directory;
		this.fileNames = finder.find(directory);
	}
	
	public JTree generateTree(){
        final JTree tree;
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Images");
    createNodes(top,directory);
    tree = new JTree(top);
    tree.addTreeSelectionListener(new TreeSelectionListener() {
		
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
			System.out.println("Evento");
			if(node == null || node.getChildCount() > 0){
				System.out.println("hola");
				return;
			}
			
			String filePath = node.getUserObject().toString();
			System.out.println(directory.getAbsolutePath());
			System.out.println(filePath);
			IJ.open(directory.getAbsolutePath() + "/" +filePath);				
			
		}
	});
        return tree;
    }

    public void createNodes(DefaultMutableTreeNode top, File directory){
    	
    	List<String> fileNames = finder.find(directory);
    	
        System.out.println("TAM: " + fileNames.size());
        int maxPositions = getMaxPosition();
        for(int i = 1; i <= maxPositions; ++i ){
            Position position = new Position(i);
            //Adding position to root
            DefaultMutableTreeNode positionNode = new DefaultMutableTreeNode(position);
            top.add(positionNode);
            int maxTimesForPosition = getMaxTime(position);
            for(int j = 1; j <= maxTimesForPosition; ++j){
                Time time = new Time(position,j);
                DefaultMutableTreeNode timeNode = new DefaultMutableTreeNode(time);
                //Adding Time node to position
                positionNode.add(timeNode);
                File[] files = directory.listFiles(new NameFilter(position,time));
                time.setFiles(files);
                for(File f : files){
                    timeNode.add(new DefaultMutableTreeNode(f.getName()));
                }
                
            }
            
        }
    }

    public int getMaxPosition(){

       //TODO: Para generalizar todavia mas podemos recibir un parametro
       // que identifique el nombre con el que empieza la posicion, o guardarlo
       // como variable de instancia de Position
        int max = 0;
        for(String name: fileNames){
            int aux;
            // Busco el segundo separador para ver el identificador de la posicion
            String[] info = name.split(finder.separator);
            int endPos = info[1].length();
            int i;
            for(i = endPos - 1; name.charAt(i) >= '0' && name.charAt(i) <= '9' ; i--){
            	;
            }
            System.out.println(i + " - " + endPos + " - " + info[1].substring(endPos - i,endPos));
            aux = Integer.valueOf(info[1].substring(i ,endPos));
            if(aux > max){
                max = aux;
            }
        }
        System.out.println("Max Pos: " + max);
        return max;
    }

    public int getMaxTime(Position position){

       //TODO: Para generalizar todavia mas podemos recibir un parametro
       // que identifique el nombre con el que empieza el tiempo, o guardarlo
       // como variable de instancia de Time
       int max = 0;
       for(String name: fileNames){
            int aux;
            
            if(name.toLowerCase().contains(position.toString())){
                int endPos = name.toLowerCase().indexOf(".");
                int i;
                for(i = endPos - 1; name.charAt(i) >= '0' && name.charAt(i) <= '9' ; i--){
                	;
                }
                System.out.println("time: " + (i + 1) + " - " + endPos + " - " + name.substring(i,endPos));
                aux = Integer.valueOf(name.substring(i + 1,endPos));
                if(aux > max){
                    max = aux;
                }

            }
        }
//        System.out.println("Max time:" + max);
        return max;
    }

    private class NameFilter implements FilenameFilter{

        Position position;
        Time time;

        private NameFilter(Position position, Time time){

            this.position = position;
            this.time = time;
        }

        public boolean accept(File dir, String name) {
           if(name.toLowerCase().contains(position.toString()) &&
                   name.toLowerCase().contains(time.toString())){
               return true;
           }else{
               return false;
           }
        }

    }
    
}
