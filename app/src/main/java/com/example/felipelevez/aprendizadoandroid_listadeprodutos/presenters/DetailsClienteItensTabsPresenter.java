package com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

public class DetailsClienteItensTabsPresenter implements DetailsClienteContrato.ItensTabs.Presenter {

    private final DetailsClienteContrato.ItensTabs.View view;

    public DetailsClienteItensTabsPresenter(DetailsClienteContrato.ItensTabs.View view) {
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