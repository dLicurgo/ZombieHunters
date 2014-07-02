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
 * @author Dalton
 */
public class Fase00 {

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
    private int pontos, maxLetras;
    private String apelido;
    private boolean acertou, mostra, executa, avisoInserido;

    public Fase00() {

        janela = new Window(800, 600);
        janela.setCursor(janela.createCustomCursor("images\\mira.png"));

        mouse = janela.getMouse();

        tec = janela.getKeyboard();

        fonte = new Font("arial", Font.TRUETYPE_FONT, 24);
        fonteMouse = new Font("arial", Font.TRUETYPE_FONT, 12);

        barra = new GameImage("images\\barra.png");
        barra.width = janela.getWidth();

        fundo = new GameImage("images\\fundo05.jpg");
        fundo.width = janela.getWidth();
        fundo.height = janela.getHeight();

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

        tempoAtual = new Time(0, 0, 2, 0, 0, false);
        tempoTotal = new Time(0, 0, 59, 0, 0, false);

        intro = new Sound("sounds\\suspense00.wav");
        intro.setRepeat(true);
        intro.setVolume(1);
        intro.play();

        pontos = 0;

        maxLetras = 12;

        apelido = "?";
        
        executa = true;
        acertou = false;
        mostra = true;
        avisoInserido = false;

        pontos = 0;

        do {

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
                tempoAtual.setSecond(2);
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

            if (h == 0 && m ==0 && s == 0) {
                
                JOptionPane.showMessageDialog(null, "Você fez " + String.valueOf(pontos) + " pontos!");
                intro.stop();
                janela.setVisible(false);
                new Fase01(apelido, pontos);
            }
            if (!avisoInserido) {
                
                JOptionPane.showMessageDialog(null, "FASE 1");
                avisoInserido = true;
                
                do {
                    
                    apelido = JOptionPane.showInputDialog("Digite um apelido");
                    if (apelido == null || apelido.isEmpty()) {
                        
                        apelido = "none";
                    }
                } while (apelido.length() > maxLetras);
                
                tempoTotal = new Time(0, 0, 59, 0, 0,false);
            }
        } while (!tec.keyDown(Keyboard.ESCAPE_KEY) && executa);
        
        intro.stop();
        janela.delay(500);
        janela.setVisible(false);
        new MenuPrincipal();
    }

    private static Sprite sorteiaEposiciona(Sprite[] sprtsDr, Sprite[] sprtsProf, Sprite[] sprtsEnf, Sprite[] sprtsPol) {

        for (int i = 0; i < sprtsDr.length; i++) {
            
            sprtsDr[i].hide();
            sprtsProf[i].hide();
            sprtsEnf[i].hide();
            sprtsPol[i].hide();
        }
        
        int i = (int) (Math.random() * 5);
        
        switch (i) {
            case 0:
                
                int j = (int) (Math.random() * 5);
                
                switch (j) {
                    
                    case 0:
                        
                        sprtsDr[0].x = -30; sprtsDr[0].y = 360;
                        sprtsProf[0].x = -30; sprtsProf[0].y = 360;
                        sprtsEnf[0].x = -30; sprtsEnf[0].y = 360;
                        sprtsPol[0].x = -30; sprtsPol[0].y = 360;
                        break;

                    case 1:
                        
                        sprtsDr[0].x = 130; sprtsDr[0].y = 360;
                        sprtsProf[0].x = 130; sprtsProf[0].y = 360;
                        sprtsEnf[0].x = 130; sprtsEnf[0].y = 360;
                        sprtsPol[0].x = 130; sprtsPol[0].y = 360;
                        break;

                    case 2:
                        
                        sprtsDr[0].x = 290; sprtsDr[0].y = 360;
                        sprtsProf[0].x = 290; sprtsProf[0].y = 360;
                        sprtsEnf[0].x = 290; sprtsEnf[0].y = 360;
                        sprtsPol[0].x = 290; sprtsPol[0].y = 360;
                        break;

                    case 3:
                        
                        sprtsDr[0].x = 450; sprtsDr[0].y = 360;
                        sprtsProf[0].x = 450; sprtsProf[0].y = 360;
                        sprtsEnf[0].x = 450; sprtsEnf[0].y = 360;
                        sprtsPol[0].x = 450; sprtsPol[0].y = 360;
                        break;
                        
                    default:
                        
                        sprtsDr[0].x = 610; sprtsDr[0].y = 360;
                        sprtsProf[0].x = 610; sprtsProf[0].y = 360;
                        sprtsEnf[0].x = 610; sprtsEnf[0].y = 360;
                        sprtsPol[0].x = 610; sprtsPol[0].y = 360;         
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
                
                int k = (int) (Math.random() * 3);
                
                switch (k) {
                    
                    case 0:
                        
                        sprtsDr[1].x = 160; sprtsDr[1].y = 380;
                        sprtsProf[1].x = 160; sprtsProf[1].y = 380;
                        sprtsEnf[1].x = 160; sprtsEnf[1].y = 380;
                        sprtsPol[1].x = 160; sprtsPol[1].y = 380;
                        break;
                        
                    case 1:
                        
                        sprtsDr[1].x = 320; sprtsDr[1].y = 360;
                        sprtsProf[1].x = 320; sprtsProf[1].y = 360;
                        sprtsEnf[1].x = 320; sprtsEnf[1].y = 360;
                        sprtsPol[1].x = 320; sprtsPol[1].y = 360;
                        break;
                        
                    default:
                        
                        sprtsDr[1].x = 670; sprtsDr[1].y = 360;
                        sprtsProf[1].x = 670; sprtsProf[1].y = 360;
                        sprtsEnf[1].x = 670; sprtsEnf[1].y = 360;
                        sprtsPol[1].x = 670; sprtsPol[1].y = 360;      
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
                
                sprtsDr[2].x = 200; sprtsDr[2].y = 250;
                sprtsProf[2].x = 200; sprtsProf[2].y = 250;
                sprtsEnf[2].x = 200; sprtsEnf[2].y = 250;
                sprtsPol[2].x = 200; sprtsPol[2].y = 250;
                
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
                
                sprtsDr[3].x = 440; sprtsDr[3].y = 300;
                sprtsProf[3].x = 440; sprtsProf[3].y = 300;
                sprtsEnf[3].x = 440; sprtsEnf[3].y = 300;
                sprtsPol[3].x = 440; sprtsPol[3].y = 300;                

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
                
                int l = (int) (Math.random() * 3);
                
                switch (l) {
                    
                    case 0:
                        
                        sprtsDr[4].x = 230; sprtsDr[4].y = 50;
                        sprtsProf[4].x = 230; sprtsProf[4].y = 50;
                        sprtsEnf[4].x = 230; sprtsEnf[4].y = 50;
                        sprtsPol[4].x = 230; sprtsPol[4].y = 50;
                        break;
                    
                    case 1:
                        
                        sprtsDr[4].x = 295; sprtsDr[4].y = 340;
                        sprtsProf[4].x = 295; sprtsProf[4].y = 340;
                        sprtsEnf[4].x = 295; sprtsEnf[4].y = 340;
                        sprtsPol[4].x = 295; sprtsPol[4].y = 340;
                        break;
                    
                    default:
                        
                        sprtsDr[4].x = 390; sprtsDr[4].y = 405;
                        sprtsProf[4].x = 390; sprtsProf[4].y = 405;
                        sprtsEnf[4].x = 390; sprtsEnf[4].y = 405;
                        sprtsPol[4].x = 390; sprtsPol[4].y = 405;        
                }
                
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