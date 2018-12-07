package com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ClienteClickListener;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ProdutoClickListener;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Produto;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterRecyclerListProdutos extends RecyclerView.Adapter<AdapterRecyclerListProdutos.ViewHolder>{


    private ArrayList<Produto> produtos;
    private String tipoLista;
    private ProdutoClickListener produtoClickListener;

    public AdapterRecyclerListProdutos(ArrayList<Produto> produtos, String tipoLista){
        this.produtos=produtos;
        this.tipoLista = tipoLista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.linhas_lista_produtos,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder,int i){

        viewHolder.tipoLista.setText(String.format(Locale.getDefault(),"%s",
                tipoLista));

        viewHolder.codigoProduto.setText(String.format(Locale.getDefault(),"%s",
                produtos.get(i).getCodigo()));

        viewHolder.descricaoProduto.setText(String.format(Locale.getDefault(),"%s",
                produtos.get(i).getDescricao()));

        viewHolder.qtdUnidadeProduto.setText(String.format(Locale.getDefault(),"%s",
                Double.toString(produtos.get(i).getQtdEstoque())));

        viewHolder.unidadeProduto.setText(String.format(Locale.getDefault(),"%s",
                produtos.get(i).getUniVenda()));

        viewHolder.precoMax.setText(String.format(Locale.getDefault(),"%s",
                produtos.get(i).getValorMax()));

        viewHolder.precoMin.setText(String.format(Locale.getDefault(),"%s",
                produtos.get(i).getValorMin()));


        viewHolder.bindClick(i);
    }

    @Override
    public int getItemCount(){
        return(produtos!=null)?produtos.size():0;
    }

    public void setOnItemClickListener(ProdutoClickListener produtoClickListener){
        this.produtoClickListener = produtoClickListener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tipoLista;
        private TextView codigoProduto;
        private TextView descricaoProduto;
        private TextView qtdUnidadeProduto;
        private TextView unidadeProduto;
        private TextView precoMax;
        private TextView precoMin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipoLista = itemView.findViewById(R.id.tv_tipo_lista);
            codigoProduto = itemView.findViewById(R.id.tv_codigo_produto);
            descricaoProduto = itemView.findViewById(R.id.tv_descricao_produto);
            qtdUnidadeProduto = itemView.findViewById(R.id.tv_qtd_unidade_produto);
            unidadeProduto = itemView.findViewById(R.id.tv_unidade_produto);
            precoMax = itemView.findViewById(R.id.tv_preco_max);
            precoMin = itemView.findViewById(R.id.tv_preco_menor);

        }

        private void bindClick(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    produtoClickListener.onProdutoClick(position);
                }
            });
        }
    }
}