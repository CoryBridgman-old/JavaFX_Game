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
public class Unit {
   
   private int health  = 0;
   private int lives; //not in use
   private String icon;
   private String trail;
   
   
   
   public void setHealth(int hp){
      this.health += hp;
   }
   public void damage(int dmg){
      health = health-dmg;
   }
   public int getHealth(){
      return health;
   }
   public void setLives(int lives){
      this.lives += lives;
   }
   public int getLives(){
      return lives;
   }
   public void setIcon(String icon){
      this.icon = icon;
   }
   public String getIcon(){
      return icon;
   }
   public void setTrail(String trail){
      this.trail = trail;
   };
   public String getTrail(){
      return trail;
   }
}
