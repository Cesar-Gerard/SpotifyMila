package com.example.spotify;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.spotify.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import model.classes.Album;

public  class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    ActivityMainBinding b;


    NavigationView navigationview;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b = ActivityMainBinding.inflate(getLayoutInflater());
        View view = b.getRoot();
        setContentView(view);



        //Creem la llista de albums la primera vegada que engeguem
        Album.createList();

        porgramarMenuDesplagable();



    }


    //Configurem el menu desplegable lateral y el conectem amb el nostre menu personalitzat
    private void porgramarMenuDesplagable() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationview = findViewById(R.id.nav_view);
        navigationview.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toogle = new ActionBarDrawerToggle(this, b.drawerLayout, toolbar, R.string.open_nav,R.string.close_nav);

        b.drawerLayout.addDrawerListener(toogle);
        toogle.syncState();




    }


    //Retirada del menu
    @Override
    public void onBackPressed() {
        if(b.drawerLayout.isDrawerOpen(GravityCompat.START)){
            b.drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }



    //Logica de navegació del menu a travès dels items determinats
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.nav_MyMusic){
            Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_global_myMusic);
        }else if(item.getItemId()==R.id.nav_Download){
           Navigation.findNavController(this,R.id.nav_host_fragment).navigate(R.id.action_global_download_Albums);
        } else if(item.getItemId()==R.id.nav_logout){
            finishAffinity();
        }

        b.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }




}