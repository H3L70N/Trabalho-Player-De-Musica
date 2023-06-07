package mz.ac.isutc.i32.mt2.tg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import mz.ac.isutc.i32.mt2.tg.databinding.GridItemBinding;
import mz.ac.isutc.i32.mt2.tg.databinding.MaiscategoriaBinding;

public class GridAdapter extends BaseAdapter {
    private AppCompatActivity context;
     ArrayList<Categorias> categorias;

     GridItemBinding b;
     MaiscategoriaBinding b1;
    ActivityResultLauncher<Intent> resultLauncher;

    GridAdapter(AppCompatActivity context, ArrayList<Categorias> categorias, ActivityResultLauncher<Intent> resultLauncher){
        this.categorias = categorias;
        this.context = context;
        this.resultLauncher = resultLauncher;


    }

    @Override
    public int getCount() {
        return categorias.size() + 1;
    }

    @Override
    public Object getItem(int i) {
        if(i == getCount()) return null;
        return categorias.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(i < getCount()-1){
            b = GridItemBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            b.gridItemName.setText(categorias.get(i).getNome());

            return b.getRoot();
        }else {
            b1 = MaiscategoriaBinding.inflate(LayoutInflater.from(context), viewGroup, false);
            b1.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), AdicionarCategoria.class);
                    resultLauncher.launch(intent);
                }
            });
            return b1.getRoot();
        }


    }
}
