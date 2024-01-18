package com.example.parquesapp.Controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.parquesapp.Clases.Espectaculo;
import com.example.parquesapp.db.DbHelper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EspectaculoController {
    private SQLiteDatabase sqLiteDatabase;
    private DbHelper dbHelper;

    /**
     * Inicializar
     * @param context
     */
    public EspectaculoController(Context context) {
        dbHelper = new DbHelper(context);}

    /**
     * Abrir conexion con la base de datos.
     */
    public void open() throws SQLException {
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    /**
     * Cerrar conexion con la base de datos
     * @throws SQLException
     */
    public void close() throws SQLException {
        dbHelper.close();}

    /*      */
    // CRUD //
    /*      */

    /**
     * createEspectaculo: Almacena un Espectaculo en la Base de datos.
     * @return boolean: Indica si se ha cumplido la insercion.
     */
    public boolean createEspectaculo(Espectaculo espectaculo){
        // Realizar consulta
        String consulta =
                "INSERT INTO " +
                        "t_espectaculos (id, name, duracion, horarios, descripcion) " +
                        "VALUES" +
                        "(" +
                        espectaculo.getId() + "," +
                        "'" + espectaculo.getName() + "'," +
                        espectaculo.getDuracion() + "," +
                        "'" + espectaculo.getHorarios() + "'," +
                        "'" + espectaculo.getDescripcion() + "'" +
                        ")";
        try {
            sqLiteDatabase.execSQL(consulta);
            return true;}
        catch (SQLException e) {
            return false;}
    }

    /**
     * readAllEspectaculo: Leer la base de datos y devuelve todos los espectaculos contenidos en esta en forma de lista.
     * @return List<Espectaculo>: Devuelvo la lista de Espectaculos contenido en la base de datos ordenados de menor a mayor id.
     */
    @SuppressLint("Range")
    public List<Espectaculo> readAllEspectaculo(){
        List<Espectaculo> espectaculos = new ArrayList<>();
        String query = "SELECT * FROM t_espectaculos ORDER BY id ASC";
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        espectaculos = cursorToListaEspectaculo(cursor);
        cursor.close();
        return espectaculos;
    }

    /**
     * updateEspectaculo: Actualiza un espectaculo de la base de datos.
     * @param espectaculo: Espectaculo actualizado que se quiere eleminar.
     * @return boolean: Indica si la actualizacion se ha ejecutado con exito.
     */
    public boolean updateEspectaculo(Espectaculo espectaculo){
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", espectaculo.getName());
        contentValues.put("duracion", espectaculo.getDuracion());
        contentValues.put("horarios", espectaculo.getHorarios());
        contentValues.put("descripcion", espectaculo.getDescripcion());

        if (0 < sqLiteDatabase.update("t_espectaculos", contentValues, "id = ?", new String[]{String.valueOf(espectaculo.getId())})) {
            return true;}
        else{
            return false;}
    }

    /**
     * deleteEspectaculo: Elimina un espectaculo de la base de datos.
     * @param espectaculo: Espectaculo que se quiere eliminar.
     * @return boolean: Indica si la eliminacionse ha ejecutado con exito.
     */
    public boolean deleteEspectaculo(Espectaculo espectaculo){
        if (0 < sqLiteDatabase.delete("t_espectaculos", "id = ?", new String[]{String.valueOf(espectaculo.getId())})){
            return true;}
        else{
            return false;}
    }

    /**
     * cursorToEspectaculo: Parsea los datos de un Cursor a una lista de Espectaculo
     * @param cursor: Consulta donde se encuentran los datos
     * @return Espectaculo: Espectaculo que contiene los datos que se encontraban en el cursor.
     *      nulo en el caso de que no se pueda completar la operacion.
     */
    @SuppressLint("Range")
    private List<Espectaculo> cursorToListaEspectaculo(Cursor cursor){
        // Variables
        List<Espectaculo> espectaculos = new ArrayList<>();
        int id;
        String nombre;
        int duracion;
        String horarios;
        String descripcion;
        Espectaculo espectaculo;

        // Leer cursor
        if (cursor.moveToFirst()) {
            do {
                // Lectura
                id = cursor.getInt(cursor.getColumnIndex("id"));
                nombre = cursor.getString(cursor.getColumnIndex("name"));
                duracion = cursor.getInt(cursor.getColumnIndex("duracion"));
                horarios = cursor.getString(cursor.getColumnIndex("horarios"));
                descripcion = cursor.getString(cursor.getColumnIndex("descripcion"));
                // Crear Espectaculo
                espectaculo = new Espectaculo(id,nombre,duracion,horarios,descripcion);
                espectaculos.add(espectaculo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return espectaculos;
    }

    /**
     * ObtenerIdDisponible: Devuelve un Espectaculo.id libre que sea el minimo disponible.
     * @return id: int id minimo disponible.
     */
    public int ObtenerIdDisponible(){
        List<Espectaculo> espectaculos = readAllEspectaculo();
        // Obtengo todos los ids
        Set<Integer> ids = new HashSet<>();
        for (Espectaculo espectaculo: espectaculos) {
            ids.add(espectaculo.getId());
        }
        // Compruebo cual es el id menor disponible
        int id = 0;
        while (ids.contains(id)){
            id++;
        }
        return id;
    }
}
