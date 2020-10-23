/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package gamePack;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;



/**
 * 
 * @author cor_b
 */
public class Main extends Application {
   
   /**
    * @param args the command line arguments
    * 
    */
   public static void main(String[] args) {
      launch(args);
   }
   
   @Override
   public void start(Stage stage) throws Exception{
      Pane root = FXMLLoader.load(getClass().getResource("GameFXML.fxml"));
      Font.loadFont(getClass().getResourceAsStream("1942.ttf"), 16);  //16?
      Font.loadFont(getClass().getResourceAsStream("webhostinghub-glyphs.ttf"), 16);  //16? why
      Scene scene = new Scene(root);
      stage.setTitle("Test Game Please Ignore");
      stage.setScene(scene);
      stage.show();
   }
   
}
