package Jogo;

import java.util.*;

public class Main {
    private static final int NUM_CARTAS = 109;
    private static final int NUM_LINHAS = 5;
    private static final int NUM_COLUNAS = 5;
    private static final int NUM_RODADAS = 12;
    private static List<Integer> baralho = new ArrayList<>();
    private static List<Jogador> jogadores = new ArrayList<>();
    private static List<List<Carta>> tabuleiro = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Passo 2: Inicializar o baralho
        for (int i = 1; i <= NUM_CARTAS; i++) {
            baralho.add(i);
        }
        Collections.shuffle(baralho);
        // Passo 3: Inicializar jogadores
        System.out.print("Digite o número de jogadores (3-6): ");
        int numJogadores = scanner.nextInt();
        scanner.nextLine();
        for (int i = 1; i <= numJogadores; i++) {
            System.out.print("Digite o nome do jogador " + i + ": ");
            String nome = scanner.nextLine();
            jogadores.add(new Jogador(nome));
        }
        // Passo 4: Inicializar tabuleiro
        for (int i = 0; i < NUM_LINHAS; i++) {
            tabuleiro.add(new ArrayList<Carta>());
            for (int j = 0; j < NUM_COLUNAS; j++) {
                tabuleiro.get(i).add(null);
            }
        }
        // Passo 5b: Distribuir cartas
        for (Jogador jogador : jogadores) {
            for (int i = 0; i < 12; i++) {
                jogador.adicionarCarta(baralho.remove(0));
            }
        }
        // Passo 5c: Colocar cartas iniciais no tabuleiro
        for (int i = 0; i < NUM_LINHAS; i++) {
            if (tabuleiro.get(i).get(0) == null) {
                tabuleiro.get(i).set(0, new Carta(baralho.remove(0)));
            }
        }
        System.out.println("Tabuleiro atual:");
        for (List<Carta> linha : tabuleiro) {
            System.out.println(linha);
        }
        // Passo 5: Jogar rodadas
        for (int r = 1; r <= NUM_RODADAS; r++) {
            System.out.println("Rodada " + r);
            System.out.println("----------");

            // Passo 5d: Escolher cartas
            for (Jogador jogador : jogadores) {
                System.out.println("Vez de " + jogador.getNome() + ".");
                System.out.println("Sua mão: " + jogador.getMao());
                int carta;
                do{ System.out.print("Digite a carta a ser jogada: ");
                    carta = scanner.nextInt();
                    scanner.nextLine();
                    if (!jogador.validarCartaEscolhida(carta)) {
                        System.out.println("Carta escolhida não está na mão do jogador. Escolha novamente.");
                    }
                }while (!jogador.validarCartaEscolhida(carta));
                jogador.setCartaEscolhida(carta);
            }
            // Passo 5e: 
            Collections.sort(jogadores, new JogadorComp());
            for (Jogador jogador : jogadores) {
                System.out.println(jogador.getNome() + ": " + jogador.getCartaEscolhida());
            }
            //Passo 5f:
            for (Jogador jogador : jogadores) {
                jogador.jogarCarta(jogador.getCartaEscolhida(), tabuleiro);
            }
            // Passo 5g: Mostrar tabuleiro e pontos
            System.out.println("Tabuleiro atual:");
            for (List<Carta> linha : tabuleiro) {
                System.out.println(linha);
            }
            for (Jogador jogador : jogadores) {
                System.out.println("Pontuação de " + jogador.getNome() + ": " + jogador.getPontuacao());
            }
            System.out.println();
        }
        // Passo 5h-i: Calcular vencedor
        Jogador vencedor = null;
        for (Jogador jogador : jogadores) {
            if (vencedor == null || jogador.getPontuacao() < vencedor.getPontuacao()) {
                vencedor = jogador;
            }
        }
        System.out.println("Fim do jogo!");
        System.out.println("Pontuações finais:");
        for (Jogador jogador : jogadores) {
            System.out.println("Pontuação de " + jogador.getNome() + ": " + jogador.getPontuacao());
            System.out.println("Cartas de " + jogador.getNome() + ": " + jogador.getCartas());
        }
        System.out.println("Vencedor: " + vencedor.getNome());
        scanner.close();
    }
}