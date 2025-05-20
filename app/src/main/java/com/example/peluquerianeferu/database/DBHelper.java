package com.example.peluquerianeferu.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.peluquerianeferu.model.Cita;
import com.example.peluquerianeferu.model.CitaServicio;
import com.example.peluquerianeferu.model.Servicio;
import com.example.peluquerianeferu.model.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DBHelper extends SQLiteOpenHelper {

    // Nombres de las tablas
    public static final String TABLE_USUARIOS = "usuarios";
    public static final String TABLE_CITAS = "citas";
    public static final String TABLE_SERVICIOS = "servicios";
    public static final String TABLE_CITA_SERVICIO = "cita_servicio";

    // Nombres de las columnas en la tabla usuarios
    public static final String COLUMN_USUARIO_ID = "usuarioId";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_NOMBRE_USUARIO = "nombreUsuario";
    public static final String COLUMN_CONTRASENA = "contrasena";
    public static final String COLUMN_ROL = "rol";
    public static final String COLUMN_TELEFONO = "telefono";

    // Nombres de las columnas en la tabla citas
    public static final String COLUMN_CITA_ID = "citaId";
    public static final String COLUMN_FECHA = "fecha";
    public static final String COLUMN_HORA = "hora";
    public static final String COLUMN_DURACION_TOTAL = "duracionTotal";
    public static final String COLUMN_PRECIO_TOTAL = "precioTotal";
    public static final String COLUMN_OBSERVACIONES = "observaciones";

    // Nombres de las columnas en la tabla servicios
    public static final String COLUMN_SERVICIO_ID = "servicioId";
    public static final String COLUMN_NOMBRE_SERVICIO = "nombre";
    public static final String COLUMN_DURACION = "duracion";
    public static final String COLUMN_PRECIO = "precio";

    // Nombres de las columnas en la tabla cita_servicio
    public static final String COLUMN_CITA_SERVICIO_ID = "citaServicioId";

    private static final String DATABASE_NAME = "peluqueria_db"; // Nombre de la base de datos
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // ----------------------------------------------------------------------------------------------------------------------------------

    @Override
    public void onCreate(SQLiteDatabase db) {
        //  Usuario
        db.execSQL("CREATE TABLE usuarios (" +
                "usuarioId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "nombreUsuario TEXT, " +
                "contrasena TEXT, " +
                "rol TEXT, " +
                "telefono TEXT)");

        // Usuario Admin
        insertarUsuarioDePrueba(db, "Cristina", "cris123", "0000","admin","66475553");
        insertarUsuarioDePrueba(db, "Alejandro Aix Utreras", "alex123", "1111","cliente","66447018");

        //  Cita
        db.execSQL("CREATE TABLE citas (" +
                "citaId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "usuarioId INTEGER, " +
                "fecha TEXT, " +
                "hora TEXT, " +
                "duracionTotal INTEGER, " +
                "precioTotal REAL, " +
                "observaciones TEXT, " +
                "FOREIGN KEY(usuarioId) REFERENCES usuarios(usuarioId))");

        // Servicios
        db.execSQL("CREATE TABLE servicios (" +
                "servicioId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "duracion INTEGER, " +
                "precio REAL)");

        // Insertar servicios por defecto
        insertarServicio(db, "Recogido", 30, 15.0F); // Duración 30 minutos, precio 15€
        insertarServicio(db, "Corte", 45, 20.0F); // Duración 45 minutos, precio 20€
        insertarServicio(db, "Tinte", 60, 30.5F); // Duración 60 minutos, precio 30€

        // CitaServicio
        db.execSQL("CREATE TABLE cita_servicio (" +
                "citaServicioId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "citaId INTEGER, " +
                "servicioId INTEGER, " +
                "FOREIGN KEY(citaId) REFERENCES citas(citaId), " +
                "FOREIGN KEY(servicioId) REFERENCES servicios(servicioId))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Este metodo se ejecuta cuando se actualiza la versión de la base de datos
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS citas");
        db.execSQL("DROP TABLE IF EXISTS servicios");
        db.execSQL("DROP TABLE IF EXISTS cita_servicio");
        onCreate(db);
    }

    // ------------------------------------------------------------------------------------------------------------------------------------------

    private void insertarUsuarioDePrueba(SQLiteDatabase db,String nombre, String nombreUsuario, String contrasena, String rol, String telefono) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE, nombre);
        values.put(COLUMN_NOMBRE_USUARIO, nombreUsuario);
        values.put(COLUMN_CONTRASENA, contrasena);
        values.put(COLUMN_ROL, rol);
        values.put(COLUMN_TELEFONO, telefono);
        db.insert(TABLE_USUARIOS, null, values);
    }

    public long insertarUsuario(Usuario usuario) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("nombre", usuario.getNombre());
        values.put("nombreUsuario", usuario.getNombreUsuario());
        values.put("contrasena", usuario.getContrasena());
        values.put("rol", usuario.getRol());
        values.put("telefono", usuario.getTelefono());

        return db.insert("usuarios", null, values);
    }


    private void insertarServicio(SQLiteDatabase db, String nombre, int duracion, float precio) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NOMBRE_SERVICIO, nombre);
        values.put(COLUMN_DURACION, duracion);
        values.put(COLUMN_PRECIO, precio);
        db.insert(TABLE_SERVICIOS, null, values);

    }

    public long insertarCita(int usuarioId, String fecha, String hora, int duracion, double precio, String observaciones) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("usuarioId", usuarioId);
        values.put("fecha", fecha);
        values.put("hora", hora);
        values.put("duracionTotal", duracion);
        values.put("precioTotal", precio);
        values.put("observaciones", observaciones);

        return db.insert("citas", null, values); // Retorna el citaId
    }

    public void insertarCitaServicio(long citaId, int servicioId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("citaId", citaId);
        values.put("servicioId", servicioId);

        db.insert("cita_servicio", null, values);
    }

    public int obtenerUsuarioIdPorNombre(String nombreUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT usuarioId FROM usuarios WHERE nombreUsuario = ?",
                new String[]{nombreUsuario}
        );

        int id = -1;
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }
        cursor.close();
        return id;
    }

    public List<Servicio> obtenerTodosLosServicios() {
        List<Servicio> servicios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM servicios", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("servicioId"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                int duracion = cursor.getInt(cursor.getColumnIndexOrThrow("duracion"));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"));

                servicios.add(new Servicio(id,nombre, precio, duracion));
            } while (cursor.moveToNext());
        }

        cursor.close();
        return servicios;
    }

    public ArrayList<Cita> obtenerCitasPorUsuarioId(int usuarioId) {
        ArrayList<Cita> listaCitas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM citas WHERE usuarioId = ?", new String[]{String.valueOf(usuarioId)});

        if (cursor.moveToFirst()) {
            do {
                int citaId = cursor.getInt(cursor.getColumnIndexOrThrow("citaId"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
                int duracion = cursor.getInt(cursor.getColumnIndexOrThrow("duracionTotal"));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precioTotal"));
                String observaciones = cursor.getString(cursor.getColumnIndexOrThrow("observaciones"));

                // Obtener los servicios de esta cita
                List<Servicio> servicios = obtenerServiciosDeCita(citaId);
                List<CitaServicio> serviciosCita = new ArrayList<>();

                for (Servicio servicio : servicios) {
                    CitaServicio cs = new CitaServicio(citaId, servicio);
                    serviciosCita.add(cs);
                }

                // Obtener el objeto Usuario completo
                Usuario usuario = obtenerUsuarioPorId(usuarioId);

                // Crear el objeto Cita con servicios incluidos
                Cita cita = new Cita(citaId, usuario, fecha, hora, duracion, precio, observaciones, serviciosCita);
                listaCitas.add(cita);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listaCitas;
    }

    public List<Servicio> obtenerServiciosDeCita(int citaId) {
        List<Servicio> listaServicios = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT s.servicioId, s.nombre, s.duracion, s.precio " +
                        "FROM servicios s " +
                        "JOIN cita_servicio cs ON s.servicioId = cs.servicioId " +
                        "WHERE cs.citaId = ?",
                new String[]{String.valueOf(citaId)}
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("servicioId"));
                String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                int duracion = cursor.getInt(cursor.getColumnIndexOrThrow("duracion"));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precio"));

                Servicio servicio = new Servicio(id, nombre, precio, duracion);
                listaServicios.add(servicio);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return listaServicios;
    }

    public Usuario obtenerUsuarioPorId(int usuarioId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Usuario usuario = null;

        Cursor cursor = db.rawQuery(
                "SELECT * FROM usuarios WHERE usuarioId = ?",
                new String[]{String.valueOf(usuarioId)}
        );

        if (cursor.moveToFirst()) {
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            String nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario"));
            String contrasena = cursor.getString(cursor.getColumnIndexOrThrow("contrasena"));
            String rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"));
            String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));

            usuario = new Usuario(usuarioId, nombre, nombreUsuario, contrasena, rol, telefono, new ArrayList<>());
        }

        cursor.close();
        return usuario;
    }


    public Cita obtenerCitaPorId(int citaId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cita cita = null;

        // Obtener la información básica de la cita
        Cursor cursor = db.rawQuery("SELECT * FROM citas WHERE citaId = ?", new String[]{String.valueOf(citaId)});

        if (cursor.moveToFirst()) {
            int usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow("usuarioId"));
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
            String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
            int duracionTotal = cursor.getInt(cursor.getColumnIndexOrThrow("duracionTotal"));
            double precioTotal = cursor.getDouble(cursor.getColumnIndexOrThrow("precioTotal"));
            String observaciones = cursor.getString(cursor.getColumnIndexOrThrow("observaciones"));

            // Obtener los servicios asociados a esta cita
            List<Servicio> servicios = obtenerServiciosDeCita(citaId);
            List<CitaServicio> serviciosCita = new ArrayList<>();

            for (Servicio servicio : servicios) {
                CitaServicio cs = new CitaServicio(citaId, servicio);
                serviciosCita.add(cs);
            }

            // Obtener el objeto Usuario
            Usuario usuario = obtenerUsuarioPorId(usuarioId);

            // Crear la cita con los servicios incluidos
            cita = new Cita(citaId, usuario, fecha, hora, duracionTotal, precioTotal, observaciones, serviciosCita);
        }

        cursor.close();
        return cita;
    }

    public void eliminarServiciosDeCita(int citaId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Eliminar los servicios asociados a la cita en la tabla 'cita_servicio'
        db.delete("cita_servicio", "citaId = ?", new String[]{String.valueOf(citaId)});
    }

    public void eliminarCita(int citaId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Eliminar la cita de la tabla 'citas'
        db.delete("citas", "citaId = ?", new String[]{String.valueOf(citaId)});
    }

    public List<Cita> obtenerCitasFuturas() {
        List<Cita> citas = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String fechaActual = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());

        Cursor cursor = db.rawQuery(
                "SELECT c.citaId, c.fecha, c.hora, c.duracionTotal, c.precioTotal, c.observaciones, u.usuarioId, u.nombre " +
                        "FROM citas c " +
                        "JOIN usuarios u ON c.usuarioId = u.usuarioId " +
                        "ORDER BY c.fecha ASC, c.hora ASC", null);


        if (cursor.moveToFirst()) {
            do {
                int citaId = cursor.getInt(cursor.getColumnIndexOrThrow("citaId"));
                String fecha = cursor.getString(cursor.getColumnIndexOrThrow("fecha"));
                String hora = cursor.getString(cursor.getColumnIndexOrThrow("hora"));
                int duracion = cursor.getInt(cursor.getColumnIndexOrThrow("duracionTotal"));
                double precio = cursor.getDouble(cursor.getColumnIndexOrThrow("precioTotal"));
                String observaciones = cursor.getString(cursor.getColumnIndexOrThrow("observaciones"));
                String nombreCliente = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                int usuarioId = cursor.getInt(cursor.getColumnIndexOrThrow("usuarioId"));

                Usuario usuario = new Usuario(usuarioId, nombreCliente);
                Cita cita = new Cita(citaId, usuario, fecha, hora, duracion, precio, observaciones);
                citas.add(cita);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return citas;
    }

    public String obtenerRolDeUsuario(int usuarioId) {
        String rol = null;

        // Si el usuario es el admin, directamente asignamos el rol
        if (usuarioId == -1) {
            rol = "admin";
        } else {
            // Si no es el admin, buscamos en la base de datos
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(
                    "SELECT rol FROM usuarios WHERE usuarioId = ?",
                    new String[]{String.valueOf(usuarioId)}
            );

            // Verificamos si el cursor tiene datos
            if (cursor != null && cursor.moveToFirst()) {
                // Intentamos obtener el índice de la columna 'rol'
                int rolColumnIndex = cursor.getColumnIndex("rol");

                // Comprobamos si el índice de la columna es válido
                if (rolColumnIndex != -1) {
                    rol = cursor.getString(rolColumnIndex);
                }
            }

            cursor.close();
        }

        return rol != null ? rol : "unknown"; // Si no hay rol, devolvemos un valor por defecto
    }

    public boolean existeNombreUsuario(String nombreUsuario) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT 1 FROM usuarios WHERE nombreUsuario = ? LIMIT 1",
                new String[]{nombreUsuario}
        );

        boolean existe = cursor.moveToFirst();
        cursor.close();
        return existe;
    }

    public Usuario obtenerPorCredenciales(String nombreUsuario, String contrasena) {
        Usuario usuario = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                "usuarios",
                null,
                "nombreUsuario = ? AND contrasena = ?",
                new String[]{nombreUsuario, contrasena},
                null,
                null,
                null
        );

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("usuarioId"));
            String nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
            String nombreUsuarioBD = cursor.getString(cursor.getColumnIndexOrThrow("nombreUsuario"));
            String contrasenaBD = cursor.getString(cursor.getColumnIndexOrThrow("contrasena"));
            String rol = cursor.getString(cursor.getColumnIndexOrThrow("rol"));
            String telefono = cursor.getString(cursor.getColumnIndexOrThrow("telefono"));

            usuario = new Usuario(nombre, nombreUsuarioBD, contrasenaBD, rol, telefono);
            usuario.setUsuarioId(id);

            cursor.close();
        }

        return usuario;
    }


    public SQLiteDatabase getWritable() {
        return this.getWritableDatabase();
    }

    public SQLiteDatabase getReadable() {
        return this.getReadableDatabase();
    }

}



