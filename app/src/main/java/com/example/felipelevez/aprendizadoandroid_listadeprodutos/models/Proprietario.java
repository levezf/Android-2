package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Proprietario implements Parcelable {

    private String cnpj;
    private String nome;
    private String localBanco;

    public Proprietario() {
        this(null , null,  null);
    }

    public Proprietario(String cnpj, String nome, String localBanco) {
        this.cnpj = cnpj;
        this.nome = nome;
        this.localBanco = localBanco;
    }

    public String getLocalBanco() {
        return localBanco;
    }

    public void setLocalBanco(String localBanco) {
        this.localBanco = localBanco;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nome);
        dest.writeString(this.cnpj);
        dest.writeString(this.localBanco);
    }

    private Proprietario(Parcel in){
        this.nome = in.readString();
        this.cnpj = in.readString();
        this.localBanco = in.readString();
    }

    public static final Creator<Proprietario> CREATOR = new Creator<Proprietario>() {
        @Override
        public Proprietario createFromParcel(Parcel in) {
            return new Proprietario(in);
        }

        @Override
        public Proprietario[] newArray(int size) {
            return new Proprietario[size];
        }
    };
}
