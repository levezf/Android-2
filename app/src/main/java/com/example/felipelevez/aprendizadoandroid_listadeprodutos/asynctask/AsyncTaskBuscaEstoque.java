package com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

public class AsyncTaskBuscaEstoque extends AsyncTask<Void, Produto, Void> {

    private final ListaProdutosContrato.Presenter presenter;
    private final int posicao_lista;
    private final SQLiteDatabase db;
    private final Produto produto;

    public AsyncTaskBuscaEstoque(ListaProdutosContrato.Presenter presenter, int posicao_lista, SQLiteDatabase db, Produto produto) {
        this.presenter = presenter;
        this.posicao_lista = posicao_lista;
        this.db = db;
        this.produto = produto;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        String sqlQuery = String.format("SELECT ESE_ESTOQUE FROM GUA_ESTOQUEEMPRESA WHERE GUA_ESTOQUEEMPRESA.ESE_CODIGO LIKE '%s' ORDER BY ESE_ESTOQUE DESC LIMIT 1;", produto.getCodigo());
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()){
            produto.setQtdEstoque((int) cursor.getDouble(0));


            try {
                Thread.sleep(1000);
            }catch (Exception ignored){}

        }
        cursor.close();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        presenter.atualizaAdapter(posicao_lista);
    }
}
