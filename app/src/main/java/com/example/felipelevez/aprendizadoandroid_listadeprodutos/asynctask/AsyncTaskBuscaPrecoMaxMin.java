package com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

public class AsyncTaskBuscaPrecoMaxMin extends AsyncTask<Void, Produto, Void> {


    private final ListaProdutosContrato.Presenter presenter;
    private final int posicao_lista;
    private final SQLiteDatabase db;
    private final Produto produto;

    public AsyncTaskBuscaPrecoMaxMin(ListaProdutosContrato.Presenter presenter, int posicao_lista, SQLiteDatabase db, Produto produto) {
        this.presenter = presenter;
        this.posicao_lista = posicao_lista;
        this.db = db;
        this.produto = produto;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        String sqlQuery = String.format("SELECT MAX(PRP_PRECOS), MIN(PRP_PRECOS) FROM GUA_PRECOS WHERE PRP_CODIGO LIKE '%s';", produto.getCodigo());
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()){
           produto.setValorMax(cursor.getString(0));
           produto.setValorMin(cursor.getString(1));
        }
        cursor.close();

        try {
            Thread.sleep(2000);
        }catch (Exception ignored){}

        return null;

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        presenter.atualizaAdapter(posicao_lista);
        Thread.currentThread().interrupt();

    }
}
