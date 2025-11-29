package data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Festa;
import model.IFesta;

public class RepositorioFesta implements IFesta {

    private final String ARQUIVO_FestaS = "Festa.ser";
    private final String ARQUIVO_ID_FestaS = "idFesta.dat";

    public RepositorioFesta() {
        ArrayList<Festa> listaVaziaDeFestas = new ArrayList<>();
        File arquivoFestas = new File(ARQUIVO_FestaS);

        // Se o arquivo "Festa.ser" ainda não existir cria um novo e escreve uma
        // lista vazia
        if (!arquivoFestas.exists()) {
            try {

                arquivoFestas.createNewFile();

                try (ObjectOutputStream escritorDeDados = new ObjectOutputStream(new FileOutputStream(ARQUIVO_FestaS))){
                    escritorDeDados.writeObject(listaVaziaDeFestas);
                }

            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de Festas!" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Festa> getAllFestas() {
        ArrayList<Festa> FestasCadastradas = new ArrayList<>();

        // Lê e retorna a lista de Festas armazenada no arquivo
        try (ObjectInputStream leitorDeObjetos = new ObjectInputStream(new FileInputStream(ARQUIVO_FestaS))) {

            FestasCadastradas = (ArrayList<Festa>) leitorDeObjetos.readObject();

        } catch (Exception e) {
            System.err.println("Erro no metodo getAllFestas: " + e.getMessage());
            e.printStackTrace();
        }

        return FestasCadastradas;
    }

    private int gerarNovoIdParaFesta() {
        File arquivoDeIds = new File(ARQUIVO_ID_FestaS);
        int idInicial = 1;
        int novoId = 0;
        int ultimoIdCadastrado = 0;

        try {
            // Se o arquivo nao existir, cria um novo, grava e retorna o id inicial
            if (!arquivoDeIds.exists()) {
                arquivoDeIds.createNewFile();
                gravarNovoId(idInicial, arquivoDeIds);
                return idInicial;
            }

            // Se o arquivo existir, le o ultimo id, incrementa, grava e retorna seu valor
            ultimoIdCadastrado = lerUltimoIdCadastrado(arquivoDeIds);
            novoId = ultimoIdCadastrado + 1;

            gravarNovoId(novoId, arquivoDeIds);

        } catch (Exception e) {
            System.err.println("Erro no metodo gerarNovoIdParaFesta: " + e.getMessage());
            e.printStackTrace();
        }
        return novoId;
    }

    private int lerUltimoIdCadastrado(File arquivoDeIds) throws IOException {
        int ultimoIdCadastrado;

        try (DataInputStream leitorDeDados = new DataInputStream(new FileInputStream(arquivoDeIds))) {
            ultimoIdCadastrado = leitorDeDados.readInt();
        }

        return ultimoIdCadastrado;
    }

    private void gravarNovoId(int novoId, File arquivoDeIds) throws FileNotFoundException, IOException {

        try (DataOutputStream escritorDeDados = new DataOutputStream(new FileOutputStream(arquivoDeIds))) {

            escritorDeDados.writeInt(novoId);
        }
    }

    private void atualizarArquivoFesta(ArrayList<Festa> FestasCadastradas) {

        try (ObjectOutputStream escritorDeObjetos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_FestaS))) {

            escritorDeObjetos.writeObject(FestasCadastradas);

        } catch (Exception e) {
            System.err.println("Erro no método atualizarArquivoFesta: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void createFesta(Festa Festa) {
        Festa.setId(gerarNovoIdParaFesta());

        ArrayList<Festa> FestasCadastrados = getAllFestas();
        FestasCadastrados.add(Festa);

        atualizarArquivoFesta(FestasCadastrados);
    }

    @Override
    public Festa readFesta(String nome) {
        ArrayList<Festa> FestasCadastradas = getAllFestas();

        for (Festa f : FestasCadastradas) {
            if ( nome.equals(f.getNome())) {
                return f;
            }
        }

        return null;
    }

    @Override
    public Festa readFestaPorId(int id) {
        ArrayList<Festa> festasCadastradas = getAllFestas();

        for (Festa f : festasCadastradas) {
            if (id == f.getId()) {
                return f;
            }
        }

        return null;
    }

    // Metodo auxiliar para descobrir indice(posicao) de um objeto na lista
    private int getIndiceDoFesta(int id) {
        ArrayList<Festa> FestasCadastradas = getAllFestas();

        for (int i = 0; i < FestasCadastradas.size(); i++) {
            if (FestasCadastradas.get(i).getId() == id) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void updateFesta(Festa Festa) {
        ArrayList<Festa> FestasCadastradas = getAllFestas();
        int indiceDaFesta = getIndiceDoFesta(Festa.getId());

        if (indiceDaFesta == -1) {
            throw new RuntimeException("Festa não encontrada.");
        }

        FestasCadastradas.set(indiceDaFesta, Festa);
        atualizarArquivoFesta(FestasCadastradas);
    }

    @Override
    public void deleteFesta(Festa Festa) {
        ArrayList<Festa> FestasCadastradas = getAllFestas();
        int indiceDaFesta = this.getIndiceDoFesta(Festa.getId());

        if (indiceDaFesta == -1) {
            throw new RuntimeException("Festa não encontrada.");
        }

        FestasCadastradas.remove(indiceDaFesta);
        atualizarArquivoFesta(FestasCadastradas);
    }
}