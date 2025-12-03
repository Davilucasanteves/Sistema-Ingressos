package model;
import java.io.Serializable;
/**
 *
 * @author mhsme
 */
public class Administrador extends Cadastro implements Serializable{

    private String nome;
    private int telefone;
    private String cpf;

    public Administrador(String nome, String cpf, int telefone, String email, String senha) {
        super(email, senha); 
        this.nome = nome;
        this.telefone = telefone;
        this.cpf = cpf;
    }
    public String getCpf() {
        return cpf;
    }
}
