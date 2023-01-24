package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Class for the enemy players
public class enemyClass 
{
	private Image imgEnemy, imgBattle;
	private ImageView ivEnemy, ivEnemyBattle;
	
	//Constructor initializes image based on string passed in
	enemyClass(String enemyName)
	{
		if(enemyName == "Brock")
		{
			 imgEnemy = new Image("file:images/brockarena.png");
			imgBattle = new Image("file:images/brockbattle.png");
		}
		else if(enemyName == "Gary")
		{
			imgEnemy = new Image("file:images/garyarena.png");
			imgBattle = new Image("file:images/garybattle.png");
		}
		else if(enemyName == "Wallace")
		{
			imgEnemy = new Image("file:images/wallacearena.png");
			imgBattle = new Image("file:images/trollbattle.png");
		}
		ivEnemy = new ImageView(imgEnemy);
		ivEnemyBattle = new ImageView(imgBattle);
	}
	
	//Returns image
	public ImageView getImage()
	{
		return ivEnemy;
	}
	
	//Returns the battle image of enemy
	public ImageView getBattleImage()
	{
		return ivEnemyBattle;
	}
	
	//Sets the location of the image
	public void setLocation(double frameWidth)
	{
		ivEnemy.setLayoutX(frameWidth/2 - 25);
		ivEnemy.setLayoutY(225);
	}
}
