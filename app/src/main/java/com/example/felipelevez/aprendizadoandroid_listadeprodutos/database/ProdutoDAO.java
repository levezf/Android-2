package com.example.felipelevez.aprendizadoandroid_listadeprodutos.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask.AsyncTaskBuscaProdutos;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ProdutoDAOContrato;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProdutoDAO extends SqliteConexaoDAO implements ProdutoDAOContrato {


    public ProdutoDAO(Context context, String localDataBase) {
        super(context, localDataBase);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        super.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }


    @Override
    public void getAll(String tipo_lista, ListaProdutosContrato.Presenter presenter){
        new AsyncTaskBuscaProdutos(presenter, this.getReadableDatabase(), tipo_lista).executeOnExecutor(new ThreadPoolExecutor( 1,  Runtime.getRuntime().availableProcessors(), 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>()));
    }



    @Override
    public ArrayList<String> getPrecosDoProduto(String codigoProduto){

        ArrayList<String> precos = new ArrayList<>();
        String sqlQuery = "SELECT PRP_PRECOS FROM GUA_PRECOS WHERE PRP_CODIGO LIKE '"+codigoProduto+"';";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);

        if(cursor.moveToFirst()){
            do{
                precos.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        cursor.close();

        return precos;
    }


}
