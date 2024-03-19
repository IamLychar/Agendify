package com.example.agendify.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agendify.Entidades.Contactos;

import java.util.ArrayList;

public class DbContactos extends DbHerramientas {

    Context context;

    // Constructor que recibe el contexto
    public DbContactos(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    // Método para insertar un nuevo contacto en la base de datos
    public long insertaContactos(String nombre, String telefono, String correo_electronico) {
        long result = 0; // Valor predeterminado en caso de error
        try {
            DbHerramientas dbHerramientas = new DbHerramientas(context);
            SQLiteDatabase dbdatos = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("nombre", nombre);
            values.put("telefono", telefono);
            values.put("correo_electronico", correo_electronico);
            result = dbdatos.insert(TABLE_CONTACTOS, null, values);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return result;
    }

    // Método para obtener la lista de todos los contactos almacenados en la base de datos
    public ArrayList<Contactos> mostrarContactos() {
        DbHerramientas dbHerramientas = new DbHerramientas(context);
        SQLiteDatabase dbdatos = this.getWritableDatabase();
        ArrayList<Contactos> listaContantos = new ArrayList<>();
        Contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = dbdatos.rawQuery("SELECT * FROM " + TABLE_CONTACTOS, null);

        if (cursorContactos.moveToFirst()) {
            do {
                contacto = new Contactos();
                contacto.setId(cursorContactos.getInt(0));
                contacto.setNombre(cursorContactos.getString(1));
                contacto.setTelefono(cursorContactos.getString(2));
                contacto.setCorreo_electronico(cursorContactos.getString(3));
                listaContantos.add(contacto);
            } while (cursorContactos.moveToNext());
        }

        cursorContactos.close();

        return listaContantos;
    }

    // Método para obtener un contacto específico por su ID
    public Contactos verContactos(int id) {
        DbHerramientas dbHerramientas = new DbHerramientas(context);
        SQLiteDatabase dbdatos = this.getWritableDatabase();
        Contactos contacto = null;
        Cursor cursorContactos = null;

        cursorContactos = dbdatos.rawQuery("SELECT * FROM " + TABLE_CONTACTOS + " WHERE id = " + id + " LIMIT 1", null);

        if (cursorContactos.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursorContactos.getInt(0));
            contacto.setNombre(cursorContactos.getString(1));
            contacto.setTelefono(cursorContactos.getString(2));
            contacto.setCorreo_electronico(cursorContactos.getString(3));
        }

        cursorContactos.close();

        return contacto;
    }

    // Método para editar un contacto existente en la base de datos
    public boolean editarContactos(int id, String nombre, String telefono, String correo_electronico) {
        boolean correcto = false;

        SQLiteDatabase dbdatos = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);
        values.put("correo_electronico", correo_electronico);

        try {
            int rowsAffected = dbdatos.update(TABLE_CONTACTOS, values, "id=?", new String[]{String.valueOf(id)});
            correcto = rowsAffected > 0;
        } catch (Exception e1) {
            e1.printStackTrace();
            correcto = false;
        } finally {
            dbdatos.close();
        }

        return correcto;
    }

    // Método para eliminar un contacto por su ID
    public boolean eliminarContactos(int id) {
        boolean correcto = false;

        SQLiteDatabase dbdatos = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        try {
            // Ejecutar la instrucción SQL para eliminar el contacto con el ID especificado
            dbdatos.execSQL("DELETE FROM " + TABLE_CONTACTOS + " WHERE id = '" + id + "'");
            correcto = true;
        } catch (Exception e1) {
            e1.printStackTrace();
            correcto = false;
        } finally {
            dbdatos.close();
        }

        return correcto;
    }
}
