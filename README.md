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
The project is divided into 4 iterations, each ending with a milestone corresponding to
deliverables that will be graded. You will be able to use the TA feedback from iteration i
for iteration i+1. 

## Project Milestone 1: (Friday Oct 23rd)

A text-based playable version of the game, i.e., players should be able to
play the game via the console using the keyboard. There should be a command to print
the state of the Map (i.e., which player is in which country and with how many armies), a
command to decide which country to attack from which country, and a command to pass
your turn to the next player. Events such as the outcome of an attack, whose turn it is to
play, the elimination of a player, etc. should be printed to the console when applicable.
Also required, the UML modeling of the problem domain (class diagrams with complete
variable and method signatures, and sequence diagrams for important scenarios), detailed
description of the choice of data structures and relevant operations: you are providing an
initial design and implementation for the Model part of the MVC. Do not worry about
any GUI yet.
Deliverables: readme file + code (source + executable in a jar file) + UML diagrams + documentation, all in one zip file.

## Documentation 

### UML Class Diagram

### Sequence Diagram
![Initialize Sequence Diagram ](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/InitializeSequence.jpeg)

![Attack Sequence Diagram ](https://github.com/hasanissa25/SYSC3110-Risk-Group10/blob/master/AttackSequence.jpeg)

### Detailed Description of Design
- Game is the main loop of the program.
* The Controller
* Creates and initialises all the other classes.
* Takes in the number of players, and handles the random allocation of Player(and their troops) to Country.
* Creates the map of all Countries and their neighbours.
* Creates the parser and starts the game.
* Processes and executes the commands that the parser returns.
* Includes the Attack Algorithm to decide which country wins in battle.

- Country 
* Part of the model.
* Represents the countries in our Risk map.
* Knows the occupying player.
* Knows the number of currently occupying troops.

- Map
* Part of the model.
* This is the Map representation of our Risk world. 
* Represented as Graph<Country, DefaultEdge>. This allows us to have an easier time manipulating countries, and their neighbouring countries.
** Every Country is a Vertex, which has neighbouring countries connected to its Edges.

- Player
* Part of the model.  
* Represents the players of the game.
* The player knows the countries they currently own. 
* The player knows the possible targets they can choose to attack, based on their countries neighbours.
* The player knows how many troops they have to deploy for next milestones when players start receiving troops throughout the game.

- Command
* A string of 4 words, which is converted into a command for internal use.
* The first word is a Command Word that dictates what type of action will be taken due to this command.

- CommandWord
* Commands that could be passed to the game to dectate a specific action.
* Command Words include Attack,Map,Quit, and Pass.
* Attac: Initiate an attack from one country to its neighbour, using a specified number of troops.
* Map: Returns a Risk world representation, showing all countries, the player that owns them, and the number of troops in that country.
* Quit: Exits the game
* Pass: Pass the turn from the current player to the next player.

- Parser
* This class handles the gathering of user input, and turns it into a Command.
* Ensures the user is following proper syntax when entering commands.
 
- UtilArray
* A Java program to remove an element from a specific index from an array.
* In our situation, it handles removing a country from an array.

## The Readme

### The rest of the deliverbles
* A UML Class diagram showing the full program and the associations between classes.
* A UML Sequence Diagram showing the two most important scenarios: Attack Command, and Initialize game. 
* The Source code, and a runnable Jar file, including the JavaDoc
* Documentation
* Readme file.

### Changes that were made since the previous deliverable 
- N/A

### The known issues 
- We have made some assumptions for the first milestone.
* We assume that defending players always use the maximum number of troops to defend their country everytime. (2 troops).
* We assume the attacking player always uses the maximum number of troops during the subsequent waves.(3 troops)
* We assume the attacking player does not move to the country after the battle for the first milestone.

### The roadmap ahead.

- Transform the console version into an interactive GUI.
- Modify the attacking strategy:
* Currently, the attacking algorithm automatically takes care of the battle after the player initiates the attack, until they or the defending player run out of troops. We would like to have the attacking player get to choose whether or not they would like to continue their attack after the first wave of 3 or less troops have finished the battle.
- Modify the defending strategy:
* Currently, the defending country automatically uses the maximum number of troops to defend. (2 troops), until the battle is over, with no player input from the defending country owner. We would prefer if the defending player gets to respond to an attack, and choose the number of troops they would like to defend with.
- Add the Move algorithm to allow players to move troops from one country to another before or after a battle.
- Add the ability to create custome maps.

- We started preparing for the next milestones through using easily maintainable code.
* The player knows how many troops they have to deploy for next milestones when players start receiving troops throughout the game.
* Seperation of concers; the controller and models are in seperate classes, making it easier to add in a view class, in order to implement the GUI.
* The attacking aglorithm can choose how many troops are attacking, and how many troops are defending. This makes it easy to set up the attack in waves, in order to allow the user to choose to back out of a battle, instead of having his army eliminated.
* Since we have access to the defending country, we can grab its associated player, and prompt them to enter how many troops they would like to defend with.

### User Manual
1. Run the JarFile
2. When promted, enter the integer number of players that will be playing the game; 2,3,4,5 or 6 players. : 6
3. Read the output to see the countries you own, and the neighbouring countries you can attack.
4. Your options are Attack, Map, Pass, Quit
  * Attack: To attack a country, enter : Attack AttackingCountry TheTargetCountry NumberOfTroops. i.e; Attack Peru Venezuela 2. Ensure that you have 1 remaining troop in the country to defend. Ensure you own the country you are attacking from. Ensure the country you want to target is neighbouring the country you are attacking from.
  * Map: Returns the current list of countries in the game, the players that own them, and the number of troops they have in that country.
  * Pass: Passes the turn from you to the next player.
  * Quit: Exit the game.
5. If you choose to attack, the game will walk you through your dice rolls, the outcome of each wave of battle, and the result of the battle.
  
