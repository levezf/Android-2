package com.example.felipelevez.aprendizadoandroid_listadeprodutos.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.DetailsClienteFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.ListaClienteFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.ProdutosFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.interfaces.ActivityMainContrato;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Proprietario;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.presenters.ActivityMainPresenter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, ActivityMainContrato.View, ActivityCompat.OnRequestPermissionsResultCallback {

    private static final String SAVED_PROPRIETARIOS = "proprietarios";
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView cnpj_drawer;
    private TextView nome_cliente_drawer;
    private ImageView arrow_menu;
    private boolean estaVisivelMenuPrincipal = true;
    private ArrayList<Proprietario> proprietarios = new ArrayList<>();
    private CardView header;
    private Proprietario proprietarioVisivel;
    private static final String SAVED_PROPRIETARIO = "proprietario_visivel";
    private Bundle savedInstanceState;
    private final static int REQUEST_CODE_ASK_PERMISSIONS = 1;
    private static final String[] REQUIRED_SDK_PERMISSIONS = new String[] {
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE };

    public String getPathDataBase() {
        return proprietarioVisivel.getLocalBanco();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;

        checkPermissions();
    }

    protected void checkPermissions() {
        final List<String> missingPermissions = new ArrayList<>();
        for (final String permission : REQUIRED_SDK_PERMISSIONS) {
            final int result = ContextCompat.checkSelfPermission(this, permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }
        if (!missingPermissions.isEmpty()) {
            final String[] permissions = missingPermissions
                    .toArray(new String[0]);
            ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_ASK_PERMISSIONS);
        } else {
            final int[] grantResults = new int[REQUIRED_SDK_PERMISSIONS.length];
            Arrays.fill(grantResults, PackageManager.PERMISSION_GRANTED);
            onRequestPermissionsResult(REQUEST_CODE_ASK_PERMISSIONS, REQUIRED_SDK_PERMISSIONS,
                    grantResults);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_PERMISSIONS:
                for (int index = permissions.length - 1; index >= 0; --index) {
                    if (grantResults[index] != PackageManager.PERMISSION_GRANTED) {
                        alertDialogMsgErroPermissao();
                        return;
                    }
                }
                setupOnCreate();
                break;
        }
    }

    private void setupOnCreate(){
        setupFindViewByIds();
        setupToolbar();
        setupNavigationDrawer();
        setupMenuPrincipal();
        ActivityMainPresenter presenter = new ActivityMainPresenter(this);
        if(savedInstanceState!= null){
            proprietarioVisivel = savedInstanceState.getParcelable(SAVED_PROPRIETARIO);
            proprietarios = savedInstanceState.getParcelableArrayList(SAVED_PROPRIETARIOS);
        }else{
            presenter.buscaBancosDisponiveis(proprietarios,Environment.getExternalStorageDirectory().getPath());

            presenter.getProprietarios(proprietarios, getApplicationContext());
            proprietarioVisivel = proprietarios.get(0);
        }
        assert proprietarioVisivel !=null;
        setupHeaderDrawerProprietario(proprietarioVisivel);

        if(savedInstanceState==null){
            inflaFragment(ProdutosFragment.newInstance());
        }
    }

    private void alertDialogMsgErroPermissao(){
        new AlertDialog.Builder(this).setTitle("Permissões negadas").
                setMessage("Não será possivel continuar com a aplicação. O aplicativo será fechado.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_PROPRIETARIO, proprietarioVisivel);
        outState.putParcelableArrayList(SAVED_PROPRIETARIOS, proprietarios);
    }

    private void setupFindViewByIds(){
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);

        View headerView = navigationView.getHeaderView(0);
        arrow_menu = headerView.findViewById(R.id.arrow);
        cnpj_drawer = headerView.findViewById(R.id.cnpj_banco);
        nome_cliente_drawer = headerView.findViewById(R.id.nome_cliente_banco);
        header = headerView.findViewById(R.id.header);
    }

    void setupToolbar(){
        setSupportActionBar(toolbar);
    }

    public void setupNavigationDrawer(){
        toggle = new ActionBarDrawerToggle(this,  drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setupMenusDrawer();
    }

    private void setupMenusDrawer() {

        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!estaVisivelMenuPrincipal){
                    setupMenuPrincipal();
                }else{
                    setupMenuClientes();
                }
            }
        });

    }

    private void setupMenuPrincipal(){
        navigationView.getMenu().clear();
        arrow_menu.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_down_black_24dp));
        navigationView.inflateMenu(R.menu.menu_drawer);
        estaVisivelMenuPrincipal = true;
    }

    private void setupMenuClientes(){
        navigationView.getMenu().clear();
        int i=3;
        for(Proprietario p: proprietarios){

            navigationView.getMenu().add(5, i, 0, p.getCnpj());
            i++;
        }
        arrow_menu.setImageDrawable(getDrawable(R.drawable.ic_keyboard_arrow_up_black_24dp));
        estaVisivelMenuPrincipal = false;
    }

    public void setupNavigationDrawerOFF(){
        drawer.removeDrawerListener(toggle);
        toggle.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().popBackStack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Fragment currentFragment;

            currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);

            if(currentFragment instanceof  ListaClienteFragment  ||  currentFragment instanceof DetailsClienteFragment){

                getSupportFragmentManager().popBackStackImmediate();

            }else  {
                finish();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(item.getGroupId() == R.id.menu_drawer){
            switch (id){
                case R.id.drawer_produtos:
                    inflaFragment(ProdutosFragment.newInstance());
                    break;

                case R.id.drawer_clientes:
                    inflaFragment(ListaClienteFragment.newInstance());
                    break;

            }
        }else{
            for(int i=0; i<proprietarios.size(); i++){
                if(id == i+3){
                    proprietarioVisivel = proprietarios.get(i);
                    inflaFragment(ProdutosFragment.newInstance());
                    setupHeaderDrawerProprietario(proprietarioVisivel);


                }
            }
        }
        setupMenuPrincipal();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupHeaderDrawerProprietario(Proprietario proprietarioVisivel) {

        nome_cliente_drawer.setText(proprietarioVisivel.getNome());
        cnpj_drawer.setText(proprietarioVisivel.getCnpj());
    }

    public void inflaFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
