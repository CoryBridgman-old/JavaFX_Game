/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamePack;

/**
 *
 * @author cor_b
 */
public class EnemyUnit extends Unit{

   private int timesKilled = 0;
   
   public EnemyUnit(){
      setHealth(30);
      setLives(4);
      setIcon("Ӝ");
      setTrail("҉");
   }

   public void setTimesKilled(int k){
      timesKilled += k;
   }
   
   public int getTimesKilled(){
      return timesKilled;
   }
   
   public void treeHit(){
      setHealth(-5);
      setLives(-1);
   }
   
   public void playerHit(){
      setHealth(-10);
      if(getHealth() <= 0){
         setLives(-1);
      }
   }
   
   public void enemyHurt(int h, PlayerUnit player){
      setHealth(h);
      player.setScore(-h, 1);
   }
   
}
