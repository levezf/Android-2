package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask.AsyncTaskListaClientes;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ClienteDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaClienteContrato;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ListaClienteModel implements ListaClienteContrato.Model {

    private ClienteDAO clienteDAO;
    private ListaClienteContrato.Presenter presenter;

    public ListaClienteModel(Context context, ListaClienteContrato.Presenter presenter) {
        clienteDAO = new ClienteDAO(context, presenter.getLocalDatabase());
        this.presenter = presenter;
    }

    @Override
    public void getAll() {
        ThreadPoolExecutor executor= new ThreadPoolExecutor( 1,  Runtime.getRuntime().availableProcessors(), 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
        executor.allowCoreThreadTimeOut(true);
        new AsyncTaskListaClientes(clienteDAO, presenter).executeOnExecutor(executor);
    }
}
