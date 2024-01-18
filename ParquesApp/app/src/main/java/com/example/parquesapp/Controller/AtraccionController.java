package com.example.parquesapp.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.parquesapp.Clases.Atraccion;
import com.example.parquesapp.Clases.Espectaculo;
import com.example.parquesapp.db.DbHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kotlin.collections.ArrayDeque;

public class AtraccionController {
    private SQLiteDatabase sqLiteDatabase;
    private DbHelper dbHelper;

    /**
     * Inicializador
     * @param context
     */
    public AtraccionController(Context context) {
        dbHelper = new DbHelper(context);
    }

    /**
     * Abrir conexion con la base de datos
     * @throws SQLException
     */
    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * Cerrar conexion con la base de datos
     * @throws SQLException
     */
    public void close() throws SQLException {
        dbHelper.close();
    }

    /*      */
    // CRUD //
    /*      */

    /**
     * createAtraccion: Almacena una Atraccion en la base de datos.
     * @param atraccion: Atraccion que se quiere almacenar.
     * @return boolean: Indica si se ha cumplido la insercion.
     */
    public boolean createAtraccion(Atraccion atraccion){
        // Realizar consulta
        String consulta =
                "INSERT INTO" +
                        "t_atraccion (id, name, wait_time, last_update, description)" +
                        "VALUES" +
                        "(" +
                        atraccion.getId() + "," +
                        "'" + atraccion.getNombre() + "'," +
                        atraccion.getTiempoEspera() + "," +
                        "'" + atraccion.getUltimaActualizacion() + "'," +
                        "'" + atraccion.getDescripcion() + "'," +
                        ")";
        try {
            sqLiteDatabase.execSQL(consulta);
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    /**
     * readAllAtraccion: Leer la base de datos y devuelve todas las atracciones contenidas en esta en forma de lista.
     * @return List<Atraccion>: Devuelve la lista de Atracciones contenida en la base de datos ordenados de menor a mayor id.
     */
    public List<Atraccion> readAllAtraccion(){
        String consulta = "SELECT * FROM t_atraccion ORDER BY id ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(consulta, null);
        List<Atraccion> atracciones = cursorToListaAtracciones(cursor);
        cursor.close();
        return atracciones;
    }

    /**
     * updateAtraccion: Actualiza una atraccion de la base de datos.
     * @param atraccion: Atraccion actualizada que se quiere actualizar.
     * @return boolean: Indica si la actualizacion se ha ejecutado con exito.
     */
    public boolean updateAtraccion(Atraccion atraccion){
        ContentValues contentValues =  new ContentValues();
        contentValues.put("name", atraccion.getNombre());
        contentValues.put("wait_time", atraccion.getTiempoEspera());
        contentValues.put("last_update", atraccion.getUltimaActualizacion());
        contentValues.put("description", atraccion.getDescripcion());

        if (0 < sqLiteDatabase.update("t_atraccion", contentValues, "id = ?", new String[]{String.valueOf(atraccion.getId())})){
            return true;}
        else{
            return false;}
    }

    /**
     * deleteAtraccion: Elimina una atraccion de la base de datos.
     * @param atraccion: Atraccion que se quiere eliminar.
     * @return boolean: Indica si la eliminacion se ha ejecutado con exito.
     */
    public boolean deleteEspectaculo(Atraccion atraccion){
        if (0 < sqLiteDatabase.delete("t_atraccion", "id = ?", new String[]{String.valueOf(atraccion.getId())})){
            return true;}
        else{
            return false;}
    }

    /**
     * cursorToAtracciones: Parsea los datos de un Cursor a una lista de Atraccion.
     * @param cursor: Consulta donde se encuentran los datos.
     * @return Atracciones: Atraccion que contieen los datos que se encontraban en el cursor.
     *      nulo en el caso de que no se pueda completar la operacion.
     */
    @SuppressLint("Range")
    private List<Atraccion> cursorToListaAtracciones(Cursor cursor){
        // Variables
        List<Atraccion> atracciones = new ArrayList<>();
        int id;
        String name;
        int wait_time;
        String last_update;
        String description;
        Atraccion atraccion;

        // Leer cursor
        if (cursor.moveToFirst())
            do {
                // Lectura
                id = cursor.getInt(cursor.getColumnIndex("id"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                wait_time = cursor.getInt(cursor.getColumnIndex("wait_time"));
                last_update = cursor.getString(cursor.getColumnIndex("last_update"));
                description = cursor.getString(cursor.getColumnIndex("description"));
                // Crear Atraccion
                atraccion = new Atraccion(id, name, wait_time, last_update, description);
                atracciones.add(atraccion);
            } while (cursor.moveToNext());
        cursor.close();
        return atracciones;
    }

    /**
     * ObtenerIdDisponible: Devuelve una
     * @return id: int id minimo disponible
     */
    public int ObtenerIdDisponible(){
        List<Atraccion> atracciones = readAllAtraccion();
        // Obtener todos los ids
        Set<Integer> ids = new HashSet<>();
        for (Atraccion atraccion: atracciones) {
            ids.add(atraccion.getId());
        }
        // Compruebo cual es el id menor disponible
        int id = 0;
        while (ids.contains(id)){
            id++;
        }
        return id;
    }
}
