package com.example.agendify.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHerramientas extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NOMBRE = "agendify.db";
    public static final String TABLE_CONTACTOS = "t_contactos";
    public static final String TABLE_NOTAS = "t_notas"; // Corregido el nombre de la tabla

    // Constructor que recibe el contexto
    public DbHerramientas(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }

    @Override
    // Método llamado al crear la base de datos por primera vez
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla de contactos
        db.execSQL("CREATE TABLE " + TABLE_CONTACTOS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT NOT NULL, " +
                "telefono TEXT NOT NULL, " +
                "correo_electronico TEXT)");

        // Crear la tabla de notas
        db.execSQL("CREATE TABLE " + TABLE_NOTAS + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "titulo TEXT NOT NULL, " +
                "descripcion TEXT NOT NULL) ");
    }

    @Override
    // Método llamado al actualizar la base de datos a una nueva versión
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Eliminar las tablas antiguas si existen
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTAS);
        // Crear las nuevas tablas
        onCreate(db);
    }
}
