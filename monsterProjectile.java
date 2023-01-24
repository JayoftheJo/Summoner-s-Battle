package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Class for the player projectile
public class monsterProjectile 
{
	Image imgProjectile;
	ImageView ivProjectile;
	int height, width;
	double xPos, yPos;
	boolean enemyCheck;
	
	//Constructor to check which monnster based on image
	monsterProjectile(int num)
	{
		if(num == 0)
		{
			imgProjectile = new Image("file:images/iceprojectile.png");
		}
		else if(num == 1)
		{
			imgProjectile = new Image("file:images/fireprojectile.png");
		}
		else if(num == 2)
		{
			imgProjectile = new Image("file:images/slashprojectile.png");
		}
		else if(num == 3)
		{
			imgProjectile = new Image("file:images/nekoprojectile.png");
		}
		else if(num == 4)
		{
			imgProjectile = new Image("file:images/boomerprojectile.png");
		}
		else if(num == 5)
		{
			imgProjectile = new Image("file:images/elecprojectile.png");
		}
		else if(num == 6)
		{
			imgProjectile = new Image("file:images/clarinet.png");
		}
		ivProjectile = new ImageView(imgProjectile);
		height = (int)imgProjectile.getHeight();
		width = (int)imgProjectile.getWidth();
		
	}
	
	//Setting the location of image
	public void setLocation(double x, double y)
	{
		xPos = x + 150;
		yPos = y + 40;
		ivProjectile.setLayoutX(xPos);
		ivProjectile.setLayoutY(yPos);
	}
	
	//Returns the image
	public ImageView getProjectileImage()
	{
		return ivProjectile;
	}
	
	//Returns height of image
	public double getHeight()
	{
		return height;
	}
		
	//Returns width of image
	public double getWidth()
	{
		return  width;
	}
		
	//Returns xPos of image
	public double getX()
	{
		return xPos;
	}
		
	//Returns yPos of image
	public double getY()
	{
		return yPos;
	}
	
	//moves the projectile
	public void move()
	{
		xPos += 10;
		ivProjectile.setLayoutX(xPos);
		ivProjectile.setLayoutY(yPos);
	}
}
