package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.content.ContentValues;
import android.content.Context;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ProprietarioDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ActivityMainContrato;

import java.util.ArrayList;

public class ActivityMainModel implements ActivityMainContrato.Model {


    private ProprietarioDAO proprietarioDAO;

    @Override
    public void buscaDadosDoProprietario(Proprietario proprietario, Context context) {
        proprietarioDAO = new ProprietarioDAO(context, proprietario.getLocalBanco());
        proprietarioDAO.getProprietario(proprietario);
    }
}