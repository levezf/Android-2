package com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.MainActivity;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters.ViewPagerAdapter;

import java.util.ArrayList;

public class ProdutosFragment extends Fragment {

    private ViewPager viewPager;
    public static final String ARG_TIPO_DE_LISTA = "TIPO_DE_LISTA";
    public static final String LISTA_NORMAL = "NORMAL";
    public static final String LISTA_PRECO_ESTOQUE = "PONTA DE ESTOQUE";
    public static final String LISTA_LANCAMENTO = "LANÇAMENTO";
    public static final String LISTA_PROMOCAO = "PROMOÇÃO";

    public static ProdutosFragment newInstance(){
        return new ProdutosFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_produto, container, false);

        setupNavigationTabs(view);

        return view;
    }


    private void setupNavigationTabs(View view){
        viewPager = view.findViewById(R.id.viewpager);
        setupViewPager();
        TabLayout tabLayout = view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        adapter.addFragment(newFragmentComArgs(LISTA_NORMAL), "NORMAL");
        adapter.addFragment(newFragmentComArgs(LISTA_PRECO_ESTOQUE), "P.ESTOQUE");
        adapter.addFragment(newFragmentComArgs(LISTA_LANCAMENTO), "LANÇAMENTO");
        adapter.addFragment(newFragmentComArgs(LISTA_PROMOCAO), "PROMOÇÃO");

        viewPager.setAdapter(adapter);
    }

    private static ListaProdutosFragment newFragmentComArgs(String tipo_de_lista){
        Bundle args = new Bundle();
        ListaProdutosFragment produtosFragment = ListaProdutosFragment.newInstance();
        args.putString(ARG_TIPO_DE_LISTA, tipo_de_lista);
        produtosFragment.setArguments(args);
        return produtosFragment;
    }


}
