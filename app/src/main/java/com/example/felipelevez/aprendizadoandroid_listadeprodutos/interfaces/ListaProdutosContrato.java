package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

import java.lang.reflect.Array;
import java.util.ArrayList;

public interface ListaProdutosContrato {

    interface View{
        void iniciaProgressBar();
        void encerraProgressBar();
        String tipoListaASerExibido();
        void setupListaProdutos();
        void preencheLista(ArrayList<Produto> produtos);
        void showDialogListView(ArrayList<String> precos);
    }
    interface Presenter{
        void buscaProdutos();
        void iniciaProgressBar();
        void encerraProgressBar();
        void exibeLista(ArrayList<Produto> produtos);
        void exibeDialogPrecos(ArrayList<String> precos);
        void buscaPrecosDoProduto(String codigo);
    }
    interface Model{
        void buscaProdutosNoBanco(String tipoDeLista);
        void buscaPrecosDoProdutoNoBanco(String codigoProduto);
    }


}
