package com.oxyzen.thesetofsevenworlds;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import com.oxyzen.framework.Game;
import com.oxyzen.framework.Graphics;
import com.oxyzen.framework.Input.TouchEvent;
import com.oxyzen.framework.Pixmap;
import com.oxyzen.framework.Screen;
import com.oxyzen.thesetofsevenworlds.Personnage;
import com.oxyzen.thesetofsevenworlds.Personnage.Difficulte;
import com.oxyzen.thesetofsevenworlds.Personnage.PersoState;


public class SauvegardeGameScreen extends Screen {
	enum GameState {
		Ready,
		Running,
		Paused,
		GameOver,
		Winner
	}

	GameState state = GameState.Ready;
	public static PersoState persostate;
	World world;
	Personnage personnage;

	int oldScore = 0;
	int oldScoreEatChallenge = 0;
	String score = "0";
	public static String scoreEatChallenge = ".."; //= "20";
	public static int nbScoreEatChallenge = 0;

	private int nbVies = 5;
	public static boolean timer = false;
	private boolean isRunning;
	private volatile Looper myLooper;
	//final MyCounter tempo = new MyCounter(5000,1000);
	private static Handler handler;
	private CountDownTimer mCountDown;
	public Handler mHandler;
	private Thread t = new Thread();

	public SauvegardeGameScreen(Game game) {
		super(game);
		world = new World();
		personnage = new Personnage(game);
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if(state == GameState.Ready)
			updateReady(touchEvents);
		if(state == GameState.Running)
			updateRunning(touchEvents, deltaTime);
		if(state == GameState.Paused)
			updatePaused(touchEvents);
		if(state == GameState.GameOver)
			updateGameOver(touchEvents);
	}

	private void updateReady(List<TouchEvent> touchEvents) {
		if(touchEvents.size() > 0)
			state = GameState.Running;
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {        
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
				if(event.x > 28 && event.x < 56 && event.y > 417 && event.y < 445) {
					world.snake.turnLeft();
				}
				if(event.x > 86 && event.x < 114 && event.y > 417 && event.y < 445) {
					world.snake.turnRight();
				}
				if(event.x > 58 && event.x < 76 && event.y > 400 && event.y < 418) {
					world.snake.turnUp();
				}
				if(event.x > 58 && event.x < 76 && event.y > 455 && event.y < 473) {
					world.snake.turnDown();
				}
			}
		}
		//g.drawRect(28, 417,28,28,Color.GREEN);
		//g.drawRect(86, 417,28,28,Color.YELLOW);

		//g.drawRect(58, 400,18,18,Color.RED);
		//g.drawRect(58, 455,18,18,Color.BLUE);
		
		world.update(deltaTime);

		/** ------------------------------ */
		/** Gestion du jeu (gameover, winner)
		 *  et traitement des vies */
		if (world.gameOver && nbVies > 0) 
		{
			final Thread t1 = new Thread();
			//Boolean timer = false;

			System.out.println("Valeur du Timer avant : " + timer);

			ExecutorService service = Executors.newSingleThreadExecutor();
			Future<Boolean> result = service.submit(new Callable<Boolean>()
					{
				int compteur = 5;
				public Boolean call() throws Exception
				{
					t1.setDaemon(true);
					while (compteur > 0)
					{
						compteur--;
						t1.sleep(1000);
						System.out.println(compteur + " secondes avant de sortir de la boucle call() du thread " + t1.getName());
					}
					return true;
				}
					});
			System.out.println("Fait quelques ch�ses en attendant le r�sultat. Un gros calcul par exemple :) ");
			int maValeur = MoveItSuckerBaby();
			if (maValeur==5) System.out.println("Gros calcul termin� :)");
			try
			{
				// The call for get() will block the main thread until the result is
				// known
				// Doing things while waiting for the result
				timer = result.get();
				System.out.println("Apr�s le traitement");
				System.out.println("Valeur du Timer apr�s : " + timer);
			}
			catch (InterruptedException e)
			{
				System.out.println("Grr interrupted :(");
			}
			catch (ExecutionException e)
			{
				if (t1.isAlive()) System.out.println("the process " + t1.getName() + " still working ");
				if (t1.currentThread() != null)
					System.out.println("the process " + t1.getName() + " still working ");
				System.out.println("Woops something went wrong");
			}






			if (timer == false)
			{
				nbVies -= 1;
				world.gameOver = false;
				timer = true;
			}
			else
			{
				/*Runnable task = new Runnable() {
					public void run() {
						new Reminder(5);}
				};
				Thread t = new Thread(task);
		        t.setDaemon(true);
		        t.start();*/
				//new Reminder(5);
				/*final AsyncTask<Void, Integer, Boolean> tsk = new AsyncTask<Void, Integer, Boolean>() {
					@Override
					protected Boolean doInBackground(Void... arg0) {
								SystemClock.sleep(5000);
							return true;
					}
					@Override
					protected void onPostExecute(Boolean result) {
						// background work is finished, 
						// we can update the UI here
						// including removing the dialog
							if(result)
							{
								timer = false;
								//if (t.isAlive() == false) timer = false;
							}
					}
				};
				tsk.execute();

				/*new Thread(new Runnable() {
		            @Override
		            public void run() {
						tempo.start();
		            }
		        }).start();*/
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

		/*if (nbVies<0) {
		nbVies -= 1;
		if(world.gameOver) {
		if(Settings.soundEnabled)
		Assets.bitten.play(1);
		state = GameState.GameOver;
		}
		}*/

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

	private static int MoveItSuckerBaby() {
		int billyBoy = 0;
		for (int i=2000000;i>0;i--)
		{
			billyBoy = i;;
			System.out.println(billyBoy);
			if (i==5) break;
		}
		return billyBoy;
	}

	private void timeBeforeLossAnAnotherLife()
	{		
		new CountDownTimer(5000,1000) //mCountDown = 
		{
			@Override
			public void onTick(long millisUntilFinished) {
			}


			@Override
			public void onFinish() {
				// DO YOUR OPERATION
				timer = false;
				// if (mCountDown != null) {
				//     mCountDown.cancel();
				// }
			}
		}.start();
	}

	private void timeBeforeLossAnAnotherLife2()
	{
		new AsyncTask<String, Void, String>() {
			protected void onPreExecute() {
				// perhaps show a dialog 
				// with a progress bar
				// to let your users know
				// something is happening
			}
			@Override
			protected String doInBackground(String... params) {
				// do some expensive work 
				// in the background here
				try {
					// >>>> Attendre que le thread en cours soit termine avant d'en demarrer un autre
					//Thread thread = new Thread();
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			protected void onPostExecute(String Result) {
				// background work is finished, 
				// we can update the UI here
				// including removing the dialog
				timer = false;
			}

		}.execute();
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
							Assets.click.play(1);
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
						Assets.click.play(1);
					game.setScreen(new MainMenuScreen(game)); 
					return;
				}
			}
		}
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.background, 0, 0);
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

	private void drawWorld(World world) {
		Graphics g = game.getGraphics();
		Snake snake = world.snake;
		SnakePart head = snake.parts.get(0);

		Egg egg = world.egg;

		Pixmap queuePixmap = null;
		Pixmap eggPixmap = null;

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
		int x = egg.x * 32;
		int y = egg.y * 32;

		g.drawPixmap(eggPixmap, x, y);

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
		/** Affichage du nombres de vies */
		for(int i = 1; i <= nbVies; i++) 
		{
			g.drawPixmap(Assets.vies,176+24*i, 0, 1, 0, 24, 24);
		}
		// Marqueur sur le PAD -- A retirer une fois le debug effectue
		//g.drawRect(28, 417,28,28,Color.GREEN);
		//g.drawRect(86, 417,28,28,Color.YELLOW);
		//g.drawRect(58, 400,18,18,Color.RED);
		//g.drawRect(58, 455,18,18,Color.BLUE);

		//g.drawPixmap(Assets.buttons, 0, 416, 64, 64, 64, 64);
		//g.drawRect(0, 416,64,64,Color.RED);
		//g.drawPixmap(Assets.buttons, 256, 416, 0, 64, 64, 64);
		//g.drawRect(256, 416,64,64,Color.RED);
	}

	private void drawPausedUI() {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.pause, 80, 100);
		g.drawLine(0, 416, 480, 416, Color.BLACK);
	}

	private void drawGameOverUI() {
		Graphics g = game.getGraphics();

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
/*ExecutorService service = Executors.newSingleThreadExecutor();

Future<Boolean> result = service.submit(new Callable<Boolean>()
		{
	int compteur = 5;
	public Boolean call() throws Exception
	{
		state = GameState.Running;
		t1.setDaemon(true);
		while (compteur > 0)
		{
			state = GameState.Running;
			compteur--;
			t1.sleep(1000);
			System.out.println(compteur + " secondes avant de sortir de la boucle call() du thread " + t1.getName());
		}
		return false;
	}
		});
System.out.println("Fait quelques ch�ses en attendant le r�sultat. Un gros calcul par exemple :) ");
int maValeur = MoveItSuckerBaby();
if (maValeur==5) System.out.println("Gros calcul termin� :)");

state = GameState.Running;
world.update(deltaTime);

try
{
	// The call for get() will block the main thread until the result is
	// known
	// Doing things while waiting for the result
	timer = result.get();
	state = GameState.Running;
	System.out.println("Apr�s le traitement");
	System.out.println("Valeur du Timer apr�s : " + timer);
}
catch (InterruptedException e)
{
	System.out.println("Grr interrupted :(");
}
catch (ExecutionException e)
{
	if (t1.isAlive()) System.out.println("the process " + t1.getName() + " still working ");
	if (t1.currentThread() != null)
	System.out.println("the process " + t1.getName() + " still working ");
	System.out.println("Woops something went wrong");
}
*/
/*Runnable task = new Runnable() {
public void run() {
	new Reminder(5);}
};
Thread t = new Thread(task);
t.setDaemon(true);
t.start();*/
//new Reminder(5);
/*final AsyncTask<Void, Integer, Boolean> tsk = new AsyncTask<Void, Integer, Boolean>() {
@Override
protected Boolean doInBackground(Void... arg0) {
			SystemClock.sleep(5000);
		return true;
}
@Override
protected void onPostExecute(Boolean result) {
	// background work is finished, 
	// we can update the UI here
	// including removing the dialog
		if(result)
		{
			timer = false;
			//if (t.isAlive() == false) timer = false;
		}
}
};
tsk.execute();

/*new Thread(new Runnable() {
@Override
public void run() {
	tempo.start();
}
}).start();*/
