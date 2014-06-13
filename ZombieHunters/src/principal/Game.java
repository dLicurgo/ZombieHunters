/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;
import javax.swing.JOptionPane;
import jplay.Animation;
import jplay.GameImage;
import jplay.Keyboard;
import jplay.Mouse;
import jplay.Sound;
import jplay.Sprite;
import jplay.Time;
import jplay.Window;

/**
 *
 * @author Dalton
 */
class Game {

    public Game() {

        Window janela = new Window(800, 600);

        GameImage fundo = new GameImage("fundo.jpg");
        fundo.width = janela.getWidth();
        fundo.height = janela.getHeight();

        Sprite zumbi = new Sprite("gengar.gif");
        zumbi.x = sorteiaX(zumbi.width);
        zumbi.y = sorteiaY(zumbi.height);

        Animation explosao = new Animation("explosao.png", 20);
        explosao.setTotalDuration(500);
        explosao.setLoop(false);
        boolean acertou = false;

        Mouse mouse = janela.getMouse();
        Keyboard tec = janela.getKeyboard();

        Sound intro = new Sound("suspense3.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        Time tempoTotal = new Time(0, 0, 20, 0, 0, false);
        Time tempoAtual = new Time(0, 0, 1, 0, 0, false);

        int pt = 0;
        boolean executa = true;
        while (!tec.keyDown(Keyboard.ESCAPE_KEY) && executa) {

            fundo.draw();
            zumbi.draw();

            if (mouse.isLeftButtonPressed()) {
                new Sound("tiro.wav").play();

                if (mouse.isOverObject(zumbi)) {
                    acertou = true;
                    zumbi.hide();
                    explosao.x = zumbi.x - (explosao.width - zumbi.width) / 2;
                    explosao.y = zumbi.y - (explosao.height - zumbi.height) / 2;
                    pt++;
                }
            }
            if (acertou) {
                explosao.draw();
                explosao.update();
            }

            if (!explosao.isPlaying()) {
                explosao.stop();
                explosao.play();
                acertou = false;
                zumbi.unhide();
            }
            if (tempoAtual.timeEnded() || acertou) {
                zumbi.x = sorteiaX(zumbi.width);
                zumbi.y = sorteiaY(zumbi.height);

                tempoAtual.setSecond(1);
            }

            if (tempoTotal.timeEnded()) {
                String apelido = "";
                do {
                    apelido += JOptionPane.showInputDialog("Digite seu apelido com no máximo 12 caracteres: ");
                } while (apelido.length() > 12);
                JOptionPane.showMessageDialog(null, "Você fez " + String.valueOf(pt) + " pontos!");
                atualizaRanking(apelido, pt);
                executa = false;

            }
            janela.update();

        }
        intro.stop();
        janela.delay(500);
        janela.setVisible(false);
        new MenuPrincipal();

    }

    private int sorteiaX(int width) {
        int i = (int) (Math.random() * 2);
        int pos;
        switch (i) {

            case 0:
                pos = 10;
                break;
            default:
                pos = 800 - width - 10;
        }
        return pos;
    }

    private int sorteiaY(int heigth) {
        int i = (int) (Math.random() * 2);
        int pos;
        switch (i) {

            case 0:
                pos = 10;
                break;
            default:
                pos = 600 - heigth - 10;
        }
        return pos;
    }

    private void atualizaRanking(String apelido, int pt) {
        try {
            Scanner origem = new Scanner(new File("lista.txt"));
            int total = Integer.parseInt(origem.nextLine());
            if (total == 0) {
                origem.close();
                total++;
                Formatter destino = new Formatter(new File("lista.txt"));
                destino.format("%d\n%s\n%d", total, apelido, pt);
                destino.close();
            } else {
                String[] nome = new String[total];
                int[] pontos = new int[total];
                for (int i = 0; i < total; i++) {
                    nome[i] = origem.nextLine();
                    pontos[i] = Integer.parseInt(origem.nextLine());
                }
                origem.close();
                Formatter destino = new Formatter(new File("lista.txt"));
                if (total < 5) {
                    int totalAtual = total + 1;
                    destino.format("%d\n", totalAtual);
                } else {
                    destino.format("%d\n", 5);
                }
                boolean inserido = false;
                for (int i = 0; i < total; i++) {
                    if (pontos[i] < pt && !inserido) {
                        destino.format("%s\n%d\n", apelido, pt);
                        inserido = true;
                    }
                    destino.format("%s\n", nome[i]);
                    destino.format("%d\n", pontos[i]);

                }
                if(!inserido){
                    destino.format("%s\n%d", apelido, pt);
                }
                destino.close();
            }
        } catch (Exception e) {
            System.out.println("Errado");
        }
    }

}
