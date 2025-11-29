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

public class RepositorioCliente {
  
    private final String arquivo= "cliente.ser";

    public RepositorioCliente() {
        ArrayList<Cliente> listaVaziaDeClientes = new ArrayList<>();
        File arquivoClientes = new File(arquivo);

        if (!arquivoClientes.exists()) {
            try {

                arquivoClientes.createNewFile();

                try (ObjectOutputStream escritorDeDados = new ObjectOutputStream(
                        new FileOutputStream(arquivo))) {

                    escritorDeDados.writeObject(listaVaziaDeClientes);
                }

            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de clientes!" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Cliente> getAllClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (ObjectInputStream leitorDeObjetos = new ObjectInputStream(new FileInputStream(arquivo))) {

            clientes = (ArrayList<Cliente>) leitorDeObjetos.readObject();

        } catch (Exception e) {
            System.err.println("Erro no metodo getAllClientes: " + e.getMessage());
            e.printStackTrace();
        }

        return clientes;
    }
    
    private void atualizarArquivoClientes(ArrayList<Cliente> clientes) {

        try (ObjectOutputStream escritorDeObjetos = new ObjectOutputStream(new FileOutputStream(arquivo))) {

            escritorDeObjetos.writeObject(clientes);

        } catch (Exception e) {
            System.err.println("Erro no método atualizarArquivoClientes " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createCliente(Cliente cliente) {
        ArrayList<Cliente> clientes = getAllClientes();
        clientes.add(cliente);
        atualizarArquivoClientes(clientes);
    }

    public boolean cpfDisponivel(String cpf) {
        for (Cliente c : getAllClientes()) {
            if (c.getCpf().equalsIgnoreCase(cpf)) {
                return false;
            }
        }
        return true;
    }
}
