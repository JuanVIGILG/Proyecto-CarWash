package com.example.carwash;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.carwash.Historial.CambiosAceiteFragment;
import com.example.carwash.Historial.LavadosFragment;
import com.example.carwash.Login.ActivityLogin;
import com.example.carwash.databinding.ActivityMainBinding;
import com.example.carwash.Cotizacion.cotizacion_Fragment;
import com.example.carwash.Vehiculo.PerfilVehiculoFragment;
import com.example.carwash.Vehiculo.RegistroVehiculoFragment;
import com.example.carwash.Usuario.PerfilUsuarioFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    FirebaseAuth mAuth;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        GetUser();
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_cotizacion, R.id.nav_registrarvehiculo,
                R.id.nav_perfil, R.id.nav_perfilvehiculos)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_inicio);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_inicio);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(getApplicationContext(), ActivityLogin.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    // OBTENER UID DEL USUARIO EN FIREBASE
    private void GetUser() {

        mAuth = FirebaseAuth.getInstance();            // Iniciar Firebase
        FirebaseUser user = mAuth.getCurrentUser();     // Obtener Usuario Actual

        try {
            if (user != null) {
                uid = user.getUid(); // Obtener el UID del Usuario Actual
            }
        }
        catch (Exception e) {
            Toast.makeText(this, "Error: "+ e, Toast.LENGTH_LONG).show();
        }
    }

    //ASIGNAR FRAGMENTOS A CADA ITEM DE LA NAVEGACION
   @Override
    public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.nav_inicio) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_inicio, new inicio_Fragment());
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_registrarvehiculo) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_inicio, new RegistroVehiculoFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_cotizacion) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_inicio, new cotizacion_Fragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_perfil) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_inicio, new PerfilUsuarioFragment());
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_perfilvehiculos) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_inicio, new PerfilVehiculoFragment());
            fragmentTransaction.commit();

        }else if (id == R.id.nav_historialaceite) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_inicio, new CambiosAceiteFragment());
            fragmentTransaction.commit();

        }
        else if (id == R.id.nav_historiallavados) {
            fragmentManager = getSupportFragmentManager();
            fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.nav_inicio, new LavadosFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_cerrar) {
            logout();

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return false;
    }

    private void show(Fragment fragment) {

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        FragmentManager fragmentManager = getSupportFragmentManager();

        fragmentManager
                .beginTransaction()
                .replace(R.id.main_content, fragment)
                .commit();

        drawerLayout.closeDrawer(GravityCompat.START);
    }



}