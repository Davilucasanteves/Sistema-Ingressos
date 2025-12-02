package model;

public class Meia extends Ingresso {
    private static final double DESCONTO = 0.50;

    public Meia(double valor, int quantidade, String tipo) {
        super(valor, quantidade, tipo);
        super.setTipo("MEIA");
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
