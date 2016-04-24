package com.oxyzen.thesetofsevenworlds;

import java.util.Random;

import com.oxyzen.thesetofsevenworlds.Personnage.Difficulte;

public class World {
    static final int WORLD_WIDTH = 10;
    static final int WORLD_HEIGHT = 13;
    static final int SCORE_INCREMENT = 10;
    static final int OBJET_DECREMENT = 1;
    static final float TICK_INITIAL = 0.5f;
    static final float TICK_DECREMENT = 0.05f;

    public Snake snake;
    public Egg egg;
    public Diamond diamond;
    public Candy candy;
    public Personnage perso;
    public Cloud cloud;
    public Ovni ovni;
    		
    public boolean gameOver = false;
    public boolean gameWinner = false;
    public int score = 0;
    
    boolean fields[][] = new boolean[WORLD_WIDTH][WORLD_HEIGHT];
    Random random = new Random();
    float tickTime = 0;
    float tick = TICK_INITIAL;

    public World() {
        snake = new Snake();
        placeEggs();
        placeDiamonds();
        placeCandy();
        cloud = new Cloud();
        ovni = new Ovni();
        /** Niveau de monde : Taupe, les Oeufs sont faits, ...*/
    }
    
    /**
     * Placement des oeufs
     */
    private void placeEggs() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        int len = snake.parts.size();
        for (int i = 0; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            fields[part.x][part.y] = true;
        }

        int eggX = random.nextInt(WORLD_WIDTH);
        int eggY = random.nextInt(WORLD_HEIGHT);
        while (true) {
            if (fields[eggX][eggY] == false)
                break;
            eggX += 1;
            if (eggX >= WORLD_WIDTH) {
            	eggX = 0;
            	eggY += 1;
                if (eggY >= WORLD_HEIGHT) {
                	eggY = 0;
                }
            }
        }
        egg = new Egg(eggX, eggY, random.nextInt(9));
    }

    /**
     * Placement des diamants et pierres précieuses
     */
    private void placeDiamonds() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        int len = snake.parts.size();
        for (int i = 0; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            fields[part.x][part.y] = true;
        }

        int diamondX = random.nextInt(WORLD_WIDTH);
        int diamondY = random.nextInt(WORLD_HEIGHT);
        while (true) {
            if (fields[diamondX][diamondY] == false)
                break;
            diamondX += 1;
            if (diamondX >= WORLD_WIDTH) {
            	diamondX = 0;
            	diamondY += 1;
                if (diamondY >= WORLD_HEIGHT) {
                	diamondY = 0;
                }
            }
        }
        diamond = new Diamond(diamondX, diamondY, random.nextInt(5));
    }
    
    /**
     * Placement des bonbons
     */
    private void placeCandy() {
        for (int x = 0; x < WORLD_WIDTH; x++) {
            for (int y = 0; y < WORLD_HEIGHT; y++) {
                fields[x][y] = false;
            }
        }

        int len = snake.parts.size();
        for (int i = 0; i < len; i++) {
            SnakePart part = snake.parts.get(i);
            fields[part.x][part.y] = true;
        }

        int candyX = random.nextInt(WORLD_WIDTH);
        int candyY = random.nextInt(WORLD_HEIGHT);
        while (true) {
            if (fields[candyX][candyY] == false)
                break;
            candyX += 1;
            if (candyX >= WORLD_WIDTH) {
            	candyX = 0;
            	candyY += 1;
                if (candyY >= WORLD_HEIGHT) {
                	candyY = 0;
                }
            }
        }
        candy = new Candy(candyX, candyY, random.nextInt(8));
    }
    
    public void update(float deltaTime) {
        if (gameOver)
            return;

        tickTime += deltaTime;

        while (tickTime > tick) {
            tickTime -= tick;
            snake.advance();
            cloud.advance();
            ovni.advance();
            if (snake.checkBitten()) {
                gameOver = true;
                return;
            }

            SnakePart head = snake.parts.get(0);
            if (head.x == egg.x && head.y == egg.y) {
                score += SCORE_INCREMENT;
				//scoreEatChallenge -= OBJET_DECREMENT;
                if (Personnage.statePlay != Difficulte.rien) GameScreen.nbScoreEatChallenge -= OBJET_DECREMENT;
                if (GameScreen.nbScoreEatChallenge == 0) 
                {
                	gameWinner = true;
                	return;
                }
                snake.eat();
                snake.advance();
                if (snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true;
                    return;
                } else {
                    placeEggs();
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
            if (head.x == diamond.x && head.y == diamond.y) { //TODO:Voir pour effectuer une autre action : incrémenter une vie, donner une armure, etc
                score += SCORE_INCREMENT;
				//scoreEatChallenge -= OBJET_DECREMENT;
                if (Personnage.statePlay != Difficulte.rien) GameScreen.nbScoreEatChallenge -= OBJET_DECREMENT;
                if (GameScreen.nbScoreEatChallenge == 0) 
                {
                	gameWinner = true;
                	return;
                }
                snake.eat();
                snake.advance();
                if (snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true;
                    return;
                } else {
                    placeDiamonds();
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
            if (head.x == candy.x && head.y == candy.y) { //TODO:Voir pour effectuer une autre action : incrémenter une vie, donner une armure, etc
                score += SCORE_INCREMENT;
				//scoreEatChallenge -= OBJET_DECREMENT;
                if (Personnage.statePlay != Difficulte.rien) GameScreen.nbScoreEatChallenge -= OBJET_DECREMENT;
                if (GameScreen.nbScoreEatChallenge == 0) 
                {
                	gameWinner = true;
                	return;
                }
                snake.eat();
                snake.advance();
                if (snake.parts.size() == WORLD_WIDTH * WORLD_HEIGHT) {
                    gameOver = true;
                    return;
                } else {
                    placeCandy();
                }

                if (score % 100 == 0 && tick - TICK_DECREMENT > 0) {
                    tick -= TICK_DECREMENT;
                }
            }
        }
    }
}
