package model;

public class Inteira extends Ingresso {
    
    public Inteira(double valor, int quantidade, String tipo) {
        super(valor, quantidade, tipo);
        super.setTipo("Inteira");
    }

    @Override
    public double calcularTotal() {
        return getValor() * getQuantidade();
    }
}
