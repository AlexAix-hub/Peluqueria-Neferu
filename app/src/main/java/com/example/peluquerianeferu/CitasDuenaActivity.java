package com.example.peluquerianeferu;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.peluquerianeferu.database.DBHelper;
import com.example.peluquerianeferu.model.Cita;

import java.util.ArrayList;
import java.util.List;

public class CitasDuenaActivity extends AppCompatActivity {

    private ListView listView;
    private List<Cita> listaCitas;
    private ArrayAdapter<String> adapter;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_duena);

        listView = findViewById(R.id.listViewCitasDueña);
        dbHelper = new DBHelper(this);

        listaCitas = dbHelper.obtenerCitasFuturas();
        List<String> textos = new ArrayList<>();

        for (Cita cita : listaCitas) {
            textos.add("Cliente: " + cita.getUsuario().getNombre() +
                    "\nFecha: " + cita.getFecha() +
                    " | Hora: " + cita.getHora());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Cita citaSeleccionada = listaCitas.get(position);
            Intent intent = new Intent(this, DetalleCitaActivity.class);
            intent.putExtra("citaId", citaSeleccionada.getCitaId());
            startActivity(intent);
        });
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        // Crear el diálogo de confirmación
        new AlertDialog.Builder(this)
                .setMessage("¿Estás seguro de que quieres salir de la aplicación?")
                .setCancelable(false) // Evitar que el usuario cierre el diálogo tocando fuera de él
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Si el usuario confirma, cerrar la actividad
                        finishAffinity(); // Cierra todas las actividades y termina la aplicación
                    }
                })
                .setNegativeButton("No", null) // Si el usuario cancela, no hacer nada
                .show();
    }

}

