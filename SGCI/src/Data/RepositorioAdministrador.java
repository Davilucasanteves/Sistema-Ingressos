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

public class RepositorioAdministrador {
  
    private final String arquivo= "administrador.ser";

    public RepositorioAdministrador() {
        ArrayList<Administrador> listaVaziaDeAdministradores = new ArrayList<>();
        File arquivoAdministradores = new File(arquivo);

        if (!arquivoAdministradores.exists()) {
            try {

                arquivoAdministradores.createNewFile();

                try (ObjectOutputStream escritorDeDados = new ObjectOutputStream(
                        new FileOutputStream(arquivo))) {

                    escritorDeDados.writeObject(listaVaziaDeAdministradores);
                }

            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de administradores!" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Administrador> getAllAdministradores() {
        ArrayList<Administrador> administradores = new ArrayList<>();
        try (ObjectInputStream leitorDeObjetos = new ObjectInputStream(new FileInputStream(arquivo))) {

            administradores = (ArrayList<Administrador>) leitorDeObjetos.readObject();

        } catch (Exception e) {
            System.err.println("Erro no metodo getAllAdministradores: " + e.getMessage());
            e.printStackTrace();
        }

        return administradores;
    }
    
    private void atualizarArquivoAdministradores(ArrayList<Administrador> administradores) {

        try (ObjectOutputStream escritorDeObjetos = new ObjectOutputStream(new FileOutputStream(arquivo))) {

            escritorDeObjetos.writeObject(administradores);

        } catch (Exception e) {
            System.err.println("Erro no método atualizarArquivoAdministradores " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createAdministrador(Administrador cliente) {
        ArrayList<Administrador> administradores = getAllAdministradores();
        administradores.add(cliente);
        atualizarArquivoAdministradores(administradores);
    }
    
    public boolean cpfDisponivel(String cpf) {
        for (Administrador a : getAllAdministradores()) {
            if (a.getCpf().equalsIgnoreCase(cpf)) {
                return false;
            }
        }
        return true;
    }

}
