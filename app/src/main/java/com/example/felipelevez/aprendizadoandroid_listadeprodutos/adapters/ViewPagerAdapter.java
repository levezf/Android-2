package com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.ListaProdutosFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<ListaProdutosFragment> fragmentList = new ArrayList<>();
    private final ArrayList<String> fragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    @Override
    public ListaProdutosFragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(ListaProdutosFragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }
    public void clear(){
        fragmentList.clear();
        fragmentTitleList.clear();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }
}
