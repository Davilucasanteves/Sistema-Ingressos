package model;

import java.io.Serializable;

public class Compra implements Serializable {
    private int id;
    private int festId;
    private String festaNome;
    private int quantidade;
    private double valorTotal;
    private String tipo;

    public Compra(int festId, String festaNome, int quantidade, double valorTotal, String tipo) {
        this.festId = festId;
        this.festaNome = festaNome;
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFestId() {
        return festId;
    }

    public void setFestId(int festId) {
        this.festId = festId;
    }

    public String getFestaNome() {
        return festaNome;
    }

    public void setFestaNome(String festaNome) {
        this.festaNome = festaNome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
