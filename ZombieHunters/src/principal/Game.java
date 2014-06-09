/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

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
        intro.play();

        Time tempoTotal = new Time(0, 0, 10, 0, 0, false);
        Time tempoAtual = new Time(0, 0, 1, 0, 0, false);

        int pt = 0;

        while (!tec.keyDown(Keyboard.ESCAPE_KEY) && !tempoTotal.timeEnded()) {

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

                String apelido = JOptionPane.showInputDialog("Digite seu apelido sem espaço: ");
                new Ranking(apelido, pt, "lista.txt");
                JOptionPane.showMessageDialog(null, "Sua pontuação: " + String.valueOf(pt));
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

}
