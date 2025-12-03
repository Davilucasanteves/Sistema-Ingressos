package model;

public class Inteira extends Ingresso {
    
    public Inteira(Festa festa, double valor, int quantidade, String tipo) {
        super(festa, valor, quantidade, tipo);
        super.setTipo("Inteira");
    }

    @Override
    public double calcularTotal() {
        return getValor() * getQuantidade();
    }
}
