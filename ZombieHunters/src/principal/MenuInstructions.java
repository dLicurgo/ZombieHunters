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
 * @author Marcelo Muniz
 */
public class MenuInstructions extends Menu {

    private Font fonteMouse;
    private GameImage instr, instrutext, botaoExit;
    private Sound intro;
    private int margem;

    public MenuInstructions() {

        super(800, 600, "images\\fundo11.jpg");

        fonteMouse = new Font("arial", Font.TRUETYPE_FONT, 12);

        instr = new GameImage("images\\instrucoes.png");
        instr.x = margem;
        instr.y = 40;

        instrutext = new GameImage("images\\instrutext.png");
        instrutext.y = 50;
        instrutext.x = margem;

        botaoExit = new GameImage("images\\voltar.png");
        botaoExit.y = 600 - botaoExit.height - 40;
        botaoExit.x = 800 - botaoExit.width;

        intro = new Sound("sounds\\suspense04.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        int margem = 20;

        do {
            
            fundo.draw();
            instrutext.draw();
            instr.draw();
            botaoExit.draw();

            Point pos = mouse.getPosition();
            janela.drawText(pos.x + "    " + pos.y, pos.x + 30, pos.y + 50, Color.WHITE, fonteMouse);

            if ((mouse.isLeftButtonPressed() && mouse.isOverObject(botaoExit))) {
                
                intro.stop();
                new Sound("sounds\\tiro.wav").play();
                janela.delay(100);
                janela.setVisible(false);
                new MenuPrincipal();
            }
            janela.update();
        } while (!tec.keyDown(Keyboard.ESCAPE_KEY));
        
        janela.exit();
    }
}