package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.categorias_db;

import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

public class Categorias implements Serializable {
    String nome;
    ArrayList<Musica> musicas;

    public Categorias(String nome, ArrayList<Musica> musicas){
        this.nome = nome;
        this.musicas = musicas;
        categorias_db.add(this);
    }

    public ArrayList<Musica> getMusicas() {
        return musicas;
    }

    public void setMusicas(ArrayList<Musica> musicas) {
        this.musicas = musicas;
    }

    public void setNome(String nome){
            this.nome = nome;
    }

    public String getNome(){
            return nome;
    }

    public  String toString(){
        return "Categoria: " + nome;
    }
}
