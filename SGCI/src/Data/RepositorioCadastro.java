/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import Model.*;

/**
 *
 * @author mhsme
 */

public class RepositorioCadastro {
   
    private final String arquivo= "cadastro.ser";

    public RepositorioCadastro() {
        ArrayList<Cadastro> listaVaziaDeCadastros = new ArrayList<>();
        File arquivoCadastros = new File(arquivo);

        if (!arquivoCadastros.exists()) {
            try {

                arquivoCadastros.createNewFile();

                try (ObjectOutputStream escritorDeDados = new ObjectOutputStream(
                        new FileOutputStream(arquivo))) {

                    escritorDeDados.writeObject(listaVaziaDeCadastros);
                }

            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de cadastros!" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Cadastro> getAllCadastros() {
        ArrayList<Cadastro> cadastros = new ArrayList<>();
        try (ObjectInputStream leitorDeObjetos = new ObjectInputStream(new FileInputStream(arquivo))) {

            cadastros = (ArrayList<Cadastro>) leitorDeObjetos.readObject();

        } catch (Exception e) {
            System.err.println("Erro no metodo getAllCadastros: " + e.getMessage());
            e.printStackTrace();
        }

        return cadastros;
    }
    
    private void atualizarArquivoCadastros(ArrayList<Cadastro> cadastros) {

        try (ObjectOutputStream escritorDeObjetos = new ObjectOutputStream(new FileOutputStream(arquivo))) {

            escritorDeObjetos.writeObject(cadastros);

        } catch (Exception e) {
            System.err.println("Erro no método atualizarArquivoCadastros " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createCadastro(Cadastro cadastro) {
        ArrayList<Cadastro> cadastros = getAllCadastros();
        cadastros.add(cadastro);

        atualizarArquivoCadastros(cadastros);
    }

    public Cadastro readCadastro(String email, String senha) {
        for (Cadastro c : getAllCadastros()) {
            if (c.getEmail().equalsIgnoreCase(email) && c.getSenha().equalsIgnoreCase(senha)) {
                return c;
            }
        }
        return null;
    }
    
    public boolean emailDisponivel(String email) {
        for (Cadastro c : getAllCadastros()) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return false;
            }
        }
        return true;
    }
}
