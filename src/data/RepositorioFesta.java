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

    private final String ARQUIVO_Festas = "Festa.ser";
    private final String ARQUIVO_ID_Festas = "idFesta.dat";

    public RepositorioFesta() {
        ArrayList<Festa> listaVazia = new ArrayList<>();
        File arquivoFestas = new File(ARQUIVO_Festas);

        if (!arquivoFestas.exists()) {
            try {
                arquivoFestas.createNewFile();
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_Festas))){
                    out.writeObject(listaVazia);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ArrayList<Festa> getAllFestas() {
        ArrayList<Festa> festas = new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(ARQUIVO_Festas))) {
            festas = (ArrayList<Festa>) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return festas;
    }

    public ArrayList<Festa> getFestasPorAdmin(String cpfAdmin) {
        ArrayList<Festa> todas = getAllFestas();
        ArrayList<Festa> filtradas = new ArrayList<>();

        for (Festa f : todas) {
            if (f.getAdminCpf() != null && f.getAdminCpf().equals(cpfAdmin)) {
                filtradas.add(f);
            }
        }
        return filtradas;
    }

    private int gerarNovoIdParaFesta() {
        File arquivoDeIds = new File(ARQUIVO_ID_Festas);
        int idInicial = 1;
        int novoId = 0;
        int ultimoIdCadastrado = 0;

        try {
            if (!arquivoDeIds.exists()) {
                arquivoDeIds.createNewFile();
                gravarNovoId(idInicial, arquivoDeIds);
                return idInicial;
            }

            ultimoIdCadastrado = lerUltimoIdCadastrado(arquivoDeIds);
            novoId = ultimoIdCadastrado + 1;

            gravarNovoId(novoId, arquivoDeIds);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return novoId;
    }

    private int lerUltimoIdCadastrado(File arquivoDeIds) throws IOException {
        try (DataInputStream in = new DataInputStream(new FileInputStream(arquivoDeIds))) {
            return in.readInt();
        }
    }

    private void gravarNovoId(int novoId, File arquivoDeIds) throws FileNotFoundException, IOException {
        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(arquivoDeIds))) {
            out.writeInt(novoId);
        }
    }

    private void atualizarArquivoFesta(ArrayList<Festa> festas) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ARQUIVO_Festas))) {
            out.writeObject(festas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createFesta(Festa festa) {
        festa.setId(gerarNovoIdParaFesta());

        ArrayList<Festa> lista = getAllFestas();
        lista.add(festa);

        atualizarArquivoFesta(lista);
    }

    @Override
    public Festa readFesta(String nome) {
        ArrayList<Festa> lista = getAllFestas();

        for (Festa f : lista) {
            if (nome.equals(f.getNome())) {
                return f;
            }
        }
        return null;
    }

    @Override
    public Festa readFestaPorId(int id) {
        ArrayList<Festa> lista = getAllFestas();

        for (Festa f : lista) {
            if (id == f.getId()) {
                return f;
            }
        }
        return null;
    }

    public int getIndiceDoFesta(int id) {
        ArrayList<Festa> lista = getAllFestas();

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    public double getValorDoIngressoDaFesta(int id) {
        ArrayList<Festa> lista = getAllFestas();

        for (Festa f : lista) {
            if (f.getId() == id) {
                return f.getIngresso().getValor();
            }
        }
        return -1;
    }

    @Override
    public void updateFesta(Festa festa) {
        ArrayList<Festa> lista = getAllFestas();
        int indice = getIndiceDoFesta(festa.getId());

        if (indice == -1) {
            throw new RuntimeException("Festa não encontrada.");
        }

        lista.set(indice, festa);
        atualizarArquivoFesta(lista);
    }

    @Override
    public void deleteFesta(Festa festa) {
        ArrayList<Festa> lista = getAllFestas();
        int indice = getIndiceDoFesta(festa.getId());

        if (indice == -1) {
            throw new RuntimeException("Festa não encontrada.");
        }

        lista.remove(indice);
        atualizarArquivoFesta(lista);
    }
}
