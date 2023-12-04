package Jogo;

public class Carta {
    private Integer valor;

    public Carta(Integer valor) {
        this.valor = valor;
    }

    public Integer getValor() {
        return valor;
    }

    public String toString() {
        if (valor == null) {
            return "[    ]";
        } else {
            return String.format("[%3d]", valor);
        }
    }
}
