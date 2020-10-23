Note: Game requires JavaFX library to be set up in order to function

gamePreview.jpg     -> A screenshot of the game in action

 -=|    Custom Fonts    |=-
1942.ttf
webhostinghub-glyphs.ttf

 -=|     The "View"     |=-
GameFXML.fxml       -> The "View", aka: application window which user interacts with

 -=|  The "Controller"  |=-
Main.java           -> Launches the JavaFX platform
GameController.java -> Handles user input and directs the AI response

 -=|     The "Model"    |=-
Map.java            -> The object which holds the game "board"
Unit.java           -> Parent class, defining the traits of a game player
PlayerUnit.java     -> Child class, extends Unit.java, responsible for the user character
EnemyUnit.java      -> Child class, extends Unit.java, responsible for the enemy character (AI controlled)
