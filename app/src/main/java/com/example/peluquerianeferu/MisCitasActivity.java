package com.example.peluquerianeferu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.peluquerianeferu.database.DBHelper;
import com.example.peluquerianeferu.model.Cita;

import java.util.ArrayList;

public class MisCitasActivity extends AppCompatActivity {

    private ListView listViewCitas;
    private ArrayAdapter<String> adapter;
    private ArrayList<Cita> listaCitas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_citas);

        listViewCitas = findViewById(R.id.listViewCitas);
        DBHelper dbHelper = new DBHelper(this);

        String nombreUsuario = getIntent().getStringExtra("nombreUsuario");
        int usuarioId = dbHelper.obtenerUsuarioIdPorNombre(nombreUsuario);

        listaCitas = dbHelper.obtenerCitasPorUsuarioId(usuarioId);

        ArrayList<String> textosCitas = new ArrayList<>();
        for (Cita cita : listaCitas) {
            textosCitas.add("Fecha: " + cita.getFecha() + " | Hora: " + cita.getHora());
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, textosCitas);
        listViewCitas.setAdapter(adapter);

        listViewCitas.setOnItemClickListener((parent, view, position, id) -> {
            Cita citaSeleccionada = listaCitas.get(position);

            Intent intent = new Intent(this, DetalleCitaActivity.class);
            intent.putExtra("citaId", citaSeleccionada.getCitaId());
            intent.putExtra("nombreUsuario", nombreUsuario);
            intent.putExtra("usuarioId", usuarioId);
            startActivity(intent);
        });
    }
}
