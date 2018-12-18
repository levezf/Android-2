package com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Proprietario;

import java.util.ArrayList;

public interface ActivityMainContrato {


    interface View{
    }
    interface Model{
        void buscaDadosDoProprietario(Proprietario proprietario, Context context);
    }
    interface Presenter{
        void buscaBancosDisponiveis(ArrayList<Proprietario> proprietarios, String path);
        void getProprietarios(ArrayList<Proprietario> proprietarios, Context context);
    }

}
