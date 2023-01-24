package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Class for creating the monsters
public class monsterClass 
{
	private Image imgMonster, imgBattle;
	private ImageView ivMonster;
	private String monster;
	
	//Constructor initialized images based on string passed in
	monsterClass(String monsterName)
	{
		if(monsterName.equals("Jack Frost"))
		{
			 imgMonster = new Image("file:images/jackfrostpixel.png");	
		}
		else if(monsterName.equals("Pyro Jack"))
		{
			imgMonster = new Image("file:images/pyrojackpixel.png");
		}
		else if(monsterName.equals("Jack Skeleton"))
		{
			imgMonster = new Image("file:images/jackskeletonpixel.png");
		}
		else if(monsterName.equals("Neko Shogun"))
		{
			imgMonster = new Image("file:images/nekoshogunpixel.png");
		}
		else if(monsterName.equals("Mokoi"))
		{
			 imgMonster = new Image("file:images/mokoipixel.png");
		}
		else if(monsterName.equals("Muito Real"))
		{
			imgMonster = new Image("file:images/mothmanpixel.png");
		}
		
		monster = monsterName;
		
		ivMonster = new ImageView(imgMonster);
	}
	
	//Sets the location of image
	public void setSpawnLocation(int xSpawn, int ySpawn)
	{
		ivMonster.setLayoutX(xSpawn);
		ivMonster.setLayoutY(ySpawn);
	}
	
	//Returns the image
	public ImageView getMonsterImage() 
	{
		return ivMonster;
	}
	
	//Returns the image based on the name of monster
	public ImageView getbattleImage()
	{
		if(monster.equals("Jack Frost"))
		{
			 imgBattle = new Image("file:images/jackfrostpixelbattle.png");	
		}
		else if(monster.equals("Pyro Jack"))
		{
			imgBattle = new Image("file:images/pyrojackpixelbattle.png");
		}
		else if(monster.equals("Jack Skeleton"))
		{
			imgBattle = new Image("file:images/jackskeletonpixelbattle.png");
		}
		else if(monster.equals("Neko Shogun"))
		{
			imgBattle = new Image("file:images/nekoshogunpixelbattle.png");
		}
		else if(monster.equals("Mokoi"))
		{
			 imgBattle = new Image("file:images/mokoipixelbattle.png");
		}
		else if(monster.equals("Muito Real"))
		{
			imgBattle = new Image("file:images/mothmanpixelbattle.png");
		}
		ivMonster.setImage(imgBattle);
		return ivMonster;

	}
}
