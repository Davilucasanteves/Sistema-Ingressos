package model;

public class Festa {
    private Ingresso ingresso;
    private String nome, data, descricao;
    private int id;

    public Festa(String nome, String data, String descricao, int id){
        this.nome=nome;
        this.data=data;
        this.descricao=descricao;
        this.id=id;
    }

    public int getIngressosDisponiveis(){
        return ingresso.getQuantidade();
    }

    public String getNome(){
        return nome;
    }

    public int getId(){
        return id;
    }

    public Ingresso getIngresso(){
        return ingresso;
    }

    public void setId(int id){
        this.id=id;
    }
}
