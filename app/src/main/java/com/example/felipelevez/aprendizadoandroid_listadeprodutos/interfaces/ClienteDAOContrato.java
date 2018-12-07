package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import android.content.ContentValues;
import android.database.Cursor;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

import java.util.ArrayList;


public interface ClienteDAOContrato {
    String insert(Cliente cliente);
    Cliente get(String id);
    ArrayList<Cliente> getAll();
    int getCount();
    void update(Cliente cliente);
    void delete(Cliente cliente);
    Cliente bindCliente(Cursor cursor);
    ContentValues bindContentValuesCliente(Cliente cliente);
    String buscaUltimoCodigoInserido();
}
