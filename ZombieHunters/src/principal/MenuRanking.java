/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.io.File;
import java.util.Scanner;
import jplay.*;

/**
 *
 * @author Dalton
 */
public class MenuRanking extends Menu {

    private Font fonte, fonteMouse;
    private GameImage titulo, botaoExit;
    private String[] nomes = {"-", "-", "-", "-", "-"};
    private String[] pontos = {"-", "-", "-", "-", "-"};
    private Sound intro;
    private int total, margem;

    public MenuRanking() {

        super(800, 600, "images\\fundo02.jpg");

        fonte = new Font("arial", Font.TRUETYPE_FONT, 24);
        fonteMouse = new Font("arial", Font.TRUETYPE_FONT, 12);

        margem = 20;
        
        titulo = new GameImage("images\\ranking-title.png");
        titulo.y = 40;
        titulo.x = margem;

        botaoExit = new GameImage("images\\voltar.png");
        botaoExit.y = 600 - botaoExit.height - 60;
        botaoExit.x = margem;

        intro = new Sound("sounds\\suspense06.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();
        
        try {
            Scanner origem = new Scanner(new File("docs\\lista.txt"));
            total = Integer.parseInt(origem.nextLine());
            
            for (int i = 0; i < total; i++) {
                nomes[i] = origem.nextLine();
                if (nomes[i] == null || nomes[i].isEmpty()) {
                    nomes[i] = "-";
                }
                pontos[i] = (origem.nextLine());
                if (pontos[i] == null || pontos[i].isEmpty()) {
                    pontos[i] = "-";
                }
            }
            
            origem.close();
        } catch (Exception e) {
            System.out.println("Erro em Menu Ranking");
        }

        do {

            fundo.draw();
            titulo.draw();
            botaoExit.draw();
            
            mostraRanking(nomes, pontos);

            Point pos = mouse.getPosition();
            janela.drawText(pos.x + "    " + pos.y, pos.x + 30, pos.y + 50, Color.RED, fonteMouse);

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

    private void mostraRanking(String[] nomes, String[] pontos) {

        janela.drawText("JOGADOR", 40, 230, Color.RED, fonte);
        janela.drawText("PONTUAÇÃO  ", 190, 230, Color.RED, fonte);

        for (int i = 0; i < nomes.length; i++) {

            janela.drawText(nomes[i], 40, 290 + 30 * i, Color.RED, fonte);
            janela.drawText(pontos[i], 190, 290 + 30 * i, Color.RED, fonte);
        }
    }
}