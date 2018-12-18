package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

import java.util.ArrayList;

public interface ListaClienteContrato {

    interface Model{
        void getAll();
    }

    interface Presenter{
        String getLocalDatabase();
        void iniciaProgressBar();
        void encerraProgressBar();
        void exibeLista(ArrayList<Cliente> clientes);
        void buscaClientes();
    }

    interface View{
        String getLocalDatabase();
        void setupListaClientes();
        void preencheLista(ArrayList<Cliente> clientes);
        void iniciaProgressBar();
        void encerraProgressBar();
    }
}
