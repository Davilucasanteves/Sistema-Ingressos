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
public class Cadastro implements Serializable{
    private String email;
    private String senha;
    
    public Cadastro(String email, String senha){
        this.email=email;
        this.senha=senha;
    }


    public String getEmail(){
        return email;
    }
    public String getSenha(){
        return senha;
    }
}

