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
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.utils.EditTextUtils;

import java.io.Serializable;


public class DetailsClienteEmailFragment extends Fragment implements DetailsClienteContrato.ItensTabs.View, Serializable {

    private EditText email;
    private static final String EXTRA_CLIENTE = "cliente";
    private Cliente cliente;
    private View view;
    private DetailsClienteItensTabsPresenter presenter;


    public DetailsClienteEmailFragment() {

    }

    public static DetailsClienteEmailFragment newInstance() {
        return new DetailsClienteEmailFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_details_cliente_email, container, false);
        this.view = view;
        setRetainInstance(true);

        setupVariaveisFindViewById();

        assert getArguments() != null;
        this.cliente = getArguments().getParcelable(EXTRA_CLIENTE);



        presenter = new DetailsClienteItensTabsPresenter(this, getContext());
        presenter.setupOrganizacaoDeExibicao(cliente);

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_CLIENTE, cliente);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupVariaveisFindViewById(){
        email = view.findViewById(R.id.et_email);
    }

    @Override
    public void bindCliente(){
        cliente.setEmail(email.getText().toString());

    }

    private boolean temCamposNulos(boolean mErro){

        String edit1 = email.getText().toString();

        if (edit1.isEmpty()) {
            if (mErro )
                email.setError( view.getResources().getString(R.string.msg_campo_nao_nulo));
            return true;
        }
        return false;
    }

    @Override
    public void adicionaMasks() {}

    @Override
    public void insereValoresNosEditText() {

        email.setText(cliente.getEmail());
    }

    @Override
    public boolean ehOperacaoValida() {
        return( (!temCamposNulos(true))
                &&  (EditTextUtils.emailEhValido(getContext(), email)));
    }
}
