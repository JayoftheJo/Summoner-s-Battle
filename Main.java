/* Program Name: Summoner's Battle: Fight for Victory
 * 
 * Name: Jaejo Antony Manjila, Hamza Rizwan
 * 
 * Date: Thursday, November 11th, 2021
 * 
 * Course Code: ICS 4U1
 * 
 * Brief Description: This program lets the user play a game where they will use three monsters of their own to battle 3 separate enemies. The user will 
 * prompted to pick a gender with their user name of their choice. The user then will be transported to the arena where they will choose 3 monsters by
 * colliding with them. After the user has picked their choices, the enemy will appear on the center of the screen where if the user collides with them, 
 * it initiates the battle. Each monster will have a different move set and will only be able to attack once before the enemy attacks back. Each attack is 
 * randomized damage. However, the special attack will deal more damage than a regular attack, but it can possibly deal no damage at all.
 * The player can also heal their monster and still keep their turn, however they can only heal 5 times in the entire game, so they must be careful with their healing.
 * If the enemy monster HP is below 0, player moves onto the next battle. This occurs again during the 2nd and 3rd battle. If the player
 * monster HP was below 0 at any point, the user will instantly lose the game and the game will end. If the user wins all 3 battles, an alert will be prompted
 * saying the player has won and the game will end. The player can also heal at any point of the game without losing turns for a total of 5 times.
 * 
 * Brief Details: 
 * Arrays: Arrays are used in this program to store various java FX components, that we require multiple of, such as buttons and alerts. Alerts are used like this 
 * so that multiple objects can have parameters set using a single for loop.
 * 
 * 2D Arrays: A 2D array is used to store the names of the attack of various monsters, this is handy as we can format the 2D array like a table, to make accessing 
 * certain pieces of data easier. We used the 2D array to easily get the name of a 
 * move for a certain monster, by using the row corresponding to the monster
 * 
 * Sorting and Searching: The bubble sort method was used to search and sort through the scores of previous and present scores that stays permanently in a file. The sorted 
 * scores were than uploaded through and alert which displays the leader board with the score and appropriate user name with it
 * 
 * Classes: We used multiple classes, We used a class for the player that contained methods for movement and setting the location, We also had a class for each monster, 
 * so that we could spawn each one on the scene, and so the player could choose which one they wanted. We also had a projectile class, which stored the type of projectile 
 * corresponding to the monster, as well as having its movement method. We had a class for the enemy trainer, and we had a class for the enemy monsters
 * 
 * Animation: We used animation in two main ways, the first was to allow the player to move around the screen, during the selection phase of the game. The other main way 
 * animation was used was with the movement of the projectiles from the player and the enemy. The animation timer was also used to check certain conditions during the game, 
 * such as for collisions or if health had become low, We also user Platform.runLater to display alerts during the game.
 * 
 * File: In the game, the file is used to store the player user name and score at the end of the game, whether they lose or win. On the title screen, the player can choose to 
 * view the names and scores of previous players, after the scores have been sorted.
 * 
 * ArrayList: Array lists are used to create and remove objects in the game. They are also used when parsing the file for scores, as the array list can expand to fit however 
 * many names and scores are in the file
 * 
 * Layouts: The pane layout was used through out the whole program. The program uses its methods to precisely position each object or image on the scene, sometimes by using the pane's size.
 * HBox and VBoxs were also used to arrange button placements on the scene which made it visually appealing
 */
package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application 
{
	//Initializing many variables
	private enemyClass enemy1, enemy2, enemy3;
	private playerClass player;
	private monsterClass jackFrost, pyroJack, jackSkeleton, nekoShogun, mokoi, muitoReal;
	private ArrayList<ImageView> playerMonsters = new ArrayList<ImageView>();
	private ArrayList<monsterProjectile> projectile;
	private ArrayList<enemyProjectile> enemyProjectile;
	private ArrayList<String> scores = new ArrayList<String>();
	private Button[] titleButtons, battleButtons;
	private TextInputDialog userNameInput;
	private Alert[] announcerAlert;
	private String[] announcerText = new String[] {"Heya there Challenger... You are new here aren't ya? Well my name is Laudius and WELCOME TO SUMMONER'S FIGHT!", 
			"Warriors here battle each other with their own Akuma, which are tamed demons...WAIT...YOU DON'T HAVE ONE???", 
			"Hehehe...That's completely fine because we offer a variety of choices for your choosing! You get to pick an Akuma based on what YOU like!", 
			"But be careful, as you win each battle, the next one gets even harder but the last one standing recieves... THE TROPHY OF ATROPHIUS", 
	"So get on with your registration and your choice of monster. Battle your way through VICTORY!"};
	private String userName, rulesTxt = "Leaderboard\n", lineOfText;
	private Label userNametxt, playerHealthDisplay, enemyHealthDisplay, playerDisplay, enemyDisplay;
	private boolean goRight, goLeft, goUp, goDown, inputNameValid, gender, attack = false, enemyAlive = true, enemyAlert, projectileCollide = false, exitAlert;
	private AnimationTimer objectTimer;
	private Image announcer;
	private ImageView ivAnnouncer, ivBattle, ivplayerBattle, enemyBattle1, enemyBattle2, enemyBattle3;
	private int monsterCount = 0, healCount = 5, battleCount = 0, pHealth1, pHealth2, pHealth3, eHealth1, eHealth2, eHealth3, damage, enemyDamage, points = 0;
	final static int RIGHT = 0, UP = 1, DOWN = 2, LEFT = 3;
	private Pane gameRoot, battleRoot;
	private String[] buttonNames = new String[] {"Start", "Rules", "Records", "Exit"};
	private Scene gameScene, battleScene;
	String[][] monsters = {{"Jack Frost", "Ice Attack", "Special Ice", "Heal: " + healCount},
			{"Pyro Jack", "Fire Attack", "Special Fire", "Heal: " + healCount},
			{"Jack Skeleton", "Slash Attack", "Special Slash", "Heal: " + healCount},
			{"Neko Shogun", "Strike Attack", "Special Strike","Heal: " + healCount},
			{"Mokoi", "Throw Attack", "Special Throw", "Heal:" + healCount},
			{"Muito", "Shock Attack", "Special Shock", "Heal:" + healCount}};
	private int currentMonster = 0, projectileCount = -1, enemyProjectileCount = -1;
	private Random rnd;
	private Font titleFont;
	private File textFile;
	private FileWriter out;
	private BufferedWriter writeFile;
	private boolean enemyProjectileMove =false;
	private boolean enemyProjectileAdded = false;
	private Stage primaryStage;

	//Start method
	public void start(Stage primaryStage) 
	{
		try 
		{
			//Bringing in the files needed, and buffer reader and writer
			textFile = new File("records.txt"); 
			
			//Checking if file exists
			if (textFile.exists())
			{ 

			} 
			else 
			{ 
				try
				{ 
					textFile.createNewFile(); 
				} 
				catch (IOException e)
				{ 
					System.out.println("File could not be created."); 
					System.out.println("IOException "+ e.getMessage()); 
				} 
			}

			//Bringing in background and logos
			Image imgArena = new Image("file:images/arenabackgroundresized.jpg");
			Image imgTitle = new Image("file:images/titlebackground.jpeg");
			Image imgLogo = new Image("file:images/titlelogo.png");
			Image imgBattle = new Image("file:images/battlebackground.jpg");
			ImageView ivTitle = new ImageView(imgTitle);
			ImageView ivLogo = new ImageView(imgLogo);
			ImageView ivArena = new ImageView(imgArena);
			ivBattle = new ImageView(imgBattle);

			//Bringing in random
			rnd = new Random();

			//Initializing the health of player/enemy
			pHealth1 = 500;
			pHealth2 = 750;
			pHealth3 = 1000;

			eHealth1 = 750;
			eHealth2 = 1000;
			eHealth3 = 1250;

			//ArrayList for projectiles
			projectile = new ArrayList<monsterProjectile>();
			enemyProjectile = new ArrayList<enemyProjectile>();

			//Object pointing to each monster
			jackFrost = new monsterClass("Jack Frost");
			pyroJack = new monsterClass("Pyro Jack");
			jackSkeleton = new monsterClass("Jack Skeleton");
			nekoShogun = new monsterClass("Neko Shogun");
			mokoi = new monsterClass("Mokoi");
			muitoReal = new monsterClass("Muito Real");

			//Labels for HP of player/enemy
			playerHealthDisplay = new Label();
			enemyHealthDisplay = new Label();
			enemyDisplay = new Label();
			playerDisplay = new Label();

			//creates pane and scene used for the title screen
			Pane root = new Pane();
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			inputNameValid = false;

			//Font used throughout the whole class
			titleFont = new Font("Algerian", 16);

			//creates pane and scene 
			gameRoot = new Pane();
			gameScene = new Scene(gameRoot,imgArena.getWidth(), imgArena.getHeight());
			gameRoot.getChildren().add(ivArena);

			battleRoot = new Pane();
			battleScene = new Scene(battleRoot);
			battleRoot.getChildren().add(ivBattle);

			//Creating HBox containing all the title buttons
			HBox titleButtonHBox = new HBox(50);
			root.getChildren().add(ivTitle);
			ivLogo.setLayoutX(-65);
			ivLogo.setLayoutY(-20);
			root.getChildren().add(ivLogo);
			titleButtons = new Button[4];
			for(int i = 0; i < titleButtons.length; i++)
			{
				titleButtons[i] = new Button();
				titleButtons[i].setText(buttonNames[i]);
				titleButtons[i].setFont(titleFont);
				titleButtons[i].setTextFill(Color.LIGHTGREEN);
				titleButtons[i].setPrefSize(100, 40);
				titleButtons[i].setStyle("-fx-background-color: black");
				titleButtonHBox.getChildren().add(titleButtons[i]);
			}

			//Going to different methods for each button action
			titleButtons[0].setOnAction(e -> btnStart(primaryStage, gameRoot));
			titleButtons[1].setOnAction(e -> btnRules());
			titleButtons[2].setOnAction(e -> {
				try {
					btnRecords();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			});
			titleButtons[3].setOnAction(e -> btnExit());

			//sets location and adds the hbox to scene
			titleButtonHBox.setLayoutX(20);
			titleButtonHBox.setLayoutY(300);
			root.getChildren().add(titleButtonHBox);

			//Initializing the buttons for the  moves
			battleButtons = new Button[3];
			battleButtons[0] = new Button();
			battleButtons[1] = new Button();
			battleButtons[2] = new Button();

			//calls a method once the button is pressed
			battleButtons[0].setOnAction(e -> normalAttack());
			battleButtons[1].setOnAction(e -> specialAttack());
			battleButtons[2].setOnAction(e -> heal());

			//When user presses a key, sets the boolean for that direction to true
			gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() 
			{
				public void handle(KeyEvent e) 
				{
					if (e.getEventType() == KeyEvent.KEY_PRESSED)
					{
						if (e.getCode() == KeyCode.RIGHT)
						{
							goRight = true;
						}
						else if(e.getCode() == KeyCode.LEFT)
						{
							goLeft = true;
						}
						else if(e.getCode() == KeyCode.UP)
						{
							goUp = true;
						}
						else if(e.getCode() == KeyCode.DOWN)
						{
							goDown = true;
						}
					}
					player.getImage();
				}
			});

			//When user releases a key, sets the boolean for that direction to false
			gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() 
			{
				public void handle (KeyEvent e)
				{
					if (e.getCode() == KeyCode.RIGHT)
					{
						goRight = false; 
					}
					else if (e.getCode() == KeyCode.LEFT)
					{
						goLeft = false;
					}
					else if (e.getCode() == KeyCode.UP)
					{
						goUp = false;
					}
					else if (e.getCode() == KeyCode.DOWN)
					{
						goDown = false;
					}
				}
			});

			//Animation timer for movement
			objectTimer = new AnimationTimer() 
			{
				public void handle(long val)
				{
					//Collision with the borders
					if(goRight == true)
					{
						if (!(player.getX() +50  > gameScene.getWidth()))
						{
							player.setDir(RIGHT);
							player.move();
						}

					}
					else if(goLeft == true)
					{
						if (!(player.getX() < 0))
						{
							player.setDir(LEFT);
							player.move();
						}
					}
					else if(goUp == true)
					{
						if (!(player.getY() < 0))
						{
							player.setDir(UP);
							player.move();
						}

					}
					else if(goDown == true)
					{
						if (!(player.getY()+ 50 > gameScene.getHeight()))
						{
							player.setDir(DOWN);
							player.move();
						}

					}

					//Check if it's a certain scene
					if(primaryStage.getScene() == gameScene)
					{
						//If player collides with any of the monsters, adds that image to an arraylist, and remove the monster from the scene
						if(player.getImage().getBoundsInParent().intersects(jackFrost.getMonsterImage().getBoundsInParent()))
						{
							monsterCount++;
							playerMonsters.add(jackFrost.getMonsterImage());
							jackFrost.setSpawnLocation(-100, -100);
							gameRoot.getChildren().remove(jackFrost.getMonsterImage());
						}
						else if((player.getImage().getBoundsInParent().intersects(pyroJack.getMonsterImage().getBoundsInParent())))
						{
							monsterCount++;
							playerMonsters.add(pyroJack.getMonsterImage());
							pyroJack.setSpawnLocation(-100, -100);
							gameRoot.getChildren().remove(pyroJack.getMonsterImage());
						}
						else if((player.getImage().getBoundsInParent().intersects(jackSkeleton.getMonsterImage().getBoundsInParent())))
						{
							monsterCount++;
							playerMonsters.add(jackSkeleton.getMonsterImage());
							jackSkeleton.setSpawnLocation(-100, -100);
							gameRoot.getChildren().remove(jackSkeleton.getMonsterImage());
						}
						else if((player.getImage().getBoundsInParent().intersects(nekoShogun.getMonsterImage().getBoundsInParent())))
						{
							monsterCount++;
							playerMonsters.add(nekoShogun.getMonsterImage());
							nekoShogun.setSpawnLocation(-100, -100);
							gameRoot.getChildren().remove(nekoShogun.getMonsterImage());
						}
						else if((player.getImage().getBoundsInParent().intersects(mokoi.getMonsterImage().getBoundsInParent())))
						{
							monsterCount++;
							playerMonsters.add(mokoi.getMonsterImage());
							mokoi.setSpawnLocation(-100, -100);
							gameRoot.getChildren().remove(mokoi.getMonsterImage());
						}
						else if((player.getImage().getBoundsInParent().intersects(muitoReal.getMonsterImage().getBoundsInParent())))
						{
							monsterCount++;
							playerMonsters.add(muitoReal.getMonsterImage());
							muitoReal.setSpawnLocation(-100, -100);
							gameRoot.getChildren().remove(muitoReal.getMonsterImage());
						}

						//If player picked up 3 monsters
						if(monsterCount == 4)
						{
							//removes all monsters from the scene
							gameRoot.getChildren().removeAll(jackFrost.getMonsterImage(), pyroJack.getMonsterImage(), 
									jackSkeleton.getMonsterImage(),nekoShogun.getMonsterImage(), 
									mokoi.getMonsterImage(), muitoReal.getMonsterImage());

							//moves monsters to prevent them from beign picked up
							mokoi.setSpawnLocation(-500, -500);
							muitoReal.setSpawnLocation(-500, -500);
							nekoShogun.setSpawnLocation(-500, -500);
							jackFrost.setSpawnLocation(-500, -500);
							pyroJack.setSpawnLocation(-500, -500);
							jackSkeleton.setSpawnLocation(-600, -500); 

							//sets each monster to have their battle image instead
							mokoi.getbattleImage();
							muitoReal.getbattleImage();
							nekoShogun.getbattleImage();
							jackFrost.getbattleImage();
							pyroJack.getbattleImage();
							jackSkeleton.getbattleImage();

							//initializes several enemy objects and adds the first to the scene
							enemy1 = new enemyClass("Brock");
							enemy2 = new enemyClass("Gary");
							enemy3 = new enemyClass("Wallace");
							enemy1.setLocation(gameScene.getWidth());
							gameRoot.getChildren().add(enemy1.getImage());

							if((player.getImage().getBoundsInParent().intersects(enemy1.getImage().getBoundsInParent())))
							{
								battleStart(primaryStage, battleRoot, battleScene, monsters);
							}
						}
					}
					//checks if an attack has been performed
					if(attack == true)
					{
						if(battleCount == 1)
						{
							if(projectileCollide == false)
							{
								//moves the projectile
								projectile.get(0).move();
								if((projectile.get(0).getProjectileImage().getBoundsInParent().intersects(enemyBattle1.getBoundsInParent())))
								{
									//When enemy health is below or equal to 0
									if(eHealth1 <= 0)
									{
										//prepares the game to change the battle to the next one
										enemyAlive = false;
										points += 1000;
										enemyHealthDisplay.setText("Enemy Health: 0");
										projectileCount--;
										battleRoot.getChildren().remove(projectile.get(0).getProjectileImage());
										projectile.remove(0);
										battleStart(primaryStage, battleRoot, battleScene, monsters);
										points += (pHealth1 * 10) - (250 * healCount);
									}
									else
									{
										//removes player projectile
										enemyProjectileMove = true;
										projectileCount--;
										battleRoot.getChildren().remove(projectile.get(0).getProjectileImage());
										projectile.remove(0);
										projectileCollide = true;
										enemyHealthDisplay.setText("Enemy Health: " + eHealth1);
									}
								}
							}
							if(enemyProjectileMove == true)
							{
								if(enemyAlive == true)
								{
									if(enemyProjectileAdded == false)
									{				
										//removes enemy projectile
										enemyProjectileCount++;
										enemyProjectile.add(enemyProjectileCount, new enemyProjectile(0));
										enemyProjectile.get(0).setLocation(enemyBattle1.getLayoutX() - 50, enemyBattle1.getLayoutY() + 10);
										battleRoot.getChildren().add(enemyProjectile.get(0).getProjectileImage());
										enemyProjectileAdded = true;
									}

									enemyProjectile.get(0).move();
									enemyProjectile.get(0).getProjectileImage();
									if((enemyProjectile.get(0).getProjectileImage().getBoundsInParent().intersects(playerMonsters.get(battleCount).getBoundsInParent())))
									{
										//removes enemy projectile
										enemyProjectileMove = false;	
										enemyProjectileAdded = false;
										battleRoot.getChildren().remove(enemyProjectile.get(0).getProjectileImage());
										enemyProjectile.remove(0);						
										enemyProjectileCount--;
										attack = false;

										//checks if enemy does a critical hit, otherwise less damage is dealt
										enemyDamage = rnd.nextInt(225);
										if(enemyDamage % 15 == 0)
										{
											enemyDamage = rnd.nextInt(20)+50;
										}
										else
										{
											enemyDamage = rnd.nextInt(20)+30;
										}
										pHealth1 = pHealth1 - enemyDamage;
										//reenables buttons to allow player to act
										for(int i = 0; i < battleButtons.length; i++)
										{
											battleButtons[i].setDisable(false);
										}
										playerHealthDisplay.setText("Health: " + pHealth1);
									}
								}
							}
						}
						else if(battleCount == 2)
						{
							if(projectileCollide == false)
							{	
								//moves the player projectile
								projectile.get(0).move();
								if((projectile.get(0).getProjectileImage().getBoundsInParent().intersects(enemyBattle2.getBoundsInParent())))
								{
									//When enemy health is below or equal to 0
									if(eHealth2 <= 0)
									{
										//gets the game ready to change scene
										enemyAlive = false;
										points += 1000;
										enemyHealthDisplay.setText("Enemy Health: 0");
										projectileCount--;
										battleRoot.getChildren().remove(projectile.get(0).getProjectileImage());
										projectile.remove(0);
										battleStart(primaryStage, battleRoot, battleScene, monsters);
										points += (pHealth2 * 10) - (250 * healCount);
									}
									else
									{
										//remove player projectile
										enemyProjectileMove = true;
										projectileCount--;
										battleRoot.getChildren().remove(projectile.get(0).getProjectileImage());
										projectile.remove(0);
										projectileCollide = true;
										enemyHealthDisplay.setText("Enemy Health: " + eHealth2);
									}
								}
							}
							if(enemyProjectileMove == true)
							{
								if(enemyAlive == true)
								{
									if(enemyProjectileAdded == false)
									{
										//adds enemy projectile
										enemyProjectileCount++;
										enemyProjectile.add(enemyProjectileCount, new enemyProjectile(1));
										enemyProjectile.get(0).setLocation(enemyBattle2.getLayoutX() - 50, enemyBattle2.getLayoutY() + 10);
										battleRoot.getChildren().add(enemyProjectile.get(0).getProjectileImage());
										enemyProjectileAdded = true;
									}
									enemyProjectile.get(0).move();				

									if((enemyProjectile.get(0).getProjectileImage().getBoundsInParent().intersects(playerMonsters.get(battleCount).getBoundsInParent())))
									{
										//removes the enemy projectile
										enemyProjectileMove = false;	
										enemyProjectileAdded = false;
										battleRoot.getChildren().remove(enemyProjectile.get(0).getProjectileImage());
										enemyProjectile.remove(0);						
										enemyProjectileCount--;
										attack = false;

										//checks if the enemy gets a critical hit, otherwise less damage is dealt to the player
										enemyDamage = rnd.nextInt(144);
										if(enemyDamage % 12 == 0)
										{
											enemyDamage = rnd.nextInt(50)+75;
										}
										else
										{
											enemyDamage = rnd.nextInt(40)+40;
										}
										pHealth2 = pHealth2 - enemyDamage;
										for(int i = 0; i < battleButtons.length; i++)
										{
											battleButtons[i].setDisable(false);
										}
										playerHealthDisplay.setText("Health: " + pHealth2);
									}
								}
							}
						}
						else if(battleCount == 3)
						{
							if(projectileCollide == false)
							{
								projectile.get(0).move();
								if((projectile.get(0).getProjectileImage().getBoundsInParent().intersects(enemyBattle3.getBoundsInParent())))
								{
									exitAlert = true;
									//When enemy health is below or equal to 0
									if(eHealth3 <= 0)
									{
									
										gameRoot.getChildren().removeAll(enemyBattle1, playerMonsters.get(battleCount), enemy1.getBattleImage(), playerHealthDisplay);
										points += 3000;
										enemyAlive = false;
										points += (pHealth3 * 10) - (100 * healCount);
										
										try 
										{
											writeScore();
										} 
										catch (IOException e) 
										{
											e.printStackTrace();
										}
										//prevents segment of code from running more than once
										eHealth3 = 1;
										
										//Run alert to end the game once enemy is defeated
										if(exitAlert == true)
										{
											Platform.runLater(new Runnable() 
											{
												public void run()
												{	
													Alert exit = new Alert(AlertType.NONE);
													exit.setGraphic(ivAnnouncer);
													exit.setContentText("WOAH CHALLENGER! CONGRATULATIONS ON OBTAIN THE TROPHY OF ATROPHIUS!\n"
															+ "I am so glad I got to meet you, " + userName + ". \n"
															+ "But do appreciate yourself for battling your through victory!\n"
															+ "Thank you for playing Summoner's Fight... \nyou journey will continue in...\n"
															+ "SUMONNER'S FIGHT: BATTLE FOR LIFE AND DEATH");
													exit.getButtonTypes().clear();
													exit.getButtonTypes().addAll(ButtonType.OK);
													exit.showAndWait();
													Platform.exit();
													System.exit(0);							
												}
											});
											exitAlert = false;
										}
									}
									else
									{
										//removes projectile after colliding with enemy
										enemyProjectileMove = true;
										projectileCount--;
										battleRoot.getChildren().remove(projectile.get(0).getProjectileImage());
										projectile.remove(0);
										projectileCollide = true;
										enemyHealthDisplay.setText("Enemy Health: " + eHealth3);
									}
								}
							}
							if(enemyProjectileMove == true)
							{
								//checks if enemy is alive
								if(enemyAlive == true)
								{
									//checks if the enemy projectile has been added you
									if(enemyProjectileAdded == false)
									{
										//creates and adds the enemy projcetile to the scene
										enemyProjectileCount++;
										enemyProjectile.add(enemyProjectileCount, new enemyProjectile(2));
										enemyProjectile.get(0).setLocation(enemyBattle3.getLayoutX() - 50, enemyBattle3.getLayoutY() + 10);
										battleRoot.getChildren().add(enemyProjectile.get(0).getProjectileImage());
										enemyProjectileAdded = true;
									}
									// moves the projectile until it collides with the player
									enemyProjectile.get(0).move();
									
									//checks collision with player and enemy
									if((enemyProjectile.get(0).getProjectileImage().getBoundsInParent().intersects(playerMonsters.get(battleCount).getBoundsInParent())))
									{
										//removes the enemy projectile from the scene
										enemyProjectileMove = false;	
										enemyProjectileAdded = false;
										battleRoot.getChildren().remove(enemyProjectile.get(0).getProjectileImage());
										enemyProjectile.remove(0);						
										enemyProjectileCount--;
										attack = false;

										//checks if the enemy performs a critial hit, otherwise they deal less damage
										enemyDamage = rnd.nextInt(100);
										if(enemyDamage % 10 == 0)
										{
											enemyDamage = rnd.nextInt(100)+125;
										}
										else
										{
											enemyDamage = rnd.nextInt(70)+60;
										}
										pHealth3 = pHealth3 - enemyDamage;

										//reeneables buttons to let player act
										for(int i = 0; i < battleButtons.length; i++)
										{
											battleButtons[i].setDisable(false);
										}
										playerHealthDisplay.setText("Health: " + pHealth3);
									}
								}
							}
						}
					}

					//If the player health is below 0 at any  point
					if(pHealth1 < 0 || pHealth2 < 0 || pHealth3 < 0)
					{
						try {
							writeScore();
						} catch (IOException e) {
							e.printStackTrace();
						}
						//Run alert to end the game
						Platform.runLater(new Runnable() 
						{
							public void run()
							{	
								//checks if the player has lost all of their health
								if(pHealth1 < 0 || pHealth2 < 0 || pHealth3 < 0)
								{
									//prevents the alert from running more than once
									pHealth1 = 1;
									pHealth2 = 1;
									pHealth3 = 1;
									//shows an alert and closes the program
									Alert exit = new Alert(AlertType.NONE);
									exit.setGraphic(ivAnnouncer);
									exit.setContentText("Oh ho! Looks like you lost one of your battles..."
											+ "\nWhy are you so weak? Anyways, nothing much to do about except move on..."
											+ "\nBUT...YOU COULD ALWAYS COME BACK AND TRY AGAIN. This is the moral of the tournament."
											+ "\nA warrior who never gives up is always better than a weakling who never tries"
											+ "\nUntil next time, WARRIOR " + userName);						          
									exit.getButtonTypes().clear();
									exit.getButtonTypes().addAll(ButtonType.OK);
									exit.showAndWait();
									Platform.exit();
									System.exit(0);
									


								}
							}});
					}
					if(battleCount == 1 && enemyAlert == true)
					{
						Platform.runLater(new Runnable() 
						{
							public void run()
							{
								//shows an alert from the enemy talking to the player
								Alert brockText = new Alert(AlertType.NONE);
								brockText.setGraphic(new ImageView(new Image("file:images/brockbattle.png")));
								brockText.setContentText("HEY TWERP! You think you are good enough for the big boy leagues?"
										+"\nWell let's see if you have the courage to BEAT ME, THE AMAZING BROCK!");
								brockText.getButtonTypes().clear();
								brockText.getButtonTypes().addAll(ButtonType.NEXT);
								brockText.showAndWait();                    

							}
						});
						enemyAlert = false;

					}
					else if(battleCount == 2 && enemyAlert == true)
					{
						Platform.runLater(new Runnable() 
						{
							public void run()
							{
								//shows an alert from the second enemy talking to the player
								Alert garyText = new Alert(AlertType.NONE);
								garyText.setGraphic(new ImageView(new Image("file:images/garybattle")));
								garyText.setContentText(userName + ", I will finally defeat you and your pathetic little Akumas!"
										+ "\nYou have always annoyed me so much but now I will be the one doing that to YOU!"
										+ "\nGet a little taste of my special skills and try not to pass out!");
								garyText.getButtonTypes().clear();
								garyText.getButtonTypes().addAll(ButtonType.NEXT);
								garyText.showAndWait();
								garyText.setOnHidden(e -> objectTimer.start());
							}
						});
						enemyAlert = false;
					}
					else if(battleCount == 3 && enemyAlert == true)
					{
						Platform.runLater(new Runnable() 
						{
							public void run()
							{
								//shows an alert from the final boss talking to the player
								Alert wallaceText = new Alert(AlertType.NONE);
								wallaceText.setGraphic(new ImageView(new Image("file:images/trollbattle")));
								wallaceText.setContentText("I see you have made it to the big leagues boy, but now is the true challenge..."
										+ "\nI have a special Akuma with me which utterly destroy you so beware..."
										+ "At the end of the day, let's have a good battle and have fun, " + userName);
								wallaceText.getButtonTypes().clear();
								wallaceText.getButtonTypes().addAll(ButtonType.NEXT);
								wallaceText.showAndWait();
								wallaceText.setOnHidden(e -> objectTimer.start());
							}
						});
						enemyAlert = false;
					}	                
				}

				private void writeScore() throws IOException 
				{
				//initializes the file writer and buffered writer
					out = new FileWriter(textFile,true);
					writeFile = new BufferedWriter(out);
					
					//writes the username and score to the file
					writeFile.write(userName);
					writeFile.newLine();
					writeFile.write(Integer.toString(points));
					writeFile.newLine();
					
					//closes buffered writer and file writer
					writeFile.close();
					out.close();
				
					
				}
			}; 
			objectTimer.start();

			//sets the scene to the title scene
			primaryStage.setScene(scene);
			primaryStage.setTitle("Summoner's Battle: Fight for Victory");
			primaryStage.show();

		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}

	//Method for when the heal button is clicked
	private void heal() 
	{
		//checks if the user still has uses of healing left over
		if(healCount <= 0)
		{
			Alert heal = new Alert(AlertType.INFORMATION);

			heal.setContentText("You have already used all 5 heals!");
			heal.showAndWait();
		}
		else
		{
			healCount--;
		}

		//Setting the player HP based on heal in each battle, prevents user from having HP exceed its starting value
		if(battleCount == 1 && healCount > 0)
		{
			pHealth1 += 200;
			if(pHealth1 > 500)
			{
				pHealth1 = 500;
			}
			playerHealthDisplay.setText("Health: " + pHealth1);
		}
		else if(battleCount == 2 && healCount > 0)
		{
			pHealth2 += 200;
			if(pHealth2 > 750)
			{
				pHealth2 = 750;
			}
			playerHealthDisplay.setText("Health: " + pHealth2);
		}
		else if(battleCount == 3 && healCount > 0)
		{
			pHealth3 += 200;
			if(pHealth3 > 1000)
			{
				pHealth3 = 1000;
			}
			playerHealthDisplay.setText("Health: " + pHealth3);
		}
		battleButtons[2].setText("Heal: " + healCount);
	}
	
	

	//When special attack button is clicked
	private void specialAttack() 
	{
		//Disabling the buttons
		for(int i = 0; i < battleButtons.length; i++)
		{
			battleButtons[i].setDisable(true);
		}
		attack = true;
		projectileCollide = false;

		//Adding the projectile to array list
		projectileCount++;
		projectile.add(projectileCount, new monsterProjectile(currentMonster));
		projectile.get(projectileCount).setLocation(playerMonsters.get(battleCount).getLayoutX(), playerMonsters.get(battleCount ).getLayoutY());
		battleRoot.getChildren().add(projectile.get(projectileCount).getProjectileImage());


		//first checks to see if the user will miss their hit, otherwise damage is calculated
		damage = rnd.nextInt(500);
		if(damage % 4 == 0)
		{
			eHealth1 = eHealth1;
			eHealth2 = eHealth2;
			eHealth3 = eHealth3;
		}
		else if(battleCount == 1)
		{
			eHealth1 = eHealth1 - rnd.nextInt(120)+50;
		}
		else if(battleCount == 2)
		{
			eHealth2 = eHealth2 - rnd.nextInt(150)+70;
		}
		else if(battleCount == 3)
		{
			eHealth3 = eHealth3 - rnd.nextInt(200)+80;
		}
	}

	//When normal attack button is clicked
	private void normalAttack() 
	{
		//disables buttons to prevent user from attacking multiple times
		for(int i = 0; i < battleButtons.length; i++)
		{
			battleButtons[i].setDisable(true);
		}
		
		attack = true;
		projectileCollide = false;
		
		//adds the projectile to the scene
		projectileCount++;
		projectile.add(projectileCount, new monsterProjectile(currentMonster));
		projectile.get(projectileCount).setLocation(playerMonsters.get(battleCount ).getLayoutX(), playerMonsters.get(battleCount).getLayoutY());
		battleRoot.getChildren().add(projectile.get(projectileCount).getProjectileImage());


		//checks if the user's attack is counted as a miss, otherwise damage is dealt according to the current battle
		damage = rnd.nextInt(200);
		if(damage % 25 == 0)
		{
			eHealth1 = eHealth1;
			eHealth2 = eHealth2;
			eHealth3 = eHealth3;
		}
		else if(battleCount == 1)
		{
			eHealth1 -= rnd.nextInt(100)+45;
		}
		else if(battleCount == 2)
		{
			eHealth2 -= rnd.nextInt(120)+60;
		}
		else if(battleCount == 3)
		{
			eHealth3 -= rnd.nextInt(180)+70;			
		}
	}

	//When start button is clicked
	public void btnStart(Stage primaryStage, Pane gameRoot)
	{
		announcer = new Image("file:images/announcer.png");
		ivAnnouncer = new ImageView(announcer);

		//An array of alert with an announcer
		announcerAlert = new Alert[5];
		for(int i = 0; i < 5; i++)
		{
			announcerAlert[i] = new Alert(AlertType.NONE);
			announcerAlert[i].setContentText(announcerText[i]);
			announcerAlert[i].setGraphic(ivAnnouncer);
			announcerAlert[i].getButtonTypes().clear();
			announcerAlert[i].getButtonTypes().addAll(ButtonType.NEXT);
			announcerAlert[i].showAndWait();
		}

		//User either picks male or female character
		Alert genderPick = new Alert(AlertType.CONFIRMATION);
		genderPick.setHeaderText(null);
		genderPick.setTitle("Customization");
		genderPick.setContentText("Pick your gender");
		genderPick.setGraphic(new ImageView("file:images/genderalert.png"));
		ButtonType male = new ButtonType("Male");
		ButtonType female = new ButtonType("Female");
		genderPick.getButtonTypes().clear();
		genderPick.getButtonTypes().addAll(male, female);
		Optional<ButtonType> result = genderPick.showAndWait();

		//Passing in boolean for each gender if picked
		if(result.get() == male)
		{
			gender = true;
			player = new playerClass(gender);
			Image imgplayerBattle = new Image("file:images/maleplayer.png");
			ivplayerBattle = new ImageView(imgplayerBattle);
		}
		else
		{
			gender = false;
			player = new playerClass(gender);
			Image imgplayerBattle = new Image("file:images/femaleplayer.png");
			ivplayerBattle = new ImageView(imgplayerBattle);
		}

		//Asking user for their name in the game
		userNameInput = new TextInputDialog();
		userNameInput.setGraphic(ivAnnouncer);
		userNameInput.setHeaderText("CHALLENGER, STATE YOUR NAME!");
		do{
			userNameInput.showAndWait();

			if(userNameInput.getEditor().getText().length() != 0 && userNameInput.getEditor().getText() != null)
			{
				userName = userNameInput.getEditor().getText();
				inputNameValid = true;
			}
			else
			{
				//Alert if user doesn't type in anything
				Alert userNameError = new Alert(AlertType.CONFIRMATION);
				userNameError.setHeaderText(null);
				userNameError.setTitle("Username Error");
				userNameError.setContentText("Please Enter your Name or Else...");	
				userNameError.getButtonTypes().clear();
				userNameError.getButtonTypes().add(ButtonType.OK);
				userNameError.showAndWait();
			}
		}while(inputNameValid == false);

		//changing scene to game scene, where the player can move around
		primaryStage.setScene(gameScene);

		//Adding the user name to the scene
		userNametxt = new Label();
		userNametxt.setText(userName);
		userNametxt.setLayoutX(20);
		userNametxt.setLayoutY(20);
		userNametxt.setTextFill(Color.BLACK);
		userNametxt.setFont(Font.font("Tahoma", FontWeight.BOLD, FontPosture.REGULAR, 36));
		gameRoot.getChildren().addAll(userNametxt, player.getImage());

		//Setting the  locations of each monster and the player
		jackFrost.setSpawnLocation(75, 75);
		pyroJack.setSpawnLocation((int)gameScene.getWidth() - 200, 75);
		jackSkeleton.setSpawnLocation(75, (int)gameScene.getHeight() - 200);
		nekoShogun.setSpawnLocation((int)gameScene.getWidth() - 200, (int)gameScene.getHeight() - 200);
		mokoi.setSpawnLocation((int)gameScene.getWidth()/2 - 25, 50);
		muitoReal.setSpawnLocation((int)gameScene.getWidth()/2 - 25, (int)gameScene.getHeight() - 125);

		player.setLocation(gameScene.getWidth()/2, gameScene.getHeight()/2);

		//adding all of the monsters to the scene
		gameRoot.getChildren().addAll(jackFrost.getMonsterImage(), pyroJack.getMonsterImage(), jackSkeleton.getMonsterImage(),nekoShogun.getMonsterImage(), mokoi.getMonsterImage(), muitoReal.getMonsterImage());
	}

	//Method for when rules button is clicked
	public void btnRules()
	{
		Alert rulesAlert = new Alert(AlertType.CONFIRMATION);
		rulesAlert.setHeaderText(null);
		rulesAlert.setTitle("How To Play");
		rulesAlert.setContentText("Heya there Challenger!\nDon't know how to play the game? Well that's completely fine becuase I WILL TEACH YOU!\n"
				+ "\nAfter you complete your registration, you will need to pick THREE Akuma of your own! \nEach Akuma will have a special"
				+ "moveset and each battle will be harder than the previous. \nYou will allowed to used 5 HEALS throughout the game anytime and IT WON'T COUNT TOWARDS YOUR TURN!\n"
				+ "After you win three battles in a row, YOU WILL BE ABLE TO "
				+ "ATTAIN THE TROPHY OF ATROPHIUS! \nBut...be careful in the finals...if you ever make it...");
		rulesAlert.getButtonTypes().clear();
		rulesAlert.getButtonTypes().add(ButtonType.OK);
		rulesAlert.showAndWait();
	}

	//Method for when records button is clicked
	public void btnRecords() throws IOException
	{
		//initializes the file reader and the buffered writer
		FileReader in  = new FileReader(textFile); 
		BufferedReader readFile = new BufferedReader(in); ;
		try 
		{
			//While loop to extract elements from the text file into an array
			while ((lineOfText = readFile.readLine())!= null) 
			{
				scores.add(lineOfText);
			}
			//calls a method to perform a sort on the scores list
			sort(scores);
			int k = 0;
			for(int i = 0; i < scores.size()/2; i++)
			{
				//adds sorted names and scores to a list
				rulesTxt += "\n" + (i + 1) + ". " + scores.get(k) + ": " + scores.get(k + 1) + "\n";
				k += 2;
			}
		}
		catch(IOException e)
		{
			System.out.println("Problem reading file.");
			System.out.println("IOException: " + e.getMessage());
		}
   
		//creates an alert displaying the names and scores of past players
		Alert pastplayersAlert = new Alert(AlertType.CONFIRMATION);
		pastplayersAlert.setHeaderText(null);
		pastplayersAlert.setTitle("Records");
		pastplayersAlert.setContentText(rulesTxt);	
		pastplayersAlert.showAndWait();
		in.close();
		readFile.close();
	}

	//Method for when exit button is clicked
	public void btnExit()
	{
		//asks the user to confirm whether they want to close the program or not
		Alert exitalert = new Alert(AlertType.CONFIRMATION);
		exitalert.setHeaderText(null);
		exitalert.setContentText("Are you sure you want to exit?");
		exitalert.setTitle("Exit");
		exitalert.getButtonTypes().clear();
		exitalert.getButtonTypes().addAll(ButtonType.YES, ButtonType.NO);
		Optional<ButtonType> result = exitalert.showAndWait();

		//If user hits yes, close program
		if(result.get() == ButtonType.YES)
		{
			Alert exitAlert = new Alert(AlertType.INFORMATION);
			exitAlert.setHeaderText(null);
			exitAlert.setContentText("Thank you for playing Summoner's Battle!\nWe hope to see you again!");
			exitAlert.setTitle("Goodbye Warrior");
			exitAlert.showAndWait();
			Platform.exit();
			System.exit(0);
		}	
	}

	//Method for when the battle starts
	public void battleStart(Stage primaryStage, Pane battleRoot, Scene battleScene, String[][] monsters)
	{
		enemyAlert = true;
		battleCount++;

		//If its the first battle
		if(battleCount == 1)
		{
			currentMonster();

			//Setting the scene
			primaryStage.setScene(battleScene);

			//Adding the player, enemyLabel, and the buttons needed for attacking/healing
			ivplayerBattle.setLayoutX(20);
			ivplayerBattle.setLayoutY(battleScene.getHeight()/2 - 150);

			//creates a vbox to display the buttons vertically 
			VBox battleButtonVBox = new VBox(50);
			battleButtonVBox.setLayoutX(25);
			battleButtonVBox.setLayoutY(400);
			battleButtonVBox.setSpacing(10);

			//sets the visuals of the battle buttons
			for(int i = 0; i < battleButtons.length; i++)
			{
				battleButtons[i].setPrefSize(200, 50);
				battleButtons[i].setText(monsters[currentMonster][i+1]);
				battleButtons[i].setFont(titleFont);
				battleButtons[i].setTextFill(Color.LIGHTGREEN);
				battleButtons[i].setStyle("-fx-background-color: black");
				battleButtonVBox.getChildren().add(battleButtons[i]);
			}

			//sets the visuals of the enemyname label
			enemyDisplay.setTextFill(Color.BLACK);
			enemyDisplay.setFont(titleFont);
			enemyDisplay.setText("Enemy Monster");
			enemyDisplay.setLayoutX(700);
			enemyDisplay.setLayoutY(100);

			//sets the visuals of the player monster name label
			playerDisplay.setText(monsters[currentMonster][0]);
			playerDisplay.setFont(titleFont);
			playerDisplay.setTextFill(Color.BLACK);
			playerDisplay.setLayoutX(225);
			playerDisplay.setLayoutY(100);

			//sets the visuals of the player health label
			playerHealthDisplay.setText("Health: " + pHealth1);
			playerHealthDisplay.setTextFill(Color.BLACK);
			playerHealthDisplay.setFont(titleFont);
			playerHealthDisplay.setLayoutX(225);
			playerHealthDisplay.setLayoutY(125);

			//sets the visuals of the enemy health label
			enemyHealthDisplay.setText("Enemy Health: " + eHealth1);
			enemyHealthDisplay.setTextFill(Color.BLACK);
			enemyHealthDisplay.setLayoutX(700);
			enemyHealthDisplay.setLayoutY(125);
			enemyHealthDisplay.setFont(titleFont);

			//sets the position of the players monster
			playerMonsters.get(battleCount).resize(400, 240);
			playerMonsters.get(battleCount).setLayoutX(200);
			playerMonsters.get(battleCount).setLayoutY(150);

			//sets the position of the enemy monster
			enemy1.getBattleImage().setLayoutX(battleScene.getWidth() - 150);
			enemy1.getBattleImage().setLayoutY(battleScene.getHeight()/2 - 150);

			//creates and sets the location of the enemy trainer
			enemyBattle1 = new ImageView(new Image("file:images/enemyBattle.png"));
			enemyBattle1.setLayoutX(enemy1.getBattleImage().getLayoutX() - 175);
			enemyBattle1.setLayoutY(enemy1.getBattleImage().getLayoutY() + 10);

			//adds various nodes to the scene
			battleRoot.getChildren().addAll(battleButtonVBox, ivplayerBattle, enemyDisplay, enemyBattle1, 
					playerMonsters.get(battleCount), enemy1.getBattleImage(), playerDisplay, playerHealthDisplay, enemyHealthDisplay);
		}
		else if(battleCount == 2)
		{
			//removes unneeded nodes from the previous battle
			battleRoot.getChildren().removeAll(enemyBattle1, playerMonsters.get(battleCount - 1), enemy1.getBattleImage());

			//Adding the player/enemy health, the enemy, and the two monsters
			projectileCollide = true;
			attack = false;
			enemyProjectileMove = false;
			enemyAlive = true;
			enemyProjectileAdded = false;

			//updates the health labels to have the correct value on them
			playerHealthDisplay.setText("Health: " + pHealth2);

			enemyHealthDisplay.setText("Enemy Health: " + eHealth2);

			//finds what the current monster is
			currentMonster();
			
			//sets the text of the buttons to match that monsters move
			playerDisplay.setText(monsters[currentMonster][0]);
			for(int i = 0; i < battleButtons.length; i++)
			{
				battleButtons[i].setText(monsters[currentMonster][i+1]);
			}

			//sets the location of the players monster
			playerMonsters.get(battleCount).resize(400, 240);
			playerMonsters.get(battleCount).setLayoutX(200);
			playerMonsters.get(battleCount).setLayoutY(150);

			//sets the location of the enemy monster and the enemy trainer
			enemy2.getBattleImage().setLayoutX(battleScene.getWidth() - 150);
			enemy2.getBattleImage().setLayoutY(battleScene.getHeight()/2 - 150);

			enemyBattle2 = new ImageView(new Image("file:images/enemyBattle2.png"));
			enemyBattle2.setLayoutX(enemy2.getBattleImage().getLayoutX() - 150);
			enemyBattle2.setLayoutY(enemy2.getBattleImage().getLayoutY() + 10);

			//adds various nodes to the scene
			battleRoot.getChildren().addAll(enemyBattle2, playerMonsters.get(battleCount), enemy2.getBattleImage());
		
			//re enables the buttons so the player can act
			for(int i = 0; i < battleButtons.length; i++)
			{
				battleButtons[i].setDisable(false);
			}
		}
		else if(battleCount == 3)
		{
			//removes unneeded nodes from the previous scene
			battleRoot.getChildren().removeAll(enemyBattle2, playerMonsters.get(battleCount - 1), enemy2.getBattleImage());

			//resets the value of various booleans
			attack = false;
			projectileCollide = true;
			enemyProjectileMove = false;
			enemyAlive = true;
			enemyProjectileAdded = false;
			
			//updates the label displaying the health of the player and enemy
			playerHealthDisplay.setText("Health: " + pHealth3);

			enemyHealthDisplay.setText("Enemy Health: " + eHealth3);

			//finds out what the current monster is
			currentMonster();
			
			//sets the text of the label and buttons to have the updated names for the monster and its moves
			playerDisplay.setText(monsters[currentMonster][0]);
			for(int i = 0; i < battleButtons.length; i++)
			{
				battleButtons[i].setText(monsters[currentMonster][i+1]);
			}

			//sets the layout of the monster, enemy monster, and the enemy trainer
			playerMonsters.get(battleCount).resize(400, 240);
			playerMonsters.get(battleCount).setLayoutX(200);
			playerMonsters.get(battleCount).setLayoutY(150);

			enemy3.getBattleImage().setLayoutX(battleScene.getWidth() - 150);
			enemy3.getBattleImage().setLayoutY(battleScene.getHeight()/2 - 150);

			enemyBattle3 = new ImageView(new Image("file:images/enemyBattle3.png"));
			enemyBattle3.setLayoutX(enemy3.getBattleImage().getLayoutX() - 100);
			enemyBattle3.setLayoutY(enemy3.getBattleImage().getLayoutY() + 40);

			//adds various nodes to the scene
			battleRoot.getChildren().addAll(enemyBattle3, playerMonsters.get(battleCount), enemy3.getBattleImage());
			
			//re enables buttons so that the player can act again
			for(int i = 0; i < battleButtons.length; i++)
			{
				battleButtons[i].setDisable(false);
			}
		}
	}

	//Method for checking which monster the user is using
	public void currentMonster()
	{
		//checks which monster the player will use in the current battle
		if(playerMonsters.get(battleCount) == jackFrost.getMonsterImage())
		{
			currentMonster = 0;
		}
		else if(playerMonsters.get(battleCount) == pyroJack.getMonsterImage())
		{
			currentMonster = 1;
		}
		else if(playerMonsters.get(battleCount) == jackSkeleton.getMonsterImage())
		{
			currentMonster = 2;
		}
		else if(playerMonsters.get(battleCount) == nekoShogun.getMonsterImage())
		{
			currentMonster = 3;
		}
		else if(playerMonsters.get(battleCount) == mokoi.getMonsterImage())
		{
			currentMonster = 4;
		}
		else if(playerMonsters.get(battleCount) == muitoReal.getMonsterImage())
		{
			currentMonster = 5;
		}

	}

	//Method for using bubble sort to sort the leader board when records button is clicked
	public static ArrayList<String> sort(ArrayList<String> scores)
	{
		boolean done = false;
		for(int end = scores.size() - 1; end > 0 && !done; end--)
		{
			done = true;
			for(int i = 0; i < end; i++)
			{
				//checks whether i is odd or even
				if(i % 2 != 0)
				{
					if(Integer.parseInt(scores.get(i)) < Integer.parseInt(scores.get(i + 2)))
					{
						//Switching the score values if one is greater than the other
						done = false; 
						String temp = scores.get(i);
						scores.set(i, scores.get(i + 2));
						scores.set(i + 2, temp);

						//Switching the names based on the score values
						String temp2 = scores.get(i - 1);
						scores.set(i - 1, scores.get(i + 1));
						scores.set(i + 1, temp2);
					}
				}
			}
		}
		return scores;
	}

	public static void main(String[] args) 
	{
		launch(args);
	}
}
