package com.example.agendify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.agendify.db.DbContactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Registro extends AppCompatActivity {

    EditText nombreEt, correoEt, telefonoEt;
    Button guardarRegistoEt;
    FloatingActionButton floatingHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        // Inicializar elementos de la interfaz
        nombreEt = findViewById(R.id.nombreEt);
        correoEt = findViewById(R.id.correoEt);
        telefonoEt = findViewById(R.id.telefonoEt);
        guardarRegistoEt = findViewById(R.id.guardarRegistoEt);
        floatingHome = findViewById(R.id.floatingHome);

        // Configuración del botón de regreso al menú principal
        floatingHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Registro.this, Menu.class));
            }
        });

        // Configuración del botón para guardar el registro de contacto
        guardarRegistoEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbContactos dbContactos = new DbContactos(Registro.this);
                long id = dbContactos.insertaContactos(nombreEt.getText().toString(), telefonoEt.getText().toString(), correoEt.getText().toString());

                if (id > 0) {
                    Toast.makeText(Registro.this, "CONTACTO GUARDADO", Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    Toast.makeText(Registro.this, "ERROR AL GUARDAR CONTACTO", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Método para limpiar los campos de entrada
    private void limpiar(){
        nombreEt.setText("");
        telefonoEt.setText("");
        correoEt.setText("");
    }
}
