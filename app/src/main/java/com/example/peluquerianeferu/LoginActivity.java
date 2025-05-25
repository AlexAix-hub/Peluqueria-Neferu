// Este código está licenciado bajo la Licencia Creative Commons Attribution-ShareAlike 4.0 Internacional.
// Para más información, visita: https://creativecommons.org/licenses/by-sa/4.0/
// Autor: Alejandro Aix Utreras - Año: 2025

package com.example.peluquerianeferu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peluquerianeferu.database.DBHelper;
import com.example.peluquerianeferu.model.Usuario;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextUsuario, editTextContrasena;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);

        DBHelper dbHelper = new DBHelper(this);

        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContrasena = findViewById(R.id.editTextContrasena);
        btnLogin = findViewById(R.id.btnLogin);

        Button btnRegistrarse = findViewById(R.id.btnRegistro);
        btnRegistrarse.setOnClickListener(v -> {
            Intent intentRegistro = new Intent(LoginActivity.this, RegistroActivity.class);
            startActivity(intentRegistro);
        });

        btnLogin.setOnClickListener(v -> {

            String nombreUsuario = editTextUsuario.getText().toString().trim();
            String contrasena = editTextContrasena.getText().toString().trim();

            Usuario usuario = dbHelper.obtenerPorCredenciales(nombreUsuario, contrasena);

            if (usuario != null) {
                String rol = usuario.getRol();
                String nombre = usuario.getNombre();

                Toast.makeText(this, "Bienvenido/a, " + nombre, Toast.LENGTH_SHORT).show();

                if (rol.equals("cliente")) {
                    // Lanzar la pantalla de cliente
                    Intent intentCliente = new Intent(LoginActivity.this, ClienteActivity.class);
                    intentCliente.putExtra("nombreUsuario", nombreUsuario);
                    startActivity(intentCliente);
                    finish();
                } else if (rol.equals("admin")) {
                    // Lanzar la pantalla de dueño
                    Intent intentDueno = new Intent(LoginActivity.this, CitasDuenaActivity.class);
                    startActivity(intentDueno);
                    finish();
                }

            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
