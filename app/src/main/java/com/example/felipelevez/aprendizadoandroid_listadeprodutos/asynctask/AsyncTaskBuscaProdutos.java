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
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingDeque;
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
                    Thread.sleep(500);
                }catch (Exception ignored){}

            }while (cursor.moveToNext());
        }else
            publishProgress( null);
        cursor.close();


        return null;
    }

    @Override
    protected void onProgressUpdate(Produto... values) {

        if (values == null){
            presenter.mostraListaVazia(true);
        }else{

            presenter.mostraListaVazia(false);

            presenter.insereItemNoAdapter(values[0]);

            ThreadPoolExecutor executor;
            executor= new ThreadPoolExecutor( 1,  Runtime.getRuntime().availableProcessors()/2, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
            executor.allowCoreThreadTimeOut(true);

            new AsyncTaskBuscaPrecoMaxMin(presenter,(presenter.getItemCountDoAdapter()-1), db, values[0]).executeOnExecutor(executor);

            executor= new ThreadPoolExecutor( 1,  Runtime.getRuntime().availableProcessors()/2, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
            executor.allowCoreThreadTimeOut(true);


            new AsyncTaskBuscaUnivenda(presenter,(presenter.getItemCountDoAdapter()-1), db, values[0]).executeOnExecutor(executor);

            executor= new ThreadPoolExecutor( 1,  Runtime.getRuntime().availableProcessors()/2, 1, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>());
            executor.allowCoreThreadTimeOut(true);

            new AsyncTaskBuscaEstoque(presenter,(presenter.getItemCountDoAdapter()-1), db, values[0]).executeOnExecutor(executor);
        }

    }

    @Override
    protected void onPostExecute(Void aVoid) {
        Thread.currentThread().interrupt();

    }
}
