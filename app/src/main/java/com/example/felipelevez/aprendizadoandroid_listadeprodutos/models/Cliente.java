package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Cliente implements Parcelable {

    private String nome;
    private String codigo;
    private String telefone;
    private String email;
    private String cnpj;
    private String endereco_rua;
    private String endereco_numero;

    public Cliente(String nome, String codigo, String telefone, String email, String cnpj, String endereco_rua, String endereco_numero) {
        this.nome = nome;
        this.codigo = codigo;
        this.telefone = telefone;
        this.email = email;
        this.cnpj = cnpj;
        this.endereco_rua = endereco_rua;
        this.endereco_numero = endereco_numero;
    }

    public Cliente() {
        this(null, null, null, null, null, null, null);
    }

    private Cliente(Parcel in){
        this.codigo = in.readString();
        this.nome = in.readString();
        this.email =  in.readString();
        this.telefone =  in.readString();
        this.cnpj =  in.readString();
        this.endereco_rua =  in.readString();
        this.endereco_numero =  in.readString();
    }

    public String getEndereco_rua() {
        return endereco_rua;
    }

    public void setEndereco_rua(String endereco_rua) {
        this.endereco_rua = endereco_rua;
    }

    public String getEndereco_numero() {
        return endereco_numero;
    }

    public void setEndereco_numero(String endereco_numero) {
        this.endereco_numero = endereco_numero;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public boolean ehNovoCliente(){
        return this.codigo == null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codigo);
        dest.writeString(this.nome);
        dest.writeString(this.email);
        dest.writeString(this.telefone);
        dest.writeString(this.cnpj);
        dest.writeString(this.endereco_rua);
        dest.writeString(this.endereco_numero);
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };
}
