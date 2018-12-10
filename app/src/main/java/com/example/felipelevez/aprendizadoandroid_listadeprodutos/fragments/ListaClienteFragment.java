package com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.activity.MainActivity;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters.AdapterRecyclerListClientes;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ClienteClickListener;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.ListaClientePresenter;

import java.util.ArrayList;

public class ListaClienteFragment extends Fragment implements ListaClienteContrato.View {


    private static final String SAVED_CLIENTES = "array_clientes";
    private ArrayList<Cliente> clientes =  new ArrayList<>();
    private View view;

    private static final String EXTRA_CLIENTE = "cliente";
    private static final String EXTRA_POSITION = "position";
    private AdapterRecyclerListClientes adapterListClientes;
    private ProgressBar progressBar;
    private RecyclerView lista_clientes;
    private TextView tv_listaVazia ;

    private ListaClientePresenter presenter;

    public static ListaClienteFragment newInstance(){
        return new ListaClienteFragment();
    }

    private FloatingActionButton fab_adicionaCliente;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);

        this.presenter = new ListaClientePresenter(this, getContext());
        this.view = view;

        setupComponentesLayout();
        executaAcaoFabAdicionaCliente();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState==null){
            if(clientes.isEmpty())
                presenter.buscaClientes();
        }else{
            clientes = savedInstanceState.getParcelableArrayList(SAVED_CLIENTES);
        }

        setupListaClientes();
    }

    private void executaAcaoFabAdicionaCliente(){
        fab_adicionaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle args = new Bundle();
                args.putParcelable(EXTRA_CLIENTE, new Cliente());
                args.putInt(EXTRA_POSITION, -1);

                inflaDetailsCliente(args);
            }
        });
    }

    private void setupComponentesLayout(){
        fab_adicionaCliente =  view.findViewById(R.id.fab);
        lista_clientes = view.findViewById(R.id.lista_recycler);
        progressBar =  view.findViewById(R.id.progressBar);
        lista_clientes = view.findViewById(R.id.lista_recycler);
        tv_listaVazia = view.findViewById(R.id.tv_lista_vazia);
    }

    private void inflaDetailsCliente(Bundle args){

        Fragment fragment = new DetailsClienteFragment(adapterListClientes);
        fragment.setArguments(args);
        ((MainActivity)getActivity()).inflaFragment(fragment);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_CLIENTES, clientes);
    }

    @Override
    public void setupListaClientes(){

        if(!clientes.isEmpty()) {
            tv_listaVazia.setVisibility(View.INVISIBLE);

            lista_clientes.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            lista_clientes.setLayoutManager(layoutManager);

            adapterListClientes = new AdapterRecyclerListClientes(clientes);

            adapterListClientes.setOnItemClickListener(new ClienteClickListener() {
                @Override
                public void onClienteClick(int position) {
                    Bundle args = new Bundle();
                    args.putParcelable(EXTRA_CLIENTE, clientes.get(position));
                    args.putInt(EXTRA_POSITION, position);
                    inflaDetailsCliente(args);
                }
            });

            lista_clientes.setAdapter(adapterListClientes);
            if (getContext() != null) {
                lista_clientes.addItemDecoration(
                        new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            }
        }else if(progressBar.getVisibility()==View.INVISIBLE){
            tv_listaVazia.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void preencheLista(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    @Override
    public void iniciaProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void encerraProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }


}
