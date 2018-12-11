package com.example.felipelevez.aprendizadoandroid_listadeprodutos.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.asynctask.AsyncTaskBuscaProdutos;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ListaProdutosContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ProdutoDAOContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

import java.util.ArrayList;

public class ProdutoDAO extends SqliteConexaoDAO implements ProdutoDAOContrato {


    public ProdutoDAO(Context context) {
        super(context);
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
        new AsyncTaskBuscaProdutos(presenter, this.getReadableDatabase(), tipo_lista).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    @Override
    public ArrayList<Produto> getAll(String tipo_lista){

        ArrayList<Produto> produtos = new ArrayList<>();

        String sqlQuery = "SELECT prec.PRP_CODIGO, prec.PRP_UNIVENDA, prod.PRO_DESCRICAO, " +
                "(SELECT MAX(PRP_PRECOS) FROM GUA_PRECOS WHERE GUA_PRECOS.PRP_CODIGO LIKE prec.PRP_CODIGO) AS PRP_VALOR_MAXIMO, "+
                "(SELECT MIN(PRP_PRECOS) FROM GUA_PRECOS WHERE GUA_PRECOS.PRP_CODIGO LIKE prec.PRP_CODIGO) AS PRP_VALOR_MINIMO, "+
                "(SELECT MAX(ESE_ESTOQUE) FROM GUA_ESTOQUEEMPRESA WHERE GUA_ESTOQUEEMPRESA.ESE_CODIGO LIKE prec.PRP_CODIGO) AS ESE_QTD_ESTOQUE "+
                "FROM GUA_PRECOS AS prec " +
                "JOIN GUA_PRODUTOS AS prod "+
                "ON prod.PRO_CODIGO LIKE prec.PRP_CODIGO "+
                "WHERE prod.PRO_STATUS LIKE '"+ tipo_lista +
                "' GROUP BY prec.PRP_CODIGO;";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()){
            do{
                produtos.add(bindProdutos(cursor));
            }while (cursor.moveToNext());
        }

        db.close();
        return produtos;
    }

    @Override
    public ArrayList<Produto> buscaProdutos_ElementosBasicos(String tipo_lista){
        ArrayList<Produto> produtos = new ArrayList<>();

        String sqlQuery = String.format("SELECT prec.PRP_CODIGO, prec.PRP_UNIVENDA, prod.PRO_DESCRICAO FROM GUA_PRECOS AS prec " +
                "JOIN GUA_PRODUTOS AS prod " +
                "ON prod.PRO_CODIGO LIKE prec.PRP_CODIGO " +
                "WHERE prod.PRO_STATUS LIKE '%s' " +
                "GROUP BY prec.PRP_CODIGO;", tipo_lista);

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()){
            do{
                produtos.add(bindProdutos(cursor));
            }while (cursor.moveToNext());
        }

        db.close();
        return produtos;
    }

    @Override
    public String buscaPrecoProduto(String codigo, String ASCorDESC){

        String sqlQuery = String.format("SELECT PRP_PRECOS FROM GUA_PRECOS WHERE PRP_CODIGO LIKE '%s' ORDER BY PRP_PRECOS %s LIMIT 1;", codigo, ASCorDESC);
        SQLiteDatabase db = this.getReadableDatabase();
        String preco=null;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()){
            preco = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return preco;
    }

    @Override
    public Double buscaEstoqueProduto(String codigo){

        String sqlQuery = String.format("SELECT ESE_ESTOQUE FROM GUA_ESTOQUEEMPRESA WHERE GUA_ESTOQUEEMPRESA.ESE_CODIGO LIKE '%s' ORDER BY ESE_ESTOQUE DESC LIMIT 1;", codigo);
        SQLiteDatabase db = this.getReadableDatabase();
        Double qtd = 0.0;
        Cursor cursor = db.rawQuery(sqlQuery, null);
        if(cursor.moveToFirst()){
            qtd = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return qtd;
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

    @Override
    public Produto bindProdutos(Cursor cursor) {

        /* return new Produto(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getDouble(5));*/


        return new Produto(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                null, null,-1);
    }

}
