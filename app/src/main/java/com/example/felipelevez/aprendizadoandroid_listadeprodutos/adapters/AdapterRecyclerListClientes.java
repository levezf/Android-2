package com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ClienteClickListener;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

import java.util.ArrayList;
import java.util.Locale;

public class AdapterRecyclerListClientes extends RecyclerView.Adapter<AdapterRecyclerListClientes.ViewHolder> {


    private ArrayList<Cliente> clientes;
    private ClienteClickListener clienteClickListener;

    public AdapterRecyclerListClientes(ArrayList<Cliente> clientes) {
        this.clientes = clientes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.linhas_lista_cliente, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tv_nome.setText(String.format(Locale.getDefault(), "%s",
                clientes.get(i).getNome()));
        viewHolder.bindClick(i);
    }

    @Override
    public int getItemCount() {
        return (clientes!=null)? clientes.size(): 0;
    }


    public void setOnItemClickListener(ClienteClickListener clienteClickListener){
        this.clienteClickListener = clienteClickListener;
    }



    public void insertItem(Cliente cliente) {
        clientes.add(cliente);
        notifyItemInserted(getItemCount());
    }

    public void updateItem(int position) {
        Cliente cliente = clientes.get(position);
        notifyItemChanged(position);
    }

    public void removerItem(int position) {
        clientes.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, clientes.size());
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_nome;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_nome = itemView.findViewById(R.id.tv_nome_cliente);
        }
        private void bindClick(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clienteClickListener.onClienteClick(position);
                }
            });
        }
    }
}
