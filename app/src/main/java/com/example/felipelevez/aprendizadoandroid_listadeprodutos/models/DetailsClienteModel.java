package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ClienteDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;

public class DetailsClienteModel  implements  DetailsClienteContrato.Parent.Model{

    private ClienteDAO clienteDAO;

    public DetailsClienteModel(Context context, DetailsClienteContrato.Parent.Presenter presenter) {
        clienteDAO = new ClienteDAO(context, presenter.getLocalDatabase());
    }

    @Override
    public void atualizaUsuarioNoBanco(Cliente cliente) {
        clienteDAO.update(cliente);
    }

    @Override
    public String insereUsuarioNoBanco(Cliente cliente) {
        return clienteDAO.insert(cliente);
    }

    @Override
    public void removeUsuarioDoBanco(Cliente cliente) {
        clienteDAO.delete(cliente);
    }


}
