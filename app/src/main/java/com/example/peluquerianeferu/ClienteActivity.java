// Este código está licenciado bajo la Licencia Creative Commons Attribution-ShareAlike 4.0 Internacional.
// Para más información, visita: https://creativecommons.org/licenses/by-sa/4.0/
// Autor: Alejandro Aix Utreras - Año: 2025

package com.example.peluquerianeferu;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.peluquerianeferu.database.DBHelper;
import com.example.peluquerianeferu.model.Servicio;
import com.example.peluquerianeferu.model.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ClienteActivity extends AppCompatActivity {

    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        Locale locale = new Locale("es", "ES");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_cliente);

        dbHelper = new DBHelper(this);

        CalendarView calendarView = findViewById(R.id.calendarView);
        AppCompatButton btnVerMisCitas = findViewById(R.id.btnVerMisCitas);
        AppCompatButton btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        calendarView.setFirstDayOfWeek(Calendar.MONDAY);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(year, month, dayOfMonth);

                Calendar today = Calendar.getInstance();
                today.set(Calendar.HOUR_OF_DAY, 0);
                today.set(Calendar.MINUTE, 0);
                today.set(Calendar.SECOND, 0);
                today.set(Calendar.MILLISECOND, 0);

                int dayOfWeek = selectedDate.get(Calendar.DAY_OF_WEEK);

                // Verificar si es sábado o domingo o fecha pasada
                if (selectedDate.before(today)) {
                    Toast.makeText(view.getContext(), "No puedes seleccionar una fecha pasada.", Toast.LENGTH_SHORT).show();
                } else if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
                    Toast.makeText(view.getContext(), "No se pueden reservar citas los fines de semana.", Toast.LENGTH_SHORT).show();
                } else {
                    // Formatear fecha (opcional)
                    String fechaSeleccionada = dayOfMonth + "/" + (month + 1) + "/" + year;
                    mostrarDialogReserva(fechaSeleccionada);
                }
            }
        });

        btnVerMisCitas.setOnClickListener(v -> {
            String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
            Intent intent = new Intent(ClienteActivity.this, MisCitasActivity.class);
            intent.putExtra("nombreUsuario", nombreUsuario);
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v -> {
            new AlertDialog.Builder(ClienteActivity.this)
                    .setTitle("Cerrar sesión")
                    .setMessage("¿Estás seguro de que deseas cerrar sesión?")
                    .setPositiveButton("Sí", (dialog, which) -> {
                        // Volver al Login y limpiar la pila de actividades
                        Intent intent = new Intent(ClienteActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish(); // cerrar esta actividad por si acaso
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        });
    }

    private void mostrarDialogReserva(String fechaSeleccionada) {
        final Dialog dialog = new Dialog(ClienteActivity.this);
        dialog.setContentView(R.layout.dialogo_reserva);

        TextView textFechaSeleccionada = dialog.findViewById(R.id.textFechaSeleccionada);
        Spinner spinnerHoras = dialog.findViewById(R.id.spinnerHoras);
        TextView textPrecioTotal = dialog.findViewById(R.id.textPrecioTotal);
        TextView textDuracionTotal = dialog.findViewById(R.id.textDuracionTotal);
        EditText editObservaciones = dialog.findViewById(R.id.editObservaciones);
        AppCompatCheckBox checkboxServicio1 = dialog.findViewById(R.id.checkboxServicio1);
        AppCompatCheckBox checkboxServicio2 = dialog.findViewById(R.id.checkboxServicio2);
        AppCompatCheckBox checkboxServicio3 = dialog.findViewById(R.id.checkboxServicio3);
        AppCompatButton btnReservar = dialog.findViewById(R.id.btnReservar);

        textFechaSeleccionada.setText(fechaSeleccionada);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getHorasDisponibles());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHoras.setAdapter(adapter);

        List<Servicio> servicios = dbHelper.obtenerTodosLosServicios();
        Map<CheckBox, Servicio> mapaServicios = new HashMap<>();

        if (servicios.size() >= 3) {
            Servicio s1 = servicios.get(0);
            Servicio s2 = servicios.get(1);
            Servicio s3 = servicios.get(2);

            checkboxServicio1.setText(s1.getNombre() + " (Precio: " + s1.getPrecio() + "€, Duración: " + s1.getDuracion() + " min)");
            checkboxServicio2.setText(s2.getNombre() + " (Precio: " + s2.getPrecio() + "€, Duración: " + s2.getDuracion() + " min)");
            checkboxServicio3.setText(s3.getNombre() + " (Precio: " + s3.getPrecio() + "€, Duración: " + s3.getDuracion() + " min)");

            mapaServicios.put(checkboxServicio1, s1);
            mapaServicios.put(checkboxServicio2, s2);
            mapaServicios.put(checkboxServicio3, s3);
        }

        for (CheckBox cb : mapaServicios.keySet()) {
            cb.setOnCheckedChangeListener((buttonView, isChecked) -> {
                actualizarPrecioYDuracion(mapaServicios, textPrecioTotal, textDuracionTotal);
            });
        }

        btnReservar.setOnClickListener(v -> {

            if (!checkboxServicio1.isChecked() && !checkboxServicio2.isChecked() && !checkboxServicio3.isChecked()) {
                Toast.makeText(this, "Debes seleccionar al menos un servicio", Toast.LENGTH_SHORT).show();
                return;
            }
            String nombreUsuario = getIntent().getStringExtra("nombreUsuario");

            int usuarioId = dbHelper.obtenerUsuarioIdPorNombre(nombreUsuario);
            String fecha = textFechaSeleccionada.getText().toString();
            String hora = spinnerHoras.getSelectedItem().toString();

            // Verificar si la hora está disponible
            boolean horaDisponible = verificarHoraDisponible(fecha, hora);

            if (!horaDisponible) {
                Toast.makeText(getApplicationContext(), "La hora seleccionada ya está ocupada.", Toast.LENGTH_LONG).show();
                return; // No se puede reservar la cita
            }


            // Extraer la duración solo con números
            String duracionTexto = textDuracionTotal.getText().toString().replaceAll("[^\\d]", "");
            if (duracionTexto.isEmpty()) {
                Toast.makeText(this, "La duración no es válida.", Toast.LENGTH_SHORT).show();
                return; // Salir si la duración es incorrecta
            }
            int duracion = Integer.parseInt(duracionTexto);

            // Extraer el precio solo con números
            String precioTexto = textPrecioTotal.getText().toString().replaceAll("[^\\d.]", "");
            if (precioTexto.isEmpty()) {
                Toast.makeText(this, "El precio no es válido.", Toast.LENGTH_SHORT).show();
                return; // Salir si el precio es incorrecto
            }
            double precio = Double.parseDouble(precioTexto);


            String observaciones = editObservaciones.getText().toString();

            // Insertar la cita en la base de datos
            long citaId = dbHelper.insertarCita(usuarioId, fecha, hora, duracion, precio, observaciones);

            // Verificar si la cita se ha insertado correctamente
            if (citaId > 0) {
                // Insertar los servicios asociados a la cita
                for (Map.Entry<CheckBox, Servicio> entry : mapaServicios.entrySet()) {
                    if (entry.getKey().isChecked()) {
                        int servicioId = entry.getValue().getServicioId();
                        dbHelper.insertarCitaServicio(citaId, servicioId);
                    }
                }

                // Mostrar mensaje de éxito y cerrar el diálogo
                Toast.makeText(this, "Cita reservada correctamente", Toast.LENGTH_LONG).show();
                // Cerrar el diálogo (si es un diálogo que se debe cerrar)
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();  // Cierra el diálogo si está abierto
                }

                Usuario usuario = dbHelper.obtenerUsuarioPorId(usuarioId);
                enviarDatosAGoogleSheets(fecha,hora,usuario.getNombre(),usuario.getTelefono(),observaciones);

            } else {
                // Si no se pudo insertar la cita, mostrar mensaje de error
                Toast.makeText(this, "Hubo un error al reservar la cita. Intenta nuevamente.", Toast.LENGTH_LONG).show();
            }


        });

        dialog.show();
    }

    private void actualizarPrecioYDuracion(Map<CheckBox, Servicio> mapaServicios, TextView textPrecioTotal, TextView textDuracionTotal) {
        double precioTotal = 0;
        int duracionTotal = 0;

        for (Map.Entry<CheckBox, Servicio> entry : mapaServicios.entrySet()) {
            CheckBox cb = entry.getKey();
            Servicio servicio = entry.getValue();

            if (cb.isChecked()) {
                precioTotal += servicio.getPrecio();
                duracionTotal += servicio.getDuracion();
            }
        }

        textPrecioTotal.setText(String.format(Locale.US, "Precio Total: %.2f€", precioTotal));
        textDuracionTotal.setText("Duración Total: " + duracionTotal + " mins");
    }

    private List<String> getHorasDisponibles() {
        List<String> horas = new ArrayList<>();
        horas.add("9:00 AM");
        horas.add("10:00 AM");
        horas.add("11:00 AM");
        horas.add("12:00 PM");
        horas.add("1:00 PM");
        horas.add("5:00 PM");
        horas.add("6:00 PM");
        horas.add("7:00 PM");
        horas.add("8:00 PM");
        horas.add("9:00 PM");

        return horas;
    }

    public boolean verificarHoraDisponible(String fecha, String hora) {
        DBHelper dbHelper = new DBHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM citas WHERE fecha = ? AND hora = ?",
                new String[]{fecha, hora}
        );

        boolean disponible = true;
        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            if (count > 0) {
                disponible = false;
            }
        }

        cursor.close();
        return disponible;
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("¿Estás seguro de que deseas salir de la aplicación?")
                .setCancelable(false) // No se puede cancelar tocando fuera del diálogo
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Si selecciona "Sí", cerrar la app
                        finishAffinity(); // Cierra la actividad y todas las anteriores
                    }
                })
                .setNegativeButton("No", null) // Si selecciona "No", no hace nada
                .show();
    }

    private void enviarDatosAGoogleSheets(String fecha, String hora, String nombre, String telefono, String observaciones) {
        String url = "https://script.google.com/macros/s/AKfycbzEkbPgXK2kysKfmbF7Zz5JHWVU4U31HXYEogLK6sbv2g2HXexxQbnAkti7XsH4Kg4D/exec";

        JSONObject datos = new JSONObject();
        try {
            datos.put("fecha", fecha);
            datos.put("hora", hora);
            datos.put("nombre", nombre);
            datos.put("telefono", telefono);
            datos.put("observaciones", observaciones);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, datos,
                response -> Log.d("GoogleSheet", "Enviado correctamente"),
                error -> Log.e("GoogleSheet", "Error al enviar", error)
        );

        queue.add(request);
    }
}