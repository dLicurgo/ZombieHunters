/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import jplay.*;

/**
 *
 * @author Dalton
 */
public class MenuPrincipal extends Menu {

    private Font fonteMouse;
    private GameImage titulo, botaoStart, botaoInstructions, botaoRanking, botaoQuit, credito;
    private Sound intro;
    private int margem;

    public MenuPrincipal() {

        super(800, 600, "images\\fundo00.jpg");

        fonteMouse = new Font("arial", Font.TRUETYPE_FONT, 12);

        titulo = new GameImage("images\\titulo.png");
        titulo.y = 50;
        titulo.x = margem;

        botaoStart = new GameImage("images\\start.png");
        botaoStart.y = 250;
        botaoStart.x = margem;

        botaoInstructions = new GameImage("images\\instructionsOpc.png");
        botaoInstructions.y = botaoStart.y + botaoStart.height;
        botaoInstructions.x = margem;

        botaoRanking = new GameImage("images\\ranking.png");
        botaoRanking.y = botaoInstructions.y + botaoInstructions.height;
        botaoRanking.x = margem;

        botaoQuit = new GameImage("images\\quit.png");
        botaoQuit.y = botaoRanking.y + botaoRanking.height;
        botaoQuit.x = margem;

        credito = new GameImage("images\\devs.png");
        credito.y = 350;
        credito.x = 300;

        intro = new Sound("sounds\\suspense01.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        margem = 20;

        while (!tec.keyDown(Keyboard.ESCAPE_KEY)) {

            fundo.draw();
            titulo.draw();
            botaoStart.draw();
            botaoInstructions.draw();
            botaoRanking.draw();
            botaoQuit.draw();
            credito.draw();

            Point pos = mouse.getPosition();
            janela.drawText(pos.x + "    " + pos.y, pos.x + 30, pos.y + 50, Color.WHITE, fonteMouse);

            if (mouse.isLeftButtonPressed()) {

                if (mouse.isOverObject(botaoStart)) {

                    intro.stop();
                    new Sound("sounds\\tiro.wav").play();
                    new Sound("sounds\\zumbi00.wav").play();
                    janela.delay(1500);
                    janela.setVisible(false);
                    new Fase00();

                } else if (mouse.isOverObject(botaoRanking)) {

                    intro.stop();
                    new Sound("sounds\\tiro.wav").play();
                    janela.delay(100);
                    janela.setVisible(false);
                    new MenuRanking();

                } else if (mouse.isOverObject(botaoInstructions)) {
                    
                    intro.stop();
                    new Sound("sounds\\tiro.wav").play();
                    janela.delay(100);
                    janela.setVisible(false);
                    new MenuInstructions();
                    
                } else if (mouse.isOverObject(botaoQuit)) {
                    
                    intro.stop();
                    new Sound("sounds\\tiro.wav").play();
                    new Sound("sounds\\zumbi01.wav").play();
                    janela.delay(1000);
                    janela.setVisible(false);
                    janela.exit();
                }
            }
            janela.update();
        }
        janela.exit();
    }
}