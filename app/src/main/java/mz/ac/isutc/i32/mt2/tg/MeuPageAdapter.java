package mz.ac.isutc.i32.mt2.tg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;

public class MeuPageAdapter extends FragmentPagerAdapter {

    private ArrayList<MeuFragment> fragments;
    private boolean estado;
    private String categoria;
    public MeuPageAdapter(@NonNull FragmentManager fm, ArrayList<MeuFragment> fragments) {
        super(fm);
        this.fragments = fragments;
        this.estado = false;
    }

    public MeuPageAdapter(@NonNull FragmentManager fm, ArrayList<MeuFragment> fragments, String categoria) {
        super(fm);
        this.fragments = fragments;
        this.estado = true;
        this.categoria = categoria;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if(estado) return categoria;
        if(position == 0) return "Categorias";
        return "PlayList";
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
