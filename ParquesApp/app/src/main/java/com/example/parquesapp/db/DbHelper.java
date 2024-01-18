package com.example.parquesapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME= "portaventura.db";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        rellenarTablaEspectaculos(sqLiteDatabase);
        rellenarTablaAtracciones(sqLiteDatabase);
        rellenarTablaUsuarios(sqLiteDatabase);
        rellenarTablaRestaurantes(sqLiteDatabase);
    }

    private void rellenarTablaRestaurantes(SQLiteDatabase sqLiteDatabase){
        if (!tablaExiste(sqLiteDatabase, "t_restaurante")) {
            sqLiteDatabase.execSQL("CREATE TABLE t_restaurante ( id INTEGER PRIMARY KEY, name TEXT NOT NULL, horario_apertura INTEGER ,  horario_cierre INTEGER)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(1,'La Cantina', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(2,'La Cocina', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(3,'La Hacienda', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(4,'La Parrilla', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(5,'La Piazza', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(6,'La Selva', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(7,'La Taverna', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(8,'La Trattoria', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(9,'La Ventana', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(10,'La Victoria', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(11,'La Salchica', 10, 18)");
            sqLiteDatabase.execSQL("insert into t_restaurante(id, name, horario_apertura, horario_cierre) values(12,'Bora Bora', 10, 18)");
        }
    }
    private void rellenarTablaAtracciones(SQLiteDatabase sqLiteDatabase){
        if (!tablaExiste(sqLiteDatabase, "t_atraccion")) {
            sqLiteDatabase.execSQL("CREATE TABLE t_atraccion ( id INTEGER PRIMARY KEY, name TEXT NOT NULL, wait_time INTEGER, last_update TEXT, description TEXT)");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(586,'Armadillos',0, '2023-10-17T17:05:22.000Z', 'Atraccion infantil con emocionantes vueltas a lomos de un armadillo')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(588,'Canoes',0, '2023-10-17T17:05:22.000Z', 'Atraccion infantil donde recorrerás un rio en una canoa')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(589,'Carousel',0, '2023-10-17T17:05:22.000Z', 'Vueltas y vueltas en el mitico Carousel')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(12142,'Coco Piloto',0, '2023-10-17T17:05:22.000Z', 'Vuela con tu amigo coco')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(592,'Crazy Barrels',0, '2023-10-17T17:05:22.000Z', 'Gira sin control en estos barriles locos')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(593,'Dragon Khan',0, '2023-10-17T17:05:22.000Z', 'Vuela al lomos del espiritu del emperador en forma de dragon')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(597,'El Salto de Blas',0, '2023-10-17T17:05:22.000Z', 'Salta con tu amigo Blas')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(600,'Furius Baco',0, '2023-10-17T17:05:22.000Z', 'Prueba el loco invento del cientifico chiflado a toda velocidad')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(601,'Grand Canyon Rapids',0, '2023-10-17T17:05:22.000Z', 'Embarcate rio abajo en unas ruedas locas')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(602,'Hurakan Condor',0, '2023-10-17T17:05:22.000Z', 'Cae desde 100 metros de altura en caida libre')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(12144,'Kiddie Dragons',0, '2023-10-17T17:05:22.000Z', 'Atraccion infantil de dragones saltarines')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(12140,'Kontiki',0, '2023-10-17T17:05:22.000Z', 'Atraccion para toda la familia')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(605,'La Granja de Elmo',0, '2023-10-17T17:05:22.000Z', 'Atraccion para toda la familia')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(608,'Los Potrillos',0, '2023-10-17T17:05:22.000Z', 'Galopa a lomos de unos potrillos')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(609,'Magic Fish',0, '2023-10-17T17:05:22.000Z', 'Agarrate fuerte que este pez va muy rapido')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(610,'Mariposas Saltarinas',0, '2023-10-17T17:05:22.000Z', 'Salta como las mariposas')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(613,'Serpiente Emplumada',0, '2023-10-17T17:05:22.000Z', 'GIRA GIRA GIRA')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(615,'Shambhala',0, '2023-10-17T17:05:22.000Z', 'Vuela hasta encontrar el reino de Shambhala, escondido en el Himalaya')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(616,'Silver River Flume',0, '2023-10-17T17:05:22.000Z', 'Cae por cascadas encima de unos trocos')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(617,'Stampida',0, '2023-10-17T17:05:22.000Z', 'Una gran carrera de carretas')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(6802,'Street Mission',0, '2023-10-17T17:05:22.000Z', 'Ayuda a coco a buscar la galleta perdida')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(618,'Tami - Tami',0, '2023-10-17T17:05:22.000Z', 'Montaña rusa infantil, pero muy divertida')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(621,'Tutuki Splash',0, '2023-10-17T17:05:22.000Z', 'Explora con una barca el temible volcan')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(12121,'Uncharted',0, '2023-10-17T17:05:22.000Z', 'Ayuda a tus amigos Drake y Sully')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(622,'VolPaiute',0, '2023-10-17T17:05:22.000Z', 'Gira mientras bailas')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(623,'Waikiki',0, '2023-10-17T17:05:22.000Z', 'Atraccion para toda la familia')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(625,'Wild Buffalos',0, '2023-10-17T17:05:22.000Z', 'Autos de choque')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(626,'Yucatán',0, '2023-10-17T17:05:22.000Z', 'Gira sin control')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(590,'Cobra imperial',0, '2023-10-17T17:05:22.000Z', 'Gira sin control')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(595,'El Diablo Tren De La Mina',0, '2023-10-17T17:05:22.000Z', 'Montate en los carros de una mina de plata dirigidos por el mismisimo diablo')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(12118,'Kiddie Balloons',0, '2023-10-17T17:05:22.000Z', 'Atraccion para toda la familia')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(12143,'Kiddie Kities',0, '2023-10-17T17:05:22.000Z', 'Atraccion para toda la familia')");
            sqLiteDatabase.execSQL("insert into t_atraccion(id, name, wait_time, last_update, description) values(619,'Tea Cups',0, '2023-10-17T17:05:22.000Z', 'Girad sin parar en estas tazas chinas')");
        }
    }
    private void rellenarTablaEspectaculos(SQLiteDatabase sqLiteDatabase) {
       if (!tablaExiste(sqLiteDatabase, "t_espectaculos")) {
            sqLiteDatabase.execSQL("CREATE TABLE t_espectaculos ( id INTEGER PRIMARY KEY, name TEXT NOT NULL, duracion INTEGER, horarios TEXT,descripcion TEXT)");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(1,'Bang Bang West', 30, '12:00-16:00', 'Espectaculo de bang bang')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(2,'Cantajuegos', 30, '10:00-17:00', 'Espectaculo de cantajuegos')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(3,'Cirque de Soleil', 30, '10:00-17:00', 'Espectaculo de circo de Soleil')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(4,'La Maldición del Emperador', 30, '09:00-15:30', 'Espectaculo de La Maldición del Emperador')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(5,'La llegada de los emisarios reales', 30, '08:00-20:00', 'Espectaculo de La llegada de los emisarios reales')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(6,'FiestAventura', 30, '12:00-13:00', 'Espectaculo de FiestAventura')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(7,'Christmas Parade', 30, '12:00-15:00', 'Espectaculo de Christmas Parade')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(8,'PortAventura Parade', 30, '11:00-18:00', 'Espectaculo de PortAventura Parade')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(9,'Noches de Fuego en Tahití', 30, '12:00-19:00', 'Espectaculo de Noches de Fuego en Tahití')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(10,'Aves del Paraíso', 30, '10:00-12:00', 'Espectaculo de Aves del Paraíso')");
            sqLiteDatabase.execSQL("insert into t_espectaculos(id, name, duracion, horarios, descripcion) values(11,'Aloha Tahití', 30, '16:00-19:00', 'Espectaculo de Aloha Tahití')");
        }
    }
    private void rellenarTablaUsuarios(SQLiteDatabase sqLiteDatabase){
        if (!tablaExiste(sqLiteDatabase, "t_usuarios")) {
            sqLiteDatabase.execSQL("CREATE TABLE t_usuarios ( id INTEGER PRIMARY KEY, user_name TEXT NOT NULL, password TEXT NOT NULL, admin INTEGER NOT NULL)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(1,'Xabier', '1234', 1)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(2,'Yasin', '1234', 1)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(3,'Javier', '1234', 1)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(4,'Luis', '1234', 1)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(5,'Fermin', '1234', 0)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(6,'Axel', '1234', 0)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(7,'Yeray', '1234', 0)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(8,'Ivan', '1234', 0)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(9,'Maria', '1234', 0)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(10,'Marta', '1234', 0)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(11,'Isabel', '1234', 0)");
            sqLiteDatabase.execSQL("insert into t_usuarios(id, user_name, password, admin) values(12,'Nuria', '1234', 0)");
        }
    }

    private boolean tablaExiste(SQLiteDatabase db, String tabla) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name=?", new String[]{tabla});
        if (cursor != null) {
            boolean existe = cursor.getCount() > 0;
            cursor.close();
            return existe;
        }
        return false;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        rellenarTablaEspectaculos(sqLiteDatabase);
        rellenarTablaUsuarios(sqLiteDatabase);
    }
}
