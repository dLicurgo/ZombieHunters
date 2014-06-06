/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package zombiehunters;

import jplay.GameImage;
import jplay.Mouse;
import jplay.Sound;
import jplay.Window;

/**
 *
 * @author Dalton
 */
class Menu {
    
    public Menu() {
    
        Window janela = new Window(800, 600);
        GameImage menu = new GameImage("menu.jpg");
        GameImage start = new GameImage("botao.png");
        GameImage ranking = new GameImage("botao.png");
        GameImage quit = new GameImage("botao.png");
        
        Sound intro = new Sound("intro.wav");
        intro.setRepeat(true);
        intro.play();
        
        Mouse mouse = janela.getMouse();
        menu.width = janela.getWidth();
        menu.height = janela.getHeight();
        start.y = 100;
        ranking.y = start.y + start.height + 100;
        quit.y = janela.getHeight() - quit.height - 100;
        
        boolean end = false;
        while(!end){
            menu.draw();
            start.draw();
            ranking.draw();
            quit.draw();
            janela.update();
            
            if(mouse.isLeftButtonPressed()){
                if(mouse.isOverObject(start)){
                    new Game(janela);
                    intro.stop();
                    janela.setVisible(false);
                } else
                    if(mouse.isOverObject(ranking)){
                        new Ranking(janela);
                        
                    } else
                        if(mouse.isOverObject(quit)){
                            end = true;
                        }
            }
        }
        janela.exit();
        
    }
    
}
