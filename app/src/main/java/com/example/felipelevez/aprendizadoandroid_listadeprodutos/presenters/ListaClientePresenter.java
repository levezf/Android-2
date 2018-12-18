package com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.ListaClienteModel;

import java.util.ArrayList;

public class ListaClientePresenter implements ListaClienteContrato.Presenter {


    ListaClienteContrato.View view;
    ListaClienteModel model;

    public ListaClientePresenter(ListaClienteContrato.View view, Context context) {

        this.view = view;
        this.model = new ListaClienteModel(context, this);
    }

    @Override
    public String getLocalDatabase() {
        return view.getLocalDatabase();
    }

    @Override
    public void iniciaProgressBar() {
        view.iniciaProgressBar();
    }

    @Override
    public void encerraProgressBar() {
        view.encerraProgressBar();
    }

    @Override
    public void exibeLista(ArrayList<Cliente> clientes) {
        view.preencheLista(clientes);
        view.setupListaClientes();
    }

    @Override
    public void buscaClientes() {
        model.getAll();
    }
}
