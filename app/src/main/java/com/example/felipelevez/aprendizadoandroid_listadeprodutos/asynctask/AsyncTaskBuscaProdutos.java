package com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask;

import android.Manifest;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.activity.MainActivity;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.database.ProdutoDAO;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;

public class AsyncTaskBuscaProdutos extends AsyncTask<Void, Produto, Void> {


    private final ListaProdutosContrato.Presenter presenter;
    private final SQLiteDatabase db;
    private final String tipoLista;

    public AsyncTaskBuscaProdutos(ListaProdutosContrato.Presenter presenter, SQLiteDatabase db, String tipoLista) {

        this.presenter = presenter;
        this.db = db;
        this.tipoLista= tipoLista;
    }

    @Override
    protected Void doInBackground(Void... voids) {

        String sqlQuery = String.format("SELECT PRO_CODIGO, PRO_DESCRICAO FROM GUA_PRODUTOS WHERE PRO_STATUS LIKE '%s' GROUP BY PRO_CODIGO ORDER BY PRO_CODIGO;", tipoLista);
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()){
            do {
                publishProgress( new Produto(cursor.getString(0), cursor.getString(1)));

                try {
                    Thread.sleep(1000);
                }catch (Exception ignored){}
            }while (cursor.moveToNext());
        }
        cursor.close();
        return null;
    }

    @Override
    protected void onProgressUpdate(Produto... values) {
        presenter.insereItemNoAdapter(values[0]);
        new AsyncTaskBuscaPrecoMaxMin(presenter,(presenter.getItemCountDoAdapter()-1), db, values[0]).executeOnExecutor(new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(15)));
        new AsyncTaskBuscaUnivenda(presenter,(presenter.getItemCountDoAdapter()-1), db, values[0]).executeOnExecutor(new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(15)));
        new AsyncTaskBuscaEstoque(presenter,(presenter.getItemCountDoAdapter()-1), db, values[0]).executeOnExecutor(new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(15)));
    }


}
