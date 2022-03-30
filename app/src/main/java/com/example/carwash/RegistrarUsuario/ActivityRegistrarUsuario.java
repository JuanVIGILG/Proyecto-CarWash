package com.example.carwash.RegistrarUsuario;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.carwash.Login.ActivityLogin;
import com.example.carwash.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

public class ActivityRegistrarUsuario extends AppCompatActivity {

    TextView txtlogin;
    EditText txtNom, txtApellido,txtTelefono,txtEmail,txtPass;
    Spinner Pais;
    Button btnRegistrar;
    Boolean retorno;
    AwesomeValidation awesomeValidation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        firebaseAuth = FirebaseAuth.getInstance();
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        awesomeValidation.addValidation(this,R.id.txtEmail, Patterns.EMAIL_ADDRESS,R.string.invalid_mail);
        awesomeValidation.addValidation(this,R.id.txtPass, ".{6,}",R.string.invalid_password);

        txtlogin = (TextView)findViewById(R.id.txtlogin);
        txtNom = (EditText) findViewById(R.id.txtNombre);
        txtApellido = (EditText) findViewById(R.id.txtApellido);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPass = (EditText) findViewById(R.id.txtPass);
        Pais = (Spinner) findViewById(R.id.Pais);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityRegistrarUsuario.this, ActivityLogin.class);
                startActivity(intent);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validar();
                String mail= txtEmail.getText().toString();
                String pass = txtPass.getText().toString();

                if (awesomeValidation.validate()){
                    firebaseAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ActivityRegistrarUsuario.this, "USUARIO CREADO CON ÉXITO", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                String errorCode = ((FirebaseAuthException) task.getException()).getErrorCode();
                                dameToastdeError(errorCode);
                            }
                        }
                    });
                }else{
                    Toast.makeText(ActivityRegistrarUsuario.this, "Complete todos los campos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public boolean validar(){
        retorno= true;

        String nom = txtNom.getText().toString();
        String ape= txtApellido.getText().toString();
        String tel = txtTelefono.getText().toString();

        if(nom.isEmpty()){
            txtNom.setError("DEBE INGRESAR EL NOMBRE");
            retorno = false;
        }
        if(ape.isEmpty()){
            txtApellido.setError("DEBE INGRESAR EL APELLIDO");
            retorno = false;
        }
        if(tel.isEmpty()){
            txtTelefono.setError("DEBE INGRESAR EL TELEFONO");
            retorno = false;
        }

        return retorno;
    }

    private void dameToastdeError(String error) {

        switch (error) {

            case "ERROR_INVALID_CUSTOM_TOKEN":
                Toast.makeText(ActivityRegistrarUsuario.this, "El formato del token personalizado es incorrecto. Por favor revise la documentación", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_CUSTOM_TOKEN_MISMATCH":
                Toast.makeText(ActivityRegistrarUsuario.this, "El token personalizado corresponde a una audiencia diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_CREDENTIAL":
                Toast.makeText(ActivityRegistrarUsuario.this, "La credencial de autenticación proporcionada tiene un formato incorrecto o ha caducado.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_EMAIL":
                Toast.makeText(ActivityRegistrarUsuario.this, "La dirección de correo electrónico está mal formateada.", Toast.LENGTH_LONG).show();
                txtEmail.setError("La dirección de correo electrónico está mal formateada.");
                txtEmail.requestFocus();
                break;

            case "ERROR_WRONG_PASSWORD":
                Toast.makeText(ActivityRegistrarUsuario.this, "La contraseña no es válida o el usuario no tiene contraseña.", Toast.LENGTH_LONG).show();
                txtPass.setError("la contraseña es incorrecta ");
                txtPass.requestFocus();
                txtPass.setText("");
                break;

            case "ERROR_USER_MISMATCH":
                Toast.makeText(ActivityRegistrarUsuario.this, "Las credenciales proporcionadas no corresponden al usuario que inició sesión anteriormente..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_REQUIRES_RECENT_LOGIN":
                Toast.makeText(ActivityRegistrarUsuario.this,"Esta operación es sensible y requiere autenticación reciente. Inicie sesión nuevamente antes de volver a intentar esta solicitud.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                Toast.makeText(ActivityRegistrarUsuario.this, "Ya existe una cuenta con la misma dirección de correo electrónico pero diferentes credenciales de inicio de sesión. Inicie sesión con un proveedor asociado a esta dirección de correo electrónico.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_EMAIL_ALREADY_IN_USE":
                Toast.makeText(ActivityRegistrarUsuario.this, "La dirección de correo electrónico ya está siendo utilizada por otra cuenta..   ", Toast.LENGTH_LONG).show();
                txtEmail.setError("La dirección de correo electrónico ya está siendo utilizada por otra cuenta.");
                txtEmail.requestFocus();
                break;

            case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                Toast.makeText(ActivityRegistrarUsuario.this, "Esta credencial ya está asociada con una cuenta de usuario diferente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_DISABLED":
                Toast.makeText(ActivityRegistrarUsuario.this, "La cuenta de usuario ha sido inhabilitada por un administrador..", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_TOKEN_EXPIRED":
                Toast.makeText(ActivityRegistrarUsuario.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_USER_NOT_FOUND":
                Toast.makeText(ActivityRegistrarUsuario.this, "No hay ningún registro de usuario que corresponda a este identificador. Es posible que se haya eliminado al usuario.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_INVALID_USER_TOKEN":
                Toast.makeText(ActivityRegistrarUsuario.this, "La credencial del usuario ya no es válida. El usuario debe iniciar sesión nuevamente.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_OPERATION_NOT_ALLOWED":
                Toast.makeText(ActivityRegistrarUsuario.this, "Esta operación no está permitida. Debes habilitar este servicio en la consola.", Toast.LENGTH_LONG).show();
                break;

            case "ERROR_WEAK_PASSWORD":
                Toast.makeText(ActivityRegistrarUsuario.this, "La contraseña proporcionada no es válida..", Toast.LENGTH_LONG).show();
                txtPass.setError("La contraseña no es válida, debe tener al menos 6 caracteres");
                txtPass.requestFocus();
                break;

        }

    }
}