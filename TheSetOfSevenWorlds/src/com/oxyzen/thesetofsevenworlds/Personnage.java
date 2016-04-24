package com.oxyzen.thesetofsevenworlds;

import java.util.List;

import com.oxyzen.framework.Game;
import com.oxyzen.framework.Graphics;
import com.oxyzen.framework.Pixmap;
import com.oxyzen.framework.Screen;
import com.oxyzen.framework.Input.TouchEvent;

public class Personnage extends Screen
{
	enum PersoState {
		Phil,
		Blake
	}

	enum Difficulte {
		rien,
		facile,
		normal,
		difficile
	}
	
	World world = new World();
	boolean highLeverage = false;

	public static Difficulte statePlay = Difficulte.rien;
	public static String scoreEatChallenge; // = "..";
	
	public Personnage(Game game) 
	{
		super(game);
	}

	@Override
	public void update(float deltaTime) 
	{
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP) {
				if(inBounds(event, 90, 236, 64, 64)) 
				{
					GameScreen.persostate = PersoState.Blake;
					game.setScreen(new GameScreen(game));
					return;
				}
				else if (inBounds(event, 164, 236, 64, 64))
				{
					GameScreen.persostate = PersoState.Phil;
					game.setScreen(new GameScreen(game));
					return;
				}
				else if (inBounds(event, 110, 120, 97, 17))
				{
					statePlay = Difficulte.facile;
					scoreEatChallenge = "30";
					GameScreen.scoreEatChallenge = scoreEatChallenge;
					GameScreen.nbScoreEatChallenge = 30;
					return;
				}
				else if (inBounds(event, 110, 160, 102, 17))
				{
					statePlay = Difficulte.normal;
					scoreEatChallenge = "60";
					GameScreen.scoreEatChallenge = scoreEatChallenge;
					GameScreen.nbScoreEatChallenge = 60;
					return;
				}
				else if (inBounds(event, 90, 200, 142, 17))
				{
					if (highLeverage) statePlay = Difficulte.difficile;
					return;
				}
			}
		}
	}

	public static Pixmap statePlayGame()
	{
		Pixmap background = null;
		
		if (statePlay == Difficulte.facile)
		{
			background = Assets.backgroundmoregrass;
		}
		else if (statePlay == Difficulte.normal)
		{
			background = Assets.backgroundmanypieces;
		}
		else if (statePlay == Difficulte.difficile)
		{
			background = Assets.backgroundgrassdash;
		}
		else
		{
			background = Assets.backgroundmoregrass;
		}
		return background;
	}

	@Override
	public void present(float deltaTime) 
	{
		Graphics g = game.getGraphics();
		g.drawPixmap(Assets.background, 0, 0);

		/** Menu du choix du personnage */
		g.drawPixmap(Assets.choixPerso, 90, 236, 0, 0, 64, 64);
		g.drawPixmap(Assets.choixPerso, 164, 236, 64, 0, 64, 64);

		/** Menu du niveau de difficulte : facile, normal, difficile */
		/** >> Facile */
		g.drawPixmap(Assets.choixDifficulte, 110, 120, 0, 0, 97, 17);
		/** >> Normal */
		g.drawPixmap(Assets.choixDifficulte, 110, 160, 0, 16, 102, 17);
		/** >> Difficile 
		 * 
		 * => Rendre grise ce niveau tant que le niveau normal n'est pas termine
		 * 			>>		highLeverage = true;
		 * */
		if (highLeverage)
		{
			g.drawPixmap(Assets.choixDifficulte, 90, 200, 0, 32, 142, 17);
		}
		else
		{
			g.drawPixmap(Assets.choixDifficulteDesactive, 90, 200, 0, 32, 142, 17);
		}
		if (statePlay == Difficulte.facile)
		{
			g.drawPixmap(Assets.curseurDifficulte, 242, 113, 0, 0, 32, 25);
		}
		else if (statePlay == Difficulte.normal)
		{
			g.drawPixmap(Assets.curseurDifficulte, 242, 153, 0, 0, 32, 25);
		}
		else if (statePlay == Difficulte.difficile)
		{
			g.drawPixmap(Assets.curseurDifficulte, 242, 193, 0, 0, 32, 25);
		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if(event.x > x && event.x < x + width - 1 && 
				event.y > y && event.y < y + height - 1) 
			return true;
		else
			return false;
	}

	@Override
	public void pause() 
	{
	}

	@Override
	public void resume() 
	{
	}

	@Override
	public void dispose() 
	{
	}


}
