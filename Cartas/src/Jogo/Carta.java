package Jogo;

class Carta {
    private int valor;

    public Carta(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public String toString() {
        return String.format("%3d", valor);
    }
}