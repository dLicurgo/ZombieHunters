/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Color;
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

    private String[] nomes = {"", "", "", "", ""};
    private String[] pontos = {"", "", "", "", ""};

    private int total;

    public MenuRanking() {

        try {
            Scanner origem = new Scanner(new File("lista.txt"));
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

        Menu mr = new Menu(800, 600, "menu.jpg");

        GameImage botaoExit = new GameImage("botao.png");
        botaoExit.x = 50;
        botaoExit.y = 600 - botaoExit.height - 88.5;

        Sound intro = new Sound("suspense5.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        while (true) {

            mr.fundo.draw();
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

        mr.janela.drawText(linhas, 50, 50, Color.yellow);
        mr.janela.drawText("|Jogador", 50, 60, Color.yellow);
        mr.janela.drawText("|Pontos", 175, 60, Color.yellow);
        mr.janela.drawText("|", 300, 60, Color.yellow);
        mr.janela.drawText(linhas, 50, 70, Color.yellow);

        mr.janela.drawText("|" + nomes[0], 50, 80, Color.yellow);
        mr.janela.drawText("|" + pontos[0], 175, 80, Color.yellow);
        mr.janela.drawText("|", 300, 80, Color.yellow);
        mr.janela.drawText(linhas, 50, 90, Color.yellow);

        mr.janela.drawText("|" + nomes[1], 50, 100, Color.yellow);
        mr.janela.drawText("|" + pontos[1], 175, 100, Color.yellow);
        mr.janela.drawText("|", 300, 100, Color.yellow);
        mr.janela.drawText(linhas, 50, 110, Color.yellow);

        mr.janela.drawText("|" + nomes[2], 50, 120, Color.yellow);
        mr.janela.drawText("|" + pontos[2], 175, 120, Color.yellow);
        mr.janela.drawText("|", 300, 120, Color.yellow);
        mr.janela.drawText(linhas, 50, 130, Color.yellow);

        mr.janela.drawText("|" + nomes[3], 50, 140, Color.yellow);
        mr.janela.drawText("|" + pontos[3], 175, 140, Color.yellow);
        mr.janela.drawText("|", 300, 140, Color.yellow);
        mr.janela.drawText(linhas, 50, 150, Color.yellow);

        mr.janela.drawText("|" + nomes[4], 50, 160, Color.yellow);
        mr.janela.drawText("|" + pontos[4], 175, 160, Color.yellow);
        mr.janela.drawText("|", 300, 160, Color.yellow);
        mr.janela.drawText(linhas, 50, 170, Color.yellow);

    }
}
