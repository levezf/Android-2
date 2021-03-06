package com.example.felipelevez.aprendizadoandroid_listadeprodutos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SqliteConexaoDAO extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bancomovel";
    private static final int DATABASE_VERSION = 1;
    static final String NOME_TABELA_CLIENTE = "GUA_CLIENTES";
    static final String COLUNA_CNPJ = "CLI_CGCCPF";
    static final String COLUNA_NOME = "CLI_RAZAOSOCIAL";
    static final String COLUNA_EMAIL = "CLI_EMAIL";
    static final String COLUNA_TELEFONE = "CLI_TELEFONE";
    static final String COLUNA_ENDERECO_RUA = "CLI_ENDERECO";
    static final String COLUNA_ENDERECO_NUMERO = "CLI_NUMERO";
    static final String COLUNA_CODIGO =" CLI_CODIGOCLIENTE";
    private static final String NOME_TABELA_PRODUTO = "GUA_PRODUTOS";
    private static final String SCRIPT_CRIACAO_TABELA_CLIENTES ="CREATE TABLE IF NOT EXISTS GUA_CLIENTES ( " +
            "CLI_CODIGOCLIENTE TEXT NOT NULL, CLI_RAZAOSOCIAL TEXT, CLI_CGCCPF TEXT, CLI_INSCRESTADUAL TEXT, CLI_ENDERECO TEXT, CLI_NUMERO TEXT, CLI_COMPLEMENTO TEXT, CLI_BAIRRO TEXT, " +
            "CLI_CODIGOMUNICIPIO TEXT, CLI_TELEFONE TEXT, CLI_FAX TEXT, CLI_CEP TEXT, CLI_STATUS TEXT, CLI_NOMEFANTASIA TEXT, CLI_DATACADASTRO TEXT, CLI_CODREGIAO TEXT, CLI_CONDICAOENTREGA TEXT," +
            " CLI_CODRAMO TEXT, CLI_CODTABPRECO TEXT, CLI_ULTIMACOMPRA TEXT, CLI_ULTIMAVISITA TEXT, CLI_OBSCREDITO TEXT, CLI_OBSGERAL TEXT, CLI_EMAIL TEXT, CLI_PRAZOMAXIMO INTEGER, " +
            "CLI_CODIGOFORMAPGTO TEXT, CLI_FORMASPAGAMENTOS TEXT, CLI_DESCFIDELIDADE REAL, CLI_BLOQUEADO TEXT, CLI_ALTTABPRECO TEXT, CLI_CODIGOCONDPGTO TEXT, CLI_FINANCEIRO REAL, " +
            "CLI_PRAZOMINIMOENT INTEGER, CLI_PRAZOMAXIMOFAT INTEGER, CLI_OBRIGARMULTIPLOEMB TEXT, CLI_CLIENTEVIP TEXT, CLI_MOTIVOBLOQUEIO TEXT, CLI_TIPOPESSOA TEXT, CLI_TRANSPORTADORA TEXT, " +
            "CLI_DESCONTO REAL, CLI_TRATARLIMITECRED INTEGER, CLI_TOLERANCIALIMITECRED REAL, CLI_EMPRESAS TEXT, CLI_PRACA TEXT, CLI_LATITUDE REAL, CLI_LONGITUDE REAL, CLI_PESSOA TEXT, " +
            "CLI_ENDERECOENTREGA TEXT, CLI_NUMEROENTREGA TEXT, CLI_COMPLEMENTOENTREGA TEXT, CLI_BAIRROENTREGA TEXT, CLI_CODMUNICIPIOENTREGA TEXT, CLI_CEPENTREGA TEXT, CLI_ENDERECOCOBRANCA TEXT, " +
            "CLI_NUMEROCOBRANCA TEXT, CLI_COMPLEMENTOCOBRANCA TEXT, CLI_BAIRROCOBRANCA TEXT, CLI_CODMUNICIPIOCOBRANCA TEXT, CLI_CEPCOBRANCA TEXT, CLI_EMAILSECUNDARIO TEXT, CLI_EMAILNF TEXT, " +
            "CLI_CODIGOGRUPOCLIENTE INTEGER, CLI_PERCFRETE REAL, CLI_EMPRESAPADRAO TEXT, CLI_PEDIDOMINIMO REAL, CLI_PARCELAMINIMA REAL, CLI_REPRESENTANTE TEXT, CLI_ENVIADO TEXT, CLI_FINANCEIROISENTO TEXT, " +
            "CLI_DATAFUNDACAO TEXT, CLI_SUFRAMA TEXT, CLI_REFERENCIACOMERCIAL1 TEXT, CLI_REFERENCIACOMERCIAL2 TEXT, CLI_REFERENCIACOMERCIAL3 TEXT, CLI_TELEFONEREFERENCIACOMERCIAL1 TEXT, " +
            "CLI_TELEFONEREFERENCIACOMERCIAL2 TEXT, CLI_TELEFONEREFERENCIACOMERCIAL3 TEXT, CLI_AREACOMERCIAL REAL, CLI_CODIGOFAIXAFATURAMENTO TEXT, CLI_TELEFONEREFERENCIABANCARIA TEXT, CLI_PREDIOPROPRIO TEXT, " +
            "CLI_FINANCEIRO_APLICA_TABELA TEXT, CLI_POSSUIREDE TEXT, CLI_NUMEROLOJAS INTEGER, CLI_NUMEROCHECKOUTS INTEGER, CLI_QTDEFUNCIONARIOS INTEGER, CLI_LIMITECREDBONIF REAL, CLI_REGIMEESPECIAL TEXT, " +
            "CLI_VALIDADESIVISA TEXT, CLI_VALIDADECRF TEXT, CLI_NUMEROSIVISA TEXT, CLI_NUMEROCRF TEXT, CLI_MARGEMCONTRIBUICAOMINIMA REAL, CLI_PAGADORFRETEPADRAO TEXT, CLI_TIPOCALCULOFRETE TEXT, " +
            "CLI_REGIMETRIBUTARIO TEXT, PRIMARY KEY ( CLI_CODIGOCLIENTE ) );";

    private static final String SCRIPT_CRIACAO_TABELA_PRODUTOS = "CREATE TABLE IF NOT EXISTS GUA_PRODUTOS ( " +
            "PRO_CODIGOEMPRESA TEXT NOT NULL, PRO_CODIGO TEXT NOT NULL, PRO_CODIGOMARCA TEXT, PRO_CODIGOGRUPO TEXT, PRO_DESCRICAO TEXT NOT NULL, PRO_STATUS TEXT, PRO_DUN14 TEXT, PRO_EAN13 TEXT, PRO_PESOBRUTO REAL," +
            " PRO_QTDEEMBALAGEM INTEGER, PRO_VOLUME TEXT, PRO_OBSERVACAO TEXT, PRO_UNIDPRODUTO TEXT, PRO_TEMPOVALIDADE TEXT, PRO_PERCCOMISSAO REAL, PRO_FLAGVALORFIXO TEXT, PRO_QTDEMINIMAVENDA REAL, " +
            "PRO_QTDEMULTIPLOVENDA REAL, PRO_PRECUSTO REAL, PRO_REFERENCIA TEXT, PRO_CODIGOFORNECEDOR TEXT, PRO_EMBALAGENS TEXT, PRO_SITESTOQUE INTEGER NOT NULL, PRO_IPI REAL NOT NULL, PRO_MKPMINIMO REAL NOT NULL, " +
            "PRO_RAMOSATIVIDADE TEXT NOT NULL, PRO_SEGMENTO TEXT NOT NULL, PRO_LINHA TEXT NOT NULL, PRO_NCM TEXT NOT NULL, PRO_SEGREGACAO INTEGER NOT NULL, PRO_EMPRESAFAT TEXT, PRO_ESTOQUEMINIMO REAL, " +
            "PRO_CODIGOSUBGRUPO TEXT, PRO_CORLEGENDANORMAL TEXT, PRO_TIPOPEDIDOS TEXT, PRO_DESEMBUTEIPI TEXT, PRO_CLA TEXT, PRO_TROCAPROIBIDA TEXT, PRO_GIRO INTEGER, PRO_RETORNO TEXT, PRO_DESPESAOPERACIONAL REAL," +
            " PRO_CONTRIBUICAOMINIMA REAL, PRO_DESCRICAOPDF TEXT, PRO_VRAQUISICAO REAL, PRO_DESCRICAOLONGA TEXT, PRO_FICHA_DADOS TEXT, PRIMARY KEY ( PRO_CODIGOEMPRESA, PRO_CODIGO ) )";

    private static final String SCRIPT_DELECAO_TABELA_CLIENTE = "DROP TABLE IF EXISTS " + NOME_TABELA_CLIENTE;
    private static final String SCRIPT_DELECAO_TABELA_PRODUTO = "DROP TABLE IF EXISTS " + NOME_TABELA_PRODUTO;

    SqliteConexaoDAO(Context context, String localDatabase) {

        super(context,  localDatabase+DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SCRIPT_CRIACAO_TABELA_CLIENTES);
        db.execSQL(SCRIPT_CRIACAO_TABELA_PRODUTOS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SCRIPT_DELECAO_TABELA_CLIENTE);
        db.execSQL(SCRIPT_DELECAO_TABELA_PRODUTO);
        onCreate(db);
    }
}
