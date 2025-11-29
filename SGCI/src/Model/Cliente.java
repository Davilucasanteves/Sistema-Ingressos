/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.io.Serializable;

/**
 *
 * @author mhsme
 */
public class Cliente extends Cadastro implements Serializable{
    private String nome;
    private int telefone;
    private String cpf;
    
    
    public Cliente(String nome, String cpf,  int telefone, String email, String senha) {
        super(email, senha);
        this.nome=nome;
        this.cpf=cpf;
        this.telefone= telefone;
    }
    public String getCpf() {
        return cpf;
    }
}
