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

        GameImage botaoStart = new GameImage("botao.png");
        botaoStart.y = 88.5;

        GameImage botaoOptions = new GameImage("botao.png");
        botaoOptions.y = botaoStart.y + botaoStart.height + 88.5;

        GameImage botaoQuit = new GameImage("botao.png");
        botaoQuit.y = botaoOptions.y + botaoOptions.height + 88.5;

        Sound intro = new Sound("intro.wav");
        intro.setRepeat(true);
        intro.play();

        Menu mp = new Menu(800, 600, "menu.jpg");

        while (!mp.tec.keyDown(Keyboard.ESCAPE_KEY)) {

            mp.fundo.draw();
            botaoStart.draw();
            botaoOptions.draw();
            botaoQuit.draw();
            mp.janela.update();

            if (mp.mouse.isLeftButtonPressed()) {

                if (mp.mouse.isOverObject(botaoStart)) {

                    intro.stop();
                    mp.janela.delay(500);
                    mp.janela.setVisible(false);
                    new Game();

                } else if (mp.mouse.isOverObject(botaoOptions)) {

                    intro.stop();
                    mp.janela.setVisible(false);
                    new MenuRanking();

                } else if (mp.mouse.isOverObject(botaoQuit)) {

                    mp.janela.exit();

                }
            }
        }
        mp.janela.exit();
    }
}
