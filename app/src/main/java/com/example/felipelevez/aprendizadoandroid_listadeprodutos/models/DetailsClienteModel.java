package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ClienteDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.DetailsClienteFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;

import java.util.ArrayList;

public class DetailsClienteModel implements DetailsClienteContrato.Model {

    private ClienteDAO clienteDAO;

    public DetailsClienteModel(Context context) {
        clienteDAO = new ClienteDAO(context);
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
