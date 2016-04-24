package com.oxyzen.thesetofsevenworlds;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.graphics.Color;

import com.oxyzen.framework.Game;
import com.oxyzen.framework.Graphics;
import com.oxyzen.framework.Input.TouchEvent;
import com.oxyzen.framework.Pixmap;
import com.oxyzen.framework.Screen;
import com.oxyzen.thesetofsevenworlds.Cloud.Type;
import com.oxyzen.thesetofsevenworlds.Ovni.Ship;
import com.oxyzen.thesetofsevenworlds.Personnage;
import com.oxyzen.thesetofsevenworlds.Personnage.Difficulte;
import com.oxyzen.thesetofsevenworlds.Personnage.PersoState;


public class GameScreen extends Screen {
	enum GameState {
		Ready,
		Running,
		Paused,
		GameOver,
		Winner
	}

	enum Themes{
		Default,
		Grass,
		GrassAndFlower,
		GrassDash
	}

	enum Trackpad{
		Neutral,
		Up,
		Down,
		Left,
		Right
	}

	boolean debugTrackPAD = false;

	GameState state = GameState.Ready;

	public static PersoState persostate;
	World world;
	Personnage personnage;
	Cloud cloud;
	Random random = new Random();
	int oldScore = 0;
	int oldScoreEatChallenge = 0;
	String score = "0";
	public static String scoreEatChallenge = ".."; //= "20";
	public static int nbScoreEatChallenge = 0;
	public static boolean timer = false;
	private int index = 0;

	private int nbVies = 5;
	private SimpleDateFormat format = new SimpleDateFormat ("hh:mm:ss");
	private long snapTimeCurrent;
	private Trackpad trackstate = Trackpad.Neutral;

	private Pixmap eggPixmap = null;
	private Egg egg = null;
	private Pixmap diamondPixmap = null;
	private Diamond diamond = null;
	private Pixmap candyPixmap = null;
	private Candy candy = null;
	private Pixmap cloudPixmap;
	private Pixmap ovniPixmap;

	private int srcX;
	private int srcY;
	private int srcWidth;
	private int srcHeight;

	private int cloudY;
	private int cloudsY;
	private int ovniY;

	public GameScreen(Game game) {
		super(game);
		world = new World();
		personnage = new Personnage(game);
		ovniY = Ovni.genY();
		cloudY = Cloud.genY();
		cloudsY = Cloud.genY();
		cloud = new Cloud();

		Ovni.lapsX();
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if(state == GameState.Ready)
		{
			if(Settings.soundEnabled)
			{
				Assets.level1.setLooping(true);
				Assets.level1.setVolume(1);
				Assets.level1.play();
			}
			updateReady(touchEvents);
		}
		if(state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if(state == GameState.Paused)
			updatePaused(touchEvents);
		if(state == GameState.GameOver)
		{
			if(Settings.soundEnabled)
			{
				if (!Assets.level1.isStopped())
				{
					Assets.level1.setLooping(true);
					Assets.level1.setVolume(1);
					Assets.level1.play();
				}
			}
			updateGameOver(touchEvents);
		}
	}

	private void updateReady(List<TouchEvent> touchEvents) {
		if(touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) 
	{        
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP) {
				if(event.x < 64 && event.y < 64) {
					if(Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.Paused;
					return;
				}
			}

			if(event.type == TouchEvent.TOUCH_DOWN) {
				if(event.x > 26 && event.x < 56 && event.y > 420 && event.y < 450) {
					world.snake.turnLeft();
					trackstate = Trackpad.Left;
				}
				if(event.x > 84 && event.x < 114 && event.y > 420 && event.y < 450) {
					world.snake.turnRight();
					trackstate = Trackpad.Right;
				}
				if(event.x > 55 && event.x < 85 && event.y > 395 && event.y < 425) {
					world.snake.turnUp();
					trackstate = Trackpad.Up;
				}
				if(event.x > 55 && event.x < 85 && event.y > 440 && event.y < 470) {
					world.snake.turnDown();
					trackstate = Trackpad.Down;
				}
			}
		}

		world.update(deltaTime);

		/** ------------------------------ */
		/** Gestion du jeu (gameover, winner)
		 *      et traitement des vies
		/** ------------------------------ */

		if (world.gameOver && nbVies > 0) 
		{
			if (timer == false)
			{	
				snapTimeCurrent = catchTime().getTime();

				nbVies -= 1;
				world.gameOver = false;
				timer = true;
			}
			else
			{
				/**
				 * Activation du shield pour 5s et puis quoi encore :)
				 */
				world.gameOver = false;
				long snapTimePost;

				snapTimePost = catchTime().getTime();

				//TODO:Faire activer le shield (Serpent cligontant) tant qu'on est protégé et que le compteur tourne.

				if ((snapTimePost-snapTimeCurrent) > 5000) 
				{
					timer = false;
				}
			}
		}
		else if (!world.gameOver && nbVies == 0)
		{
			if(Settings.soundEnabled)
				Assets.bitten.play(1);
			state = GameState.GameOver;
		}
		else if(world.gameWinner)
		{
			if(Settings.soundEnabled)
				//TODO : Ajouter une musique differente pour le Winner
				Assets.bitten.play(1);
			state = GameState.Winner;		
		}
		/** ------------------------------ */

		/**
		 * Decremente le nombre de hit (eat :) ) a manger
		 * et l'affiche dans la barre de menu
		 */
		if(oldScoreEatChallenge != nbScoreEatChallenge)
		{
			oldScoreEatChallenge = nbScoreEatChallenge;
			scoreEatChallenge = " " + oldScoreEatChallenge;
			//if(Settings.soundEnabled)
			//	Assets.eat.play(1);
		}
		/**
		 * Incremente le score et l'affiche dans la barre de menu
		 */
		if(oldScore != world.score) 
		{
			oldScore = world.score;
			score = "" + oldScore;
			if(Settings.soundEnabled)
				Assets.eat.play(1);
		}
	}

	private Date catchTime()
	{
		Date currentTime = new Date();
		String heureString = format.format(currentTime);
		try {
			currentTime = format.parse(heureString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return currentTime;
	}


	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP) {
				if(event.x > 80 && event.x <= 240) {
					if(event.y > 100 && event.y <= 148) {
						if(Settings.soundEnabled)
							Assets.click.play(1);
						state = GameState.Running;
						return;
					}                    
					if(event.y > 148 && event.y < 196) {
						if(Settings.soundEnabled)
						{
							Assets.click.play(1);
							if (Assets.level1.isLooping())
							{
								Assets.level1.setLooping(false);
								Assets.level1.stop();
							}
						}
						game.setScreen(new MainMenuScreen(game)); 
						return;
					}
				}
			}
		}
	}

	/** 
	 * Redemarrage du jeu si appui sur le button en forme de croix
	 */
	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();

		for(int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP) {
				if(event.x >= 0 && event.x <= 64 &&
						event.y >= 416 && event.y <= 480) {
					if(Settings.soundEnabled)
					{
						Assets.click.play(1);
						Assets.level1.stop();
						Assets.gameover.stop();
					}
					game.setScreen(new MainMenuScreen(game)); 
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();
		Pixmap background = null;

		background = Personnage.statePlayGame();
		g.drawPixmap(background, 0, 0);

		drawWorld(world);
		if(state == GameState.Ready)
			drawReadyUI();
		if(state == GameState.Running)
			drawRunningUI();
		if(state == GameState.Paused)
			drawPausedUI();
		if(state == GameState.GameOver)
			drawGameOverUI();
		if(state == GameState.Winner)
			drawGameWinnerUI();

		drawText(g, score, g.getWidth() / 2 - score.length()*20 / 2, g.getHeight() - 42);

		if (Personnage.statePlay == Difficulte.rien) scoreEatChallenge = "..";
		drawChallengeEat(g, scoreEatChallenge, g.getWidth() - 32 - scoreEatChallenge.length()*20 / 2, g.getHeight() - 64);
	}

	private Pixmap candy(Pixmap candyPixmap)
	{
		candy = world.candy;

		if(candy.type == Candy.RED_SUGAR)
		{
			srcX = 1;
			srcY = 1;
			srcWidth = 31;
			srcHeight = 36;
			candyPixmap = Assets.candy;
		}
		if(candy.type == Candy.GREEN_SUGAR)
		{
			srcX = 36;
			srcY = 1;
			srcWidth = 34;
			srcHeight = 36;
			candyPixmap = Assets.candy;
		}
		if(candy.type == Candy.YELLOW_STAR)
		{
			srcX = 73;
			srcY = 1;
			srcWidth = 34;
			srcHeight = 36;
			candyPixmap = Assets.candy;
		}
		if(candy.type == Candy.YELLOW_DARK_PLANET)
		{
			srcX = 109;
			srcY = 1;
			srcWidth = 37;
			srcHeight = 36;
			candyPixmap = Assets.candy;
		}
		if(candy.type == Candy.PINK_DONUTS)
		{
			srcX = 147;
			srcY = 1;
			srcWidth = 34;
			srcHeight = 34;
			candyPixmap = Assets.candy;
		}
		if(candy.type == Candy.YELLOW_STAR_5)
		{
			srcX = 184;
			srcY = 1;
			srcWidth = 34;
			srcHeight = 36;
			candyPixmap = Assets.candy;
		}
		if(candy.type == Candy.RED_SUGAR_6)
		{
			srcX = 224;
			srcY = 1;
			srcWidth = 31;
			srcHeight = 36;
			candyPixmap = Assets.candy;
		}

		if(candy.type == Candy.BLUE_SUGAR)
		{
			srcX = 258;
			srcY = 1;
			srcWidth = 34;
			srcHeight = 36;
			candyPixmap = Assets.candy;
		}

		this.candyPixmap = candyPixmap;
		return this.candyPixmap;
	}

	private Pixmap diamond(Pixmap diamondPixmap)
	{
		diamond = world.diamond;

		if(diamond.type == Diamond.PURPLE)
		{
			srcX = 1;
			srcY = 1;
			srcWidth = 35;
			srcHeight = 35;
			diamondPixmap = Assets.diamond;
		}
		if(diamond.type == Diamond.GREEN)
		{
			srcX = 42;
			srcY = 1;
			srcWidth = 35;
			srcHeight = 35;
			diamondPixmap = Assets.diamond;
		}
		if(diamond.type == Diamond.BLUE)
		{
			srcX = 78;
			srcY = 1;
			srcWidth = 35;
			srcHeight = 35;
			diamondPixmap = Assets.diamond;
		}
		if(diamond.type == Diamond.RED)
		{
			srcX = 115;
			srcY = 1;
			srcWidth = 35;
			srcHeight = 35;
			diamondPixmap = Assets.diamond;
		}
		if(diamond.type == Diamond.PINK)
		{
			srcX = 152;
			srcY = 1;
			srcWidth = 35;
			srcHeight = 35;
			diamondPixmap = Assets.diamond;
		}

		this.diamondPixmap = diamondPixmap;
		return this.diamondPixmap;
	}

	private Pixmap egg(Pixmap eggPixmap)
	{
		egg = world.egg;

		if(egg.type == Egg.TYPE_1)
			eggPixmap = Assets.oeufPlat1;
		if(egg.type == Egg.TYPE_2)
			eggPixmap = Assets.oeufPlat2;
		if(egg.type == Egg.TYPE_3)
			eggPixmap = Assets.oeufPlat3;
		if(egg.type == Egg.TYPE_4)
			eggPixmap = Assets.coquille;
		if(egg.type == Egg.TYPE_5) 
			eggPixmap = Assets.oeufDur;
		if(egg.type == Egg.TYPE_6)
			eggPixmap = Assets.troisOeufs;
		if(egg.type == Egg.TYPE_7)
			eggPixmap = Assets.deuxOeufs;
		if(egg.type == Egg.TYPE_8)
			eggPixmap = Assets.deuxOeufsCouches;
		if(egg.type == Egg.TYPE_9)
			eggPixmap = Assets.unOeuf;

		this.eggPixmap = eggPixmap;
		return this.eggPixmap;
	}

	private int eggX(int x)
	{
		x = egg.x * 32;		
		return x;
	}

	private int eggY(int y)
	{
		y = egg.y * 32;
		return y;
	}

	private int diamondX(int x)
	{
		x = diamond.x * 32;
		return x;
	}

	private int diamondY(int y)
	{
		y = diamond.y * 32;
		return y;
	}

	private int candyX(int x)
	{
		x = candy.x * 32;
		return x;
	}

	private int candyY(int y)
	{
		y = candy.y * 32;
		return y;
	}

	private void drawWorld(World world) {
		Graphics g = game.getGraphics();
		Snake snake = world.snake;
		SnakePart head = snake.parts.get(0);

		Pixmap queuePixmap = null;

		int x = 0,y = 0;

		g.drawPixmap(egg(this.eggPixmap), eggX(x), eggY(y));
		g.drawPixmap(diamond(this.diamondPixmap), diamondX(x), diamondY(y), srcX, srcY, srcWidth, srcHeight);
		g.drawPixmap(candy(this.candyPixmap), candyX(x), candyY(y), srcX, srcY, srcWidth, srcHeight);

		int len = snake.parts.size()-1;
		for(int i = 1; i < len; i++) {
			SnakePart part = snake.parts.get(i);
			x = part.x * 32;
			y = part.y * 32;
			g.drawPixmap(Assets.body, x, y);
		}

		for(int i = snake.parts.size()-1; i < snake.parts.size(); i++) {
			SnakePart part = snake.parts.get(i);
			x = part.x * 32;
			y = part.y * 32;

			if(snake.directionQueueMoinsUn == Snake.UP)
				queuePixmap = Assets.qDown;
			if(snake.directionQueueMoinsUn == Snake.DOWN)
				queuePixmap = Assets.qUp;
			if(snake.directionQueueMoinsUn == Snake.LEFT)
				queuePixmap = Assets.qRight;
			if(snake.directionQueueMoinsUn == Snake.RIGHT)
				queuePixmap = Assets.qLeft;

			g.drawPixmap(queuePixmap, x, y);
		}

		Pixmap headPixmap = null;
		if(snake.direction == Snake.UP) 
			if (persostate == PersoState.Blake) headPixmap = Assets.blakeUp;
			else headPixmap = Assets.philUp;
		if(snake.direction == Snake.LEFT)
			if (persostate == PersoState.Blake) headPixmap = Assets.blakeLeft;
			else headPixmap = Assets.philLeft;
		if(snake.direction == Snake.DOWN)
			if (persostate == PersoState.Blake) headPixmap = Assets.blakeDown;
			else headPixmap = Assets.philDown;
		if(snake.direction == Snake.RIGHT)
			if (persostate == PersoState.Blake) headPixmap = Assets.blakeRight;
			else headPixmap = Assets.philRight;

		x = head.x * 32 + 16;
		y = head.y * 32 + 16;
		g.drawPixmap(headPixmap, x - headPixmap.getWidth() / 2, y - headPixmap.getHeight() / 2);

		boolean bPaintCloud = true;
		if (bPaintCloud) screenCloud();

		boolean bPaintOvni = true;
		if (bPaintOvni) screenOvni();
	}

	private void screenCloud()
	{
		Graphics g = game.getGraphics();
		Cloud cloud = world.cloud;
		CloudX cloudX = cloud.part.get(0);

		int x = 0;
		int step = 22;
		x = cloudX.x * step + 300;

		g.drawPixmap(cloud.Pixmap(cloudPixmap, Type.cloud),x,cloudY);
		g.drawPixmap(cloud.Pixmap(cloudPixmap, Type.clouds),x,cloudsY);
	}

	private void screenOvni()
	{
		Graphics g = game.getGraphics();
		Ovni ovni = world.ovni;
		OvniXY ovniXY = ovni.part.get(0);
		//int[] y = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100, 105, 110, 115};

		int x = 0;
		int y = 0;
		int step = 22;
		x = ovniXY.x * step - 140;
		y = ovniXY.y[index++]; //* step - 140;
		//int yy = ovniXY.y * step - 140;
		
		if (index > ovniXY.y.length-1)
		{
			index = 0;
		}
		g.drawPixmap(ovni.Pixmap(ovniPixmap, Ship.ovni),x,y);
					
		//g.drawPixmap(ovni.Pixmap(ovniPixmap, Ship.ovni),x,ovniY);
		//g.drawPixmap(ovni.Pixmap(ovniPixmap, Ship.ovni),x,y[index++]);
	}

	private void drawReadyUI() {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.ready, 47, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}

	private void drawRunningUI() {
		Graphics g = game.getGraphics();

		/** Affichage du bouton PAUSE */
		g.drawPixmap(Assets.buttonPauseStop, 0, 0, 64, 0, 65, 65);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
		/** Affichage du Joystick */
		g.drawPixmap(Assets.pad, 0, 380);
		/** Affichage et gestion du trackpad */
		if (trackstate == Trackpad.Up) 
		{
			g.drawPixmap(Assets.trackpadup, 36, 405);
		}
		if (trackstate == Trackpad.Down) 
		{
			g.drawPixmap(Assets.trackpaddown, 36, 405);
		}
		if (trackstate == Trackpad.Left) 
		{
			g.drawPixmap(Assets.trackpadleft, 36, 405);
		}
		if (trackstate == Trackpad.Right) 
		{
			g.drawPixmap(Assets.trackpadright, 36, 405);
		}
		if (trackstate == Trackpad.Neutral) 
		{
			g.drawPixmap(Assets.trackpad, 36, 405);
		}
		/** Affichage du nombres de vies */
		for(int i = 1; i <= nbVies; i++) 
		{
			g.drawPixmap(Assets.vies,176+24*i, 0, 1, 0, 24, 24);
		}
		/** Calibrage du TrackPAD -- Marqueurs de couleur utilisés lors du DEBUG */
		if (debugTrackPAD)
		{
			g.drawRect(26, 420,30,30,Color.GREEN);
			g.drawRect(84, 420,30,30,Color.YELLOW);
			g.drawRect(55, 395,30,30,Color.RED);
			g.drawRect(55, 440,30,30,Color.BLUE);
		}
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.pause, 80, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();

		if(Settings.soundEnabled)
		{
			Assets.level1.setLooping(false);
			Assets.level1.setVolume(0);
			Assets.level1.stop();

			Assets.gameover.setVolume(1);
			Assets.gameover.play();
		}

		g.drawPixmap(Assets.gameOver, 62, 100);
		g.drawPixmap(Assets.gameovertext,122,100);
		g.drawPixmap(Assets.buttonPauseStop, 0, 416, 0, 0, 64, 64); //125, 200, 0, 0, 65, 65
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}

	private void drawGameWinnerUI() {
		Graphics g = game.getGraphics();

		if(world.gameWinner) {
			Settings.addScore(world.score);
			Settings.save(game.getFileIO());
		}

		g.drawPixmap(Assets.gameOver, 62, 100);
		g.drawPixmap(Assets.gameovertext,122,100);
		g.drawPixmap(Assets.buttonPauseStop, 0, 416, 0, 0, 64, 64); //125, 200, 0, 0, 65, 65
		g.drawLine(0, 416, 480, 416, Color.BLACK);
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

	public void drawChallengeEat(Graphics g, String eat, int x, int y)
	{
		int len = eat.length();
		for (int i = 0; i < len; i++) {
			char character = eat.charAt(i);

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
		if(state == GameState.Running)
			state = GameState.Paused;

		if(world.gameOver) {
			Settings.addScore(world.score);
			Settings.save(game.getFileIO());
		}
	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}

