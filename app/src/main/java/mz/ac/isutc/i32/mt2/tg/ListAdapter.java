package mz.ac.isutc.i32.mt2.tg;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import mz.ac.isutc.i32.mt2.tg.databinding.MusicaChoiceListBinding;
import mz.ac.isutc.i32.mt2.tg.databinding.MusicalistBinding;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Musica> musicas;

    private MusicalistBinding b;
    private MusicaChoiceListBinding binding;

    ListAdapter(Context context,ArrayList<Musica> musicas){
        this.musicas = musicas;
        this.context = context;
    }


    @Override
    public int getCount() {
        return musicas.size();
    }

    @Override
    public Object getItem(int i) {
        return musicas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        b = MusicalistBinding.inflate(LayoutInflater.from(context), viewGroup, false);
        byte[] art = musicas.get(i).getArt();
        b.titulo.setText(musicas.get(i).getTitulo());
        b.autor.setText(musicas.get(i).getArtista());

        try{
            Bitmap songImage = BitmapFactory
                    .decodeByteArray(art, 0, art.length);
            b.image.setImageBitmap(songImage);
        }catch (Exception e){
            //e.printStackTrace();
        }
        return b.getRoot();


    }

}
