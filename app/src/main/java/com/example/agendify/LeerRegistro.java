package com.example.agendify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.agendify.Entidades.Contactos;
import com.example.agendify.adaptadores.ListaContactosAdapter;
import com.example.agendify.db.DbContactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LeerRegistro extends AppCompatActivity {

    RecyclerView RecycleListaContactos;
    ArrayList<Contactos> listaArrayContactos;

    FloatingActionButton floatingVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_registro);

        // Inicialización de vistas
        RecycleListaContactos = findViewById(R.id.RecycleListaContactos);
        floatingVolver = findViewById(R.id.floatingVolver);

        // Configuración del RecyclerView con un LinearLayoutManager
        RecycleListaContactos.setLayoutManager(new LinearLayoutManager(this));

        // Crear una nueva instancia de DbContactos
        DbContactos dbContactos = new DbContactos(LeerRegistro.this);

        // Inicialización de la lista de contactos
        listaArrayContactos = new ArrayList<>();

        // Inicializar el adaptador con la lista de contactos de la base de datos
        ListaContactosAdapter adapter = new ListaContactosAdapter(dbContactos.mostrarContactos());
        RecycleListaContactos.setAdapter(adapter);

        // Configuración del botón flotante para volver al menú principal
        floatingVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeerRegistro.this, Menu.class));
            }
        });
    }
}
