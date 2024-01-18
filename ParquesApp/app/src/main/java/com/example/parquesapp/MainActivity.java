package com.example.parquesapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.parquesapp.Clases.Atraccion;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.parquesapp.Controller.AtraccionController;
import com.example.parquesapp.db.DbHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private List<JSONObject> jsonObjectList = new ArrayList<>();
    private List<Atraccion> atraccionesDesdeDB = new ArrayList<>();
    private List<Atraccion> atracciones = new ArrayList<>();
    private AtraccionController atraccionController = new AtraccionController(this);
    public boolean admin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        atraccionController.open();
        initializeDataBase();
        try {
            actualizarTiempoEspera();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        obtenerAtraccionesBD();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, 2);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            String nombre = data.getStringExtra("name");
            int id = data.getIntExtra("id", 0);
            int tiempoEspera = data.getIntExtra("wait_time", 0);
            String ultimaActualizacion = data.getStringExtra("last_update");
            boolean is_open = true;

            Atraccion atraccion = new Atraccion(id, nombre, tiempoEspera, ultimaActualizacion,is_open);
            atracciones.add(atraccion);
            MyCustomAdapter adapter = new MyCustomAdapter(this, atracciones);
            listView.setAdapter(adapter);
        }
        else if(requestCode == 2 && resultCode == RESULT_OK && data != null){
            int adminValue = data.getIntExtra("adminValue", 0);
            if(adminValue == 1){
                admin = true;
            } else {
                admin = false;
            }
        }
    }

    public void cerrarSesion(View view) {
        admin = false;
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivityForResult(intent, 2);
    }

    private void obtenerAtraccionesBD() {
        try (DbHelper dbHelper = new DbHelper(MainActivity.this)) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            //dbHelper.rellenar(db);

            Cursor cursor = db.rawQuery("SELECT * FROM t_atraccion", null);
            if (cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(0);
                    String nombre = cursor.getString(1);
                    int tiempoEspera = cursor.getInt(2);
                    String ultimaActualizacion = cursor.getString(3);
                    String descripcion = cursor.getString(4);

                    Atraccion atraccion = new Atraccion(id, nombre, tiempoEspera, ultimaActualizacion, descripcion);

                    atraccionesDesdeDB.add(atraccion);
                } while (cursor.moveToNext());
            }

            db.close();

            runOnUiThread(() -> {
                atracciones.clear();
                atracciones.addAll(atraccionesDesdeDB);
                MyCustomAdapter adapter = new MyCustomAdapter(this, atracciones);
                listView.setAdapter(adapter);
            });
        } catch (Exception e) {
            throw e;
        }
    }

    private void anadirDatos(){
        try (DbHelper dbHelper = new DbHelper(MainActivity.this)) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            Cursor curs = db.rawQuery("SELECT * FROM t_atraccion", null);
            if (curs.moveToFirst()) {
                do {
                    for (Atraccion atraccion : atracciones) {
                        if (atraccion.getId() == curs.getInt(0)) {
                            atraccion.setDescripcion(curs.getString(4));
                        }
                    }
                } while (curs.moveToNext());
            }
            curs.close();
        } catch(Exception e) {
            System.out.println("Hay una exception");
        }
    }

    private void procesarDatosApi(String response) {
        try {
            System.out.println(response);
            JSONObject json = new JSONObject(response);
            JSONArray atraccionesArray = json.getJSONArray("rides");
            int c = 0;
            for (int i = 0; i < atraccionesArray.length(); i++) {
                JSONObject atraccionObject = atraccionesArray.getJSONObject(i);

                jsonObjectList.add(atraccionObject);

                int id = atraccionObject.getInt("id");
                String nombre = atraccionObject.getString("name");
                int tiempoEspera = atraccionObject.getInt("wait_time");
                String ultimaActualizacion = atraccionObject.getString("last_updated");
                boolean is_open = atraccionObject.getBoolean("is_open");
                if (is_open) {
                    c = c + 1;
                    Atraccion atraccion = new Atraccion(id, nombre, tiempoEspera, ultimaActualizacion, is_open);
                    atracciones.add(atraccion);
                }
            }

            anadirDatos();
            actualizarListView();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void actualizarTiempoEspera() throws MalformedURLException {
        AsyncTask.execute(() -> {
            try {
                // Conectar API
                URL url = new URL("https://queue-times.com/parks/19/queue_times.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Leer respuesta
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String linea;
                StringBuilder respuesta = new StringBuilder();
                while ((linea = reader.readLine()) != null) {respuesta.append(linea);}
                reader.close();
                connection.disconnect();

                // Actualizar tiempos espera
                try {
                    JSONObject jsonObject = new JSONObject(respuesta.toString());
                    JSONArray ridesArray = jsonObject.getJSONArray("rides");

                    List<Atraccion> atracciones = atraccionController.readAllAtraccion();
                    for (int i = 0; i < ridesArray.length(); i++) {
                        JSONObject ride = ridesArray.getJSONObject(i);
                        int id = ride.getInt("id");
                        int waitTime = ride.getInt("wait_time");

                        // Busco por id la atraccion correspondiente
                        for (Atraccion atraccionBBDD: atracciones)
                        {
                            if (id == atraccionBBDD.getId())
                            {
                                Atraccion atraccion = atraccionBBDD;
                                atraccion.setWait_time(waitTime);
                                atraccionController.updateAtraccion(atraccion);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            atracciones = atraccionController.readAllAtraccion();
            actualizarListView();
        });
    }

    private void actualizarListView() {
        runOnUiThread(() -> {
            MyCustomAdapter adapter = new MyCustomAdapter(this, atracciones);
            listView.setAdapter(adapter);
        });
    }

    private void obtenerDatosApi() {
        AsyncTask.execute(() -> {
            try {
                // Conectar con API
                URL url = new URL("https://queue-times.com/parks/19/queue_times.json");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    Scanner scanner = new Scanner(inputStream, "UTF-8").useDelimiter("\\A");
                    String response = scanner.hasNext() ? scanner.next() : "";

                    procesarDatosApi(response);
                }
                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private static class MyCustomAdapter extends BaseAdapter {
        private final List<Atraccion> atracciones;
        private final LayoutInflater inflater;

        public MyCustomAdapter(Context context, List<Atraccion> atracciones) {
            this.atracciones = atracciones;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return atracciones.size();
        }

        @Override
        public Object getItem(int position) {
            return atracciones.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint({"SetTextI18n", "InflateParams"})
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.row_atraccion, null);
            }

            Atraccion atraccion = atracciones.get(position);
            TextView textViewNombre = convertView.findViewById(R.id.textViewNombre);
            textViewNombre.setText(atraccion.getNombre());

            TextView textViewId = convertView.findViewById(R.id.textViewDescripcion);
            textViewId.setText("Descripcion: AÑADIR");

            TextView textViewTiempoEspera = convertView.findViewById(R.id.textViewTiempoEspera);

            // Paso a horas y minutos
            int minutosIniciales = atraccion.getTiempoEspera();
            int horas = (int) TimeUnit.MINUTES.toHours(minutosIniciales);
            int minutos = (int) (minutosIniciales - TimeUnit.HOURS.toMinutes(horas));
            textViewTiempoEspera.setText(String.format("%02d:%02d", horas, minutos));
            if(atraccion.getTiempoEspera()>0){
                textViewTiempoEspera.setTextColor(Color.RED);
            }else{
                textViewTiempoEspera.setTextColor(Color.BLUE);
            }

            TextView textViewDesc = convertView.findViewById(R.id.textViewDescripcion);
            textViewDesc.setText(String.valueOf(atraccion.getDescripcion()));


            ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView);
            Context context = convertView.getContext();
            int imagenResId = context.getResources().getIdentifier("id" + atraccion.getId(), "drawable", context.getPackageName());
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imagenResId);
            imageView.setImageBitmap(bitmap);

            return convertView;
        }
    }

    public void initializeDataBase(){
        try (DbHelper dbHelper = new DbHelper(MainActivity.this)){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            if(db != null){
                Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MainActivity.this, "La base de datos no se ha creado", Toast.LENGTH_LONG).show();
            }

        } catch(Exception e) {
            throw e;
        }
    }
    public void new_atraction(View view){
        Intent intent = new Intent(MainActivity.this, NuevaAtraccion.class);
        startActivityForResult(intent, 1);
    }
    public void ver_restaurantes(View view){
        Intent intent = new Intent(MainActivity.this, RestaurantesActivity.class);
        intent.putExtra("admin", admin);
        startActivityForResult(intent, 4);
    }
    public void ver_espectaculos(View view){
        Intent intent = new Intent(MainActivity.this, EspectaculoActivity.class);
        intent.putExtra("admin", admin);
        startActivityForResult(intent, 3);
    }
}
