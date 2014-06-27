/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

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
class Teste {

    private static int pt;

    public static void main(String[] args) {
        Window janela = new Window(800, 600);

        Animation explosao = new Animation("explosao.png", 20);
        explosao.setTotalDuration(1000);
        explosao.setLoop(false);

        Animation explosaoM = new Animation("explosao.png", 20);
        explosaoM.setTotalDuration(1000);
        explosaoM.setLoop(false);

        GameImage fundo = new GameImage("fundo.jpg");
        fundo.width = janela.getWidth();
        fundo.height = janela.getHeight();

        Keyboard tec = janela.getKeyboard();
        Mouse mouse = janela.getMouse();

        Sprite zumbi = new Sprite("gengar.gif");
        zumbi.x = sorteiaX(zumbi.width);
        zumbi.y = sorteiaY(zumbi.height);

        Sprite zumbiM = new Sprite("gengarMenor.gif");
        zumbiM.x = sorteiaMX(zumbiM.width);
        zumbiM.y = sorteiaMY(zumbiM.height);

        Time tempoAtual = new Time(0, 0, 1, 900, 700, false);
        Time tempoAtualM = new Time(0, 0, 1, 900, 700, false);
        
        boolean acertou = false;
        boolean acertouM = false;

        while (!tec.keyDown(Keyboard.ESCAPE_KEY)) {
            fundo.draw();
            zumbi.draw();
            zumbiM.draw();

            if (mouse.isLeftButtonPressed()) {
                new Sound("tiro.wav").play();
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
            

            

            janela.update();
        }

        janela.exit();
    }

    private static int sorteiaX(int width) {
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

    private static int sorteiaY(int height) {
        int i = (int) (Math.random() * 2);
        int pos;
        switch (i) {

            case 0:
                pos = 10;
                break;
            default:
                pos = 600 - height - 10;
        }
        return pos;
    }

    private static double sorteiaMX(int width) {
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

    private static double sorteiaMY(int height) {
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

}
