package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

import java.util.ArrayList;

public interface DetailsClienteContrato {

    interface ItensTabs{

        interface View{
            void adicionaMasks();
            void insereValoresNosEditText();
            boolean ehOperacaoValida();
            void bindCliente();

        }
        interface Presenter{
            void setupOrganizacaoDeExibicao(Cliente cliente);
        }

    }

    interface Parent{

        interface View{
            String getLocalDatabase();
            void atualizaLista(int tipoDeAtualizacao);
            void voltar();
            boolean ehDadosValidos();
            void executaAcaoBotaoSalvar();
            void bindCliente();
        }

        interface Presenter{
            String getLocalDatabase();
            void executaAcaoBotaoSalvar(Cliente cliente);
            void executaAcaoBotaoDeletar(Cliente cliente);
        }

        interface Model{
            void atualizaUsuarioNoBanco(Cliente cliente);
            String insereUsuarioNoBanco(Cliente cliente);
            void removeUsuarioDoBanco(Cliente cliente);
        }
    }


}
