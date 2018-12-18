package com.example.felipelevez.aprendizadoandroid_listadeprodutos.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ClienteDAOContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

import java.util.ArrayList;

public class ClienteDAO extends SqliteConexaoDAO implements ClienteDAOContrato{

    public ClienteDAO(Context context, String localDataBase) {
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
    public String insert(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT "+COLUNA_CODIGO+" FROM "+NOME_TABELA_CLIENTE+" ORDER BY "+COLUNA_CODIGO+" DESC LIMIT 1;", null);
        cursor.moveToFirst();
        cliente.setCodigo(String.valueOf(Integer.valueOf((cursor.getString(0)) +1)));
        cursor.close();

        String insert_sql = String.format(
                "INSERT INTO %s  " +
                        "(%s, %s, %s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s');",
                NOME_TABELA_CLIENTE,
                COLUNA_CODIGO, COLUNA_NOME, COLUNA_ENDERECO_RUA, COLUNA_ENDERECO_NUMERO, COLUNA_CNPJ, COLUNA_EMAIL, COLUNA_TELEFONE,
                cliente.getCodigo(), cliente.getNome(), cliente.getEndereco_rua(), cliente.getEndereco_numero(), cliente.getCnpj(), cliente.getEmail(), cliente.getTelefone());

        db.execSQL(insert_sql);

        db.close();
        return cliente.getCodigo();
    }

    @Override
    public Cliente get(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(NOME_TABELA_CLIENTE,
                new String[]{COLUNA_CODIGO, COLUNA_CNPJ, COLUNA_ENDERECO_RUA, COLUNA_ENDERECO_NUMERO, COLUNA_EMAIL, COLUNA_NOME, COLUNA_TELEFONE},
                COLUNA_CODIGO + "=?",
                new String[]{id}, null, null, null, null);
        cursor.moveToFirst();
        Cliente cliente = bindCliente(cursor);
        cursor.close();
        return cliente;
    }

    @Override
    public ArrayList<Cliente> getAll() {

        //SystemClock.sleep(6000);

        ArrayList<Cliente> clientes = new ArrayList<>();                                   //0              1           2                     3                      4                  5        6
        String selectQuery = String.format("SELECT %s, %s, %s, %s, %s, %s, %s  FROM %s;", COLUNA_CODIGO, COLUNA_CNPJ, COLUNA_ENDERECO_RUA, COLUNA_ENDERECO_NUMERO, COLUNA_EMAIL, COLUNA_NOME, COLUNA_TELEFONE, NOME_TABELA_CLIENTE);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                clientes.add(bindCliente(cursor));
            } while (cursor.moveToNext());
        }
        db.close();

        return clientes;
    }

    @Override
    public int getCount() {
        String countQuery = "SELECT  * FROM " + NOME_TABELA_CLIENTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    @Override
    public void update(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql_update = String.format("UPDATE %s SET %s = '%s', %s = '%s', %s = '%s' , %s = '%s', %s = '%s', %s = '%s' WHERE %s = '%s';",
                NOME_TABELA_CLIENTE, COLUNA_CNPJ, cliente.getCnpj(), COLUNA_NOME, cliente.getNome(),
                COLUNA_EMAIL, cliente.getEmail(), COLUNA_TELEFONE, cliente.getTelefone(),
                COLUNA_ENDERECO_NUMERO, cliente.getEndereco_numero(), COLUNA_ENDERECO_RUA, cliente.getEndereco_rua(),
                COLUNA_CODIGO, cliente.getCodigo());
        db.execSQL(sql_update);
    }

    @Override
    public void delete(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(NOME_TABELA_CLIENTE, COLUNA_CODIGO + " = '"+cliente.getCodigo()+"'", null);
        db.close();
    }

    @Override
    public Cliente bindCliente(Cursor cursor) {
        /*return new Cliente(
                cursor.getString(cursor.getColumnIndex(COLUNA_CODIGO_CLIENTE)),
                cursor.getString(cursor.getColumnIndex(COLUNA_NOME)),
                cursor.getString(cursor.getColumnIndex(COLUNA_EMAIL)),
                cursor.getString(cursor.getColumnIndex(COLUNA_TELEFONE)),
                cursor.getString(cursor.getColumnIndex(COLUNA_CNPJ)),
                cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO_NUMERO)),
                cursor.getString(cursor.getColumnIndex(COLUNA_ENDERECO_RUA)));*/
        return new Cliente(
                cursor.getString(5),
                cursor.getString(0),
                cursor.getString(6),
                cursor.getString(4),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
    }

    @Override
    public ContentValues bindContentValuesCliente(Cliente cliente) {
        ContentValues values = new ContentValues();
        values.put(COLUNA_NOME, cliente.getNome());
        values.put(COLUNA_EMAIL, cliente.getEmail());
        values.put(COLUNA_TELEFONE, cliente.getTelefone());
        values.put(COLUNA_CNPJ, cliente.getCnpj());
        values.put(COLUNA_CODIGO, cliente.getCodigo());
        values.put(COLUNA_ENDERECO_NUMERO, cliente.getEndereco_numero());
        values.put(COLUNA_ENDERECO_RUA, cliente.getEndereco_rua());
        return values;
    }

    @Override
    public String buscaUltimoCodigoInserido() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT "+COLUNA_CODIGO+" FROM " + NOME_TABELA_CLIENTE + " ORDER BY"+COLUNA_CODIGO+" DESC", null);
        if(cursor.moveToFirst()){
            String codigo = cursor.getString(cursor.getColumnIndex(COLUNA_CODIGO));
            cursor.close();
            return codigo;
        }
        return null;
    }


}
