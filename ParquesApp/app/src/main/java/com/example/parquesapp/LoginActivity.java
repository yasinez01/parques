package com.example.parquesapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parquesapp.db.DbHelper;

public class LoginActivity extends AppCompatActivity {

    private String userName;
    private String password;
    public boolean sesionIniciada;
    EditText userNameEditText, passwordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sesionIniciada = false;
    }

    @Override
    public void onBackPressed() {
    }

    public void comprobarDatosUsuario(View view){
        userNameEditText = findViewById(R.id.userName);
        passwordEditText = findViewById(R.id.password);

        userName = userNameEditText.getText().toString();
        password = passwordEditText.getText().toString();

        try (DbHelper dbHelper = new DbHelper(LoginActivity.this)) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM t_usuarios WHERE user_name = ? AND password = ?", new String[]{userName, String.valueOf(password)});
            if (cursor.moveToFirst()) {
                sesionIniciada = true;
                int adminColumnIndex = cursor.getColumnIndex("admin");
                if (adminColumnIndex != -1) {
                    int adminValue = cursor.getInt(adminColumnIndex);
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("adminValue", adminValue);
                    setResult(RESULT_OK, returnIntent);
                    finish();
                }
            } else {
                sesionIniciada = false;
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e){
            throw e;
        }
    }

    public void Volver(View view){
        finish();
    }
}