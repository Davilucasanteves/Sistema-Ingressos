package model;

public class Meia extends Ingresso {
    private static final double DESCONTO = 0.50;

    public Meia(Festa festa, double valor, int quantidade, String tipo) {
        super(festa, valor, quantidade, tipo);
        super.setTipo("Meia");
    }

    public double getDesconto() {
        return DESCONTO;
    }

    @Override
    public double calcularTotal() {
        double precoComDesconto = getValor() * (1 - DESCONTO);
        return precoComDesconto * getQuantidade();
    }
}
