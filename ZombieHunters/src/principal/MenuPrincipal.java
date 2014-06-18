/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.Sound;

/**
 *
 * @author Dalton
 */
class MenuPrincipal {

    public MenuPrincipal() {

        int margem = 20;
        
        GameImage titulo = new GameImage("images\\titulo.png");
        titulo.y = 50;
        titulo.x = margem;
        
        GameImage botaoStart = new GameImage("images\\start.png");
        botaoStart.y = 250;
        botaoStart.x = margem;

        GameImage botaoInstructions = new GameImage("images\\instructionsOpc.png");
        botaoInstructions.y = botaoStart.y + botaoStart.height;
        botaoInstructions.x = margem;
        
        GameImage botaoRanking = new GameImage("images\\ranking.png");
        botaoRanking.y = botaoInstructions.y + botaoInstructions.height;
        botaoRanking.x = margem;
        
        GameImage botaoQuit = new GameImage("images\\quit.png");
        botaoQuit.y = botaoRanking.y + botaoRanking.height;
        botaoQuit.x = margem;
        
        GameImage credito = new GameImage("images\\devs.png");
        credito.y = 300;
        credito.x = 400;

        Sound intro = new Sound("sounds\\intro.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        Menu mp = new Menu(800, 600, "images\\fundo.png");

        while (!mp.tec.keyDown(Keyboard.ESCAPE_KEY)) {

            mp.fundo.draw();
            titulo.draw();
            botaoStart.draw();
            botaoInstructions.draw();
            botaoRanking.draw();
            botaoQuit.draw();
            credito.draw();
            mp.janela.update();

            if (mp.mouse.isLeftButtonPressed()) {
                if (mp.mouse.isOverObject(botaoStart)) {
                    intro.stop();
                    mp.janela.delay(500);
                    mp.janela.setVisible(false);
                    new Game();
                } else if (mp.mouse.isOverObject(botaoRanking)) {
                    intro.stop();
                    mp.janela.setVisible(false);
                    new MenuRanking();
                } else if (mp.mouse.isOverObject(botaoInstructions)) {
                    intro.stop();
                    mp.janela.setVisible(false);
                    new MenuInstructions();
                } else if (mp.mouse.isOverObject(botaoQuit)) {
                    mp.janela.exit();
                }
            }
        }
        mp.janela.exit();
    }
}
