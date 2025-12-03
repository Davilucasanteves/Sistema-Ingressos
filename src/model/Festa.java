package model;

import java.io.Serializable;

public class Festa implements Serializable {
<<<<<<< HEAD

    private Ingresso ingresso;
    private String nome, data, descricao;
    private int id, qtdIngresso;
    private String adminCpf;

    public Festa(String nome, String data, String descricao, int qtdIngresso){
        this.nome = nome;
        this.data = data;
        this.descricao = descricao;
        this.qtdIngresso = qtdIngresso;
=======
    private Ingresso ingresso= new Ingresso(1,1,"n/a");
    private String nome, data, descricao;
    private int id;

    public Festa(String nome, String data, String descricao){
        this.nome=nome;
        this.data=data;
        this.descricao=descricao;
        //this.qtdIngresso= qtdIngresso;

    }

    
    public int getQuantidade(){
        if (ingresso != null) {
            return ingresso.getQuantidade();
        }
        return 0;
    }

    public double getValor(){
        if (ingresso != null) {
            return ingresso.getValor();
        }
        return 0;
    }

    public void setQuantidade(int quantidade){//setter pra a quantidade de ingressos do objeto ingresso que é do objeto festa
        if (ingresso != null) {
            ingresso.setQuantidade(quantidade);
        }
    }

    public void setValor(double valor){//setter pra o valor do ingresso do objeto ingresso que é do objeto festa
        if (ingresso != null) {
            ingresso.setValor(valor);
        }
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

<<<<<<< HEAD
    public int getQtdIngresso() {
        return qtdIngresso;
    }
=======
    // public int getQtdIngresso() {
    //     return qtdIngresso;
    // }
>>>>>>> 2c22673027db90c5f179d97deb226a881339a645

    public Ingresso getIngresso(){
        return ingresso;
    }

    
    public String getAdminCpf() {
        return adminCpf;
    }

    
    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome=nome;
    }

    public void setData(String data){
        this.data=data;
    }

    public void setDescricao(String descricao){
        this.descricao=descricao;
    }

    public void setIngresso(Ingresso ingresso){
        this.ingresso = ingresso;
    }

    public void setAdminCpf(String adminCpf) {
        this.adminCpf = adminCpf;
    }
}
