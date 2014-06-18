/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Formatter;
import java.util.Scanner;
import javax.swing.JOptionPane;
import jplay.*;

/**
 *
 * @author Dalton
 */
class Game {

    Window janela = new Window(800, 600);
    Font fonte = new Font("arial", Font.TRUETYPE_FONT, 24);

    GameImage fundogui = new GameImage("images\\fundogui.png");
    GameImage fundo = new GameImage("images\\fundo.jpg");

    Sprite zumbi = new Sprite("images\\gengar.gif");
    Sprite zumbiM = new Sprite("images\\gengarMenor.gif");

    Animation explosao = new Animation("anims\\explosao.png", 20);
    Animation explosaoM = explosao;

    Mouse mouse = janela.getMouse();
    Keyboard tec = janela.getKeyboard();

    Time tempoTotal = new Time(0, 0, 20, 0, 0, false);
    Time tempoAtual = new Time(0, 0, 1, 0, 0, false);
    Time tempoAtualM = new Time(0, 0, 1, 900, 700, false);

    Sound intro = new Sound("sounds\\suspense3.wav");

    int pt = 0;
    String apelido = "";

    public Game() {

        do {
            apelido += JOptionPane.showInputDialog("Digite seu apelido com no máximo 12 caracteres: ");
        } while (apelido.length() > 12);

        fundogui.width = janela.getWidth();

        fundo.width = janela.getWidth();
        fundo.height = janela.getHeight();

        zumbi.x = sorteiaX(zumbi.width);
        zumbi.y = sorteiaY(zumbi.height);

        zumbiM.x = sorteiaMX(zumbiM.width);
        zumbiM.y = sorteiaMY(zumbiM.height);

        explosao.setTotalDuration(800);
        explosao.setLoop(false);
        boolean acertou = false;

        explosaoM.setTotalDuration(500);
        explosaoM.setLoop(false);
        boolean acertouM = false;

        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        boolean executa = true;
        while (!tec.keyDown(Keyboard.ESCAPE_KEY) && executa) {

            fundo.draw();
            fundogui.draw();
            janela.drawText(apelido + " pontos: " + Integer.toString(pt), 20, 30, Color.BLACK, fonte);
            janela.drawText("Tempo: " + tempoTotal.toString(), 600, 30, Color.BLACK, fonte);
            zumbi.draw();
            zumbiM.draw();

            if (mouse.isLeftButtonPressed()) {
                new Sound("sounds\\tiro.wav").play();
                if (mouse.isOverObject(zumbiM)) {
                    acertouM = true;
                    zumbiM.hide();
                    explosaoM.x = zumbiM.x - (explosaoM.width - zumbiM.width) / 2;
                    explosaoM.y = zumbiM.y - (explosaoM.height - zumbiM.height) / 2;
                    pt += 5;
                } else if (mouse.isOverObject(zumbi)) {
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
                if (!explosao.isPlaying()) {
                    explosao.stop();
                    explosao.play();
                    acertou = false;
                    zumbi.unhide();
                }
            }
            if (acertouM) {
                explosaoM.draw();
                explosaoM.update();
                if (!explosaoM.isPlaying()) {
                    explosaoM.stop();
                    explosaoM.play();
                    acertouM = false;
                    zumbiM.unhide();
                }
            }
            if (tempoAtualM.timeEnded() || acertouM) {
                zumbiM.x = sorteiaMX(zumbiM.width);
                zumbiM.y = sorteiaMY(zumbiM.height);
                tempoAtualM.setSecond(1);
            }
            if (tempoAtual.timeEnded() || acertou) {
                zumbi.x = sorteiaX(zumbi.width);
                zumbi.y = sorteiaY(zumbi.height);
                tempoAtual.setSecond(1);
            }
            if (tempoTotal.timeEnded()) {
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

    private int sorteiaY(int height) {
        int i = (int) (Math.random() * 2);
        int pos;
        switch (i) {

            case 0:
                pos = (int) (fundogui.y + fundogui.height);
                break;
            default:
                pos = 600 - height - 10;
        }
        return pos;
    }

    private int sorteiaMX(int width) {
        int i = (int) (Math.random() * 4);
        int pos;
        switch (i) {

            case 0:
                pos = 150;
                break;
            case 1:
                pos = 800 - width - 150;
                break;
            default:
                pos = 800;
        }
        return pos;
    }

    private int sorteiaMY(int height) {
        int i = (int) (Math.random() * 4);
        int pos;
        switch (i) {

            case 0:
                pos = 150;
                break;
            case 1:
                pos = 600 - height - 150;
                break;
            default:
                pos = 600;
        }
        return pos;
    }

    private void atualizaRanking(String apelido, int pt) {
        try {
            Scanner origem = new Scanner(new File("docs\\lista.txt"));
            int total = Integer.parseInt(origem.nextLine());
            if (total == 0) {
                origem.close();
                total++;
                Formatter destino = new Formatter(new File("docs\\lista.txt"));
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
                Formatter destino = new Formatter(new File("docs\\lista.txt"));
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
                if (!inserido) {
                    destino.format("%s\n%d", apelido, pt);
                }
                destino.close();
            }
        } catch (Exception e) {
            System.out.println("Errado");
        }
    }
}
