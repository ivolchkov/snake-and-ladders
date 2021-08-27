# Snake and ladders
Rest service which stands for implementation of "Moving your token" across the board using dice rolls. Players will need the ability to roll a dice, move their token the number of squares indicated by the dice roll and should win if they land on the final square.  
There are next requirements for moving the token:  
1. Token Can Move Across the Board.  
As a player  
I want to be able to move my token  
So that I can get closer to the goal.
2. Moves are determined By Dice Rolls  
As a player  
I want to move my token based on the roll of a die  
So that there is an element of chance in the game.  
3. Player Can Win the Game  
As a player  
I want to be able to win the game  
So that I can gloat to everyone around.
##Prerequisites
- Maven;
- MySQL;
- Lombok;
- Log4J;
- JUnit;
- Mockito;
- Spring Boot;
- Spring Data;
- Spring MVC.
##How to run the project
1. Make sure you have already installed JDK 11, Maven, MySQL server;
2. Clone the project to your local folder;
3. Create a schema called ```snake_and_ladders``` in your database;
4. Configure your database credentials respectively:  
```spring.datasource.username=$USER_NAME```  
```spring.datasource.password=$PASSWORD```
5. Change ```spring.jpa.hibernate.ddl-auto``` property to ```create``` for the first start and then change to ```update```;
6. Run the project using ```mvn clean sping-boot:run``` command.

