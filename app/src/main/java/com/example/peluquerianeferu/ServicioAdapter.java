package com.example.peluquerianeferu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.peluquerianeferu.model.Servicio;

import java.util.List;

public class ServicioAdapter extends ArrayAdapter<Servicio> {

    public ServicioAdapter(Context context, List<Servicio> servicios) {
        super(context, 0, servicios);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_servicio, parent, false);
        }

        Servicio servicio = getItem(position);

        TextView textNombre = convertView.findViewById(R.id.textNombreServicio);
        TextView textDuracion = convertView.findViewById(R.id.textDuracionServicio);
        TextView textPrecio = convertView.findViewById(R.id.textPrecioServicio);

        textNombre.setText(servicio.getNombre());
        textDuracion.setText("Duración: " + servicio.getDuracion() + " min");
        textPrecio.setText("Precio: " + servicio.getPrecio() + "€");

        return convertView;
    }
}

