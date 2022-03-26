package com.example.carwash.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.carwash.R;
import com.example.carwash.RegistrarUsuario.ActivityRegistrarUsuario;

public class ActivityLogin extends AppCompatActivity {

    TextView txtRegistrarse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtRegistrarse = (TextView)findViewById(R.id.txtRegistrarse);

        txtRegistrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityLogin.this, ActivityRegistrarUsuario.class);
                startActivity(intent);
            }
        });
    }
}