package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

//Class for the player in game
public class playerClass 
{
	//Initializing variables
	private Image imgEast, imgWest, imgNorth, imgSouth;
	private ImageView ivPlayer;
	final static int RIGHT = 0, UP = 1, DOWN = 2, LEFT = 3;
	private int dir;
	private double xPos, yPos, width, height;	
	private boolean gender;

	//Constructor made based on gender passed in
	playerClass(boolean gender)
	{
		if(gender == true)
		{
			imgEast = new Image("file:images/playerRIGHT.png");
			imgWest = new Image("file:images/playerLEFT.png");
			imgNorth = new Image("file:images/playerUP.png");
			imgSouth = new Image("file:images/playerDOWN.png");
		}
		else
		{
			imgEast = new Image("file:images/femaleplayerRIGHT.png");
			imgWest = new Image("file:images/femaleplayerLEFT.png");
			imgNorth = new Image("file:images/femaleplayerUP.png");
			imgSouth = new Image("file:images/femaleplayerDOWN.png");
		}

		ivPlayer = new ImageView(imgEast);

	}

	//Returns the height of the image
	public double getHeight()
	{
		return height;
	}
	
	//Returns the width of the image
	public double getWidth()
	{
		return width;
	}		
	//Returns the direction of the image
	public int getDirection()
	{
		return dir;
	}

	public void setDir(int dir)
	{
		this.dir = dir;	
	}

	//Returns the image based on direction
	public ImageView getImage()
	{
		if(dir == RIGHT)
		{
			ivPlayer.setImage(imgEast);
		}
		else if(dir == LEFT)
		{
			ivPlayer.setImage(imgWest);
		}
		else if(dir == UP)
		{
			ivPlayer.setImage(imgNorth);
		}
		else if(dir == DOWN)
		{
			ivPlayer.setImage(imgSouth);
		}

		return ivPlayer;
	}

	//Sets the x position of the image
	public void setX(double x)
	{

		ivPlayer.setLayoutX(x);
	}

	//Sets the y position of the image
	public void setY(double y)
	{

		ivPlayer.setLayoutY(y);
	}

	//Returns the xPos of the image
	public int getX()
	{
		return (int) xPos;
	}

	//Method to move the image
	public void move()
	{

		if(dir == LEFT)
		{
			xPos -= 10;
			ivPlayer.setImage(imgWest);
		}
		else if(dir == RIGHT)
		{
			xPos += 10;
			ivPlayer.setImage(imgEast);
		}
		else if(dir == UP)
		{
			yPos -= 10;
			ivPlayer.setImage(imgNorth);
		}
		else if(dir == DOWN)
		{
			yPos += 10;
			ivPlayer.setImage(imgSouth);
		}
		ivPlayer.setLayoutX(xPos);
		ivPlayer.setLayoutY(yPos);
	}

	//Returns the yPos of the image
	public int getY() 
	{
		return (int) ivPlayer.getLayoutY();
	}

	//Sets the initial location of the image
	public void setLocation(double frameWidth, double frameHeight) {
		xPos = frameWidth;
		yPos = frameHeight;
		ivPlayer.setLayoutX(xPos);
		ivPlayer.setLayoutY(yPos);
		
	}
}