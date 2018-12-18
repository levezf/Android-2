package com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
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


public class DetailsClienteDadosFragment extends Fragment implements DetailsClienteContrato.ItensTabs.View, Serializable {

    private EditText nome;
    private EditText cnpj;
    private EditText telefone;
    private static final String EXTRA_CLIENTE = "cliente";
    private Cliente cliente;
    private View view;
    private DetailsClienteItensTabsPresenter presenter;

    public DetailsClienteDadosFragment() {

    }

    public static DetailsClienteDadosFragment newInstance() {
        return new DetailsClienteDadosFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view =  inflater.inflate(R.layout.fragment_details_cliente_dados, container, false);

        this.view = view;
        setRetainInstance (true);
        setupVariaveisFindViewById();

        assert getArguments() != null;
        this.cliente = getArguments().getParcelable(EXTRA_CLIENTE);


        presenter = new DetailsClienteItensTabsPresenter(this, getContext());
        presenter.setupOrganizacaoDeExibicao(cliente);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void setupVariaveisFindViewById(){
        nome = view.findViewById(R.id.et_nome);
        cnpj = view.findViewById(R.id.et_cpf);
        telefone = view.findViewById(R.id.et_telefone);
    }

    @Override
    public void bindCliente(){
        cliente.setNome(nome.getText().toString());
        cliente.setCnpj(cnpj.getText().toString());
        cliente.setTelefone(telefone.getText().toString());
    }

    private boolean temCamposNulos(boolean mErro){

        String edit1 = nome.getText().toString();
        String edit2 = cnpj.getText().toString();
        String edit3 =  telefone.getText().toString();

        if(edit1.isEmpty() || edit2.isEmpty() || edit3.isEmpty()) {
            if (edit1.isEmpty()) {
                if (mErro )
                    this.nome.setError( view.getResources().getString(R.string.msg_campo_nao_nulo));
            }
            if (edit2.isEmpty() ) {
                if (mErro )
                    this.cnpj.setError( view.getResources().getString(R.string.msg_campo_nao_nulo));
            }
            if (edit3.isEmpty() ) {
                if (mErro)
                    this.telefone.setError( view.getResources().getString(R.string.msg_campo_nao_nulo));
            }
            return true;
        }
        return false;
    }

    @Override
    public void adicionaMasks() {
        telefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(getString(R.string.codigo_pais)));
        cnpj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            boolean isUpdating;
            String old = "";
            String maskCNPJ = "##.###.###/####-##";

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String str = (s.toString().replaceAll("[^0-9]*", ""));
                String mask = maskCNPJ;

                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if ((m != '#' && str.length() > old.length()) || (m != '#' && str.length() < old.length() && str.length() != i)) {
                        mascara += m;
                        continue;
                    }

                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                cnpj.setText(mascara);
                cnpj.setSelection(mascara.length());
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void insereValoresNosEditText() {

        nome.setText(cliente.getNome());
        cnpj.setText(cliente.getCnpj());
        telefone.setText(cliente.getTelefone());

    }

    @Override
    public boolean ehOperacaoValida() {
        return( (!temCamposNulos(true))
                &&  (EditTextUtils.cnpjEhValido(getContext(), cnpj))
                &&  (EditTextUtils.phoneEhValido(getContext(), telefone)));
    }
}
