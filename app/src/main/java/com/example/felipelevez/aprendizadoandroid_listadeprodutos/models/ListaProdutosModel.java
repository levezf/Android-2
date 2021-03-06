package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask.AsyncTaskListaPrecoProdutos;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ProdutoDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListaProdutosModel implements ListaProdutosContrato.Model {

    private final ListaProdutosContrato.Presenter presenter;
    private final ProdutoDAO produtoDAO;

    public ListaProdutosModel(Context context, ListaProdutosContrato.Presenter presenter) {
        produtoDAO = new ProdutoDAO(context, presenter.getLocalDatabase());
        this.presenter = presenter;
    }

    @Override
    public void  buscaProdutosNoBanco(String tipoDeLista) {
        produtoDAO.getAll(tipoDeLista, presenter);
    }

    @Override
    public void buscaPrecosDoProdutoNoBanco(String codigoProduto) {
        ThreadPoolExecutor executor= new ThreadPoolExecutor( 1,  Runtime.getRuntime().availableProcessors()/2, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        executor.allowCoreThreadTimeOut(true);
        new AsyncTaskListaPrecoProdutos(produtoDAO, presenter, codigoProduto).executeOnExecutor(executor);
    }
}
