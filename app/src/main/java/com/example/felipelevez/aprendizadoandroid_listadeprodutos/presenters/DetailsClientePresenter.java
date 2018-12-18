package com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.DetailsClienteFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.DetailsClienteModel;


public class DetailsClientePresenter implements DetailsClienteContrato.Parent.Presenter {

    private final DetailsClienteContrato.Parent.View view;
    private final DetailsClienteModel model;

    public DetailsClientePresenter(DetailsClienteContrato.Parent.View view, Context context) {

        this.view = view;
        this.model = new DetailsClienteModel(context, this);
    }

    @Override
    public String getLocalDatabase() {
        return view.getLocalDatabase();
    }

    @Override
    public void executaAcaoBotaoSalvar(Cliente cliente) {
        if(view.ehDadosValidos()){
            view.bindCliente();
            if(ehClienteNovo(cliente)){
                cliente.setCodigo(model.insereUsuarioNoBanco(cliente));
                view.atualizaLista(DetailsClienteFragment.INSERIR);
            }else{
                model.atualizaUsuarioNoBanco(cliente);
                view.atualizaLista(DetailsClienteFragment.EDITAR);
            }
        }
    }

    private boolean ehClienteNovo(Cliente cliente){
        return cliente.getCodigo()==null;
    }

    @Override
    public void executaAcaoBotaoDeletar(Cliente cliente) {
        if(!ehClienteNovo(cliente)){
            model.removeUsuarioDoBanco(cliente);
            view.atualizaLista(DetailsClienteFragment.REMOVER);
        }
        view.voltar();
    }
}
