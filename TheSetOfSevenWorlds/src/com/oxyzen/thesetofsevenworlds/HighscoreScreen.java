package com.oxyzen.thesetofsevenworlds;

import java.util.List;

import com.oxyzen.framework.Game;
import com.oxyzen.framework.Graphics;
import com.oxyzen.framework.Screen;
import com.oxyzen.framework.Input.TouchEvent;

public class HighscoreScreen extends Screen {
	String lines[] = new String[5];

	public HighscoreScreen(Game game) {
		super(game);

		for (int i = 0; i < 5; i++) {
			lines[i] = "" + (i + 1) + ". " + Settings.highscores[i];
		}
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if(Settings.soundEnabled)
		{
			Assets.highscore.setLooping(true);
			Assets.highscore.setVolume(1);
			Assets.highscore.play();
		}

		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x < 64 && event.y > 416) {
					if(Settings.soundEnabled)
					{
						Assets.click.play(1);
						Assets.highscore.setLooping(false);
						Assets.highscore.stop();
						//return;
					}
					game.setScreen(new MainMenuScreen(game));
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.background, 0, 0);
		g.drawPixmap(Assets.mainMenu, 64, 20, 0, 42, 196, 42);
		g.drawPixmap(Assets.piwiBack, 170,250);

		int y = 100;
		for (int i = 0; i < 5; i++) {
			drawText(g, lines[i], 20, y);
			y += 50;
		}

		g.drawPixmap(Assets.buttonGaucheDroite, 0, 416, 64, 0, 65, 65);
	}

	public void drawText(Graphics g, String line, int x, int y) {
		int len = line.length();
		for (int i = 0; i < len; i++) {
			char character = line.charAt(i);

			if (character == ' ') {
				x += 20;
				continue;
			}

			int srcX = 0;
			int srcWidth = 0;
			if (character == '.') {
				srcX = 200;
				srcWidth = 10;
			} else {
				srcX = (character - '0') * 20;
				srcWidth = 20;
			}

			g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
			x += srcWidth;
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
