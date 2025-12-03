package model;

import java.io.Serializable;

public class Festa implements Serializable {

    private Ingresso ingresso;
    private String nome, data, descricao;
    private int id, qtdIngresso;
    private String adminCpf;

    public Festa(String nome, String data, String descricao, int qtdIngresso){
        this.nome = nome;
        this.data = data;
        this.descricao = descricao;
        this.qtdIngresso = qtdIngresso;
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

    public String getData(){
        return data;
    }

    public String getDescricao(){
        return descricao;
    }

    public int getQtdIngresso() {
        return qtdIngresso;
    }

    public Ingresso getIngresso(){
        return ingresso;
    }

    
    public String getAdminCpf() {
        return adminCpf;
    }

    
    public void setId(int id){
        this.id = id;
    }

    public void setIngresso(Ingresso ingresso){
        this.ingresso = ingresso;
    }

    public void setAdminCpf(String adminCpf) {
        this.adminCpf = adminCpf;
    }
}
