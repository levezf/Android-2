package com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;


public class AsyncTaskBuscaUnivenda extends AsyncTask<Void, Produto, Void>{

    private final ListaProdutosContrato.Presenter presenter;
    private final int posicao_lista;
    private final SQLiteDatabase db;
    private final Produto produto;

    public AsyncTaskBuscaUnivenda(ListaProdutosContrato.Presenter presenter, int posicao_lista, SQLiteDatabase db, Produto produto) {
        this.presenter = presenter;
        this.posicao_lista = posicao_lista;
        this.db = db;
        this.produto = produto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String sqlQuery = String.format("SELECT PRP_UNIVENDA FROM GUA_PRECOS WHERE PRP_CODIGO LIKE '%s' LIMIT 1;", produto.getCodigo());

        try (Cursor cursor = db.rawQuery(sqlQuery, null)) {
            if (cursor.moveToFirst()) {
                produto.setUniVenda(cursor.getString(0));
            }
        }

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


