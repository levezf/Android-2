package com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.ViewGroup;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.ListaProdutosFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<Fragment> fragmentList = new ArrayList<>();
    private final ArrayList<String> fragmentTitleList = new ArrayList<>();
    private FragmentManager manager;

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
        this.manager = manager;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       /* Fragment fragment = getItem(position);
        FragmentTransaction trans = manager.beginTransaction();
        trans.add(container.getId(),fragment);
        trans.commit();*/
        //return fragment;
        Object ret = super.instantiateItem(container, position);
        fragmentList.set(position, (Fragment) ret);
        return ret;

    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        fragmentList.add(fragment);
        fragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentTitleList.get(position);
    }

}
