package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Class for the enemy projectile
public class enemyProjectile  
{
	//Initializing variables
	private Image imgEnemyProjectile;
	private ImageView ivEnemyProjectile;
	private double xPos, yPos;
	
	//Constructor
	enemyProjectile(int num) 
	{
		if(num == 0)
		{
			imgEnemyProjectile = new Image("file:images/iceprojectile.png");
			ivEnemyProjectile = new ImageView(imgEnemyProjectile);
		}
		else if(num == 1)
		{
			imgEnemyProjectile = new Image("file:images/fireprojectile.png");
			ivEnemyProjectile = new ImageView(imgEnemyProjectile);
		}
		else if(num == 2)
		{
			imgEnemyProjectile = new Image("file:images/clarinet.png");
			ivEnemyProjectile = new ImageView(imgEnemyProjectile);
		}
	}
	
	//Method for projectile to move
	public void move()
	{
		xPos -= 10;
		ivEnemyProjectile.setLayoutX(xPos);
		ivEnemyProjectile.setLayoutY(yPos);
	}
	
	//Sets the location of image
	public void setLocation(double x, double y)
	{
		xPos = x;
		yPos = y+50;
		getProjectileImage().setLayoutX(xPos);
		getProjectileImage().setLayoutY(yPos);
		System.out.println(xPos);
		System.out.println(yPos);
	}
	
	//Returns the image
	public ImageView getProjectileImage()
	{
		return ivEnemyProjectile;
	}
}
