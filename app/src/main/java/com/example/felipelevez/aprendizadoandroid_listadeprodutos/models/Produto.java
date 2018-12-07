package com.example.felipelevez.aprendizadoandroid_listadeprodutos.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Produto implements Parcelable {

    private String codigo;
    private String uniVenda;
    private String descricao;
    private String valorMax;
    private String valorMin;
    private double qtdEstoque;

    public Produto(String codigo, String uniVenda, String descricao, String valorMax, String valorMin, double qtdEstoque) {
        this.codigo = codigo;
        this.uniVenda = uniVenda;
        this.descricao = descricao;
        this.valorMax = valorMax;
        this.valorMin = valorMin;
        this.qtdEstoque = qtdEstoque;
    }
    private Produto(Parcel in){
        this.codigo = in.readString();
        this.uniVenda = in.readString();
        this.descricao =  in.readString();
        this.valorMax =  in.readString();
        this.valorMin =  in.readString();
        this.qtdEstoque =  in.readDouble();
    }
    public static final Creator<Produto> CREATOR = new Creator<Produto>() {
        @Override
        public Produto createFromParcel(Parcel in) {
            return new Produto(in);
        }

        @Override
        public Produto[] newArray(int size) {
            return new Produto[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.codigo);
        dest.writeString(this.uniVenda);
        dest.writeString(this.descricao);
        dest.writeString(this.valorMax);
        dest.writeString(this.valorMin);
        dest.writeDouble(this.qtdEstoque);
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setUniVenda(String uniVenda) {
        this.uniVenda = uniVenda;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValorMax(String valorMax) {
        this.valorMax = valorMax;
    }

    public void setValorMin(String valorMin) {
        this.valorMin = valorMin;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getUniVenda() {
        return uniVenda;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getValorMax() {
        return valorMax;
    }

    public String getValorMin() {
        return valorMin;
    }

    public double getQtdEstoque() {
        return qtdEstoque;
    }

}
