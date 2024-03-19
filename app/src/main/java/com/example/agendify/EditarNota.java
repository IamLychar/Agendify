package com.example.agendify;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendify.Entidades.Notas;
import com.example.agendify.db.DbNotas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarNota extends AppCompatActivity {

    Button BtnGuardaNota;

    EditText EdTvTitulo, EdTvDescripcion;

    boolean correcto = false;

    FloatingActionButton floatingActionButtonEditarNota;
    FloatingActionButton floatingActionButtonBorrarNota;

    Notas nota;
    int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_nota);

        // Inicialización de vistas
        EdTvTitulo = findViewById(R.id.EdTvTitulo);
        EdTvDescripcion = findViewById(R.id.EdTvDescripcion);
        BtnGuardaNota = findViewById(R.id.BtnGuardaNota);
        floatingActionButtonEditarNota = findViewById(R.id.floatingActionButtonEditarNota);
        floatingActionButtonEditarNota.setVisibility(View.INVISIBLE);
        floatingActionButtonBorrarNota = findViewById(R.id.floatingActionButtonBorrarNota);
        floatingActionButtonBorrarNota.setVisibility(View.INVISIBLE);

        // Obtener el ID de la nota desde los extras o savedInstanceState
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id = Integer.parseInt(null);
            }else {
                id = extras.getInt("ID");
            }
        } else {
            id = savedInstanceState.getInt("ID");
        }

        // Obtener datos de la nota desde la base de datos
        DbNotas dbNotas = new DbNotas(EditarNota.this);
        nota = dbNotas.verNota(id);

        // Rellenar campos con los datos de la nota
        if (nota != null){
            EdTvTitulo.setText(nota.getTitulo());
            EdTvDescripcion.setText(nota.getDescripcion());
        }

        // Configurar el botón para guardar cambios
        BtnGuardaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar que los campos obligatorios no estén vacíos
                if (!EdTvTitulo.getText().toString().equals("") && !EdTvDescripcion.getText().toString().equals("")){
                    // Intentar editar la nota en la base de datos
                    correcto = dbNotas.editarNota(id,EdTvTitulo.getText().toString(),EdTvDescripcion.getText().toString());

                    // Mostrar mensaje según el resultado de la edición
                    if (correcto){
                        Toast.makeText(EditarNota.this, "REGISTRO MODIFICADO", Toast.LENGTH_SHORT).show();
                        verNotaActivity();
                    } else {
                        Toast.makeText(EditarNota.this, "ERROR AL MODIFICAR REGISTRO", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(EditarNota.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Método para iniciar la actividad de visualización de la nota después de la edición
    private void verNotaActivity(){
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
