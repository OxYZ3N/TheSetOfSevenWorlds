package com.oxyzen.thesetofsevenworlds;

import java.util.List;

import android.graphics.Color;

import com.oxyzen.framework.Game;
import com.oxyzen.framework.Graphics;
import com.oxyzen.framework.Input.TouchEvent;
import com.oxyzen.framework.Screen;
import com.oxyzen.thesetofsevenworlds.Personnage.Difficulte;

public class MainMenuScreen extends Screen {
	public MainMenuScreen(Game game) {
		super(game);
		Personnage.scoreEatChallenge = "..";
		Personnage.statePlay = Difficulte.rien;
	}   

	public void update(float deltaTime) {

		Graphics g = game.getGraphics();

		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();       

		if(Settings.soundEnabled)
		{
			Assets.intro.setLooping(true);
			Assets.intro.setVolume(1);
			if (!Assets.intro.isPlaying()) Assets.intro.play();
		}

		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP) {
				// Active ou desactive le son
				if(inBounds(event, 0, g.getHeight() - 64, 64, 64)) {
					Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
					{
						Assets.intro.setLooping(true);
						Assets.intro.setVolume(1);
						Assets.intro.play();
						Assets.click.play(1);
						return;
					}
					else
					{
						Assets.click.play(1);
						Assets.intro.setLooping(false);
						Assets.intro.stop();
					}
				}
				// Lance le jeu
				if(inBounds(event, 64, 290, 192, 42) ) {
					//game.setScreen(new GameScreen(game));
					game.setScreen(new Personnage(game));
					if(Settings.soundEnabled)
					{
						if (Assets.intro.isLooping()) Assets.intro.stop();
						Assets.click.play(1);
						return;
					}
				}
				// Affiche l'ecran des high scores
				if(inBounds(event, 64, 290 + 42, 192, 42) ) {
					game.setScreen(new HighscoreScreen(game));
					if(Settings.soundEnabled) 
					{
						if (Assets.intro.isLooping()) Assets.intro.stop();
						Assets.click.play(1);
						return;
					}
				}
				// Affiche l'ecran d'aide
				/* TODO : A recoder */
				if(inBounds(event, 64, 290 + 84, 192, 42) ) {
					game.setScreen(new HelpScreen(game));
					if(Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
				// Affiche l'ecran A propos de l'auteur
				if(inBounds(event, 253, 416, 64, 64) ) {
					game.setScreen(new AboutScreen(game));
					if(Settings.soundEnabled)
						Assets.click.play(1);
					return;
				}
			}
		}
	}

	private boolean inBounds(TouchEvent event, int x, int y, int width, int height) {
		if(event.x > x && event.x < x + width - 1 && 
				event.y > y && event.y < y + height - 1) 
			return true;
		else
			return false;
	}

	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.background, 0, 0);
		//g.drawPixmap(Assets.pad, 0, 380);
		//g.drawRect(58, 400,18,18,Color.RED);
		//g.drawRect(58, 455,18,18,Color.BLUE);
		//g.drawRect(28, 425,18,18,Color.GREEN);
		//g.drawRect(98, 425,18,18,Color.YELLOW);
		//g.drawPixmap(Assets.logo, 32, 20);
		g.drawPixmap(Assets.backgroundpeaceone,0,35);
		g.drawPixmap(Assets.backgroundpeacetwo,130,15);
		g.drawPixmap(Assets.aboutauthor,253, 416, 1, 0, 64, 64);
		g.drawPixmap(Assets.mainMenu, 64, 290); //220

		// Active ou desactive le son
		if(Settings.soundEnabled)
			g.drawPixmap(Assets.soundOnOff, 0, 416, 0, 0, 64, 64);
		else
			g.drawPixmap(Assets.soundOnOff, 0, 416, 64, 0, 64, 64);


	}

	public void pause() {        
		Settings.save(game.getFileIO());
	}

	public void resume() {

	}

	public void dispose() {

	}
}

