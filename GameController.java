/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gamePack;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;

//
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

//CATCH KEY PRESS EVENTS
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Cory Bridgman
 */
public class GameController implements Initializable {

   @FXML
   private VBox gameScene, rightSide, midSide, leftSide;
   @FXML
   private Label hitPoints, messageReport, upBtn, downBtn, leftBtn, rightBtn,
           scoreCard, scoreLabel, userFeedback, scoreGoal, enemyHP, damageDealt,
           pointsAquired, damageLabel, pointsLabel, enemyHPLabel, enemiesKilled;
   
   @FXML
   private Button resetBtn, closeBtn;

   private int pressCount = 0, releaseCount = 0;
   private boolean isPressed = false; //Used to "lockout" keyPress method
   private boolean playerBoom = false;
   private boolean enemyBoom = false;
   private boolean gameOver = false;
   private int startGame = 0;
   private int playerX; // x means ROWS
   private int playerY; // y means COLUMNS
   private int enemyX;
   private int enemyY;
   private int chaseChance = 4; //the % chance (x10) that enemy WONT chase

   private Map map = new Map();
   private PlayerUnit player = new PlayerUnit();
   private EnemyUnit enemy = new EnemyUnit();

   /**
    * Initializes the controller class, and detects keyPress events.
    */
   @Override
   public void initialize(URL url, ResourceBundle rb) {
      gameScene.setOnKeyPressed(event -> keyPress(event)); //Player turn & keylock
      gameScene.setOnKeyReleased(event -> keyPress(event)); //Enemy turn & keyunlock
   }

   public void keyPress(KeyEvent e) { //the main method      
      //Starts the game when keys pressed
      if (startGame < 2 && !isPressed && !gameOver) {
         if (startGame == 0) {
            messageReport.setText("Controls:\n"
                    + "W = Up\nA = Left\nS = Down\nD = Right");
            userFeedback.setText("GO!");
            userFeedback.setStyle("-fx-font-size:40; -fx-text-fill:green; "
                    + "-fx-font-weight: bold;");
            map.generateMap(); //fill arrays
            loadMap(); //fill map
            isPressed = true;
         } else if (startGame == 1) {
            userFeedback.setText("");
            //set player position
            playerX = map.getSize(0) - 1;
            playerY = map.getSize(1) / 2;
            map.getLabel(playerX, playerY).setText(player.getIcon()); 
            map.getLabel(playerX, playerY).setStyle(""
                    + "-fx-text-fill:rgb(150,250,150);"
                    + "-fx-background-color: black");
            //set enemy position
            enemyX = 0;
            enemyY = map.getSize(1) / 2;
            map.getLabel(enemyX, enemyY).setText(enemy.getIcon());
            map.getLabel(enemyX, enemyY).setStyle(""
                    + "-fx-text-fill:black;"
                    + "-fx-background-color:rgb(150,60,60)");

            //load scorechart
            pointsLabel.setText("Points Aquired:");
            pointsAquired.setText(player.getPointsAquired()+"");
            damageLabel.setText("Damage Dealt:");
            damageDealt.setText(player.getEnemyDamage()+"");
            scoreLabel.setText("Score:");
            scoreGoal.setText("/250");
            scoreCard.setText(""+player.getScore());
            scoreCard.setStyle("-fx-text-fill:rgb(1,1,1)");
            enemyHPLabel.setText("Enemy Hit Points:");
            enemiesKilled.setText("Enemies Killed: " + enemy.getTimesKilled());
            
            isPressed = true;
         }
      }
      if (startGame >= 2 && !gameOver) {
         //
         if (playerBoom) {
            checkCollision(3);
         }
         if (enemyBoom) {
            checkCollision(4);
         }
         //
         if (e.getCode().toString().equals("W") && !isPressed && !(playerX == 0)) {
            playerX = playerX - 1;
            movePlayer(playerX, playerY, 1);
            btnColor(1);
            isPressed = true;
            if(playerX == enemyX && playerY == enemyY){player.setHealth(-10);}
            gameOver = player.checkWin(enemy, userFeedback);
         }
         if (e.getCode().toString().equals("D") && !isPressed && !(playerY == map.getSize(1) - 1)) {
            playerY = playerY + 1;
            movePlayer(playerX, playerY, 2);
            btnColor(2);
            isPressed = true;
            if(playerX == enemyX && playerY == enemyY){player.setHealth(-10);}
            gameOver = player.checkWin(enemy, userFeedback);
         }
         if (e.getCode().toString().equals("S") && !isPressed && !(playerX == map.getSize(0) - 1)) {
            playerX = playerX + 1;
            movePlayer(playerX, playerY, 3);
            btnColor(3);
            isPressed = true;
            if(playerX == enemyX && playerY == enemyY){player.setHealth(-10);}
            gameOver = player.checkWin(enemy, userFeedback);
         }
         if (e.getCode().toString().equals("A") && !isPressed && !(playerY == 0)) {
            playerY = playerY - 1;
            movePlayer(playerX, playerY, 4);
            btnColor(4);
            isPressed = true;
            if(playerX == enemyX && playerY == enemyY){player.setHealth(-10);}
            gameOver = player.checkWin(enemy, userFeedback);
         }
      }
      
      //reset key press
      if ((e.getEventType().toString().equals("KEY_RELEASED")) && !gameOver) {
         if (startGame >= 2) {
            moveEnemy();
         }
         startGame++;
//      System.out.println("key Released");
         btnColor(5);
         isPressed = false;
         gameOver = player.checkWin(enemy, userFeedback); //Check for win/lose after enemy move as well
      } else {
//      System.out.println("key Pressed");
      }
      
      
      if(gameOver){
         btnColor(5);
      }
      if(startGame >= 2){
      hitPoints.setText(player.getHealth()+""); //update player hp
      hitPoints.setStyle("-fx-font-weight:bold;"
              + "-fx-text-fill:rgb("+(250-(2.5*player.getHealth()))+",1,1);");
      enemyHP.setText(enemy.getHealth()+""); //update enemy hp
      
      scoreCard.setText(player.getScore()+""); //update player score
      pointsAquired.setText(player.getPointsAquired()+"");
      damageDealt.setText(player.getEnemyDamage()+"");
      enemiesKilled.setText("Enemies Killed: " + enemy.getTimesKilled());
      }
   }

//used to show flashing arrow keys
   public void btnColor(int btn) {
      if (btn == 1) {
         upBtn.setStyle("-fx-background-color:rgb(215,250,215)");
      }
      if (btn == 2) {
         rightBtn.setStyle("-fx-background-color:rgb(215,250,215)");
      }
      if (btn == 3) {
         downBtn.setStyle("-fx-background-color:rgb(215,250,215)");
      }
      if (btn == 4) {
         leftBtn.setStyle("-fx-background-color:rgb(215,250,215)");
      }
      if (btn == 5) {
         upBtn.setStyle("-fx-background-color:rgb(225,225,225)");
         rightBtn.setStyle("-fx-background-color:rgb(225,225,225)");
         downBtn.setStyle("-fx-background-color:rgb(225,225,225)");
         leftBtn.setStyle("-fx-background-color:rgb(225,225,225)");
      }
   }

   public void loadMap() { //perhaps loadMap() if used once?
      for (int i = 0; i < map.getSize(0); i++) { //rows
         HBox blockRow = new HBox();
         midSide.getChildren().add(blockRow);
         blockRow.setAlignment(Pos.CENTER); //set alignment for HBox contents
         for (int k = 0; k < map.getSize(1); k++) { //columns
            blockRow.getChildren().add(map.getLabel(i, k));
         }
      }
   }
public void reloadMap(){
   int reFiller = 0;
   for (int i = 0; i < map.getSize(0); i++){
      for (int k = 0; k < map.getSize(1); k++){
         if(reFiller > 9){
               reFiller = 0;
            }
         map.getLabel(i,k).setText(reFiller+"");
         map.getLabel(i,k).setStyle("");
         reFiller++;
      }
   }
}
   /**
    * Moves the player icon based on user input
    *
    * @param x the row
    * @param y the column
    * @param direction the direction the player input declares
    */
   public void movePlayer(int x, int y, int direction) { // 1=up 2=right 3=left 4=down
      //NOTE: x = rows, y = columns
      checkCollision(0);

      if (map.hasScore(map.getLabel(x, y).getText())) {
         player.setScore(Integer.parseInt(map.getLabel(x, y).getText()), 0);
         scoreCard.setText(""+player.getScore());
         scoreCard.setStyle("-fx-text-fill:rgb(0,"
                 +(1*player.getScore())+",0)");
      }
      map.getLabel(x, y).setText(player.getIcon());
      map.getLabel(x, y).setStyle("-fx-text-fill:rgb(150,250,150);"
              + "-fx-background-color: black");
      
      if (direction == 1) {            //up
         x += 1;
      } else if (direction == 2) {     //right
         y += -1;
      } else if (direction == 3) {     //down
         x += -1;
      } else if (direction == 4) {     //left
         y += 1;
      } else {
         System.out.println("MovementException: how did this happen?");
      }
      //leave player trail (unless checkCollision is true)
      if(!(map.getLabel(x, y).getText().equals("X"))){
         map.getLabel(x, y).setText(player.getTrail());
         map.getLabel(x, y).setStyle("-fx-text-fill:rgb(75,210,75);"
                 + "-fx-background-color: white");
      }
      if(playerX == enemyX && playerY == enemyY){
        enemy.enemyHurt(-5, player);
      }
   }

   /**
    * Enemies turn to move
    */
   public void moveEnemy() {
      int chasePlayer = rollNum(10); //chance to chase player
      int moveDir = -99; //initialize to error

      //if enemy is killed
      if(enemy.getHealth() <= 0){
            enemyX=0; enemyY = map.getSize(1)/2;
            map.getLabel(enemyX, enemyY).setText(enemy.getIcon());
            map.getLabel(enemyX, enemyY).setStyle("-fx-text-fill:black;"
              + "-fx-background-color:rgb(150,60,60)");
            enemy.setHealth(30);
            return; //end movement phase
         }
      
      //If player steps on enemy
      if (playerX == enemyX && playerY == enemyY) { 
         chasePlayer = 1; //force move in random direction (away from player)
      } else {

         map.getLabel(enemyX, enemyY).setText(enemy.getTrail()); //enemy trail
         map.getLabel(enemyX, enemyY).setStyle("-fx-text-fill:black;"
                 + "-fx-background-color: rgb(235,215,215)");
      }
      //dont chase, random movement
      if (chasePlayer < chaseChance) {
         boolean hasMoved = false;
         while (!hasMoved) {
            moveDir = rollNum(4);
            if (moveDir == 0 && (enemyX != 0)) {       //move UP
               enemyX += -1;
               hasMoved = true;
            } else if (moveDir == 1 && (enemyY != map.getSize(1) - 1)) { //move RIGHT
               enemyY += 1;
               hasMoved = true;
            } else if (moveDir == 2 && (enemyX != map.getSize(0) - 1)) { //move DOWN
               enemyX += 1;
               hasMoved = true;
            } else if (moveDir == 3 && (enemyY != 0)) { //move LEFT
               enemyY += -1;
               hasMoved = true;
            }
         }
      }
      //chase player
      if (chasePlayer >= chaseChance) {
         moveDir = rollNum(2);
         if (playerX < enemyX) { //player below enemy
            if (playerY == enemyY) { //player below and inline with enemy
               enemyX += -1;
            } else if (playerY < enemyY) { //player below and left of enemy
               if (moveDir == 0) {
                  enemyX += -1;
               } else if (moveDir == 1) {
                  enemyY += -1;
               }
            } else if (playerY > enemyY) { //player below and right of enemy
               if (moveDir == 0) {
                  enemyX += -1;
               } else if (moveDir == 1) {
                  enemyY += 1;
               }
            }
         } else if (playerX > enemyX) { //player above enemy
            if (playerY == enemyY) { //player above and inline with enemy
               enemyX += 1;
            } else if (playerY < enemyY) { //player above and left of enemy
               if (moveDir == 0) {
                  enemyX += 1;
               } else if (moveDir == 1) {
                  enemyY += -1;
               }
            } else if (playerY > enemyY) {
               if (moveDir == 0) {
                  enemyX += 1;
               } else if (moveDir == 1) {
                  enemyY += 1;
               }
            }
         } else if (playerX == enemyX) { //player and enemy in same row
            if (playerY < enemyY) { //player directly left of enemt
               enemyY += -1;
            } else if (playerY > enemyY) {
               enemyY += 1;
            }
         }
      }
      
      if(playerX == enemyX && playerY == enemyY){
        enemy.enemyHurt(-5, player);
        player.setHealth(-10);
      }
      
      checkCollision(1);
      map.getLabel(enemyX, enemyY).setText(enemy.getIcon());
      map.getLabel(enemyX, enemyY).setStyle("-fx-text-fill:black;"
              + "-fx-background-color:rgb(150,60,60)"); 
   }

   /**
   * When the player or enemy steps on the opposing units trail cause explosion
   */
   public void checkCollision(int c) { //0 for player 1 for enemy 
      if (c == 0) { //check player collision
         if (map.getLabel(playerX, playerY).getText().equals(enemy.getTrail())) {
            System.out.println("PLAYER HIT!");
            playerBoom = true;
            player.setHealth(-5);
            
            for (int i = 0; i < playerY; i++) {                    //explode left
               map.getLabel(playerX, i).setText("X");
               map.getLabel(playerX, i).setStyle("-fx-text-fill:rgb(250,210,210);"
                       + "-fx-background-color:rgb(100,25,25)");
            }
            for (int i = (map.getSize(1) - 1); i > playerY; i--) { //explode right
               map.getLabel(playerX, i).setText("X");
               map.getLabel(playerX, i).setStyle("-fx-text-fill:rgb(250,210,210);"
                       + "-fx-background-color:rgb(100,25,25)");
            }
            for (int i = 0; i < playerX; i++) {                    //explode top
               map.getLabel(i, playerY).setText("X");
               map.getLabel(i, playerY).setStyle("-fx-text-fill:rgb(250,210,210);"
                       + "-fx-background-color:rgb(100,25,25)");
            }
            for (int i = (map.getSize(0) - 1); i > playerX; i--) { //explode down
               map.getLabel(i, playerY).setText("X");
               map.getLabel(i, playerY).setStyle("-fx-text-fill:rgb(250,210,210);"
                       + "-fx-background-color:rgb(100,25,25)");
            }
         }
        checkDamaged(); //see if unit hit by blast
      }
      if (c == 1) { //check enemy collision
         if (map.getLabel(enemyX, enemyY).getText().equals(player.getTrail())) {
            System.out.println("ENEMY HIT!");
            enemyBoom = true;

            for (int i = 0; i < enemyY; i++) {                      //explode left
               map.getLabel(enemyX, i).setText("⁂");
               map.getLabel(enemyX, i).setStyle("-fx-text-fill:rgb(210,250,210);"
                       + "-fx-background-color:rgb(25,100,25)");
            }
            for (int i = (map.getSize(1) - 1); i > enemyY; i--) {   //explode right
               map.getLabel(enemyX, i).setText("⁂");
               map.getLabel(enemyX, i).setStyle("-fx-text-fill:rgb(210,250,210);"
                       + "-fx-background-color:rgb(25,100,25)");
            }
            for (int i = 0; i < enemyX; i++) {                      //explode top
               map.getLabel(i, enemyY).setText("⁂");
               map.getLabel(i, enemyY).setStyle("-fx-text-fill:rgb(210,250,210);"
                       + "-fx-background-color:rgb(25,100,25)");
            }
            for (int i = (map.getSize(0) - 1); i > enemyX; i--) {    //explode down  
               map.getLabel(i, enemyY).setText("⁂");
               map.getLabel(i, enemyY).setStyle("-fx-text-fill:rgb(210,250,210);"
                       + "-fx-background-color:rgb(25,100,25)");
            }
         }
         checkDamaged(); //see if unit hit by blast
      }
      if (c == 3) {
         //clear player blast
         for (int i = 0; i < playerY; i++) {
            map.getLabel(playerX, i).setText("░");
            map.getLabel(playerX, i).setStyle("-fx-background-color:rgb(240,250,240)");
         }
         for (int i = (map.getSize(1) - 1); i > playerY; i--) {    //explode right
            map.getLabel(playerX, i).setText("░");
            map.getLabel(playerX, i).setStyle("-fx-background-color:rgb(240,250,240)");
         }
         for (int i = 0; i < playerX; i++) {                     //explode top
            map.getLabel(i, playerY).setText("░");
            map.getLabel(i, playerY).setStyle("-fx-background-color:rgb(240,250,240)");
         }
         for (int i = (map.getSize(0) - 1); i > playerX; i--) {    //explode right
            map.getLabel(i, playerY).setText("░");
            map.getLabel(i, playerY).setStyle("-fx-background-color:rgb(240,250,240)");
         }
         playerBoom = false;
      }
      if (c == 4) {
         //clear enemy blast
         for (int i = 0; i < enemyY; i++) {
            map.getLabel(enemyX, i).setText("░");
            map.getLabel(enemyX, i).setStyle("-fx-background-color:rgb(250,240,240)");
         }
         for (int i = (map.getSize(1) - 1); i > enemyY; i--) {
            map.getLabel(enemyX, i).setText("░");
            map.getLabel(enemyX, i).setStyle("-fx-background-color:rgb(250,240,240)");
         }
         for (int i = 0; i < enemyX; i++) {
            map.getLabel(i, enemyY).setText("░");
            map.getLabel(i, enemyY).setStyle("-fx-background-color:rgb(250,240,240)");
         }
         for (int i = (map.getSize(0) - 1); i > enemyX; i--) {
            map.getLabel(i, enemyY).setText("░");
            map.getLabel(i, enemyY).setStyle("-fx-background-color:rgb(250,240,240)");
         }
         enemyBoom = false;
      }
   }

   /**
   Roll a random number between 0 and "n"
   */
   public int rollNum(int n) {
      return (int) ((Math.random() * n));
   }

   /**
   Check if explosion tiles hit a unit
   */
   public void checkDamaged(){
      if(map.getLabel(playerX, playerY).getText().equals("⁂")){ ////////////////////////////////
         System.out.println("SPLASH HIT PLAYER");
         player.setHealth(-5);
      }
      if(map.getLabel(enemyX, enemyY).getText().equals("X")){
         enemy.enemyHurt(-5, player);
         System.out.println("SPLASH HIT ENEMY");
      }
   }
   
   
   @FXML
   private void restartGame(ActionEvent event) {
      reloadMap();
      userFeedback.setText("");
      
      //reset player position
      playerX = map.getSize(0) - 1;
      playerY = map.getSize(1) / 2;
      map.getLabel(playerX, playerY).setText(player.getIcon()); 
      map.getLabel(playerX, playerY).setStyle(""
              + "-fx-text-fill:rgb(150,250,150);"
              + "-fx-background-color: black");
      player.setHealth(-(player.getHealth()));
      player.setHealth(100);
      hitPoints.setText(player.getHealth()+"");
      hitPoints.setStyle("-fx-font-weight:bold;");
      
      //reset enemy
      enemyX = 0;
      enemyY = map.getSize(1) / 2;
      map.getLabel(enemyX, enemyY).setText(enemy.getIcon());
      map.getLabel(enemyX, enemyY).setStyle(""
              + "-fx-text-fill:black;"
              + "-fx-background-color:rgb(150,60,60)");
      enemy.setHealth(-(enemy.getHealth()));
      enemy.setHealth(30);
      enemyHP.setText(enemy.getHealth()+"");
      enemy.setTimesKilled(-(enemy.getTimesKilled())); //reset enemy kills to 0
      enemiesKilled.setText("Enemies Killed: "+enemy.getTimesKilled());

      //reset score
      scoreLabel.setText("Score:");
      scoreGoal.setText("/250");
      player.setScore(-(player.getPointsAquired()), 0);
      pointsAquired.setText(player.getPointsAquired()+"");
      player.setScore(-(player.getEnemyDamage()), 1);
      damageDealt.setText(player.getEnemyDamage()+"");
      scoreCard.setText(""+player.getScore());
      scoreCard.setStyle("-fx-text-fill:rgb(1,1,1)");
      
      //reset other
      userFeedback.setText("");
      userFeedback.setStyle("");
      gameOver = false;
      playerBoom = false;
      enemyBoom = false;
   }
   
   @FXML
   private void quitGame(ActionEvent event){
      System.exit(0);
   }
}



// Special characters of interest (FROM 'WINDOWS CHARACTER MAP'):

/*
   
   Ӝ ֎ ᴥ ᵿ // enemies?
   ˂ ˃ ˄ ˅ or ▲ ► ▼ ◄ 
   ҉
   ҈ //bush? enigma?
   ۩ //house?
   † //grave or church
   ←↑→↓
   ☺ 
   ×
   Θ
   ֍
   ⁂
   ࣢ ░ ◌ //debris/flame
   … ⁝ //fence 
   ⁘   //fencepost
   ═ ║ ╔ ╗ ╚ ╝ ╠ ╣ ╦ ╩ ╬ //rails, pipes, walls
   ≈ ░ ▒ //water or terrain
   ‼
   ⸙ //plant
   † //sword?
   
 */
