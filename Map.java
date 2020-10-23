package gamePack;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 *
 * @author cor_b
 */
public class Map {

   private int x = 12; //rows (one x per row)
   private int y = 15; //columns (all y per x)
   
   private int filler = 0;

   private Label[][] map = new Label[x][y]; //[i][k]

   public void generateMap() {
      //generate map Labels
      for (int i = 0; i < map.length; i++) {
         for (int k = 0; k < map[i].length; k++) {
            if(filler > 9){
               filler = 0;
            }
            
            Label content = new Label();
            content.setId("gameBlock");
            content.setText(""+filler);
            map[i][k] = content;
            filler++;
         }
      }
   }
   
   public int getSize(int xORy){//0 for x 1 for y
      if(xORy == 0) {
         return x;
      }
      else {
         return y;
      }
   }
   public Label getLabel(int a, int b){
      return map[a][b];
   }
   
   //sloppy way to check if string (aka: game block) is an integer (has score)
   public boolean hasScore(String a){
      try{
         Integer.parseInt(a);
      }catch(Exception notAnInt){
         return false;
      }
      return true;
   }
}
