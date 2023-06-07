package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.musicas_db;

import android.graphics.drawable.Drawable;

import java.io.File;
import java.io.Serializable;

public class Musica implements Serializable {
    private String artista;
    private String titulo;
    private byte[] art;
    private File musicFile;
    private Categorias categoria;




    public Musica(String artista, String titulo, byte[] art, File musicFile) {
        this.artista = artista;
        this.titulo = titulo;
        this.art = art;
        this.musicFile = musicFile;
        musicas_db.add(this);
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Categorias getCategoria() {
        return categoria;
    }

    public void setCategoria(Categorias categoria) {
        this.categoria = categoria;
    }

    public byte[] getArt() {
        return art;
    }

    public void setArt(byte[] art) {
        this.art = art;
    }

    public File getMusicFile() {
        return musicFile;
    }

    public void setMusicFile(File musicFile) {
        this.musicFile = musicFile;
    }

    @Override
    public String toString() {
        return "Musica{" +
                "artista='" + artista + '\'' +
                ", titulo='" + titulo + '\'' +
                ", categoria='" + categoria.getNome() + '\'' +
                '}';
    }
}
