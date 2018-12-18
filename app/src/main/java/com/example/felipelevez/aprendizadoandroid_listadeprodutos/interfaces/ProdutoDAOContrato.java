package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import java.util.ArrayList;

public interface ProdutoDAOContrato {
    ArrayList<String> getPrecosDoProduto(String codigoProduto);
    void getAll(String tipo_lista, ListaProdutosContrato.Presenter presenter);
}
