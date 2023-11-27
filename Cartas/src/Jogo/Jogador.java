package Jogo;

import java.util.*;

class Jogador {
    private static final int NUM_COLUNAS = 5;
    private String nome;
    private List<Integer> mao = new ArrayList<>();
    private List<Integer> cartas = new ArrayList<>();
    private int cartaEscolhida;

    public Jogador(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public List<Integer> getMao() {
        return mao;
    }

    public List<Integer> getCartas() {
        return cartas;
    }
    
    public boolean validarCartaEscolhida(int carta) {
        return mao.contains(carta);
    }

    public int getCartaEscolhida() {
        return cartaEscolhida;
    }

    public void setCartaEscolhida(int cartaEscolhida) {
        this.cartaEscolhida = cartaEscolhida;
    }

    public int getPontuacao() {
        int pontuacao = 0;
        for (int carta : cartas) {
            pontuacao += getPontosDaCarta(carta);
        }
        return pontuacao;
    }

    public void adicionarCarta(int carta) {
        mao.add(carta);
    }

    public void jogarCarta(int carta, List<List<Carta>> tabuleiro) {
        mao.remove(Integer.valueOf(carta));
        int linha = -1;
        int coluna = -1;
        int diferencaMinima = Integer.MAX_VALUE;
        for (int i = 0; i < tabuleiro.size(); i++) {
            int ultimaCarta = tabuleiro.get(i).get(NUM_COLUNAS - 1).getValor();
            if (carta > ultimaCarta && carta - ultimaCarta < diferencaMinima
                    && tabuleiro.get(i).get(NUM_COLUNAS - 1) == null) {
                linha = i;
                coluna = NUM_COLUNAS - 1;
                diferencaMinima = carta - ultimaCarta;
            }
        }
        if (linha == -1) {
            for (int i = 0; i < tabuleiro.size(); i++) {
                int ultimaCarta = tabuleiro.get(i).get(NUM_COLUNAS - 1).getValor();
                if (ultimaCarta - carta < diferencaMinima && tabuleiro.get(i).get(NUM_COLUNAS - 1) == null) {
                    linha = i;
                    coluna = 0;
                    diferencaMinima = ultimaCarta - carta;
                }
            }
        }
        if (linha == -1) {
            linha = getLinhaComMaiorUltimaCarta(tabuleiro);
            coluna = 0;
            // Regra para coletar todas as cartas da linha
            for (Carta c : tabuleiro.get(linha)) {
                if (c != null) {
                    cartas.add(c.getValor());
                }
            }
            tabuleiro.set(linha, new ArrayList<Carta>());
        }
        // Adicionando a nova carta na posição apropriada
        if (tabuleiro.get(linha).size() == 0) {
            tabuleiro.get(linha).add(new Carta(carta));
        } else {
            int insertIndex = 0;
            for (int i = 0; i < tabuleiro.get(linha).size(); i++) {
                Carta currentCard = tabuleiro.get(linha).get(i);
                if (currentCard == null || currentCard.getValor() > carta) {
                    insertIndex = i;
                    break;
                }
            }
            tabuleiro.get(linha).add(insertIndex, new Carta(carta));
        }
    }

    private int getLinhaComMaiorUltimaCarta(List<List<Carta>> tabuleiro) {
        int maiorUltimaCarta = Integer.MIN_VALUE;
        int linhaComMaiorUltimaCarta = 0;
        for (int i = 0; i < tabuleiro.size(); i++) {
            int ultimaCarta = tabuleiro.get(i).get(NUM_COLUNAS - 1).getValor();
            if (ultimaCarta > maiorUltimaCarta) {
                maiorUltimaCarta = ultimaCarta;
                linhaComMaiorUltimaCarta = i;
            }
        }
        return linhaComMaiorUltimaCarta;
    }

    private int getPontosDaCarta(int carta) {
        int pontos = 1;
        if (carta % 10 == 5) {
            pontos++;
        }
        if (carta % 10 == carta / 10 % 10 && carta != 100 && carta != 200 && carta != 300) {
            pontos += 3;
        }
        if (carta % 10 == 0) {
            pontos += 2;
        }
        return pontos;
    }
}