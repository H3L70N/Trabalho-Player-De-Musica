package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.categorias_db;
import static mz.ac.isutc.i32.mt2.tg.DB.musicas_db;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import java.util.ArrayList;

import mz.ac.isutc.i32.mt2.tg.databinding.ActivityPlayListBinding;

public class PlayListActivity extends AppCompatActivity {

    ActivityPlayListBinding b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityPlayListBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());

        inicializar();
    }

    void inicializar(){
        Bundle extras = getIntent().getExtras();
        String categoria = extras.getString("categoria");

        PlayList fr = new PlayList(categoria);
        ArrayList<MeuFragment> fragments = new ArrayList<>();
        fragments.add(fr);

        MeuPageAdapter adapter = new MeuPageAdapter(getSupportFragmentManager(), fragments, categoria);

        b.pager.setAdapter(adapter);

    }
}