package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask.AsyncTaskListaClientes;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ClienteDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaClienteContrato;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

public class ListaClienteModel implements ListaClienteContrato.Model {

    private ClienteDAO clienteDAO;
    private ListaClienteContrato.Presenter presenter;

    public ListaClienteModel(Context context, ListaClienteContrato.Presenter presenter) {
        clienteDAO = new ClienteDAO(context);
        this.presenter = presenter;
    }

    @Override
    public void getAll() {
        new AsyncTaskListaClientes(clienteDAO, presenter).executeOnExecutor(THREAD_POOL_EXECUTOR);
    }
}
