package model;

import java.io.Serializable;

public class Festa implements Serializable {
    private Ingresso ingresso;
    private String nome, data, descricao;
    private int id;

    public Festa(String nome, String data, String descricao, int id){
        this.nome=nome;
        this.data=data;
        this.descricao=descricao;
        this.id=id;
    }

    public int getQuantidade(){
        if (ingresso != null) {
            return ingresso.getQuantidade();
        }
        return 0;
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

    public void setIngresso(Ingresso ingresso){
        this.ingresso=ingresso;
    }
}
