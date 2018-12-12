package com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters;

import android.content.Context;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

public class DetailsClienteItensTabsPresenter implements DetailsClienteContrato.ItensTabs.Presenter {


    private DetailsClienteContrato.ItensTabs.View view;
    private Context context;

    public DetailsClienteItensTabsPresenter(DetailsClienteContrato.ItensTabs.View view, Context context) {

        this.context = context;
        this.view = view;

    }

    @Override
    public void setupOrganizacaoDeExibicao(Cliente cliente) {

        view.adicionaMasks();
        if(cliente.getCodigo()!= null){
            view.insereValoresNosEditText();
        }

    }
}