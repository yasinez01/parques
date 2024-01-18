package com.example.parquesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.parquesapp.Clases.Espectaculo;
import com.example.parquesapp.Clases.Restaurante;

public class NuevoRestaurante extends AppCompatActivity {

    private int id;
    private EditText editTextName;
    private EditText editTextApertura;
    private EditText editTextCierre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_restaurante);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        // Conecto con los elementos de la vista
        editTextName = findViewById(R.id.activity_nuevo_restaurante_etNombre);
        editTextApertura= findViewById(R.id.activity_nuevo_restaurante_etApertura);
        editTextCierre = findViewById(R.id.activity_nuevo_restaurante_etCierre);

    }

    public void AnadirRestaurante(View view) {
        //Variables
        String nombre;
        int apertura;
        int cierre;

        // Obtengo los datos nuevos y compruebo su valided
        try { nombre = editTextName.getText().toString();}
        catch (Exception e){
            Toast.makeText(this,"El formato usado en nombre no es válido. Ej: Restaurante.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ej: Restaurante", Toast.LENGTH_LONG).show();
            return;}
        if (nombre.equals("")){Toast.makeText(this,"El campo nombre no puede estar vacio.", Toast.LENGTH_LONG).show(); return;}

        try { apertura = Integer.parseInt(editTextApertura.getText().toString());}
        catch (Exception e){
            Toast.makeText(this,"El formato usado en horario de apertura no es válido.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ej: 12", Toast.LENGTH_LONG).show();
            return;}
        if (apertura < 0){Toast.makeText(this,"El campo numero no puede estar vacio y debe ser mayo o igual que 0.", Toast.LENGTH_LONG).show();return ;}

        try { cierre = Integer.parseInt(editTextCierre.getText().toString());}
        catch (Exception e){
            Toast.makeText(this,"El formato usado en horario de cierre no es válido.", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Ej: 12", Toast.LENGTH_LONG).show();
            return;}
        if (cierre < 0){Toast.makeText(this,"El campo numero no puede estar vacio y debe ser mayo o igual que 0.", Toast.LENGTH_LONG).show();return ;}


        // Modifico el espectaculo
        Restaurante restaurante = new Restaurante(id,nombre,apertura,cierre);

        // Envio el espectaculo
        Intent intent = new Intent();
        intent.putExtra("restaurante", restaurante);
        setResult(1, intent);
        finish();
    }
}