package com.example.parquesapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.parquesapp.Clases.Espectaculo;
import com.example.parquesapp.Controller.EspectaculoController;

import java.util.ArrayList;
import java.util.List;

public class EspectaculoActivity extends AppCompatActivity {
    private ListView listView;
    private EspectaculoController espectaculoController = new EspectaculoController(this);
    private List<Espectaculo> espectaculos = new ArrayList<>();
    ArrayAdapter<Espectaculo> adaptador;

    // Creacion de la Vista
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.espectaculo_activity);

        // Iniciar Lista
        listView = findViewById(R.id.list_espectaculos);
        espectaculoController.open();
        espectaculos = espectaculoController.readAllEspectaculo();
        adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, espectaculos);
        listView.setAdapter(adaptador);

        boolean esAdmin = getIntent().getBooleanExtra("admin", false);
        comprobarSiEsAdmin(esAdmin);

        // Seleccionar elementos lista
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ModificarOBorrarEspectaculo(view, espectaculos.get(position));
            }
        });*/
    }

    private void comprobarSiEsAdmin(boolean esAdmin) {
        Button miBoton = findViewById(R.id.nuevoEspectaculo);
        ListView listaEspectaculos = findViewById(R.id.list_espectaculos);
        if (!esAdmin) {
            miBoton.setVisibility(View.INVISIBLE);
            miBoton.setEnabled(false);
            //listaEspectaculos.setClickable(false);
            listaEspectaculos.setOnItemClickListener(null);
        } else {
            miBoton.setVisibility(View.VISIBLE);
            miBoton.setEnabled(true);
            //listaEspectaculos.setClickable(true);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    ModificarOBorrarEspectaculo(view, espectaculos.get(position));
                }
            });
        }
    }

    // Funciones OnClick

    /**
     * NuevoEspectaculo: Abre una vista para crear un nuevo espectaculo y devuelve este.
     * @param view
     */
    public void NuevoEspectaculo(View view){
        Intent intent = new Intent(EspectaculoActivity.this, NuevoEspectaculo.class);
        int id = espectaculoController.ObtenerIdDisponible();
        intent.putExtra("id", id);
        startActivityForResult(intent, 1);
    }

    /**
     * ModificarOBorrarEspectaculo: Abre una nueva vista para modificar o borrar el espectaculo seleccionado.
     * @param view
     * @param espectaculo
     */
    public void ModificarOBorrarEspectaculo(View view, Espectaculo espectaculo){
        Intent intent = new Intent(EspectaculoActivity.this, ModificarOEliminarEspectaculo.class);
        intent.putExtra("espectaculo", espectaculo);
        startActivityForResult(intent,2);
    }
    public void Volver(View view){
        finish();
    }

    // Respuesta
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == 1)
        { // Añadir Espectaculo
            if (resultCode == 1)
            { // Exito
                // Recibir espectaculo
                Espectaculo espectaculo = data.getParcelableExtra("espectaculo");
                // Añadirlo
                espectaculoController.createEspectaculo(espectaculo);
                // Actualizar la lista y vista
                espectaculos = espectaculoController.readAllEspectaculo();
                adaptador.clear();
                adaptador.addAll(espectaculos);
                adaptador.notifyDataSetChanged();}}
        if (requestCode == 2)
        { // Modificar o Eliminar
            if (resultCode == 1)
            { //Modificar
                // Recibir espectaculo
                Espectaculo espectaculo = data.getParcelableExtra("espectaculo");
                // Introducirlo
                espectaculoController.updateEspectaculo(espectaculo);
                // Actualizar la lista y vista
                espectaculos = espectaculoController.readAllEspectaculo();
                adaptador.clear();
                adaptador.addAll(espectaculos);
                adaptador.notifyDataSetChanged();
            } else if (resultCode == 2)
            { //Eliminar
                // Recibir espectaculo
                Espectaculo espectaculo = data.getParcelableExtra("espectaculo");
                // Eliminarlo
                espectaculoController.deleteEspectaculo(espectaculo);
                // Actualizar la lista y vista
                espectaculos = espectaculoController.readAllEspectaculo();
                adaptador.clear();
                adaptador.addAll(espectaculos);
                adaptador.notifyDataSetChanged();
            }
        }
    }
}
