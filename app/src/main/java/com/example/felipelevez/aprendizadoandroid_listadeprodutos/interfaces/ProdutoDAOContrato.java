package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import android.database.Cursor;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.ListaProdutosPresenter;

import java.util.ArrayList;

public interface ProdutoDAOContrato {
    ArrayList<Produto> getAll(String tipo_lista);
    ArrayList<String> getPrecosDoProduto(String codigoProduto);
    Produto bindProdutos(Cursor cursor);
    ArrayList<Produto> buscaProdutos_ElementosBasicos(String tipo_lista);
    String buscaPrecoProduto(String codigo, String preco);
    Double buscaEstoqueProduto(String codigo);
    void getAll(String tipo_lista, ListaProdutosContrato.Presenter presenter);
}
