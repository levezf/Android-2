package com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask;

import android.os.AsyncTask;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ProdutoDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

import java.util.ArrayList;

public class AsyncTaskProdutos extends AsyncTask<Void, Void , ArrayList<Produto>> {

    private final ListaProdutosContrato.Presenter presenter;
    private final ProdutoDAO produtoDAO;
    private String tipoDeLista;

    public AsyncTaskProdutos(ProdutoDAO produtoDAO, ListaProdutosContrato.Presenter presenter, String tipoDeLista){
        this.presenter = presenter;
        this.produtoDAO = produtoDAO;
        this.tipoDeLista = tipoDeLista;
    }

    @Override
    protected void onPreExecute() {
        presenter.iniciaProgressBar();
    }

    @Override
    protected ArrayList<Produto> doInBackground(Void... params) {
        return produtoDAO.buscaProdutos_ElementosBasicos(tipoDeLista);
    }

    @Override
    protected void onPostExecute(ArrayList<Produto> produtos) {
        presenter.encerraProgressBar();
        presenter.exibeLista(produtos);
    }
}