package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

import java.util.ArrayList;

public interface DetailsClienteContrato {

    interface View{
        void msgUsuarioNaoSelecionado();
        void executaAcaoBotaoSalvar();
        void voltar();
        void adicionaMaskTelefone();
        void setItemNaoSelecionado(int visibilidade);
        void insereValoresNosEditText();
        void atualizaLista(int tipoDeAtualizacao);
        void adicionaMaskCNPJ();
        boolean ehOperacaoValida();
    }
    interface Model{
        void atualizaUsuarioNoBanco(Cliente cliente);
        String insereUsuarioNoBanco(Cliente cliente);
        void removeUsuarioDoBanco(Cliente cliente);
    }
    interface Presenter{
        void executaAcaoBotaoSalvar(Cliente cliente);
        void setupOrganizacaoDeExibicao(boolean vazio, Cliente cliente);
        void executaAcaoBotaoDeletar(Cliente cliente);
    }

}
