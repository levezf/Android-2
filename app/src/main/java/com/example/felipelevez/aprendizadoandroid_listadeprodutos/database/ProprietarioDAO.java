package com.example.felipelevez.aprendizadoandroid_listadeprodutos.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Proprietario;

public class ProprietarioDAO extends SqliteConexaoDAO {

    public ProprietarioDAO(Context context, String localDatabase) {
        super(context, localDatabase);
    }

    public void getProprietario(Proprietario proprietario){

        String sql_query = "SELECT PAR_NOME, PAR_CNPJEMPRESA FROM GUA_PARAMETROS;";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(sql_query, null);
        if(cursor.moveToFirst()){
            proprietario.setNome(cursor.getString(0));
            proprietario.setCnpj(cursor.getString(1));
        }
        cursor.close();
    }
}
