package com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.activity.MainActivity;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters.AdapterRecyclerListClientes;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters.ViewPagerAdapter;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.DetailsClientePresenter;


public class DetailsClienteFragment extends Fragment implements DetailsClienteContrato.Parent.View {

    private static final String EXTRA_CLIENTE = "cliente";
    private static final String EXTRA_POSITION = "position";
    private static final String ARG_ADAPTER = "adapter_list";
    private Cliente cliente;
    private int position_lista;
    private View view;
    private ViewPager viewPager;
    private FloatingActionButton fab;
    private DetailsClientePresenter presenter;
    public static final int INSERIR  = 1;
    public static final int REMOVER  = 2;
    public static final int EDITAR  = 3;
    private AdapterRecyclerListClientes adapterRecyclerListClientes;
    private DetailsClienteDadosFragment dados = null;
    private DetailsClienteEmailFragment email = null;
    private DetailsClienteEnderecoFragment endereco = null;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabLayout;

    public DetailsClienteFragment() {

    }

    public static DetailsClienteFragment newInstance() {
        return new DetailsClienteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_details_cliente, container, false);

        assert getActivity() != null;
        ((MainActivity) getActivity()).setupNavigationDrawerOFF();

        this.view = view;
        setupComponentesDoLayout();


        if (savedInstanceState != null){
            cliente = savedInstanceState.getParcelable(EXTRA_CLIENTE);
            position_lista = savedInstanceState.getInt(EXTRA_POSITION);
            adapterRecyclerListClientes = (AdapterRecyclerListClientes) savedInstanceState.getSerializable(ARG_ADAPTER);
        }else{
            assert getArguments() != null;
            cliente = getArguments().getParcelable(EXTRA_CLIENTE);
            position_lista = getArguments().getInt(EXTRA_POSITION);
            adapterRecyclerListClientes = (AdapterRecyclerListClientes)getArguments().getSerializable(ARG_ADAPTER);


        }
        setupNavigationTabs(view);
        presenter = new DetailsClientePresenter(this, getContext());
        executaAcaoBotaoSalvar();



        return view;
    }

    private void setupComponentesDoLayout(){
        fab = view.findViewById(R.id.fab);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp, getResources().newTheme()));

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_CLIENTE, cliente);
        outState.putInt(EXTRA_POSITION, position_lista);
        outState.putSerializable(ARG_ADAPTER, adapterRecyclerListClientes);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    public void executaAcaoBotaoSalvar() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dados = (DetailsClienteDadosFragment) viewPagerAdapter.getItem(0);
                email = (DetailsClienteEmailFragment) viewPagerAdapter.getItem(1);
                endereco = (DetailsClienteEnderecoFragment) viewPagerAdapter.getItem(2);

                presenter.executaAcaoBotaoSalvar(cliente);
            }
        });

    }
    @Override
    public String getLocalDatabase() {
        assert getActivity()!=null;
        return ((MainActivity)getActivity()).getPathDataBase();
    }

    @Override
    public void bindCliente() {


        dados.bindCliente();
        email.bindCliente();
        endereco.bindCliente();
    }

    @Override
    public boolean ehDadosValidos(){

        viewPager.setCurrentItem(0);
        if(!dados.ehOperacaoValida()){
            return false;
        }else {
            viewPager.setCurrentItem(1);
            if(!email.ehOperacaoValida()){
                return false;
            }else{
                viewPager.setCurrentItem(2);
                return endereco.ehOperacaoValida();
            }
        }
    }

    @Override
    public void atualizaLista(int tipoDeAtualizacao){
        switch (tipoDeAtualizacao){
            case INSERIR:
                adapterRecyclerListClientes.insertItem(cliente);
                break;
            case REMOVER:
                adapterRecyclerListClientes.removerItem(position_lista);
                break;
            case EDITAR:
                adapterRecyclerListClientes.updateItem(position_lista);
                break;
        }
        voltar();
    }

    private void setupNavigationTabs(View view){

        setupViewPager();
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setupViewPager() {
        viewPagerAdapter = new ViewPagerAdapter(getChildFragmentManager());

        setupFragmentsViewPager();


        viewPagerAdapter.addFragment(dados, getString(R.string.tab_dados));
        viewPagerAdapter.addFragment(email, getString(R.string.tab_email));
        viewPagerAdapter.addFragment(endereco, getString(R.string.tab_endereco));
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
    }

    void setupFragmentsViewPager(){
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_CLIENTE, cliente);
        dados = DetailsClienteDadosFragment.newInstance();
        dados.setArguments(args);

        email = DetailsClienteEmailFragment.newInstance();
        email.setArguments(args);

        endereco = DetailsClienteEnderecoFragment.newInstance();
        endereco.setArguments(args);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_apagar) {

            presenter.executaAcaoBotaoDeletar(cliente);
        }

        return super.onOptionsItemSelected(item);
    }

    public void voltar(){
        if (getActivity() != null)
            getActivity().onBackPressed();
    }
}
