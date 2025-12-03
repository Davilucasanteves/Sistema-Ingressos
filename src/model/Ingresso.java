package model;

import java.io.Serializable;

public class Ingresso implements Serializable {

    private Festa festa;
    private double valor;
    private int quantidade;
    private String tipo;


    public Ingresso(Festa festa, double valor, int quantidade, String tipo) {
        this.festa=festa;
        this.valor=valor;
        this.quantidade=quantidade;
        this.tipo=tipo;
    }

    public double getValor() {
        return valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public String getTipo() {
        return tipo;
    }

    public Festa getFesta() {
        return festa;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double calcularTotal(){
        return valor*quantidade;
    }

    @Override
    public String toString() {
        return "Festa: "+ this.festa.getNome()
               +"\nvalor: " + this.getValor()
               + "\nquantidade: " + this.getQuantidade()
               + "\ntipo: " + this.getTipo();
    }
}