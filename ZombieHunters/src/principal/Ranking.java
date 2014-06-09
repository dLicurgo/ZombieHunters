package principal;

import java.io.File;
import java.util.Formatter;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Dalton
 */
public class Ranking {

    public Ranking(String jogador, int pontos, String arquivo) {

        try {
            Scanner arq = new Scanner(new File(arquivo));

            if (!arq.hasNext()) {
                arq.close();
                Formatter backup = new Formatter(new File(arquivo));
                backup.format("%s\n%d", jogador, pontos);
                backup.close();
            } else {
                Formatter backup = new Formatter(new File("backup.txt"));
                while (arq.hasNext()) {
                    backup.format("%s\n%d\n", arq.next(), arq.nextInt());
                }
                backup.close();
                arq.close();

                arq = new Scanner(new File("backup.txt"));
                backup = new Formatter(new File("lista.txt"));

                boolean inserido = false;
                while (arq.hasNext()) {
                    String nomeAtual = arq.next();
                    int pontoAtual = arq.nextInt();

                    if (pontoAtual < pontos && !inserido) {
                        if (!arq.hasNext()) {

                            backup.format("%s\n%d", jogador, pontos);
                        } else {
                            backup.format("%s\n%d\n", jogador, pontos);
                        }
                        inserido = true;
                    }
                    if (!arq.hasNext()) {
                        backup.format("%s\n%d", nomeAtual, pontoAtual);
                    } else{
                        backup.format("%s\n%d\n", nomeAtual, pontoAtual);
                    }
                }

                if (!inserido) {
                    backup.format("%s\n%d", jogador, pontos);
                }

                arq.close();
                backup.close();

            }

        } catch (Exception e) {
        }
    }

}
