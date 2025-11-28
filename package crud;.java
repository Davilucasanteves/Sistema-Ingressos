package crud;

import model.Festa;
import java.util.ArrayList;

public class FestaCRUD {

    private ArrayList<Festa> listaFestas = new ArrayList<>();

    public void cadastrarFesta(Festa festa) {
        listaFestas.add(festa);
    }

    public ArrayList<Festa> listarFesta() {
        return listaFestas;
    }

    public void atualizarFesta(int idFesta, Festa festaAtualizada) {
        for (int i = 0; i < listaFestas.size(); i++) {
            if (listaFestas.get(i).getIdFesta() == idFesta) {
                listaFestas.set(i, festaAtualizada);
            }
        }
    }

    public void deletarFesta(int idFesta) {
        listaFestas.removeIf(f -> f.getIdFesta() == idFesta);
    }
}
