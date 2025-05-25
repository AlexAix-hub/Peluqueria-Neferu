// Este código está licenciado bajo la Licencia Creative Commons Attribution-ShareAlike 4.0 Internacional.
// Para más información, visita: https://creativecommons.org/licenses/by-sa/4.0/
// Autor: Alejandro Aix Utreras - Año: 2025

package com.example.peluquerianeferu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.peluquerianeferu.database.DBHelper;
import com.example.peluquerianeferu.model.Cita;
import com.example.peluquerianeferu.model.Servicio;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DetalleCitaActivity extends AppCompatActivity {

    private TextView textFechaHora, textDuracion, textPrecio, textObservaciones, textServiciosTitulo;
    private ListView listViewServicios;
    private AppCompatButton btnEliminarCita;

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_cita);
        dbHelper = new DBHelper(this);

        int usuarioId = getIntent().getIntExtra("usuarioId", -1);
        Log.d("DetalleCitaActivity", "ID DEL USUARIO: " + usuarioId);
        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        String usuarioRol = dbHelper.obtenerRolDeUsuario(usuarioId);

        // Inicializamos las vistas
        textFechaHora = findViewById(R.id.textFechaHora);
        textDuracion = findViewById(R.id.textDuracion);
        textPrecio = findViewById(R.id.textPrecio);
        textObservaciones = findViewById(R.id.textObservaciones);
        listViewServicios = findViewById(R.id.listViewServicios);
        btnEliminarCita = findViewById(R.id.btnEliminarCita);

        // Obtener el citaId que pasamos desde la actividad anterior
        int citaId = getIntent().getIntExtra("citaId", -1);

        // Cargar los datos de la cita
        cargarDetalleCita(citaId);

        if ("admin".equals(usuarioRol)) {

            btnEliminarCita.setVisibility(View.GONE);  // O también podrías usar setEnabled(false
        } else {

            btnEliminarCita.setVisibility(View.VISIBLE);
        }

        btnEliminarCita.setOnClickListener(v -> {

            // Eliminar los servicios asociados a la cita
            dbHelper.eliminarServiciosDeCita(citaId);

            // Eliminar la cita
            dbHelper.eliminarCita(citaId);

            Toast.makeText(getApplicationContext(), "Cita eliminada correctamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(DetalleCitaActivity.this, ClienteActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("nombreUsuario", nombreUsuario); // si necesitas pasarlo
            startActivity(intent);
            finish();
        });

    }

    private void cargarDetalleCita(int citaId) {
        Cita cita = dbHelper.obtenerCitaPorId(citaId); // Método que debes implementar

        if (cita != null) {

            double precio = cita.getPrecioTotal();
            String precioFormateado = String.format(Locale.US, "%.2f€", precio);

            textFechaHora.setText("Fecha y Hora: " + cita.getFecha() + " - " + cita.getHora());
            textDuracion.setText("Duración: " + cita.getDuracionTotal() + " min");
            textPrecio.setText("Precio: " + precioFormateado);
            textObservaciones.setText("Observaciones: " + cita.getObservaciones());

            List<Servicio> serviciosCita = dbHelper.obtenerServiciosDeCita(citaId);

            ServicioAdapter servicioAdapter = new ServicioAdapter(this, serviciosCita);
            listViewServicios.setAdapter(servicioAdapter);
        }
    }
}

