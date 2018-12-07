package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.AsyncTask.AsyncTaskListaPrecoProdutos;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.AsyncTask.AsyncTaskProdutos;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ClienteDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ProdutoDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.ListaProdutosPresenter;

import java.util.ArrayList;

public class ListaProdutosModel implements ListaProdutosContrato.Model {

    private ListaProdutosContrato.Presenter presenter;
    private ProdutoDAO produtoDAO;

    public ListaProdutosModel(Context context, ListaProdutosContrato.Presenter presenter) {
        produtoDAO = new ProdutoDAO(context);
        this.presenter = presenter;
    }


    @Override
    public void  buscaProdutosNoBanco(String tipoDeLista) {
        new AsyncTaskProdutos(produtoDAO, presenter, tipoDeLista).execute();
    }

    @Override
    public void buscaPrecosDoProdutoNoBanco(String codigoProduto) {
        new AsyncTaskListaPrecoProdutos(produtoDAO, presenter, codigoProduto).execute();
    }
}
