package com.example.parquesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parquesapp.Clases.Espectaculo;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NuevoEspectaculo extends AppCompatActivity {
    // Variables
    EditText editTextName;
    EditText editTextDuracion;
    EditText editTextHorarios;
    EditText editTextDescripcion;
    int id;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_espectaculo);

        // Obtengo el id enviado
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        // Conecto con los elementos de la vista
        editTextName = findViewById(R.id.activity_nuevo_espectaculo_etNombre);
        editTextDuracion = findViewById(R.id.activity_nuevo_espectaculo_etDuracion);
        editTextDescripcion = findViewById(R.id.activity_nuevo_espectaculo_etDescripcion);
        editTextHorarios = findViewById(R.id.activity_nuevo_espectaculo_etHorarios);
    }

    // Acciones de los botones
    /**
     * Volver: Vuelve a la activity anterior sin realizar cambios.
     * @param view
     */
    public void Volver(View view){
        finish();
    }

    /**
     * AñadirEspectaculo: Devuelve un Espectaculo a crear al activity que le llama a partir de los datos rellenados en la vista.
     * @param view
     */
    public void AñadirEspectaculo(View view){
        // Variables
        String nombre;
        int duracion;
        String horarios;
        String descripcion;

        // Compruebo si los datos introducidos son validos
        try { nombre = editTextName.getText().toString();}
        catch (Exception e){
            Toast.makeText(this,"El formato usado en nombre no es válido. Ej: Espectaculo.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ej: Espectaculo", Toast.LENGTH_LONG).show();
            return;}
        if (null == nombre){Toast.makeText(this,"El campo nombre no puede estar vacio.", Toast.LENGTH_LONG).show();return;}

        try { duracion = Integer.parseInt(editTextDuracion.getText().toString());}
        catch (Exception e){
            Toast.makeText(this,"El formato usado en duración no es válido.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ej: 30", Toast.LENGTH_LONG).show();
            return;}
        if (duracion < 0){Toast.makeText(this,"El campo numero no puede estar vacio y debe ser mayo o igual que 0.", Toast.LENGTH_LONG).show();return ;}

        try { horarios = editTextHorarios.getText().toString();}
        catch (Exception e){
            Toast.makeText(this,"El formato usado en horarios no es válido.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ej: 16:00-18:00", Toast.LENGTH_LONG).show();
            return;}
        if (null == horarios){Toast.makeText(this,"El campo horarios no puede estar vacio.", Toast.LENGTH_SHORT).show();return;}
        if(!comprobarHoras(horarios)){
            Toast.makeText(this,"El campo horarios no cumple con el formato.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ej: 16:00-18:00", Toast.LENGTH_LONG).show();
            return;}

        try { descripcion = editTextDescripcion.getText().toString();}
        catch (Exception e){
            Toast.makeText(this,"El formato usado en descripcion no es válido.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ej: Descripción del espectaculo.", Toast.LENGTH_LONG).show();
            return;}
        if (null == descripcion) {Toast.makeText(this,"El campo descripcion no puede estar vacio.", Toast.LENGTH_LONG).show();return ;}

        // Creo el espectaculo
        Espectaculo espectaculo = new Espectaculo(id,nombre,duracion,horarios,descripcion);

        // Envio el espectaculo
        Intent intent = new Intent();
        intent.putExtra("espectaculo", espectaculo);
        setResult(1, intent);
        finish();
    }

    /**
     * ComprobarHoras: Comprueba que el formato de las horas es valido.
     * @param horario
     * @return boolean: Indica si el String de horas propocionado es valido.
     */
    public static boolean comprobarHoras(String horario) {
        String patron = "^(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]-(0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$";
        Pattern pattern = Pattern.compile(patron);
        Matcher matcher = pattern.matcher(horario);
        return matcher.matches();
    }
}