/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Color;
import java.io.File;
import java.util.Scanner;
import javax.swing.JFrame;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.Sound;
import jplay.Window;

/**
 *
 * @author Dalton
 */
class MenuRanking {

    public MenuRanking() {

        String plr1 = "";
        String plr2 = "";
        String plr3 = "";
        String plr4 = "";
        String plr5 = "";
        
        String pt1 = "";        
        String pt2 = "";        
        String pt3 = "";        
        String pt4 = "";        
        String pt5 = "";

        boolean ok = false;
        
        try {
            Scanner arq = new Scanner(new File("lista.txt"));

            plr1 = arq.next();
            pt1 = arq.next();
            plr2 = arq.next();
            pt2 = arq.next();
            plr3 = arq.next();
            pt3 = arq.next();
            plr4 = arq.next();
            pt4 = arq.next();
            plr5 = arq.next();
            pt5 = arq.next();

            ok = true;
            arq.close();

        } catch (Exception e) {
        }
        
        Menu mr = new Menu(800, 600, "menu.jpg");
        
        GameImage botaoExit = new GameImage("botao.png");
        botaoExit.y = 600 - botaoExit.height - 88.5;
        
        
        Sound intro = new Sound("suspense5.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();
                
        while (true){

            mr.fundo.draw();
            botaoExit.draw();

            if (ok) {
                String linha = "----------------------------------------------";
                
                mr.janela.drawText(linha, 50, 50, Color.RED);
                mr.janela.drawText("|JOGADOR", 50, 60, Color.RED);
                mr.janela.drawText(linha, 50, 70, Color.RED);
                mr.janela.drawText("|" + plr1, 50, 80, Color.RED);
                mr.janela.drawText(linha, 50, 90, Color.RED);
                mr.janela.drawText("|" + plr2, 50, 100, Color.RED);
                mr.janela.drawText(linha, 50, 110, Color.RED);
                mr.janela.drawText("|" + plr3, 50, 120, Color.RED);
                mr.janela.drawText(linha, 50, 130, Color.RED);
                mr.janela.drawText("|" + plr4, 50, 140, Color.RED);
                mr.janela.drawText(linha, 50, 150, Color.RED);
                mr.janela.drawText("|" + plr5, 50, 160, Color.RED);
                mr.janela.drawText(linha, 50, 170, Color.RED);
                
                mr.janela.drawText("PONTUAÇÃO  |", 150, 60, Color.RED);
                mr.janela.drawText("       " + pt1 + "                  |", 150, 80, Color.RED);
                mr.janela.drawText("       " + pt2 + "                  |", 150, 100, Color.RED);
                mr.janela.drawText("       " + pt3 + "                  |", 150, 120, Color.RED);
                mr.janela.drawText("       " + pt4 + "                  |", 150, 140, Color.RED);
                mr.janela.drawText("       " + pt5 + "                  |", 150, 160, Color.RED);
            }
            
            if((mr.mouse.isLeftButtonPressed() && mr.mouse.isOverObject(botaoExit)) || mr.tec.keyDown(Keyboard.ESCAPE_KEY)){
                
                intro.stop();
                mr.janela.delay(500);
                mr.janela.setVisible(false);
                new MenuPrincipal();
                
            }
            
            mr.janela.update();
        }

    }

}
