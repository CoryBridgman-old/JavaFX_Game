/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamePack;

import javafx.scene.control.Label;

/**
 *
 * @author cor_b
 */
public class PlayerUnit extends Unit{

   private int score = 0;
   private int enemyDamage = 0;
   private int pointsAquired = 0;
  
   public PlayerUnit(){
      setHealth(100);
      setLives(3); //not in use
      setIcon("☺");
      setTrail("⸙");
   }

   public void setScore(int points, int t){ //0 = points, 1 = enemy damage
      if(t == 0){
         pointsAquired += points;
      }else if(t == 1){
         enemyDamage += points;
      }
      this.score += points;
   }
   
   public int getScore(){
      return score;
   }
   
   public int getEnemyDamage(){
      return enemyDamage;
   }
   
   public int getPointsAquired(){
      return pointsAquired;
   }
   
   public boolean checkWin(EnemyUnit enemy, Label gameMsg){
      //Win condition
      if(score >= 250){
         System.out.println("WIN!");
         gameMsg.setText(" You Win!\nScore: "+ score);
         gameMsg.setStyle("-fx-font-size:40; -fx-text-fill:green; "
                 + "-fx-font-weight: bold;-fx-background-color:rgb(185,240,185,0.5)");
         return true;
      }
      //Lose conditon
      if(getHealth() <= 0){
         System.out.println("LOSE");
         gameMsg.setText(" You lost!\nScore: "+ score);
         gameMsg.setStyle("-fx-font-size:40; -fx-text-fill:rgb(125,60,60); "
           + "-fx-font-weight: bold; -fx-background-color:rgb(235,180,180,0.5)");
         return true;
      }
      
      //Enemy killed
      if(enemy.getHealth() <= 0){
         System.out.println("enemy killed");
         enemy.setTimesKilled(1);
         setScore(20, 1); //enemy kill is worth 20 points of enemy damage
      }
      return false;
   }
}
