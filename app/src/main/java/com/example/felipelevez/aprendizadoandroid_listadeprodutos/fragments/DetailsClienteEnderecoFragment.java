package com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.DetailsClienteItensTabsPresenter;

import java.io.Serializable;


public class DetailsClienteEnderecoFragment extends Fragment implements DetailsClienteContrato.ItensTabs.View, Serializable {

    private EditText endereco_numero;
    private EditText endereco_rua;
    private static final String EXTRA_CLIENTE = "cliente";
    private Cliente cliente;
    private View view;

    public DetailsClienteEnderecoFragment() {

    }

    public static DetailsClienteEnderecoFragment newInstance() {
        return new DetailsClienteEnderecoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_details_cliente_endereco, container, false);

        this.view = view;
        setRetainInstance (true);

        setupVariaveisFindViewById();

        assert getArguments() != null;
        this.cliente = getArguments().getParcelable(EXTRA_CLIENTE);

        DetailsClienteItensTabsPresenter presenter = new DetailsClienteItensTabsPresenter(this);
        presenter.setupOrganizacaoDeExibicao(cliente);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_CLIENTE, cliente);
    }

    private void setupVariaveisFindViewById(){
        endereco_rua = view.findViewById(R.id.et_rua);
        endereco_numero = view.findViewById(R.id.et_numero);
    }

    @Override
    public void bindCliente(){
        cliente.setEndereco_rua(endereco_rua.getText().toString());
        cliente.setEndereco_numero(endereco_numero.getText().toString());
    }

    private boolean temCamposNulos(boolean mErro){

        String edit1 = endereco_numero.getText().toString();
        String edit2 = endereco_rua.getText().toString();


        if(edit1.isEmpty() || edit2.isEmpty()) {
            if (edit1.isEmpty() ) {
                if (mErro)
                    endereco_numero.setError( view.getResources().getString(R.string.msg_campo_nao_nulo));
            }
            if (edit2.isEmpty() ) {
                if (mErro)
                    endereco_rua.setError( view.getResources().getString(R.string.msg_campo_nao_nulo));
            }

            return true;
        }
        return false;
    }

    @Override
    public void adicionaMasks() { }

    @Override
    public void insereValoresNosEditText() {
        endereco_numero.setText(cliente.getEndereco_numero());
        endereco_rua.setText(cliente.getEndereco_rua());
    }

    @Override
    public boolean ehOperacaoValida() {
        return((!temCamposNulos(true)));
    }
}
