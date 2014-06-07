/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testes;

import jplay.Animation;
import jplay.Keyboard;
import jplay.Mouse;
import jplay.Sprite;
import jplay.Window;

/**
 *
 * @author Dalton
 */
public class Teste {

    public static void main(String[] args) {
        Window janela = new Window(800, 600);

        Animation explosao = new Animation("explosao.png", 20);
        explosao.setTotalDuration(1000);
        explosao.setLoop(false);

        Keyboard tec = janela.getKeyboard();
        Mouse mouse = janela.getMouse();

        boolean resp = false;
        while (!tec.keyDown(Keyboard.ESCAPE_KEY)) {

            if(mouse.isLeftButtonPressed()){
                resp = true;
            }
            if(resp){
            explosao.draw();
            explosao.update();
            }
            
            if(!explosao.isPlaying()){
                explosao.stop();
                explosao.play();
                resp = false;
            }
            janela.update();
        }
        janela.exit();
    }
}
