package com.example.agendify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.agendify.Entidades.Notas;
import com.example.agendify.adaptadores.ListaNotasAdapter;
import com.example.agendify.db.DbNotas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class LeerNota extends AppCompatActivity {

    RecyclerView RecycleListaNotas;
    ArrayList<Notas> listaArrayNotas;

    FloatingActionButton floatingVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leer_nota);

        // Inicialización de vistas
        RecycleListaNotas = findViewById(R.id.RecycleListaNotas);
        floatingVolver = findViewById(R.id.floatingVolver);

        // Configuración del RecyclerView con un LinearLayoutManager
        RecycleListaNotas.setLayoutManager(new LinearLayoutManager(this));

        // Crear una nueva instancia de DbNotas
        DbNotas dbNotas = new DbNotas(LeerNota.this);

        // Inicialización de la lista de notas
        listaArrayNotas = new ArrayList<>();
        Log.d("LeerNota", "Número de notas obtenidas: " + listaArrayNotas.size());

        // Inicializar el adaptador con la lista de notas de la base de datos
        ListaNotasAdapter adapter = new ListaNotasAdapter(dbNotas.mostrarNotas());
        RecycleListaNotas.setAdapter(adapter);

        // Configuración del botón flotante para volver al menú principal
        floatingVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LeerNota.this, Menu.class));
            }
        });
    }
}
