// En DbNotas.java
package com.example.agendify.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.agendify.Entidades.Notas;

import java.util.ArrayList;

public class DbNotas extends DbHerramientas {

    Context context;

    // Constructor que recibe el contexto
    public DbNotas(Context context) {
        super(context);
        this.context = context;
    }

    // Método para insertar una nueva nota en la base de datos
    public long insertaNota(String titulo, String descripcion) {
        long result = 0;

        try {
            SQLiteDatabase dbNotas = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("titulo", titulo);
            values.put("descripcion", descripcion);

            result = dbNotas.insert(TABLE_NOTAS, null, values);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    // Método para obtener y devolver una lista de todas las notas desde la base de datos
    public ArrayList<Notas> mostrarNotas() {
        SQLiteDatabase dbNotas = this.getWritableDatabase();

        ArrayList<Notas> listaNotas = new ArrayList<>();
        Notas nota = null;
        Cursor cursorNotas = null;

        cursorNotas = dbNotas.rawQuery("SELECT * FROM " + TABLE_NOTAS, null);

        if (cursorNotas.moveToFirst()) {
            do {
                nota = new Notas();
                nota.setId(cursorNotas.getInt(0));
                nota.setTitulo(cursorNotas.getString(1));
                nota.setDescripcion(cursorNotas.getString(2));

                listaNotas.add(nota);
            } while (cursorNotas.moveToNext());
        }

        cursorNotas.close();

        return listaNotas;
    }

    // Método para ver una nota específica basada en su ID
    public Notas verNota(int id) {
        SQLiteDatabase dbNotas = this.getWritableDatabase();

        Notas nota = null;
        Cursor cursorNotas = null;

        cursorNotas = dbNotas.rawQuery("SELECT * FROM " + TABLE_NOTAS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorNotas.moveToFirst()) {
            nota = new Notas();
            nota.setId(cursorNotas.getInt(0));
            nota.setTitulo(cursorNotas.getString(1));
            nota.setDescripcion(cursorNotas.getString(2));
        }

        cursorNotas.close();

        return nota;
    }

    // Método para editar una nota existente en la base de datos
    public boolean editarNota(int id, String titulo, String descripcion) {
        boolean correcto = false;

        SQLiteDatabase dbNotas = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titulo", titulo);
        values.put("descripcion", descripcion);

        try {
            int rowsAffected = dbNotas.update(TABLE_NOTAS, values, "id=?", new String[]{String.valueOf(id)});
            correcto = rowsAffected > 0;
        } catch (Exception e1) {
            e1.printStackTrace();
            correcto = false;
        } finally {
            dbNotas.close();
        }

        return correcto;
    }

    // Método para eliminar una nota basada en su ID
    public boolean eliminarNota(int id) {
        boolean correcto = false;

        SQLiteDatabase dbNotas = this.getWritableDatabase();

        try {
            dbNotas.execSQL("DELETE FROM " + TABLE_NOTAS + " WHERE id = '"+id+"'");
            correcto = true;
        } catch (Exception e1) {
            e1.printStackTrace();
            correcto = false;
        } finally {
            dbNotas.close();
        }

        return correcto;
    }
}
