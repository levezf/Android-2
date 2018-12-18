package com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters;

import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ActivityMainContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.ActivityMainModel;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Proprietario;

import java.io.File;
import java.util.ArrayList;

public class ActivityMainPresenter implements ActivityMainContrato.Presenter {

    private ActivityMainContrato.Model model;
    private ActivityMainContrato.View view;


    public ActivityMainPresenter(ActivityMainContrato.View view) {
        this.view = view;
    }

    public void getProprietarios(ArrayList<Proprietario> proprietarios, Context context){

        model = new ActivityMainModel();

        for (Proprietario p : proprietarios) {
            model.buscaDadosDoProprietario(p, context);
        }
    }

    @Override
    public void buscaBancosDisponiveis(ArrayList<Proprietario> proprietarios, String path) {



        path += "/databases";
        String pathDataBase = path;

        File f = new File(path);
        File[] files = f.listFiles();
        for (File file : files){
            if(file.isDirectory()){
                proprietarios.add(new Proprietario(null ,null, pathDataBase+"/"+file.getName()+"/"));
            }
        }
    }

}
