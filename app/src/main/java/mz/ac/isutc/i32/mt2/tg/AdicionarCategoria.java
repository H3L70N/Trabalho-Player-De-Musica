package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.musicas_db;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import mz.ac.isutc.i32.mt2.tg.databinding.ActivityAdicionarCategoriaBinding;

public class AdicionarCategoria extends AppCompatActivity {

    ActivityAdicionarCategoriaBinding b;
    ArrayList<Musica> musicas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityAdicionarCategoriaBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        inicializar();
    }

    void inicializar(){
        String [] musicasSelect = new String[musicas_db.size()];

        for(int i = 0; i < musicas_db.size(); i++){
            musicasSelect[i] = musicas_db.get(i).getTitulo();
        }

        ArrayAdapter<String> adapter =  new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, musicasSelect);
        b.lista.setAdapter(adapter);

        b.lista.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        b.submeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                musicas = new ArrayList<>();
                SparseBooleanArray selected = b.lista.getCheckedItemPositions();

                for(int i = 0; i < selected.size(); i++){
                    if(selected.valueAt(i)){
                        musicas.add(musicas_db.get(selected.keyAt(i)));
                    }
                }

                if(b.editTextTextPersonName2.getText().toString().equals("")){
                    Toast.makeText(AdicionarCategoria.this, "Adicione o nome da categoria", Toast.LENGTH_SHORT).show();
                } else if (musicas.size() == 0) {
                    Toast.makeText(AdicionarCategoria.this, "Adicione algumas musicas a categoria", Toast.LENGTH_SHORT).show();
                }else{
                    Categorias novaCategoria = new Categorias(b.editTextTextPersonName2.getText().toString(), musicas);

                    for(Musica musica : musicas){
                        musica.setCategoria(novaCategoria);
                    }
                    Intent intent = new Intent();
                    setResult(34, intent);
                    finish();
                }


            }
        });
    }
}