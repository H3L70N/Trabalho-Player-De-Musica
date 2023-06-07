package mz.ac.isutc.i32.mt2.tg;

import static mz.ac.isutc.i32.mt2.tg.DB.categorias_db;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MeuFragment extends Fragment {

    public CharSequence getTitle(int i){
        return categorias_db.get(i).getNome();
    }


    SharedViewModel viewModel;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ViewPager viewPager = (ViewPager) view.getRootView(). findViewById(R.id.pager);
        TabLayout tabLayout = (TabLayout) view.getRootView().findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
    }
}
