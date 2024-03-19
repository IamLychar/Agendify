package com.example.agendify;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agendify.Entidades.Notas;
import com.example.agendify.db.DbNotas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VerNota extends AppCompatActivity {

    Button BtnGuardaNota;
    EditText EdTvTitulo, EdTvDescripcion;
    FloatingActionButton floatingActionButtonEditarNota;
    FloatingActionButton floatingActionButtonBorrarNota;
    FloatingActionButton floatingHome, floatingVolver;

    Notas nota;
    int id =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota);

        // Inicialización de elementos de la interfaz
        EdTvTitulo = findViewById(R.id.EdTvTitulo);
        EdTvDescripcion = findViewById(R.id.EdTvDescripcion);
        BtnGuardaNota = findViewById(R.id.BtnGuardaNota);
        floatingActionButtonEditarNota = findViewById(R.id.floatingActionButtonEditarNota);
        floatingActionButtonBorrarNota= findViewById(R.id.floatingActionButtonBorrarNota);
        floatingHome= findViewById(R.id.floatingHome);
        floatingVolver= findViewById(R.id.floatingVolver);

        // Obtener el ID de la nota desde los extras o el estado guardado
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = savedInstanceState.getInt("ID");
        }

        // Inicialización de la base de datos y obtención de la nota
        DbNotas dbNotas = new DbNotas(VerNota.this);
        nota = dbNotas.verNota(id);

        // Mostrar los detalles de la nota
        if (nota != null){
            EdTvTitulo.setText(nota.getTitulo());
            EdTvDescripcion.setText(nota.getDescripcion());
            BtnGuardaNota.setVisibility(View.INVISIBLE);
            EdTvTitulo.setInputType(InputType.TYPE_NULL);
            EdTvDescripcion.setInputType(InputType.TYPE_NULL);
        }

        // Configuración de los botones flotantes y sus acciones
        floatingVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerNota.this, LeerNota.class));
            }
        });

        floatingHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VerNota.this, Menu.class));
            }
        });

        floatingActionButtonEditarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VerNota.this, EditarNota.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        floatingActionButtonBorrarNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Confirmar la eliminación de la nota mediante un cuadro de diálogo
                AlertDialog.Builder builder = new AlertDialog.Builder(VerNota.this);
                builder.setMessage("¿Desea eliminar esta nota?")
                        .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Eliminar la nota y volver a la lista de notas
                                if (dbNotas.eliminarNota(id)){
                                    lista();
                                }
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // No hacer nada en caso de cancelar la eliminación
                            }
                        }).show();
            }
        });
    }

    // Método para ir a la lista de notas
    private void lista(){
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
