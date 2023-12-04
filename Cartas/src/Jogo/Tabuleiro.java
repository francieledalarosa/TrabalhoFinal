package Jogo;

import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private static final int NUM_LINHAS = 5;
    private static final int NUM_COLUNAS = 5;
    private List<List<Carta>> linhas;

    public Tabuleiro() {
        linhas = new ArrayList<>();
        for (int i = 0; i < NUM_LINHAS; i++) {
            linhas.add(new ArrayList<>());
        }
    }

    public void exibirTabuleiro() {
        for (List<Carta> linha : linhas) {
            for (Carta carta : linha) {
                if (carta == null) {
                    System.out.print("[    ] "); // Use espaços para manter o formato
                } else {
                    System.out.printf("[%3d] ", carta.getValor());
                }
            }
            System.out.println(); // Nova linha para a próxima linha do tabuleiro
        }
    }
    

    public void posicionarCarta(int linha, Carta carta) {
        linhas.get(linha).add(carta);
        // Ordenar a linha após a adição para manter a ordem crescente
        linhas.get(linha).sort((c1, c2) -> Integer.compare(c1.getValor(), c2.getValor()));
    }

    public List<List<Carta>> getLinhas() {
        return linhas;
    }
}

