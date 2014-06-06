/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zombiehunters;

import java.awt.Color;
import java.io.File;
import java.util.Scanner;
import jplay.GameImage;
import jplay.Mouse;
import jplay.Window;

/**
 *
 * @author Dalton
 */
class Ranking {

    public Ranking(Window pai) {

        Window janela = new Window(800, 600);
        GameImage fundo = new GameImage("menu.jpg");
        GameImage exit = new GameImage("botao.png");
        Mouse mouse = janela.getMouse();

        fundo.width = janela.getWidth();
        fundo.height = janela.getHeight();

        exit.y = 400;

        boolean ok = false;
        
        String nm1 = "";
        String nm2 = "";
        String nm3 = "";
        String nm4 = "";
        String nm5 = "";
        
        String pt1 = "";
        String pt2 = "";
        String pt3 = "";
        String pt4 = "";
        String pt5 = "";

        try {
            Scanner arq = new Scanner(new File("ranking.txt"));

            nm1 = arq.next();
            pt1 = arq.next();
            nm2 = arq.next();
            pt2 = arq.next();
            nm3 = arq.next();
            pt3 = arq.next();
            nm4 = arq.next();
            pt4 = arq.next();
            nm5 = arq.next();
            pt5 = arq.next();

            ok = true;
            arq.close();
        } catch (Exception e) {
        }

        boolean saiu = false;
        while (!saiu) {

            fundo.draw();
            exit.draw();

            if(ok){
            janela.drawText(nm1, 100, 100, Color.GREEN);
            janela.drawText(pt1, 200, 100, Color.GREEN);
            janela.drawText(nm2, 100, 120, Color.GREEN);
            janela.drawText(pt2, 200, 120, Color.GREEN);
            janela.drawText(nm3, 100, 140, Color.GREEN);
            janela.drawText(pt3, 200, 140, Color.GREEN);
            janela.drawText(nm4, 100, 160, Color.GREEN);
            janela.drawText(pt4, 200, 160, Color.GREEN);
            janela.drawText(nm5, 100, 180, Color.GREEN);
            janela.drawText(pt5, 200, 180, Color.GREEN);
            
            }
            janela.update();

            if (mouse.isLeftButtonPressed() && mouse.isOverObject(exit)) {
                saiu = true;
            }
        }
        janela.setVisible(false);
        pai.setVisible(true);

    }
}
