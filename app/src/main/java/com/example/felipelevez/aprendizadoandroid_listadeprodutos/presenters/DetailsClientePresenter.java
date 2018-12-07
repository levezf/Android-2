package com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters;

import android.content.Context;
import android.view.View;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.DetailsClienteFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.DetailsClienteModel;

public class DetailsClientePresenter implements DetailsClienteContrato.Presenter {

    private DetailsClienteModel model;
    private DetailsClienteContrato.View view;


    public DetailsClientePresenter(DetailsClienteContrato.View view, Context context) {
        this.view = view;
        model = new DetailsClienteModel(context);
    }

    @Override
    public void executaAcaoBotaoSalvar(Cliente cliente) {

        if(view.ehOperacaoValida()) {
            if (ehNovoCliente(cliente)) {
                cliente.setCodigo(model.insereUsuarioNoBanco(cliente));
                view.atualizaLista(DetailsClienteFragment.INSERIR);
            } else {
                model.atualizaUsuarioNoBanco(cliente);
                view.atualizaLista(DetailsClienteFragment.EDITAR);
            }
            view.voltar();
        }


    }
    private boolean ehNovoCliente(Cliente cliente){
        return cliente.getCodigo()==null;
    }

    @Override
    public void setupOrganizacaoDeExibicao(boolean vazio, Cliente cliente) {
        if(!vazio) {
            view.adicionaMaskTelefone();
            view.adicionaMaskCNPJ();
            view.setItemNaoSelecionado(View.INVISIBLE);

            if (!ehNovoCliente(cliente)) {
                view.insereValoresNosEditText();

            }
            view.executaAcaoBotaoSalvar();

        }else{
            view.msgUsuarioNaoSelecionado();
        }
    }

    @Override
    public void executaAcaoBotaoDeletar(Cliente cliente) {
        if(!ehNovoCliente(cliente)){
            model.removeUsuarioDoBanco(cliente);
            view.atualizaLista(DetailsClienteFragment.REMOVER);
        }
    }
}
