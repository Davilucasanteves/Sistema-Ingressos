package data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import model.Compra;

public class RepositorioCompra {

    private final String ARQUIVO_COMPRAS = "Compra.ser";
    private final String ARQUIVO_ID_COMPRAS = "idCompra.dat";

    public RepositorioCompra() {
        ArrayList<Compra> listaVaziaDeCompras = new ArrayList<>();
        File arquivoCompras = new File(ARQUIVO_COMPRAS);

        if (!arquivoCompras.exists()) {
            try {
                arquivoCompras.createNewFile();
                try (ObjectOutputStream escritorDeDados = new ObjectOutputStream(new FileOutputStream(ARQUIVO_COMPRAS))) {
                    escritorDeDados.writeObject(listaVaziaDeCompras);
                }
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo de Compras!" + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Compra> getAllCompras() {
        ArrayList<Compra> comprasCadastradas = new ArrayList<>();

        try (ObjectInputStream leitorDeObjetos = new ObjectInputStream(new FileInputStream(ARQUIVO_COMPRAS))) {
            comprasCadastradas = (ArrayList<Compra>) leitorDeObjetos.readObject();
        } catch (Exception e) {
            System.err.println("Erro no metodo getAllCompras: " + e.getMessage());
            e.printStackTrace();
        }

        return comprasCadastradas;
    }

    private int gerarNovoIdParaCompra() {
        File arquivoDeIds = new File(ARQUIVO_ID_COMPRAS);
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
            System.err.println("Erro no metodo gerarNovoIdParaCompra: " + e.getMessage());
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

    private void gravarNovoId(int novoId, File arquivoDeIds) throws IOException {
        try (DataOutputStream escritorDeDados = new DataOutputStream(new FileOutputStream(arquivoDeIds))) {
            escritorDeDados.writeInt(novoId);
        }
    }

    private void atualizarArquivoCompra(ArrayList<Compra> comprasCadastradas) {
        try (ObjectOutputStream escritorDeObjetos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_COMPRAS))) {
            escritorDeObjetos.writeObject(comprasCadastradas);
        } catch (Exception e) {
            System.err.println("Erro no método atualizarArquivoCompra: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void createCompra(Compra compra) {
        compra.setId(gerarNovoIdParaCompra());
        ArrayList<Compra> comprasCadastradas = getAllCompras();
        comprasCadastradas.add(compra);
        atualizarArquivoCompra(comprasCadastradas);
    }

    public ArrayList<Compra> getComprasPorFestId(int festId) {
        ArrayList<Compra> comprasCadastradas = getAllCompras();
        ArrayList<Compra> comprasDaFesta = new ArrayList<>();

        for (Compra c : comprasCadastradas) {
            if (c.getFestId() == festId) {
                comprasDaFesta.add(c);
            }
        }

        return comprasDaFesta;
    }
}
