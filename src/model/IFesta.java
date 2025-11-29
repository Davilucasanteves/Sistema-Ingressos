package model;

import java.util.ArrayList;

public interface IFesta {
    public ArrayList<Festa> getAllFestas();
    public void createFesta (Festa Festa);
    public Festa readFesta(String nome);
    public Festa readFestaPorId(int id);
    public void updateFesta(Festa Festa);
    public void deleteFesta(Festa Festa);
}