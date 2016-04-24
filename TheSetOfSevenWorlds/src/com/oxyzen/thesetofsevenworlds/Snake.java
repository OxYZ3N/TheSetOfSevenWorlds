package com.oxyzen.thesetofsevenworlds;

import java.util.ArrayList;
import java.util.List;

public class Snake {
	public static final int UP = 0;
	public static final int LEFT = 1;
	public static final int DOWN = 2;
	public static final int RIGHT = 3;

	public List<SnakePart> parts = new ArrayList<SnakePart>();
	public int direction;
	public int directionQueueMoinsUn;

	public Snake() {        
		direction = UP;
		directionQueueMoinsUn = UP;
		parts.add(new SnakePart(5, 6));
		parts.add(new SnakePart(5, 7));
		parts.add(new SnakePart(5, 8));
		parts.add(new SnakePart(5, 9));
	}

	public void snakeQueue() {
		SnakePart end = parts.get(parts.size()-1);
		parts.add(new SnakePart(end.x, end.y+1));
	}

	public void turnLeft() {
		direction += 1;
		if(direction > RIGHT) {
			direction = UP;
		}
	}

	public void turnRight() {
		direction -= 1;
		if(direction < UP) {
			direction = RIGHT;
		}
	}

	public void turnUp() {
		direction += 1;
		if(direction < RIGHT || direction > LEFT) {
			direction = UP;
		}
	}

	public void turnDown() {
		direction -= 1;
		if(direction < RIGHT || direction > LEFT) {
			direction = DOWN;
		}
	}

	public void eat() {
		SnakePart end = parts.get(parts.size()-1); 
		parts.add(new SnakePart(end.x, end.y));
	}

	public void advance() {
		SnakePart head = parts.get(0);               

		int len = parts.size() - 1;
		//SnakePart queue = parts.get(len);
		//SnakePart end = parts.get(parts.size()-1);
		SnakePart queue = parts.get(parts.size()-1);
		int queueUp = queue.y-1;
		int queueLeft = queue.x-1;
		int queueDown = queue.y+1;
		int queueRight = queue.x+1;
		
		for(int i = len; i > 0; i--) {
			SnakePart before = parts.get(i-1);
			SnakePart part = parts.get(i);
			
			part.x = before.x;
			part.y = before.y;
			
			if (queue.y == (queueUp) )
			directionQueueMoinsUn = UP;
			if (queue.x == (queueLeft) )
			directionQueueMoinsUn = LEFT;
			if (queue.y == (queueDown) )
			directionQueueMoinsUn = DOWN;
			if (queue.x == (queueRight) )
			directionQueueMoinsUn = RIGHT;
		}

		if(direction == UP)
			head.y -= 1;
		if(direction == LEFT)
			head.x -= 1;
		if(direction == DOWN)
			head.y += 1;
		if(direction == RIGHT)
			head.x += 1;

		 if(head.x < 0)
			 head.x = 9;
		 if(head.x > 9)
			 head.x = 0;
		 if(head.y < 0)
			 head.y = 12;
		 if(head.y > 12)
			 head.y = 0;
	}

	public boolean checkBitten() {
		int len = parts.size();
		SnakePart head = parts.get(0);
		for(int i = 1; i < len; i++) {
			SnakePart part = parts.get(i);
			if(part.x == head.x && part.y == head.y)
				return true;
		}        
		return false;
	}      
}

