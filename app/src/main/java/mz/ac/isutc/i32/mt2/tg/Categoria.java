package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.categorias_db;
import static mz.ac.isutc.i32.mt2.tg.DB.musicas_db;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import mz.ac.isutc.i32.mt2.tg.databinding.CategorialistBinding;

public class Categoria extends MeuFragment {

    CategorialistBinding b;

    ActivityResultLauncher<Intent> resultLauncher;
    SharedViewModel viewModel;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        b = CategorialistBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(getActivity()).get(SharedViewModel.class);

        resultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == 34){
                    GridAdapter adapter = new GridAdapter((AppCompatActivity) getActivity(), categorias_db, resultLauncher);
                    b.grid.setAdapter(adapter);
                    DB db = new DB(getActivity());
                    db.gravar();
                }
            }
        });

        GridAdapter adapter = new GridAdapter((AppCompatActivity) getActivity(), categorias_db, resultLauncher);


        b.grid.setAdapter(adapter);

        b.grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), PlayListActivity.class);
                intent.putExtra("categoria", categorias_db.get(i).getNome());
                startActivity(intent);
            }
        });

        return b.getRoot();
    }


}
