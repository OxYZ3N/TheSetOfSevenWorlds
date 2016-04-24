package com.oxyzen.thesetofsevenworlds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.oxyzen.framework.Pixmap;

public class Ovni {

	enum Direction {
		LEFT,
		RIGHT,
		UP,
		DOWN
	}

	enum Ship {
		ovni
	}

	public List<OvniXY> part = new ArrayList<OvniXY>();

	private Direction directionOvni;
	public int x; //, y;
	public int[] y = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115};
	public Ovni()
	{
		directionOvni = Direction.RIGHT;
		part.add(new OvniXY(1, y));
	}

	public void advance()
	{
		OvniXY ovni = part.get(0);

		if(directionOvni == Direction.LEFT)
			ovni.x -= 1;
		if(directionOvni == Direction.RIGHT)
			ovni.x += 1;
		//if(directionOvni == Direction.UP)
		//	ovni.y -= 1;
		//if(directionOvni == Direction.DOWN)
		//	ovni.y += 1;
	}

	public Pixmap Pixmap(Pixmap ovni, Ship ship)
	{
		if (ship == Ship.ovni)
			ovni = Assets.ovni;
		return ovni;
	}

	public static int genY() {
		Random random = new Random();
		int genY = random.nextInt(200-40)+40;
		return genY;
	}

	public static Object[] lapsX()
	{
		int[] x = {20, 40, 60, 80};
		int[] y = {30, 50, 70, 90};
		List<Integer> liste = new ArrayList<Integer>();
		for(int index = 0; index < x.length  ; index++) 
		{
			liste.add(x[index]);
			System.out.println(liste.get(index) + " ");
		}

		return liste.toArray();
	}
}
