package model;

public class Festa {
    private int idFesta;
    private String local;
    private String data;
    private double preco;

    public Festa(int idFesta, String local, String data, double preco) {
        this.idFesta = idFesta;
        this.local = local;
        this.data = data;
        this.preco = preco;
    }

    public int getIdFesta() {
        return idFesta;
    }

    public void setIdFesta(int idFesta) {
        this.idFesta = idFesta;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
