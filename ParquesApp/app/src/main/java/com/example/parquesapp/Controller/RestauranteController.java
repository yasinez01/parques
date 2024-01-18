package com.example.parquesapp.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.parquesapp.Clases.Espectaculo;
import com.example.parquesapp.Clases.Restaurante;
import com.example.parquesapp.db.DbHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestauranteController {

    private static final String TAG = RestauranteController.class.getSimpleName();

    private SQLiteDatabase database;
    private DbHelper dbHelper;

    public RestauranteController(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    // Método para agregar un restaurante a la base de datos
    public long agregarRestaurante(Restaurante restaurante) {
        ContentValues values = new ContentValues();
        values.put("name", restaurante.getName());
        values.put("horario_apertura", restaurante.getHorario_apertura());
        values.put("horario_cierre", restaurante.getHorario_cerradura());

        return database.insert("t_restaurante", null, values);
    }

    // Método para obtener todos los restaurantes de la base de datos
    public List<Restaurante> obtenerTodosRestaurantes() {
        List<Restaurante> restaurantes = new ArrayList<>();
        String query = "SELECT * FROM t_restaurante ORDER BY id ASC";
        Cursor cursor = database.rawQuery(query, null);
        restaurantes = cursorToListaRestaurantes(cursor);
        cursor.close();
        return restaurantes;
    }

    // Método para actualizar un restaurante en la base de datos
    public int actualizarRestaurante(Restaurante restaurante) {
        ContentValues values = new ContentValues();
        values.put("name", restaurante.getName());
        values.put("horario_apertura", restaurante.getHorario_apertura());
        values.put("horario_cierre", restaurante.getHorario_cerradura());

        return database.update("t_restaurante", values, "id = ?", new String[]{String.valueOf(restaurante.getId())});
    }

    // Método para eliminar un restaurante de la base de datos
    public void eliminarRestaurante(long restauranteId) {
        database.delete("t_restaurante", "id = ?", new String[]{String.valueOf(restauranteId)});
    }

    // Convierte un cursor a un objeto Restaurante
    private Restaurante cursorToRestaurante(Cursor cursor) {
        Restaurante restaurante = null;

        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int aperturaIndex = cursor.getColumnIndex("horario_apertura");
            int cerraduraIndex = cursor.getColumnIndex("horario_cierre");

            int id = cursor.getInt(idIndex);
            String name = cursor.getString(nameIndex);
            int horarioApertura = cursor.getInt(aperturaIndex);
            int horarioCierre = cursor.getInt(cerraduraIndex);

            restaurante = new Restaurante(id, name, horarioApertura, horarioCierre);
        }

        return restaurante;
    }
    @SuppressLint("Range")
    private List<Restaurante> cursorToListaRestaurantes(Cursor cursor){
        // Variables
        List<Restaurante> restaurantes = new ArrayList<>();
        int id;
        String nombre;
        int apertura;
        int cierre;
        Restaurante restaurante;

        // Leer cursor
        if (cursor.moveToFirst()) {
            do {
                // Lectura
                id = cursor.getInt(cursor.getColumnIndex("id"));
                nombre = cursor.getString(cursor.getColumnIndex("name"));
                apertura = cursor.getInt(cursor.getColumnIndex("horario_apertura"));
                cierre = cursor.getInt(cursor.getColumnIndex("horario_cierre"));

                // Crear Espectaculo
                restaurante = new Restaurante(id,nombre,apertura,cierre);
                restaurantes.add(restaurante);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return restaurantes;
    }

    public int ObtenerIdDisponible(){
        List<Restaurante> restaurantes = obtenerTodosRestaurantes();
        // Obtengo todos los ids
        Set<Integer> ids = new HashSet<>();
        for (Restaurante restaurante: restaurantes) {
            ids.add(restaurante.getId());
        }
        int id = 0;
        while (ids.contains(id)){
            id++;
        }
        return id;
    }

}

