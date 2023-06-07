package mz.ac.isutc.i32.mt2.tg;

import android.app.Activity;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class DB {
    public static ArrayList<Musica> musicas_db = new ArrayList<>();
    public static ArrayList<Categorias> categorias_db = new ArrayList<>();


    public static ArrayList<File> buscarMusicas (File file){
        ArrayList<File> arrayList = new ArrayList<>();

        File [] files = file.listFiles();
        if(files != null){
            for(File singleFile : files) {
                if (singleFile.isDirectory() && !singleFile.isHidden()){
                    arrayList.addAll(buscarMusicas(singleFile));
                }else{
                    if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav") || singleFile.getName().endsWith(".m4a")){
                        arrayList.add(singleFile);
                    }
                }
            }
        }


        return arrayList;
    }

//    public static boolean shuffle = false;

    private static int repeat = 0;

    public static int getRepeat() {
        return repeat;
    }

//    public static int repeat(){
//        if(repeat == 0){
//            repeat = 1;
//        } else if (repeat == 1) {
//            repeat = 2;
//        }else{
//            repeat = 0;
//        }
//        return repeat;
//    }
//










    private Activity activity;
    final String FILE_NAME = "FileCategorias";
    File file;

    DB(Activity activity){
        this.activity = activity;
        file = activity.getFileStreamPath(FILE_NAME);


    }




    public void gravar(){
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(categorias_db);
            System.out.println("Categorias Gravadas");
            oos.close();
            fos.close();
        }catch (FileNotFoundException e){
            System.out.println("File Not Found Write");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<Categorias> ler(){
        ArrayList<Categorias> categorias = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream oin = new ObjectInputStream(fis);
            categorias = (ArrayList<Categorias>) oin.readObject();
            System.out.println("Categorias Lidas");
            oin.close();
            fis.close();
        }catch (FileNotFoundException e){
            System.out.println("File Not Found Read");
        }catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(Categorias categoria : categorias){
            for(Musica musica : musicas_db){
                for(Musica musica1 : categoria.getMusicas()){
                    if(musica.getTitulo().equals(musica1.getTitulo())){
                        musica.setCategoria(categoria);
                    }
                }
            }
        }

        return categorias;
    }


}
