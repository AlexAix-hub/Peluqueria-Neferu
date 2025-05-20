package com.example.peluquerianeferu;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peluquerianeferu.database.DBHelper;
import com.example.peluquerianeferu.model.Usuario;

public class RegistroActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextNombreUsuario, editTextTelefono,editTextPassword;
    private Button btnGuardarRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        DBHelper dbHelper = new DBHelper(this);

        // Referencias UI
        editTextNombre = findViewById(R.id.editTextNombre);
        editTextNombreUsuario = findViewById(R.id.editTextNombreUsuario);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        btnGuardarRegistro = findViewById(R.id.btnGuardarRegistro);

        // Acción al pulsar guardar
        btnGuardarRegistro.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString().trim();
            String nombreUsuario = editTextNombreUsuario.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();
            String telefono = editTextTelefono.getText().toString().trim();

            // Validación simple
            if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(nombreUsuario) || TextUtils.isEmpty(password) || TextUtils.isEmpty(telefono)) {
                Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.existeNombreUsuario(nombreUsuario)) {
                Toast.makeText(this, "El nombre de usuario ya está en uso", Toast.LENGTH_SHORT).show();
                return;
            }

            // Crear usuario y guardar
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setNombre(nombre);
            nuevoUsuario.setNombreUsuario(nombreUsuario);
            nuevoUsuario.setContrasena(password);
            nuevoUsuario.setRol("cliente"); // Por defecto
            nuevoUsuario.setTelefono(telefono);

            long resultado = dbHelper.insertarUsuario(nuevoUsuario);

            if (resultado != -1) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "Error al registrar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
