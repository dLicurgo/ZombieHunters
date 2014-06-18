/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import jplay.*;

/**
 *
 * @author Marcelo Muniz
 */
class MenuInstructions {

    public MenuInstructions() {
        
        int margem = 20;
        Menu mi = new Menu(800, 600, "images\\fundo.png");
        
        GameImage titulo = new GameImage("images\\instrucoes.png");
        titulo.x = margem;
        titulo.y = 40;

        GameImage instrutext = new GameImage("images\\instrutext.png");
        instrutext.y = 50;
        instrutext.x = 50;

        GameImage botaoExit = new GameImage("images\\voltar.png");
        botaoExit.y = 600 - botaoExit.height - 40;
        botaoExit.x = margem;

        Sound intro = new Sound("sounds\\suspense5.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        while (true) {
            mi.fundo.draw();
            instrutext.draw();
            titulo.draw();
            botaoExit.draw();
            
            if ((mi.mouse.isLeftButtonPressed() && mi.mouse.isOverObject(botaoExit)) || mi.tec.keyDown(Keyboard.ESCAPE_KEY)) {
                intro.stop();
                mi.janela.delay(500);
                mi.janela.setVisible(false);
                new MenuPrincipal();
            }
            mi.janela.update();
        }
    }
}