package com.example.felipelevez.aprendizadoandroid_listadeprodutos.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.felipelevez.aprendizadoandroid_listadeprodutos.R;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.ListaClienteFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.ListaProdutosFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.fragments.ProdutosFragment;
import com.example.felipelevez.aprendizadoandroid_listadeprodutos.models.Cliente;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private FragmentManager fragmentManager;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            inflaFragment(ProdutosFragment.newInstance());
        }

        setupToolbar();
        setupNavigationDrawer();
    }

    void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void setupNavigationDrawer(){
        drawer = findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this,  drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

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
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

        switch (id){
            case R.id.drawer_produtos:
                inflaFragment(ProdutosFragment.newInstance());
                break;

            case R.id.drawer_clientes:
                inflaFragment(ListaClienteFragment.newInstance());
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void inflaFragment(Fragment fragment){
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
