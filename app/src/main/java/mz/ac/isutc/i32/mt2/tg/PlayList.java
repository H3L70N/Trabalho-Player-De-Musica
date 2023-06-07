package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.musicas_db;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.File;
import java.util.ArrayList;

import mz.ac.isutc.i32.mt2.tg.databinding.PlaylistBinding;

public class PlayList extends MeuFragment {

    PlaylistBinding b;
    SharedViewModel v;
    boolean bool;

    private ArrayList<Musica> musicas;

    String categoria;


    PlayList(String categoria){
        this.categoria = categoria;
        musicas = new ArrayList<>();
        for(Musica musica : musicas_db){
            if(musica.getCategoria() != null){
                if(musica.getCategoria().getNome().equals(categoria)){
                    musicas.add(musica);
                }
            }
        }
        this.musicas = musicas;
        bool = true;
    }

    PlayList(ArrayList<File> files, boolean b){
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        for(File file : files){
            retriever.setDataSource(file.getAbsolutePath());
            String titulo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artista = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            if(titulo == null) titulo = file.getName().replace(".mp3", "").replace(".m4a", "").replace(".wav", "");
            if(artista == null) artista = "Unknown Artist";
            byte [] art = retriever.getEmbeddedPicture();
            new Musica(artista, titulo, art, file);
        }
        musicas = musicas_db;
        bool = false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = PlaylistBinding.inflate(inflater, container, false);
        v = new ViewModelProvider(getActivity()).get(SharedViewModel.class);
        ListAdapter adapter = new ListAdapter(getContext(), musicas);

        b.list.setAdapter(adapter);
        b.list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        b.list.setItemsCanFocus(false);
        b.list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(getContext(), Player.class);
                intent.putExtra("indice", musicas.indexOf(adapterView.getAdapter().getItem(i)));
                if (bool) {
                    intent.putExtra("categoria", categoria);
                }

                startActivity(intent);
            }
        });
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        return b.getRoot();
    }
    //@Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        ViewPager viewPager = (ViewPager) view.getRootView(). findViewById(R.id.pager);
//        TabLayout tabLayout = (TabLayout) view.getRootView().findViewById(R.id.tab_layout);
//        tabLayout.setupWithViewPager(viewPager);
//    }
}
