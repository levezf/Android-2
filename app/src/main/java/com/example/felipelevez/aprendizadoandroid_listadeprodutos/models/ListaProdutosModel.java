package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask.AsyncTaskListaPrecoProdutos;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask.AsyncTaskProdutos;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ProdutoDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class ListaProdutosModel implements ListaProdutosContrato.Model {

    private ListaProdutosContrato.Presenter presenter;
    private ProdutoDAO produtoDAO;

    public ListaProdutosModel(Context context, ListaProdutosContrato.Presenter presenter) {
        produtoDAO = new ProdutoDAO(context);
        this.presenter = presenter;
    }


    @Override
    public void  buscaProdutosNoBanco(String tipoDeLista) {
        produtoDAO.getAll(tipoDeLista, presenter);
        //new AsyncTaskProdutos(produtoDAO, presenter, tipoDeLista).execute();
    }

    @Override
    public void buscaPrecosDoProdutoNoBanco(String codigoProduto) {
        new AsyncTaskListaPrecoProdutos(produtoDAO, presenter, codigoProduto).executeOnExecutor(THREAD_POOL_EXECUTOR);
    }
}
