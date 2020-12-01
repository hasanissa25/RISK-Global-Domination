# SYSC 3110 Project – RISK!
# Group 10

#### Hasan [@hasanissa25](https://github.com/hasanissa25) - 100921446
#### John Afolayan [@John-Afolayan](https://github.com/John-Afolayan)
#### Temitayo Adegoke [@tayothegamer](https://github.com/tayothegamer)


## Project Kanban
https://github.com/hasanissa25/SYSC3110-Risk-Group10/projects/1?add_cards_query=is%3Aopen

## Project Description
The goal of this team project is to reproduce a simplified version of the classic strategy
game RISK. If you don’t know the game, you can play “RISK: Global Domination” as a
free download from Steam. The Map and the rules can also be found on Wikipedia.
We will skip the initial phase where players normally take turns placing armies on
countries, and instead we will start with a random allocation of armies. This corresponds
to the “auto” setup in “RISK: Global Domination”.
We will also skip the phase of drawing cards and using them to receive more armies.
But for the rest we will stick with the official rules, and the official Map and countries.
The number of players ranges from 2 to 6, and the corresponding initial number of armies
is 50, 35, 30, 25, and 20 respectively, depending on the number of players.

## Documentation 

### UML Class Diagram
![UML Class Diagram](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/UML4.png)

### Sequence Diagram
![Initialize Sequence Diagram ](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/InitializeSequence.jpeg)

![Attack Sequence Diagram ](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/AttackSequence.jpeg)

### Detailed Description of Design

#### Game
* The Model
* Takes an Attack Command, and passes it to the Attack Algorithm
method.
* Handles the random allocation of Players (and their troops) to
Countries.
* Creates the map of all Countries and their neighbours.
* Has the countries, and the players in the current game.
* Calls update on its listeners (Views) that are subscribed to it

#### View
* The visual representation of the Model. The actual look of the game.
* Shows the game map
* Shows the current players countries
* Shows the number of troops in each country ◦ Shows the result of an attack
* Handles the update method invoked by the model, and updates it self, to refect those changes, in-order for the user to see the results of his actions.
* Has buttons that the user can click, that invoke actions on the action listeners for those specifc buttons.
* Has a text area, that shows results of attacks such as number of troops lost, and the winner and loser of a battle.

#### Controller
* Listens to user inputs (Mouse clicks) on the view, translates them into actions to be taken by the model
* Handles New game button click from the view, informing the model.
* Handles Attack button click from the view, informing the model.
* Handles Pass turn button click from the view, informing the model. • Handles Quit button click from the view, informing the model.

#### Country 
* Part of the model.
* Represents the countries in our Risk map.
* Knows the occupying player.
* Knows the number of currently occupying troops.

#### Map
* Part of the model.
* This is the Map representation of our Risk world. 
* Represented as Graph<Country, DefaultEdge>. This allows us to have an easier time manipulating countries, and their neighbouring countries.
 * Every Country is a Vertex, which has neighbouring countries connected to its Edges.

#### Player
* Part of the model.  
* Represents the players of the game.
* The player knows the countries they currently own. 
* The player knows the possible targets they can choose to attack, based on their countries neighbours.
* The player knows how many troops they have to deploy for next milestones when players start receiving troops throughout the game.

#### Command
* A string of 4 words, which is converted into a command for internal use.
* The first word is a Command Word that dictates what type of action will be taken due to this command.

#### CommandWord
* Commands that could be passed to the game to dectate a specific action.
* Command Words include Attack,Map,Quit, and Pass.
* Attac: Initiate an attack from one country to its neighbour, using a specified number of troops.
* Map: Returns a Risk world representation, showing all countries, the player that owns them, and the number of troops in that country.
* Quit: Exits the game
* Pass: Pass the turn from the current player to the next player.

#### Parser
* This class handles the gathering of user input, and turns it into a Command.
* Ensures the user is following proper syntax when entering commands.
 
#### UtilArray
* A Java program to remove an element from a specific index from an array.
* In our situation, it handles removing a country from an array.

#### Continent
* Represents the continents in the map.
* A continent is a collection of countries.
* Holding a continent provides a bonus number of troops at the start of a new players turn.

#### Coordinate
* This class is an x and y coordinate that represents the position of a country on the map.

#### Edge
* A direct path between two countries, known as an “edge”.
* An edge is used in graphs, to connect to the source vertex and the target vertex.
* In our code, we are identifying countries that are neighbouring each other, by checking if they have an edge between them.

## The Readme

### The rest of the deliverbles
* A UML Class diagram showing the full program and the associations between classes.
* A UML Sequence Diagram showing the two most important scenarios: Attack Command, and Initialize game. 
* The Source code, and a runnable Jar file, including the JavaDoc, and JUnit tests
* Documentation
* Readme file.

## The changes that were made since the previous deliverable:
* Implemented MVC
  * Our Model package includes Country, Map, Player, and Game.
    * The model contains all the data that we will be working with. The model has views which listen to its changes, and update themselves accordingly. 
  * Added a controller called Controller.
    * The controller is the brain of our project. It takes the user input from the view, translates it into commands, and sends them to the model. The model makes changes based on the request from the controller(Request from the user through the view), and calls update on all the views that are listening to it. 
  * Added a view called View
    * The view is the actual visual feel/look representation of our model. When the model is changed, the views represent those changes visually for the user to see and decide on his next action. The view does not change the model, but it changes its representation of the model. 

### The known issues 
* Issues that have been handled from Milestone 1:
  * We assume the attacking player always uses the maximum number of troops during the subsequent waves.(3 troops)
  * We assume the attacking player does not move to the country after the battle for the first milestone.
  * Currently, you can attack countries with 0 troops, as we have no implemented the Move function yet until the next milestones. That is when we will move into the country.
* Issues that are yet to be handled from Milestone 1:
  * We assume that defending players always use the maximum number of troops to defend their country every-time. (2 troops).
* New issues that came with Milestone 2:
  * Currently, after a successful attack, the attacker automatically takes over the defeated territory, and the surviving troops move over. The attacker does not have a choice in how many troops to move over, as the Move function has not been implemented yet, until Milestone 3.

### The roadmap ahead.
* Tasks that we previously set that were accomplished this milestone:
  * Transform the console version into an interactive GUI.
  * Modify the attacking strategy:
* Tasks that need to be accomplished by next milestone:
  * Add the Move algorithm to allow players to move troops from one country to another before or after a battle.
  * Add the ability to create custom maps.

### User Manual
* Run the JarFile, or run the View.View class.
![Run](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/1.png)

* Press NewGame at the bottom left.

* Choose the map you would like to play on.

* Select the number of players from the drop down menu
![New Game](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/2.png)

* The current players countries are highlighted in green, and the number of troops in each country is shown on the country.
![New Game](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/3.png)

* Your options are Attack, PassTurn, SaveGame, LoadGame, QuitGame
  * Attack: To attack a country:
![Attack](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/4.png)
    * Press the Attack button
    * Press on one of the countries you own to select the attacking country (The countries you may attack with are highlighted in green)
    * Press on one of the countries, that are directly neighbouring the country you would like to attack from, in order to initiate an attack on them. (You may not attack your countries highlighted in green)
    * Select the number of troops you would like to attack with.
    ![Troops](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/5.png)

    * You may not attack with a country that only has 1 troop(You always must have 1 troop left to defend!)
    * The result of the attack will be in the text field at the bottom.
    * If you won the attack, the troops that survived the attack, will now move over to the country you have just defeated, and that country will now be highlighted green, as it was added to your conquered countries.
  * Pass: Passes the turn from you to the next player. 
    * Press the PassTurn button at the bottom right.
  * SaveGame: Saves the current state of the game.
    * Press the SaveGame button at the bottom right.
  * LoadGame: Loads a previously saved state of the game. 
    * Press the LoadGame button at the bottom right.
  * Quit: Exit the game.
    * Press the QuitGame button at the bottom.

### JUnit Tests
* Clone the project from GitHub as a Maven project: https://github.com/hasanissa25/SYSC3110-Risk-Group10.git
* Run the ModelTesting.java class.
