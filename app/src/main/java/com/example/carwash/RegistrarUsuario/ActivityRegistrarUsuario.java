package com.example.carwash.RegistrarUsuario;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.carwash.Login.ActivityLogin;
import com.example.carwash.R;

public class ActivityRegistrarUsuario extends AppCompatActivity {

    TextView txtlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        txtlogin = (TextView)findViewById(R.id.txtlogin);

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegistrarUsuario.this, ActivityLogin.class);
                startActivity(intent);
            }
        });
    }
    }