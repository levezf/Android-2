package com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask;

import android.os.AsyncTask;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ClienteDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

import java.util.ArrayList;

public class AsyncTaskListaClientes extends AsyncTask<Void, Void , ArrayList<Cliente>> {

    private final ListaClienteContrato.Presenter presenter;
    private final ClienteDAO clienteDAO;

    public AsyncTaskListaClientes(ClienteDAO clienteDAO, ListaClienteContrato.Presenter presenter){
        this.presenter = presenter;
        this.clienteDAO = clienteDAO;
    }

    @Override
    protected void onPreExecute() {
        presenter.iniciaProgressBar();
    }

    @Override
    protected ArrayList<Cliente> doInBackground(Void... params) {
        return clienteDAO.getAll();
    }

    @Override
    protected void onPostExecute(ArrayList<Cliente> clientes) {
        presenter.encerraProgressBar();
        presenter.exibeLista(clientes);
    }
}
