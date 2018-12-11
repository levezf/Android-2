package com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask;

import android.os.AsyncTask;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ProdutoDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;

import java.util.ArrayList;

public class AsyncTaskListaPrecoProdutos extends AsyncTask<Void, Void , ArrayList<String>> {


    private final ListaProdutosContrato.Presenter presenter;
    private final ProdutoDAO produtoDAO;
    private String codigo;

    public AsyncTaskListaPrecoProdutos(ProdutoDAO produtoDAO, ListaProdutosContrato.Presenter presenter, String codigo){
        this.presenter = presenter;
        this.produtoDAO = produtoDAO;
        this.codigo = codigo;
    }

    @Override
    protected void onPreExecute() {
        presenter.iniciaProgressBarDialog();
    }

    @Override
    protected ArrayList<String> doInBackground(Void... params) {
        return produtoDAO.getPrecosDoProduto(codigo);
    }
    @Override
    protected void onPostExecute(ArrayList<String> produtos) {
        presenter.encerraProgressBarDialog();
        presenter.exibeDialogPrecos(produtos);
    }

}
