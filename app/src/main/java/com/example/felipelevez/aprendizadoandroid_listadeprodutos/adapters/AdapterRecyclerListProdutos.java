package com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.ProdutosFragment;
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
        setHasStableIds(true);
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


        if(produtos.get(i).getValorMax() == null || produtos.get(i).getValorMin() == null ){

            viewHolder.precoMax.setVisibility(View.INVISIBLE);
            viewHolder.precoMin.setVisibility(View.INVISIBLE);
            viewHolder.progressBarPrecos.setVisibility(View.VISIBLE);

        }else{
            viewHolder.precoMax.setText(String.format(Locale.getDefault(), "%s",
                    "R$ " + produtos.get(i).getValorMax()));

            viewHolder.precoMin.setText(String.format(Locale.getDefault(), "%s",
                    "R$ " + produtos.get(i).getValorMin()));

            viewHolder.precoMax.setVisibility(View.VISIBLE);
            viewHolder.precoMin.setVisibility(View.VISIBLE);
            viewHolder.progressBarPrecos.setVisibility(View.INVISIBLE);
        }


        if(produtos.get(i).getQtdEstoque() == -1 || produtos.get(i).getUniVenda() == null ){

            viewHolder.qtdUnidadeProduto.setVisibility(View.INVISIBLE);
            viewHolder.unidadeProduto.setVisibility(View.INVISIBLE);
            viewHolder.progressBarEstoque.setVisibility(View.VISIBLE);


        }else{
            viewHolder.unidadeProduto.setText(String.format(Locale.getDefault(), "%s",
                    produtos.get(i).getUniVenda()));

            viewHolder.qtdUnidadeProduto.setText(String.format(Locale.getDefault(), "%s",
                    Double.toString((int) produtos.get(i).getQtdEstoque())));

            viewHolder.qtdUnidadeProduto.setVisibility(View.VISIBLE);
            viewHolder.unidadeProduto.setVisibility(View.VISIBLE);
            viewHolder.progressBarEstoque.setVisibility(View.INVISIBLE);
        }

        int color = viewHolder.itemView.getResources().getColor(R.color.branco);

        switch (viewHolder.tipoLista.getText().toString()){
            case ProdutosFragment
                    .LISTA_LANCAMENTO:
                color = viewHolder.itemView.getResources().getColor(R.color.verde);
                break;
            case ProdutosFragment
                    .LISTA_PRECO_ESTOQUE:
                color = viewHolder.itemView.getResources().getColor(R.color.laranja);
                break;
            case ProdutosFragment
                    .LISTA_PROMOCAO:
                color = viewHolder.itemView.getResources().getColor(R.color.azul);
                break;
        }

        viewHolder.tipoLista.setTextColor(color);


        viewHolder.bindClick(i);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void insertItem(Produto produto) {
        this.produtos.add(produto);
        notifyItemInserted(getItemCount());
    }

    public void updateItem(int position) {
        Produto produto = produtos.get(position);
        notifyItemChanged(position);
    }

    @Override
    public int getItemCount(){
        return(produtos!=null)?produtos.size():0;
    }

    public void setAll(ArrayList<Produto> produtos){
        this.produtos = produtos;
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
        private ProgressBar progressBarPrecos;
        private ProgressBar progressBarEstoque;

        private View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tipoLista = itemView.findViewById(R.id.tv_tipo_lista);
            codigoProduto = itemView.findViewById(R.id.tv_codigo_produto);
            descricaoProduto = itemView.findViewById(R.id.tv_descricao_produto);
            qtdUnidadeProduto = itemView.findViewById(R.id.tv_qtd_unidade_produto);
            unidadeProduto = itemView.findViewById(R.id.tv_unidade_produto);
            precoMax = itemView.findViewById(R.id.tv_preco_max);
            precoMin = itemView.findViewById(R.id.tv_preco_menor);
            progressBarPrecos = itemView.findViewById(R.id.progressBarPrecos);
            progressBarEstoque = itemView.findViewById(R.id.progressBarEstoque);
            this.itemView = itemView;



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