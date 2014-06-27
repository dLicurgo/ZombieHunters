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
import java.util.Formatter;
import java.util.Scanner;
import javax.swing.JOptionPane;
import jplay.*;

/**
 *
 * @author Elena
 */
public class Fase02 {

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

    public Fase02(String ap, int pts) {

        janela = new Window(800, 600);
        janela.setCursor(janela.createCustomCursor("images\\mira.png"));

        mouse = janela.getMouse();

        tec = janela.getKeyboard();

        fonte = new Font("arial", Font.TRUETYPE_FONT, 24);
        fonteMouse = new Font("arial", Font.TRUETYPE_FONT, 12);

        barra = new GameImage("images\\barra.png");
        barra.width = janela.getWidth();

        fundo = new GameImage("images\\fundo07.jpg");
        fundo.width = janela.getWidth();
        fundo.height = janela.getHeight();

        explosao = new Animation("anims\\sangue01.png", 6);
        explosao.setTotalDuration(800);
        explosao.setLoop(false);

        spritesDr = new Sprite[6];
        spritesProf = new Sprite[6];
        spritesEnf = new Sprite[6];
        spritesPol = new Sprite[6];

        for (int i = 0; i < spritesDr.length; i++) {

            spritesDr[i] = new Sprite("images\\doutor0" + i + ".png");
            spritesEnf[i] = new Sprite("images\\enfermeira0" + i + ".png");
            spritesProf[i] = new Sprite("images\\professor0" + i + ".png");
            spritesPol[i] = new Sprite("images\\policial0" + i + ".png");

        }

        tempoTotal = new Time(0, 0, 59, 0, 0, false);
        tempoAtual = new Time(0, 0, 1, 0, 0, false);

        intro = new Sound("sounds\\suspense05.wav");
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
                atualizaRanking(apelido, pontos);
                executa = false;
            }

            if (!avisoInserido) {

                JOptionPane.showMessageDialog(null, "FASE 3");
                avisoInserido = true;
            }
        }

        intro.stop();
        janela.delay(500);
        janela.setVisible(false);
        new MenuPrincipal();
    }

    private void atualizaRanking(String apelido, int pts) {

        try {
            Scanner origem = new Scanner(new File("docs\\lista.txt"));
            int total = Integer.parseInt(origem.nextLine());

            if (total == 0) {

                origem.close();
                total++;
                Formatter destino = new Formatter(new File("docs\\lista.txt"));
                destino.format("%d\n%s\n%d", total, apelido, pts);
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

                    if (pontos[i] < pts && !inserido) {

                        destino.format("%s\n%d\n", apelido, pts);
                        inserido = true;
                    }

                    destino.format("%s\n", nome[i]);
                    destino.format("%d\n", pontos[i]);
                }
                if (!inserido) {

                    destino.format("%s\n%d", apelido, pts);
                }

                destino.close();
            }
        } catch (Exception e) {
            System.out.println("Erro de atualização de ranking");
        }
    }

    private static Sprite sorteiaEposiciona(Sprite[] sprtsDr, Sprite[] sprtsProf, Sprite[] sprtsEnf, Sprite[] sprtsPol) {

        for (int i = 0; i < sprtsPol.length; i++) {

            sprtsDr[i].hide();
            sprtsProf[i].hide();
            sprtsEnf[i].hide();
            sprtsPol[i].hide();

        }

        int i = (int) (Math.random() * 6);

        switch (i) {
            
            case 0:
                
                int j = (int) (Math.random() * 3);
                
                switch (j) {
                    
                    case 0:
                        
                        sprtsDr[0].x = 175; sprtsDr[0].y = 393;
                        sprtsProf[0].x = 175; sprtsProf[0].y = 393;
                        sprtsEnf[0].x = 175; sprtsEnf[0].y = 393;
                        sprtsPol[0].x = 175; sprtsPol[0].y = 393;
                        break;
                        
                    case 1:
                        
                        sprtsDr[0].x = -9; sprtsDr[0].y = 384;
                        sprtsProf[0].x = -9; sprtsProf[0].y = 384;
                        sprtsEnf[0].x = -9; sprtsEnf[0].y = 384;
                        sprtsPol[0].x = -9; sprtsPol[0].y = 384;
                        break;
                    
                    default:
                        
                        sprtsDr[0].x = 625; sprtsDr[0].y = 348;
                        sprtsProf[0].x = 625; sprtsProf[0].y = 348;
                        sprtsEnf[0].x = 625; sprtsEnf[0].y = 348;
                        sprtsPol[0].x = 625; sprtsPol[0].y = 348;        
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
                
                int k = (int) (Math.random() * 4);
                
                switch (k) {
                    
                    case 0:
                        
                        sprtsDr[1].x = 11; sprtsDr[1].y = 395;
                        sprtsProf[1].x = 11; sprtsProf[1].y = 395;
                        sprtsEnf[1].x = 11; sprtsEnf[1].y = 395;
                        sprtsPol[1].x = 11; sprtsPol[1].y = 395;
                        break;
                    
                    case 1:
                        
                        sprtsDr[1].x = 145; sprtsDr[1].y = 392;
                        sprtsProf[1].x = 145; sprtsProf[1].y = 392;
                        sprtsEnf[1].x = 145; sprtsEnf[1].y = 392;
                        sprtsPol[1].x = 145; sprtsPol[1].y = 392;
                        break;
                    
                    case 2:
                        
                        sprtsDr[1].x = 260; sprtsDr[1].y = 398;
                        sprtsProf[1].x = 260; sprtsProf[1].y = 398;
                        sprtsEnf[1].x = 260; sprtsEnf[1].y = 398;
                        sprtsPol[1].x = 260; sprtsPol[1].y = 398;
                        break;
                    
                    default:
                        
                        sprtsDr[1].x = 675; sprtsDr[1].y = 351;
                        sprtsProf[1].x = 675; sprtsProf[1].y = 351;
                        sprtsEnf[1].x = 675; sprtsEnf[1].y = 351;
                        sprtsPol[1].x = 675; sprtsPol[1].y = 351;        
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
                
                int l = (int) (Math.random() * 4);
                
                switch (l) {
                    
                    case 0:
                        
                        sprtsDr[2].x = 50; sprtsDr[2].y = 400;
                        sprtsProf[2].x = 50; sprtsProf[2].y = 400;
                        sprtsEnf[2].x = 50; sprtsEnf[2].y = 400;
                        sprtsPol[2].x = 50; sprtsPol[2].y = 400;
                        break;
                    
                    case 1:
                        
                        sprtsDr[2].x = 160; sprtsDr[2].y = 388;
                        sprtsProf[2].x = 160; sprtsProf[2].y = 388;
                        sprtsEnf[2].x = 160; sprtsEnf[2].y = 388;
                        sprtsPol[2].x = 160; sprtsPol[2].y = 388;
                        break;
                   
                    case 2:
                        
                        sprtsDr[2].x = 278; sprtsDr[2].y = 389;
                        sprtsProf[2].x = 278; sprtsProf[2].y = 389;
                        sprtsEnf[2].x = 278; sprtsEnf[2].y = 389;
                        sprtsPol[2].x = 278; sprtsPol[2].y = 389;
                        break;
                    
                    default:
                        
                        sprtsDr[2].x = 663; sprtsDr[2].y = 353;
                        sprtsProf[2].x = 663; sprtsProf[2].y = 353;
                        sprtsEnf[2].x = 663; sprtsEnf[2].y = 353;
                        sprtsPol[2].x = 663; sprtsPol[2].y = 353;       
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
                
                int m = (int) (Math.random() * 5);
                
                switch (m) {
                    
                    case 0:
                        
                        sprtsDr[3].x = 24; sprtsDr[3].y = 393;
                        sprtsProf[3].x = 24; sprtsProf[3].y = 393;
                        sprtsEnf[3].x = 24; sprtsEnf[3].y = 393;
                        sprtsPol[3].x = 24; sprtsPol[3].y = 393;
                        break;
                    
                    case 1:
                        
                        sprtsDr[3].x = 198; sprtsDr[3].y = 395;
                        sprtsProf[3].x = 198; sprtsProf[3].y = 395;
                        sprtsEnf[3].x = 198; sprtsEnf[3].y = 395;
                        sprtsPol[3].x = 198; sprtsPol[3].y = 395;
                        break;
                    
                    case 2:
                        
                        sprtsDr[3].x = 666; sprtsDr[3].y = 367;
                        sprtsProf[3].x = 666; sprtsProf[3].y = 367;
                        sprtsEnf[3].x = 666; sprtsEnf[3].y = 367;
                        sprtsPol[3].x = 666; sprtsPol[3].y = 367;
                        break;
                    
                    default:
                        
                        sprtsDr[3].x = 326; sprtsDr[3].y = 393;
                        sprtsProf[3].x = 326; sprtsProf[3].y = 393;
                        sprtsEnf[3].x = 326; sprtsEnf[3].y = 393;
                        sprtsPol[3].x = 326; sprtsPol[3].y = 393;      
                }

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
            
            case 4:
                
                int n = (int) (Math.random() * 8);
                
                switch (n) {
                    
                    case 0:
                        
                        sprtsDr[4].x = 107; sprtsDr[4].y = 237;
                        sprtsProf[4].x = 107; sprtsProf[4].y = 237;
                        sprtsEnf[4].x = 107; sprtsEnf[4].y = 237;
                        sprtsPol[4].x = 107; sprtsPol[4].y = 237;
                        break;
                    
                    case 1:
                        
                        sprtsDr[4].x = 156; sprtsDr[4].y = 230;
                        sprtsProf[4].x = 156; sprtsProf[4].y = 230;
                        sprtsEnf[4].x = 156; sprtsEnf[4].y = 230;
                        sprtsPol[4].x = 156; sprtsPol[4].y = 230;
                        break;
                    
                    case 2:
                        
                        sprtsDr[4].x = 211; sprtsDr[4].y = 243;
                        sprtsProf[4].x = 211; sprtsProf[4].y = 243;
                        sprtsEnf[4].x = 211; sprtsEnf[4].y = 243;
                        sprtsPol[4].x = 211; sprtsPol[4].y = 243;
                        break;
                    
                    case 3:
                        
                        sprtsDr[4].x = 374; sprtsDr[4].y = 363;
                        sprtsProf[4].x = 374; sprtsProf[4].y = 363;
                        sprtsEnf[4].x = 374; sprtsEnf[4].y = 363;
                        sprtsPol[4].x = 374; sprtsPol[4].y = 363;
                        break;
                    
                    case 4:
                        
                        sprtsDr[4].x = 24; sprtsDr[4].y = 403;
                        sprtsProf[4].x = 24; sprtsProf[4].y = 403;
                        sprtsEnf[4].x = 24; sprtsEnf[4].y = 403;
                        sprtsPol[4].x = 24; sprtsPol[4].y = 403;
                        break;
                    
                    case 5:
                        
                        sprtsDr[4].x = 267; sprtsDr[4].y = 382;
                        sprtsProf[4].x = 267; sprtsProf[4].y = 382;
                        sprtsEnf[4].x = 267; sprtsEnf[4].y = 382;
                        sprtsPol[4].x = 267; sprtsPol[4].y = 382;
                        break;
                    
                    case 6:
                        
                        sprtsDr[4].x = 330; sprtsDr[4].y = 388;
                        sprtsProf[4].x = 330; sprtsProf[4].y = 388;
                        sprtsEnf[4].x = 330; sprtsEnf[4].y = 388;
                        sprtsPol[4].x = 330; sprtsPol[4].y = 388;
                        break;
                    
                    default:
                        
                        sprtsDr[4].x = 516; sprtsDr[4].y = 386;
                        sprtsProf[4].x = 516; sprtsProf[4].y = 386;
                        sprtsEnf[4].x = 516; sprtsEnf[4].y = 386;
                        sprtsPol[4].x = 516; sprtsPol[4].y = 386;
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

            default:
                
                int o = (int) (Math.random() * 8);
                
                switch (o) {
                    
                    case 0:
                        
                        sprtsDr[5].x = 247; sprtsDr[5].y = 38;
                        sprtsProf[5].x = 247; sprtsProf[5].y = 38;
                        sprtsEnf[5].x = 247; sprtsEnf[5].y = 38;
                        sprtsPol[5].x = 247; sprtsPol[5].y = 38;
                        break;
                        
                    case 1:
                        
                        sprtsDr[5].x = 277; sprtsDr[5].y = 181;
                        sprtsProf[5].x = 277; sprtsProf[5].y = 181;
                        sprtsEnf[5].x = 277; sprtsEnf[5].y = 181;
                        sprtsPol[5].x = 277; sprtsPol[5].y = 181;
                        break;
                    
                    case 2:
                        
                        sprtsDr[5].x = 308; sprtsDr[5].y = 156;
                        sprtsProf[5].x = 308; sprtsProf[5].y = 156;
                        sprtsEnf[5].x = 308; sprtsEnf[5].y = 156;
                        sprtsPol[5].x = 308; sprtsPol[5].y = 156;
                        break;
                        
                    case 3:
                        
                        sprtsDr[5].x = 329; sprtsDr[5].y = 154;
                        sprtsProf[5].x = 329; sprtsProf[5].y = 154;
                        sprtsEnf[5].x = 329; sprtsEnf[5].y = 154;
                        sprtsPol[5].x = 329; sprtsPol[5].y = 154;
                        break;
                        
                    case 4:
                        
                        sprtsDr[5].x = 471; sprtsDr[5].y = 385;
                        sprtsProf[5].x = 471; sprtsProf[5].y = 385;
                        sprtsEnf[5].x = 471; sprtsEnf[5].y = 385;
                        sprtsPol[5].x = 471; sprtsPol[5].y = 385;
                        break;
                    
                    case 5:
                        
                        sprtsDr[5].x = 542; sprtsDr[5].y = 341;
                        sprtsProf[5].x = 542; sprtsProf[5].y = 341;
                        sprtsEnf[5].x = 542; sprtsEnf[5].y = 341;
                        sprtsPol[5].x = 542; sprtsPol[5].y = 341;
                        break;
                    
                    case 6:
                        
                        sprtsDr[5].x = 525; sprtsDr[5].y = 378;
                        sprtsProf[5].x = 525; sprtsProf[5].y = 378;
                        sprtsEnf[5].x = 525; sprtsEnf[5].y = 378;
                        sprtsPol[5].x = 525; sprtsPol[5].y = 378;
                        break;
                    
                    default:
                        
                        sprtsDr[5].x = 433; sprtsDr[5].y = 387;
                        sprtsProf[5].x = 433; sprtsProf[5].y = 387;
                        sprtsEnf[5].x = 433; sprtsEnf[5].y = 387;
                        sprtsPol[5].x = 433; sprtsPol[5].y = 387;     
                }

                int i5 = (int) (Math.random() * 4);
                
                switch (i5) {
                    
                    case 0:
                        return sprtsDr[5];
                    case 1:
                        return sprtsProf[5];
                    case 2:
                        return sprtsEnf[5];
                    default:
                        return sprtsPol[5];
                }
        }
    }
}