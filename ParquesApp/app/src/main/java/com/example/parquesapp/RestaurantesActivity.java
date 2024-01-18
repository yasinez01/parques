package com.example.parquesapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parquesapp.Clases.Atraccion;
import com.example.parquesapp.Clases.Espectaculo;
import com.example.parquesapp.Clases.Restaurante;
import com.example.parquesapp.Controller.RestauranteController;
import com.example.parquesapp.db.DbHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class RestaurantesActivity extends AppCompatActivity {
    private ListView listView;
    private RestauranteController controller;
    private List<Restaurante> restaurantes;

    private ArrayAdapter<Restaurante> adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurantes_activity);
        listView = findViewById(R.id.list_restaurantes);
        controller = new RestauranteController(this);
        controller.open();
        restaurantes = controller.obtenerTodosRestaurantes();
        //anadirDatosVista();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, restaurantes);
        listView.setAdapter(adaptador);

        boolean esAdmin = getIntent().getBooleanExtra("admin", false);
        comprobarSiEsAdmin(esAdmin);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModificarOEliminarRestaurante(view, restaurantes.get(position));
            }
        });*/
        //controller.close();
    }

    private void comprobarSiEsAdmin(boolean esAdmin) {
        Button miBoton = findViewById(R.id.nuevoRestaurante);
        ListView listaRestaurantes = findViewById(R.id.list_restaurantes);
        if (!esAdmin) {
            miBoton.setVisibility(View.INVISIBLE);
            miBoton.setEnabled(false);
            System.out.println("No eres admin RestauranteActivity");
            listaRestaurantes.setOnItemClickListener(null);
        } else {
            miBoton.setVisibility(View.VISIBLE);
            miBoton.setEnabled(true);
            System.out.println("Eres admin RestauranteActivity");
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ModificarOEliminarRestaurante(view, restaurantes.get(position));
                }
            });
        }
    }

    public void ModificarOEliminarRestaurante(View view, Restaurante restaurante){
        Intent intent = new Intent(RestaurantesActivity.this, ModificarOEliminarRestaurante.class);
        intent.putExtra("restaurante", restaurante);
        startActivityForResult(intent,2);
    }
    /*
    @SuppressLint("Range")
    public void cargarRestaurantesDesdeDB(Context context) {
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Asegúrate de que la tabla t_restaurante esté creada
        try {
            // Asegúrate de que la tabla t_restaurante esté creada
            dbHelper.onCreate(db);
        } catch (Exception e) {
            // Manejo de errores si la tabla ya existe
        }
        // Consulta la tabla t_restaurante
        String query = "SELECT * FROM t_restaurante";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String nombre = cursor.getString(cursor.getColumnIndex("name"));
                int horarioApertura = cursor.getInt(cursor.getColumnIndex("horario_apertura"));
                int horarioCierre = cursor.getInt(cursor.getColumnIndex("horario_cierre"));

                Restaurante restaurante = new Restaurante(id, nombre, horarioApertura, horarioCierre);
                restaurantes.add(restaurante);
            } while (cursor.moveToNext());
        }
        cursor.close();
    }
    */
   public void Volver(View view){
       finish();
   }

    public void NuevoRestaurante(View view){
        Intent intent = new Intent(RestaurantesActivity.this, NuevoRestaurante.class);
        int id = controller.ObtenerIdDisponible();
        intent.putExtra("id", id);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1)
        { // Añadir Espectaculo
            if (resultCode == 1)
            { // Exito
                // Recibir espectaculo
                Restaurante restaurante = data.getParcelableExtra("restaurante");
                // Añadirlo
                controller.agregarRestaurante(restaurante);
                // Actualizar la lista y vista
                restaurantes = controller.obtenerTodosRestaurantes();
                adaptador.clear();
                adaptador.addAll(restaurantes);
                adaptador.notifyDataSetChanged();}}
        if (requestCode == 2)
        { // Modificar o Eliminar
            if (resultCode == 1)
            { //Modificar
                // Recibir espectaculo
                Restaurante restaurante = data.getParcelableExtra("restaurante");
                // Introducirlo
                controller.actualizarRestaurante(restaurante);
                // Actualizar la lista y vista
                restaurantes = controller.obtenerTodosRestaurantes();
                adaptador.clear();
                adaptador.addAll(restaurantes);
                adaptador.notifyDataSetChanged();
            } else if (resultCode == 2)
            { //Eliminar
                // Recibir espectaculo
                Restaurante restaurante = data.getParcelableExtra("restaurante");
                // Eliminarlo
                controller.eliminarRestaurante(restaurante.getId());
                // Actualizar la lista y vista
                restaurantes = controller.obtenerTodosRestaurantes();
                adaptador.clear();
                adaptador.addAll(restaurantes);
                adaptador.notifyDataSetChanged();
            }
        }
    }


}
