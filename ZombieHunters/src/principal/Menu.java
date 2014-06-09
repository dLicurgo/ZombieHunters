/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package principal;

import jplay.GameImage;
import jplay.Keyboard;
import jplay.Mouse;
import jplay.Window;

/**
 *
 * @author Dalton
 */
public class Menu {
    
    public Window janela;
    public Mouse mouse;
    public Keyboard tec;
    public GameImage fundo;
    
    public Menu(int width, int height, String imagem){
        
        this.janela = new Window(width, height);
        this.mouse = janela.getMouse();
        this.tec = janela.getKeyboard();
        this.fundo = new GameImage(imagem);
        
        this.fundo.width = janela.getWidth();
        this.fundo.height = janela.getHeight();
    }
    
}
