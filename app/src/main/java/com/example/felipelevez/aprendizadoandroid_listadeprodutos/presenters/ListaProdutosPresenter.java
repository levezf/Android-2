package com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.ListaClienteModel;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.ListaProdutosModel;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

import java.util.ArrayList;

public class ListaProdutosPresenter implements ListaProdutosContrato.Presenter {

    private final ListaProdutosContrato.View view;
    private final ListaProdutosModel model;


    public ListaProdutosPresenter(ListaProdutosContrato.View view, Context context) {
        this.view = view;
        this.model = new ListaProdutosModel(context, this);
    }

    @Override
    public int getItemCountDoAdapter(){
        return view.getItemCountDoAdapter();
    }

    @Override
    public String getLocalDatabase() {
        return view.getLocalDatabase();
    }

    @Override
    public void insereItemNoAdapter(Produto produto) {
        view.insereItemNoAdapter(produto);
    }

    @Override
    public void atualizaAdapter(int posicao_lista) {
        view.atualizaAdapter(posicao_lista);
    }

    @Override
    public void buscaProdutos() {
        model.buscaProdutosNoBanco(view.tipoListaASerExibido());
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
    public void exibeLista(ArrayList<Produto> produtos) {
        view.preencheLista(produtos);
        view.setupListaProdutos();
    }

    @Override
    public void exibeDialogPrecos(ArrayList<String> precos) {
        view.showDialogListView(precos);
    }

    @Override
    public void buscaPrecosDoProduto(String codigo) {
        model.buscaPrecosDoProdutoNoBanco(codigo);
    }

    @Override
    public void iniciaProgressBarDialog() {
        view.iniciaProgressBarDialog();
    }

    @Override
    public void encerraProgressBarDialog() {
        view.encerraProgressBarDialog();
    }
}
