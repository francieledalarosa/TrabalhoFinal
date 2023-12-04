package Jogo;

import java.util.ArrayList;
import java.util.List;

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
            if (tabuleiro.get(i).size() == NUM_COLUNAS) {
                // regra f.iii
                for (Carta c : tabuleiro.get(i)) {
                    int ultimaCarta = -1;
                    if (!tabuleiro.get(i).isEmpty() && tabuleiro.get(i).get(tabuleiro.get(i).size() - 1) != null) {
                        ultimaCarta = tabuleiro.get(i).get(tabuleiro.get(i).size() - 1).getValor();
                    }
                    cartas.add(ultimaCarta);
                }
                tabuleiro.set(i, new ArrayList<>());
            } else {
                int ultimaCarta = tabuleiro.get(i).isEmpty() ? -1 : tabuleiro.get(i).get(tabuleiro.get(i).size() - 1).getValor();
                if (carta > ultimaCarta && carta - ultimaCarta < diferencaMinima) {
                    linha = i;
                    coluna = tabuleiro.get(i).size();
                    diferencaMinima = carta - ultimaCarta;
                }
            }
        }

        if (linha == -1) {
            for (int i = 0; i < tabuleiro.size(); i++) {
                if (tabuleiro.get(i).size() == NUM_COLUNAS) {
                    for (Carta c : tabuleiro.get(i)) {
                        cartas.add(c.getValor());
                    }
                    tabuleiro.set(i, new ArrayList<>());
                } else {
                    int ultimaCarta = tabuleiro.get(i).isEmpty() ? -1 : tabuleiro.get(i).get(tabuleiro.get(i).size() - 1).getValor();
                    if (ultimaCarta - carta < diferencaMinima) {
                        linha = i;
                        coluna = 0;
                        diferencaMinima = ultimaCarta - carta;
                    }
                }
            }
        }

        if (linha == -1) {
            linha = getLinhaComMaiorUltimaCarta(tabuleiro);
            coluna = 0;
            for (Carta c : tabuleiro.get(linha)) {
                if (c != null) {
                    cartas.add(c.getValor());
                }
            }
            tabuleiro.set(linha, new ArrayList<>());
        }

        if (tabuleiro.get(linha).size() == 0) {
            tabuleiro.get(linha).add(coluna, new Carta(carta));
        } else {
            int insertIndex = coluna;
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

class JogadorComp implements java.util.Comparator<Jogador> {
    @Override
    public int compare(Jogador a, Jogador b) {
        return a.getCartaEscolhida() - b.getCartaEscolhida();
    }
}
