package com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.adapters.AdapterRecyclerListClientes;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.DetailsClienteContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.DetailsClientePresenter;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.utils.EditTextUtils;


public class DetailsClienteFragment extends Fragment implements DetailsClienteContrato.View {

    private static final String EXTRA_CLIENTE = "cliente";
    private static final String EXTRA_POSITION = "position";
    private Cliente cliente;
    private int position_lista;
    private View view;
    private FloatingActionButton fab;

    public static final int INSERIR  = 1;
    public static final int REMOVER  = 2;
    public static final int EDITAR  = 3;

    private EditText nome;
    private EditText cnpj;
    private EditText telefone;
    private EditText email;
    private EditText endereco_rua;
    private EditText endereco_numero;

    private TextInputLayout hintAnimationNome;
    private TextInputLayout hintAnimationEmail;
    private TextInputLayout hintAnimationTelefone;
    private TextInputLayout hintAnimationCnpj;
    private TextInputLayout hintAnimationEndereco;
    private TextInputLayout hintAnimationNumero;


    private AdapterRecyclerListClientes adapterRecyclerListClientes;
    private boolean vazio = false;
    private DetailsClientePresenter presenter;

    public DetailsClienteFragment(AdapterRecyclerListClientes adapterRecyclerListClientes) {
        this.adapterRecyclerListClientes =adapterRecyclerListClientes;
    }

    public DetailsClienteFragment() {
    }

    public static DetailsClienteFragment newInstance() {
        return new DetailsClienteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view =  inflater.inflate(R.layout.fragment_details_cliente, container, false);

        if (savedInstanceState != null){
            assert getArguments() != null;
            cliente = savedInstanceState.getParcelable(EXTRA_CLIENTE);
            position_lista = savedInstanceState.getInt(EXTRA_POSITION);
        }else{
            assert getArguments() != null;
            cliente = getArguments().getParcelable(EXTRA_CLIENTE);
            position_lista = getArguments().getInt(EXTRA_POSITION);
        }

        this.view = view;

        setupVariaveisFindViewById();
        presenter = new DetailsClientePresenter(this, getContext());
        presenter.setupOrganizacaoDeExibicao(vazio, cliente);


        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_CLIENTE, cliente);
        outState.putInt(EXTRA_POSITION, position_lista);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public void executaAcaoBotaoSalvar() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindCliente();
                presenter.executaAcaoBotaoSalvar(cliente);
            }
        });

    }
    private void bindCliente(){
        cliente.setEmail(email.getText().toString());
        cliente.setNome(nome.getText().toString());
        cliente.setTelefone(telefone.getText().toString());
        cliente.setCnpj(cnpj.getText().toString());
        cliente.setEndereco_rua(endereco_rua.getText().toString());
        cliente.setEndereco_numero(endereco_numero.getText().toString());
    }


    private void msgErroCamposNulos(){
        assert  getActivity()!=null;
        Snackbar.make(view, R.string.msg_preencher_todos_os_campos, Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void adicionaMaskTelefone() {
        telefone.addTextChangedListener(new PhoneNumberFormattingTextWatcher(getString(R.string.codigo_pais)));
    }

    @Override
    public void setItemNaoSelecionado(int visibilidade) {
       // tv_item_nao_selecionado.setVisibility(visibilidade);
    }

    @Override
    public void atualizaLista(int tipoDeAtualizacao){
        switch (tipoDeAtualizacao){
            case INSERIR:
                adapterRecyclerListClientes.insertItem(cliente);
                break;
            case REMOVER:
                adapterRecyclerListClientes.removerItem(position_lista);
                break;
            case EDITAR:
                adapterRecyclerListClientes.updateItem(position_lista);
                break;
        }
    }

    @Override
    public void adicionaMaskCNPJ() {
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
    public boolean ehOperacaoValida() {

        return (!temCamposNulos(true)  &&
                EditTextUtils.cnpjEhValido(getContext(), cnpj) &&
                EditTextUtils.emailEhValido(getContext(), email)  &&
                EditTextUtils.phoneEhValido(getContext(), telefone));
    }


    @Override
    public void insereValoresNosEditText() {
        nome.setText(cliente.getNome());
        email.setText(cliente.getEmail());
        telefone.setText(cliente.getTelefone());
        cnpj.setText(cliente.getCnpj());
        endereco_rua.setText(cliente.getEndereco_rua());
        endereco_numero.setText(cliente.getEndereco_numero());
    }

    private void setupVariaveisFindViewById() {
        //tv_item_nao_selecionado = view.findViewById(R.id.tv_user_nao_selecionado);
        nome = view.findViewById(R.id.et_nome);
        email = view.findViewById(R.id.et_email);
        telefone = view.findViewById(R.id.et_telefone);
        cnpj = view.findViewById(R.id.et_cpf);
        endereco_rua = view.findViewById(R.id.et_rua);
        endereco_numero = view.findViewById(R.id.et_numero);
        fab = view.findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp, getResources().newTheme()));

        hintAnimationNome = view.findViewById(R.id.hintAnimationNome);
        hintAnimationEmail = view.findViewById(R.id.hintAnimationEmail);
        hintAnimationTelefone = view.findViewById(R.id.hintAnimationPhone);
        hintAnimationCnpj = view.findViewById(R.id.hintAnimationCNPJ);
        hintAnimationEndereco = view.findViewById(R.id.hintAnimationRua);
        hintAnimationNumero = view.findViewById(R.id.hintAnimationNumero);
    }


   /* private boolean ehTabletSW600() {
        return getResources().getBoolean(R.bool.twoPaneMode);
    }
*/

    @Override
    public void msgUsuarioNaoSelecionado() {
       // tv_item_nao_selecionado.setVisibility(View.VISIBLE);
        nome.setVisibility(View.INVISIBLE);
        email.setVisibility(View.INVISIBLE);
        telefone.setVisibility(View.INVISIBLE);
        endereco_rua.setVisibility(View.INVISIBLE);
        endereco_numero.setVisibility(View.INVISIBLE);
        cnpj.setVisibility(View.INVISIBLE);

        hintAnimationEmail.setVisibility(View.INVISIBLE);
        hintAnimationTelefone.setVisibility(View.INVISIBLE);
        hintAnimationCnpj.setVisibility(View.INVISIBLE);
        hintAnimationNome.setVisibility(View.INVISIBLE);
        hintAnimationEndereco.setVisibility(View.INVISIBLE);
        hintAnimationNumero.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(!vazio) {
            int id = item.getItemId();

            if (id == R.id.action_apagar) {
                if (cliente.getCodigo()!=null) {
                    bindCliente();
                    presenter.executaAcaoBotaoDeletar(cliente);
                }
                voltar();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void voltar(){
        /*if(ehTabletSW600())
            voltaInicioSW600();
        else*/
            voltaInicio();
    }


  /*  private void voltaInicioSW600() {
        assert getActivity() !=null ;

        ((MainActivity) getActivity()).inflaFragment(ListaClienteFragment.newInstance(), R.id.fragment_lista);
        ((MainActivity) getActivity()).alteraUserFragment(new User(),R.id.fragment_details, true);
    }*/

    private void voltaInicio() {
        if (getActivity() != null)

            getActivity().getSupportFragmentManager().popBackStackImmediate();
         //((MainActivity) getActivity()).inflaFragment(ListaClienteFragment.newInstance());

    }


    private boolean temCamposNulos( boolean mErro) {

        String edit1 = nome.getText().toString();
        String edit3 = email.getText().toString();
        String edit4 = telefone.getText().toString();
        String edit2 = cnpj.getText().toString();
        String edit6 = endereco_numero.getText().toString();
        String edit5 = endereco_rua.getText().toString();

        if(edit1.isEmpty() || edit2.isEmpty() || edit3.isEmpty() ||
            edit4.isEmpty() || edit5.isEmpty() || edit6.isEmpty()) {
            if (edit1.isEmpty()) {
                if (mErro)
                    nome.setError(getString(R.string.msg_campo_nao_nulo));
            }
            if (edit2.isEmpty()) {
                if (mErro)
                    cnpj.setError(getString(R.string.msg_campo_nao_nulo));
            }
            if (edit3.isEmpty()) {
                if (mErro)
                    email.setError(getString(R.string.msg_campo_nao_nulo));
            }
            if (edit4.isEmpty()) {
                if (mErro)
                    telefone.setError(getString(R.string.msg_campo_nao_nulo));
            }
            if (edit5.isEmpty()) {
                if (mErro)
                    endereco_rua.setError(getString(R.string.msg_campo_nao_nulo));
            }
            if (edit6.isEmpty()) {
                if (mErro)
                    endereco_numero.setError(getString(R.string.msg_campo_nao_nulo));
            }

            return true;

        }
        return false;

    }



}
