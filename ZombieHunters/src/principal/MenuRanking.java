/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Scanner;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.Sound;

/**
 *
 * @author Dalton
 */
class MenuRanking {

    Font fonte = new Font("arial",Font.TRUETYPE_FONT,24);
    
    private String[] nomes = {"-", "-", "-", "-", "-"};
    private String[] pontos = {"-", "-", "-", "-", "-"};

    private int total;

    public MenuRanking() {

        try {
            Scanner origem = new Scanner(new File("docs\\lista.txt"));
            total = Integer.parseInt(origem.nextLine());
            for (int i = 0; i < total; i++) {

                nomes[i] = origem.nextLine();
                System.out.println(i + ":" + nomes[i]);
                
                pontos[i] = (origem.nextLine());
                System.out.println(i + ":" + pontos[i]);

            }
            origem.close();
        } catch (Exception e) {
            System.out.println("Erro");
        }

        Menu mr = new Menu(800, 600, "images\\fundo.png");
        
        int margem = 20;
        
        GameImage titulo = new GameImage("images\\ranking-title.png");
        titulo.y = 40;
        titulo.x = margem;
        
        GameImage botaoExit = new GameImage("images\\voltar.png");
        botaoExit.y = 600 - botaoExit.height - 88.5;
        botaoExit.x = margem;

        Sound intro = new Sound("sounds\\suspense5.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        while (true) {

            mr.fundo.draw();
            titulo.draw();
            botaoExit.draw();

            mostraRanking(mr, nomes, pontos);

            if ((mr.mouse.isLeftButtonPressed() && mr.mouse.isOverObject(botaoExit)) || mr.tec.keyDown(Keyboard.ESCAPE_KEY)) {

                intro.stop();
                mr.janela.delay(500);
                mr.janela.setVisible(false);
                new MenuPrincipal();

            }

            mr.janela.update();
        }

    }

    private void mostraRanking(Menu mr, String[] nomes, String[] pontos) {

        String linhas = "---------------------------------------------------------------";

        mr.janela.drawText("JOGADOR", 200, 200, Color.BLACK, fonte);
                mr.janela.drawText(nomes[0], 200, 240, Color.BLACK, fonte);
                mr.janela.drawText(nomes[1], 200, 270, Color.BLACK, fonte);
                mr.janela.drawText(nomes[2], 200, 300, Color.BLACK, fonte);
                mr.janela.drawText(nomes[3], 200, 330, Color.BLACK, fonte);
                mr.janela.drawText(nomes[4], 200, 360, Color.BLACK, fonte);
                
                mr.janela.drawText("PONTUAÇÃO  ", 350, 200, Color.BLACK, fonte);
                mr.janela.drawText("       " + pontos[0] + "                  ", 350, 240, Color.BLACK, fonte);
                mr.janela.drawText("       " + pontos[1] + "                  ", 350, 270, Color.BLACK, fonte);
                mr.janela.drawText("       " + pontos[2] + "                  ", 350, 300, Color.BLACK, fonte);
                mr.janela.drawText("       " + pontos[3] + "                  ", 350, 330, Color.BLACK, fonte);
                mr.janela.drawText("       " + pontos[4] + "                  ", 350, 360, Color.BLACK, fonte);

    }
}
