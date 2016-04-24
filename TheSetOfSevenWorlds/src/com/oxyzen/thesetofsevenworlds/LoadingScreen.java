package com.oxyzen.thesetofsevenworlds;

import com.oxyzen.thesetofsevenworlds.Assets;
import com.oxyzen.thesetofsevenworlds.MainMenuScreen;
import com.oxyzen.thesetofsevenworlds.Settings;
import com.oxyzen.framework.Game;
import com.oxyzen.framework.Graphics;
import com.oxyzen.framework.Screen;
import com.oxyzen.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen {
    public LoadingScreen(Game game) {
        super(game);
    }

    public void update(float deltaTime) {
        Graphics g = game.getGraphics();
        Assets.background = g.newPixmap("background.png", PixmapFormat.RGB565);
        Assets.backgroundmanypieces = g.newPixmap("backgroundmanypieces.png", PixmapFormat.RGB565);
        Assets.backgroundmoregrass = g.newPixmap("backgroundmoregrass.png", PixmapFormat.RGB565);
        Assets.backgroundflower = g.newPixmap("backgroundflower.png", PixmapFormat.RGB565);
        Assets.backgroundgrassdash = g.newPixmap("backgroundgrassdash.png", PixmapFormat.RGB565);
        Assets.backgroundpeaceone = g.newPixmap("backgroundpeaceone.png", PixmapFormat.ARGB4444);
        Assets.backgroundpeacetwo = g.newPixmap("backgroundpeacetwo.png", PixmapFormat.ARGB4444);
        Assets.aboutauthor = g.newPixmap("aboutauthor.png", PixmapFormat.ARGB4444);
        Assets.pad = g.newPixmap("Piwi_Pad.png",PixmapFormat.ARGB4444);
        Assets.trackpad = g.newPixmap("trackpad.png",PixmapFormat.ARGB4444);
        Assets.trackpadup = g.newPixmap("trackpadup.png",PixmapFormat.ARGB4444);
        Assets.trackpaddown = g.newPixmap("trackpaddown.png",PixmapFormat.ARGB4444);
        Assets.trackpadleft = g.newPixmap("trackpadleft.png",PixmapFormat.ARGB4444);
        Assets.trackpadright = g.newPixmap("trackpadright.png",PixmapFormat.ARGB4444);
        Assets.oeufPlat1 = g.newPixmap("Oeuf_Plat_1.png",PixmapFormat.ARGB4444);
        Assets.oeufPlat2 = g.newPixmap("Oeuf_Plat_2.png",PixmapFormat.ARGB4444);
        Assets.oeufPlat3 = g.newPixmap("Oeuf_Plat_3.png",PixmapFormat.ARGB4444);
        Assets.coquille = g.newPixmap("Coquille.png",PixmapFormat.ARGB4444);
        Assets.oeufDur = g.newPixmap("Oeuf_dur.png",PixmapFormat.ARGB4444);
        Assets.troisOeufs = g.newPixmap("3Oeufs.png",PixmapFormat.ARGB4444);
        Assets.deuxOeufs = g.newPixmap("2Oeufs.png",PixmapFormat.ARGB4444);
        Assets.deuxOeufsCouches = g.newPixmap("2Oeufs_couches.png",PixmapFormat.ARGB4444);
        Assets.unOeuf = g.newPixmap("1Oeuf.png",PixmapFormat.ARGB4444);
        Assets.piwiBack = g.newPixmap("piwiBack.png",PixmapFormat.ARGB4444);
        Assets.gameOver = g.newPixmap("gameoverPoule.png", PixmapFormat.ARGB4444);
        Assets.gameovertext = g.newPixmap("gameOverText.png",PixmapFormat.ARGB4444);
        Assets.mainMenu = g.newPixmap("mainmenu.png", PixmapFormat.ARGB4444);
        Assets.soundOnOff = g.newPixmap("Piwi_S_On_Off.png", PixmapFormat.ARGB4444);
        Assets.buttonPauseStop = g.newPixmap("Piwi_Stop_Pause.png",PixmapFormat.ARGB4444);
        Assets.buttonGaucheDroite = g.newPixmap("Patte_Gauche_Droite.png",PixmapFormat.ARGB4444);
        /** Perso : corps des personnages */
        Assets.body = g.newPixmap("body.png",PixmapFormat.ARGB4444);
        /** Perso : corps du ver nu */
        Assets.bodyWormUp = g.newPixmap("bodywormup.png",PixmapFormat.ARGB4444); //TODO: Ver nu
        Assets.bodyWormDown = g.newPixmap("bodywormdown.png",PixmapFormat.ARGB4444);
        Assets.bodyWormLeft = g.newPixmap("bodywormleft.png",PixmapFormat.ARGB4444);
        Assets.bodyWormRight = g.newPixmap("bodywormright.png",PixmapFormat.ARGB4444);
        /** Perso : bouclier des personnages */
        Assets.bodyShieldUp = g.newPixmap("bodyshieldup.png",PixmapFormat.ARGB4444); //TODO: Bouclier corps
        Assets.bodyShieldDown = g.newPixmap("bodyshielddown.png",PixmapFormat.ARGB4444);
        Assets.bodyShieldLeft = g.newPixmap("bodyshieldleft.png",PixmapFormat.ARGB4444);
        Assets.bodyShieldright = g.newPixmap("bodyshieldright.png",PixmapFormat.ARGB4444);
        /** Perso : Phil */
        Assets.philUp = g.newPixmap("philup.png",PixmapFormat.ARGB4444);
        Assets.philDown = g.newPixmap("phildown.png",PixmapFormat.ARGB4444);
        Assets.philLeft = g.newPixmap("philleft.png",PixmapFormat.ARGB4444);
        Assets.philRight = g.newPixmap("philright.png",PixmapFormat.ARGB4444);
        /** Perso : Blake */
        Assets.blakeUp = g.newPixmap("blakeup.png", PixmapFormat.ARGB4444);
        Assets.blakeDown = g.newPixmap("blakedown.png", PixmapFormat.ARGB4444);
        Assets.blakeLeft = g.newPixmap("blakeleft.png", PixmapFormat.ARGB4444);
        Assets.blakeRight = g.newPixmap("blakeright.png", PixmapFormat.ARGB4444);
        /** Perso : Blake masque d'etoile */
        Assets.blakeStarsUp = g.newPixmap("blakestarsup.png", PixmapFormat.ARGB4444);
        Assets.blakeStarsDown = g.newPixmap("blakestarsdown.png", PixmapFormat.ARGB4444);
        Assets.blakeStarsLeft = g.newPixmap("blakestarsleft.png", PixmapFormat.ARGB4444);
        Assets.blakeStarsRight = g.newPixmap("blakestarsright.png", PixmapFormat.ARGB4444);
        /** Perso : Blake masque ancephale plat */
        Assets.blakeBipUp = g.newPixmap("blakebipup.png", PixmapFormat.ARGB4444);
        Assets.blakeBipDown = g.newPixmap("blakebipdown.png", PixmapFormat.ARGB4444);
        Assets.blakeBipLeft = g.newPixmap("blakebipleft.png", PixmapFormat.ARGB4444);
        Assets.blakeBipRight = g.newPixmap("blakebipright.png", PixmapFormat.ARGB4444);
        /** Perso : Blake masque binaire */
        Assets.blakeBinaryUp = g.newPixmap("blakebinaryup.png", PixmapFormat.ARGB4444);
        Assets.blakeBinaryDown = g.newPixmap("blakebinarydown.png", PixmapFormat.ARGB4444);
        Assets.blakeBinaryLeft = g.newPixmap("blakebinaryleft.png", PixmapFormat.ARGB4444);
        Assets.blakeBinaryRight = g.newPixmap("blakebinaryright.png", PixmapFormat.ARGB4444);
        /** Perso : Queue */
        Assets.qUp = g.newPixmap("qup.png", PixmapFormat.ARGB4444);
        Assets.qDown = g.newPixmap("qdown.png", PixmapFormat.ARGB4444);
        Assets.qLeft = g.newPixmap("qleft.png", PixmapFormat.ARGB4444);
        Assets.qRight = g.newPixmap("qright.png", PixmapFormat.ARGB4444);
        /** Perso : Queue bouclier */
        Assets.qShieldUp = g.newPixmap("qshieldup.png", PixmapFormat.ARGB4444);
        Assets.qShieldDown = g.newPixmap("qshielddown.png", PixmapFormat.ARGB4444);
        Assets.qShieldLeft = g.newPixmap("qshieldleft.png", PixmapFormat.ARGB4444);
        Assets.qShieldRight = g.newPixmap("qshieldright.png", PixmapFormat.ARGB4444);
        /** Perso : Queue ver nu */
        Assets.qWormUp = g.newPixmap("qwormup.png", PixmapFormat.ARGB4444);
        Assets.qWormDown = g.newPixmap("qwormdown.png", PixmapFormat.ARGB4444);
        Assets.qWormLeft = g.newPixmap("qwormleft.png", PixmapFormat.ARGB4444);
        Assets.qWormRight  = g.newPixmap("qwormright.png", PixmapFormat.ARGB4444);
        /** Perso : Choix du personnage */
        Assets.choixPerso = g.newPixmap("choixperso.png", PixmapFormat.ARGB4444);
        /** Vies */
        Assets.vies = g.newPixmap("vies.png",PixmapFormat.ARGB4444);
        /** Choix de la difficulte */
        Assets.choixDifficulte = g.newPixmap("difficulte.png",PixmapFormat.ARGB4444);
        Assets.choixDifficulteDesactive = g.newPixmap("desdifficulte.png",PixmapFormat.ARGB4444);
        Assets.curseurDifficulte = g.newPixmap("curseurDifficulte.png",PixmapFormat.ARGB4444);
        /** Diamant violet, vert, bleu, rouge, rose */
        Assets.diamond = g.newPixmap("diamond.png",PixmapFormat.ARGB4444);
        /** Bonbons de couleurs différentes */
        Assets.candy = g.newPixmap("candy.png",PixmapFormat.ARGB4444);
        /** Nuage et nuage(s) 		  */
        Assets.cloud = g.newPixmap("cloud.png",PixmapFormat.ARGB4444);
        Assets.clouds = g.newPixmap("clouds.png",PixmapFormat.ARGB4444);
        /** O.V.N.I 		  */
        Assets.ovni = g.newPixmap("ovni.png",PixmapFormat.ARGB4444);
        /** ------------------------- */
        Assets.logo = g.newPixmap("logo.png", PixmapFormat.ARGB4444);     
        Assets.buttons = g.newPixmap("buttons.png", PixmapFormat.ARGB4444);
        Assets.help1 = g.newPixmap("help1.png", PixmapFormat.ARGB4444);
        Assets.help2 = g.newPixmap("help2.png", PixmapFormat.ARGB4444);
        Assets.help3 = g.newPixmap("help3.png", PixmapFormat.ARGB4444);
        Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
        Assets.ready = g.newPixmap("ready.png", PixmapFormat.ARGB4444);
        Assets.pause = g.newPixmap("pausemenu.png", PixmapFormat.ARGB4444);
        //Assets.gameOver = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
        //Assets.headUp = g.newPixmap("headup.png", PixmapFormat.ARGB4444);
        //Assets.headLeft = g.newPixmap("headleft.png", PixmapFormat.ARGB4444);
        //Assets.headDown = g.newPixmap("headdown.png", PixmapFormat.ARGB4444);
        //Assets.headRight = g.newPixmap("headright.png", PixmapFormat.ARGB4444);
        //Assets.tail = g.newPixmap("tail.png", PixmapFormat.ARGB4444);
        //Assets.stain1 = g.newPixmap("stain1.png", PixmapFormat.ARGB4444);
        //Assets.stain2 = g.newPixmap("stain2.png", PixmapFormat.ARGB4444);
        //Assets.stain3 = g.newPixmap("stain3.png", PixmapFormat.ARGB4444);
        /** Musique du jeu */
        Assets.intro = game.getAudio().newMusic("intro.ogg");
        Assets.highscore = game.getAudio().newMusic("highscore.ogg");
        Assets.gameover = game.getAudio().newMusic("gameover.ogg");
        Assets.level1 = game.getAudio().newMusic("level1.ogg");
        /** Bruitage et son dans le jeu */
        Assets.bonus = game.getAudio().newSound("bonus.ogg"); //TODO: A activer en cas de bonus : diamands, vies supp, etc...
        Assets.superbonus = game.getAudio().newSound("superbonus.ogg"); //TODO: A activer en cas de super bonus
        Assets.dead = game.getAudio().newSound("dead.ogg"); //TODO: A activer en cas de vie en moins
        Assets.shield = game.getAudio().newSound("shield.ogg"); //TODO: A activer lorsque l'on gagne l'armure jusqu'a ce que l'on perde une vie
        Assets.dudeworm = game.getAudio().newSound("dudeworm.ogg"); //TODO: A activer quand le perso se tranforme en ver nu :)
        Assets.less5seconds = game.getAudio().newSound("less5seconds.ogg"); //TODO: Plus que 5 secondes avant de terminer le niveau !!	
        Assets.click = game.getAudio().newSound("click.ogg");
        Assets.eat = game.getAudio().newSound("eat.ogg");
        Assets.bitten = game.getAudio().newSound("bitten.ogg");
        
        Settings.load(game.getFileIO());
        game.setScreen(new MainMenuScreen(game));
    }
    
    public void present(float deltaTime) {

    }

    public void pause() {

    }

    public void resume() {

    }

    public void dispose() {

    }
}
