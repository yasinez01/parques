package com.example.parquesapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class NuevaAtraccion extends Activity {

    String name, last_update;
    int id, wait_time;

    EditText nameEditText, idEditText, waitTimeEditText, lastUpdateEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_atraccion);
    }

    public void añadir_atraccion(View view){
        Intent resultIntent = new Intent();

        nameEditText = findViewById(R.id.name);
        idEditText = findViewById(R.id.id);
        waitTimeEditText = findViewById(R.id.wait_time);
        lastUpdateEditText = findViewById(R.id.last_update);

        name = nameEditText.getText().toString();
        id = Integer.parseInt(idEditText.getText().toString());
        wait_time = Integer.parseInt(waitTimeEditText.getText().toString());
        last_update = lastUpdateEditText.getText().toString();

        resultIntent.putExtra("name", name);
        resultIntent.putExtra("id", id);
        resultIntent.putExtra("wait_time", wait_time);
        resultIntent.putExtra("last_update", last_update);

        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
