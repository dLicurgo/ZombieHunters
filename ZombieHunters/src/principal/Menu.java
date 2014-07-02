/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package principal;

import jplay.*;

/**
 *
 * @author Dalton
 */
public class Menu {
    
    protected Window janela;
    protected Mouse mouse;
    protected Keyboard tec;
    protected GameImage fundo;
    
    public Menu(int width, int height, String imagem){
        
        janela = new Window(width, height);
        janela.setCursor(janela.createCustomCursor("images\\mira.png"));
        
        mouse = janela.getMouse();
        
        tec = janela.getKeyboard();
        
        fundo = new GameImage(imagem);
        fundo.width = janela.getWidth();
        fundo.height = janela.getHeight();
    }
}