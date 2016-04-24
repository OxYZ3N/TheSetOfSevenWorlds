package com.oxyzen.thesetofsevenworlds;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.oxyzen.framework.Pixmap;

public class Cloud {

	enum Direction {
		LEFT,
		RIGHT
	}

	enum Type {
		cloud,
		clouds
	}

	public static final int ordonnee_1 = 20;
	public static final int ordonnee_2 = 40;
	public List<CloudX> part = new ArrayList<CloudX>();
	
	private Direction directionCloud;
	public int x;

	public Cloud()
	{
		directionCloud = Direction.LEFT;
		part.add(new CloudX(1));
	}

	public void advance() 
	{
		CloudX cloud = part.get(0);

		if(directionCloud == Direction.LEFT)
			cloud.x -= 1;
		if(directionCloud == Direction.RIGHT)
			cloud.x += 1;
	}

	public Pixmap Pixmap(Pixmap cloud, Type type)
	{
		if (type == Type.cloud)
			cloud = Assets.cloud;
		if (type == Type.clouds)
			cloud = Assets.clouds;
		return cloud;
	}

	public static int genY() {
		Random random = new Random();
		int genY = random.nextInt(200-40)+40; //random.nextInt(max - min) + min;
		return genY;
	}
}
