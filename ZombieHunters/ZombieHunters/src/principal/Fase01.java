/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import javax.swing.JOptionPane;
import jplay.*;

/**
 *
 * @author Elena
 */
public class Fase01 {

    private Window janela;
    private Mouse mouse;
    private Keyboard tec;
    private Font fonte, fonteMouse;
    private GameImage barra, fundo;
    private Animation explosao;
    private Sprite[] spritesDr, spritesProf, spritesEnf, spritesPol;
    private Sprite aleatorio;
    private Time tempoTotal, tempoAtual;
    private Sound intro;
    private int pontos;
    private String apelido;
    private boolean acertou, mostra, executa, avisoInserido;

    public Fase01(String ap, int pts) {

        janela = new Window(800, 600);
        janela.setCursor(janela.createCustomCursor("images\\mira.png"));

        mouse = janela.getMouse();

        tec = janela.getKeyboard();

        fonte = new Font("arial", Font.TRUETYPE_FONT, 24);
        fonteMouse = new Font("arial", Font.TRUETYPE_FONT, 12);

        barra = new GameImage("images\\barra.png");
        barra.width = janela.getWidth();

        fundo = new GameImage("images\\fundo06.jpg");
        fundo.width = janela.getWidth();
        fundo.height = janela.getHeight() + 75;

        explosao = new Animation("anims\\sangue01.png", 6);
        explosao.setTotalDuration(800);
        explosao.setLoop(false);

        spritesDr = new Sprite[5];
        spritesProf = new Sprite[5];
        spritesEnf = new Sprite[5];
        spritesPol = new Sprite[5];

        for (int i = 0; i < spritesDr.length; i++) {
            spritesDr[i] = new Sprite("images\\doutor0" + i + ".png");
            spritesEnf[i] = new Sprite("images\\enfermeira0" + i + ".png");
            spritesProf[i] = new Sprite("images\\professor0" + i + ".png");
            spritesPol[i] = new Sprite("images\\policial0" + i + ".png");

        }

        tempoTotal = new Time(0, 0, 59, 0, 0, false);
        tempoAtual = new Time(0, 0, 1, 0, 0, false);

        intro = new Sound("sounds\\suspense03.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        pontos = pts;
        apelido = ap;

        executa = true;
        acertou = false;
        mostra = true;
        avisoInserido = false;

        while (!tec.keyDown(Keyboard.ESCAPE_KEY) && executa) {

            fundo.draw();
            barra.draw();

            janela.drawText("Pontuação de " + apelido + ": " + Integer.toString(pontos), 20, 30, Color.RED, fonte);
            janela.drawText("Tempo: " + tempoTotal.toString(), 600, 30, Color.RED, fonte);

            if (mostra) {

                aleatorio = sorteiaEposiciona(spritesDr, spritesProf, spritesEnf, spritesPol);
                aleatorio.unhide();
                mostra = false;
            }

            aleatorio.draw();

            Point pos = mouse.getPosition();
            janela.drawText(pos.x + "    " + pos.y, pos.x + 30, pos.y + 50, Color.WHITE, fonteMouse);

            if (mouse.isLeftButtonPressed()) {

                new Sound("sounds\\tiro.wav").play();

                if (mouse.isOverObject(aleatorio)) {

                    new Sound("sounds\\zumbi01.wav").play();
                    acertou = true;
                    explosao.x = pos.x;
                    explosao.y = pos.y;
                    pontos++;
                }
            }
            if (tempoAtual.timeEnded()) {

                mostra = true;
                tempoAtual.setSecond(1);
            }
            if (acertou) {

                explosao.draw();
                explosao.update();

                if (!explosao.isPlaying()) {

                    explosao.stop();
                    explosao.play();
                    acertou = false;
                    mostra = true;
                }
            }

            int h = tempoTotal.getHour();
            int m = tempoTotal.getMinute();
            int s = tempoTotal.getSecond();

            janela.update();

            if (h == 0 && m == 0 && s == 0) {

                JOptionPane.showMessageDialog(null, "Você fez " + String.valueOf(pontos) + " pontos!");
                intro.stop();
                janela.setVisible(false);
                new Fase02(apelido, pontos);
            }

            if (!avisoInserido) {

                JOptionPane.showMessageDialog(null, "FASE 2");
                avisoInserido = true;
            }
        }
        intro.stop();
        janela.delay(500);
        janela.setVisible(false);
        new MenuPrincipal();
    }

    private static Sprite sorteiaEposiciona(Sprite[] sprtsDr, Sprite[] sprtsProf, Sprite[] sprtsEnf, Sprite[] sprtsPol) {

        for (int i = 0; i < sprtsPol.length; i++) {

            sprtsDr[i].hide();
            sprtsProf[i].hide();
            sprtsEnf[i].hide();
            sprtsPol[i].hide();

        }

        int i = (int) (Math.random() * 5);

        switch (i) {

            case 0:

                int j = (int) (Math.random() * 4);

                switch (j) {
                    
                    case 0:
                        
                        sprtsDr[0].x = 0; sprtsDr[0].y = 300;
                        sprtsProf[0].x = 0; sprtsProf[0].y = 300;
                        sprtsEnf[0].x = 0; sprtsEnf[0].y = 300;
                        sprtsPol[0].x = 0; sprtsPol[0].y = 300;
                        break;

                    case 1:
                        
                        sprtsDr[0].x = 200; sprtsDr[0].y = 300;
                        sprtsProf[0].x = 200; sprtsProf[0].y = 300;
                        sprtsEnf[0].x = 200; sprtsEnf[0].y = 300;
                        sprtsPol[0].x = 200; sprtsPol[0].y = 300;
                        break;

                    case 2:
                        
                        sprtsDr[0].x = 0; sprtsDr[0].y = 400;
                        sprtsProf[0].x = 0; sprtsProf[0].y = 400;
                        sprtsEnf[0].x = 0; sprtsEnf[0].y = 400;
                        sprtsPol[0].x = 0; sprtsPol[0].y = 400;
                        break;

                    default:
                        
                        sprtsDr[0].x = 0; sprtsDr[0].y = 600;
                        sprtsProf[0].x = 0; sprtsProf[0].y = 600;
                        sprtsEnf[0].x = 0; sprtsEnf[0].y = 600;
                        sprtsPol[0].x = 0; sprtsPol[0].y = 600;
                }

                int i0 = (int) (Math.random() * 4);
                
                switch (i0) {
                    
                    case 0:
                        return sprtsDr[0];
                    case 1:
                        return sprtsProf[0];
                    case 2:
                        return sprtsEnf[0];
                    default:
                        return sprtsPol[0];
                }

            case 1:
                
                int k = (int) (Math.random() * 2);
                
                switch (k) {
                    
                    case 0:
                        
                        sprtsDr[1].x = 400; sprtsDr[1].y = 360;
                        sprtsProf[1].x = 400; sprtsProf[1].y = 360;
                        sprtsEnf[1].x = 400; sprtsEnf[1].y = 360;
                        sprtsPol[1].x = 400; sprtsPol[1].y = 360;
                        break;
                    
                    default:
                        
                        sprtsDr[1].x = 580; sprtsDr[1].y = 360;
                        sprtsProf[1].x = 580; sprtsProf[1].y = 360;
                        sprtsEnf[1].x = 580; sprtsEnf[1].y = 360;
                        sprtsPol[1].x = 580; sprtsPol[1].y = 360;      
                }
                
                int i1 = (int) (Math.random() * 4);
                
                switch (i1) {
                    
                    case 0:
                        return sprtsDr[1];
                    case 1:
                        return sprtsProf[1];
                    case 2:
                        return sprtsEnf[1];
                    default:
                        return sprtsPol[1];
                }

            case 2:
                
                int l = (int) (Math.random() * 2);
                
                switch (l) {
                    
                    case 0:
                        
                        sprtsDr[2].x = 180; sprtsDr[2].y = 350;
                        sprtsProf[2].x = 180; sprtsProf[2].y = 350;
                        sprtsEnf[2].x = 180; sprtsEnf[2].y = 350;
                        sprtsPol[2].x = 180; sprtsPol[2].y = 350;
                        break;
                        
                    default:
                        
                        sprtsDr[2].x = 580; sprtsDr[2].y = 300;
                        sprtsProf[2].x = 580; sprtsProf[2].y = 300;
                        sprtsEnf[2].x = 580; sprtsEnf[2].y = 300;
                        sprtsPol[2].x = 580; sprtsPol[2].y = 300;   
                }
                
                int i2 = (int) (Math.random() * 4);
                
                switch (i2) {
                    
                    case 0:
                        return sprtsDr[2];
                    case 1:
                        return sprtsProf[2];
                    case 2:
                        return sprtsEnf[2];
                    default:
                        return sprtsPol[2];
                }

            case 3:
                
                sprtsDr[3].x = 530; sprtsDr[3].y = 300;
                sprtsProf[3].x = 530; sprtsProf[3].y = 300;
                sprtsEnf[3].x = 530; sprtsEnf[3].y = 300;
                sprtsPol[3].x = 530; sprtsPol[3].y = 300;
                
                int i3 = (int) (Math.random() * 4);
                
                switch (i3) {
                    
                    case 0:
                        return sprtsDr[3];
                    case 1:
                        return sprtsProf[3];
                    case 2:
                        return sprtsEnf[3];
                    default:
                        return sprtsPol[3];
                }
            
            default:
                
                sprtsDr[4].x = 280; sprtsDr[4].y = 250;
                sprtsProf[4].x = 280; sprtsProf[4].y = 250;
                sprtsEnf[4].x = 280; sprtsEnf[4].y = 250;
                sprtsPol[4].x = 280; sprtsPol[4].y = 250;
                
                int i4 = (int) (Math.random() * 4);
                
                switch (i4) {
                    
                    case 0:
                        return sprtsDr[4];
                    case 1:
                        return sprtsProf[4];
                    case 2:
                        return sprtsEnf[4];
                    default:
                        return sprtsPol[4];
                }
        }
    }
}