package com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters.AdapterRecyclerListProdutos;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ProdutoClickListener;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.ListaProdutosPresenter;

import java.util.ArrayList;

public class ListaProdutosFragment  extends Fragment implements ListaProdutosContrato.View {


    private static final String SAVED_PRODUTOS = "save_produtos";
    private View view;
    private ArrayList<Produto> produtos = new ArrayList<>();
    private com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.ListaProdutosPresenter presenter;
    private String tipo_lista = "";
    private ProgressBar progressBar;
    private RecyclerView lista_produtos;
    private TextView tv_listaVazia ;
    private AdapterRecyclerListProdutos adapterListProdutos;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public ListaProdutosFragment() {

    }

    public static ListaProdutosFragment newInstance(){
        return new ListaProdutosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lista_produtos, container, false);
        this.view = view;
        setupVariaveisLayout();

        if(getArguments()!=null)
            this.tipo_lista = (getArguments().getString(ProdutosFragment.ARG_TIPO_DE_LISTA));

        this.presenter = new ListaProdutosPresenter(this, getContext());

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupListaProdutos();
        if(savedInstanceState==null){
            if(produtos.isEmpty()){
                presenter.buscaProdutos();
            }
        }else{
            produtos =  savedInstanceState.getParcelableArrayList(SAVED_PRODUTOS);
        }



    }


    private void setupVariaveisLayout(){
        lista_produtos = view.findViewById(R.id.lista_recycler);
        progressBar =  view.findViewById(R.id.progressBar);
        tv_listaVazia =  view.findViewById(R.id.tv_lista_vazia);

    }

    @Override
    public void setupListaProdutos(){

        if(produtos.isEmpty()) {
            tv_listaVazia.setVisibility(View.INVISIBLE);
            lista_produtos.setHasFixedSize(true);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            lista_produtos.setLayoutManager(layoutManager);

            adapterListProdutos = new AdapterRecyclerListProdutos(produtos, tipo_lista);


            adapterListProdutos.setOnItemClickListener(new ProdutoClickListener() {
                @Override
                public void onProdutoClick(int position) {
                    presenter.buscaPrecosDoProduto(produtos.get(position).getCodigo());
                }
            });
            lista_produtos.setVisibility(View.VISIBLE);
            lista_produtos.setAdapter(adapterListProdutos);


        }else if(progressBar.getVisibility()==View.INVISIBLE){
            tv_listaVazia.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showDialogListView(ArrayList<String> precos){

        assert getContext()!= null;
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());

        ListView listView = new ListView(getContext());


        listView.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1, precos));

        builder.setCancelable(true);
        builder.setPositiveButton("OK",null);
        builder.setView(listView);
        AlertDialog dialog=builder.create();

        dialog.show();
    }

    @Override
    public void insereItemNoAdapter(Produto produto) {
        this.produtos.add(produto);
        adapterListProdutos.insertItem(produto);
    }

    @Override
    public void atualizaAdapter(int posicao_lista) {
        adapterListProdutos.updateItem(posicao_lista);
    }

    @Override
    public int getItemCountDoAdapter() {
        return adapterListProdutos.getItemCount();
    }

    @Override
    public void preencheLista(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    @Override
    public String tipoListaASerExibido(){

        switch (tipo_lista){
            case ProdutosFragment.LISTA_NORMAL:
                return "N";
            case ProdutosFragment.LISTA_PRECO_ESTOQUE:
                return "P";
            case ProdutosFragment.LISTA_LANCAMENTO:
                return "L";
            case ProdutosFragment.LISTA_PROMOCAO:
                return "R";

                default: return null;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_PRODUTOS, produtos);
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
