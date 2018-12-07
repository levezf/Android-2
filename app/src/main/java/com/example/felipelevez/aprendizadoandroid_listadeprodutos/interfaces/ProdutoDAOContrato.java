package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import android.database.Cursor;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

import java.util.ArrayList;

public interface ProdutoDAOContrato {
    ArrayList<Produto> getAll(String tipo_lista);
    ArrayList<String> getPrecosDoProduto(String codigoProduto);
    Produto bindProdutos(Cursor cursor);
}
