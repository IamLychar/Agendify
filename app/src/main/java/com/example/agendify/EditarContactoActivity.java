package com.example.agendify;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agendify.Entidades.Contactos;
import com.example.agendify.db.DbContactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditarContactoActivity extends AppCompatActivity {

    EditText nombreEt,correoEt,telefonoEt;

    TextView TexViNmbEditContacto,TexViCorreoEditContacto,TexViTelefonoEditContacto;

    Button guardarRegistoEt;

    FloatingActionButton floatingActionButtonVectorEditar, floatingActionButtonVectorBorrar;

    Contactos contactos;

    int id = 0;

    boolean correcto = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacto);

        // Inicialización de vistas
        nombreEt = findViewById(R.id.nombreEt);
        correoEt = findViewById(R.id.correoEt);
        telefonoEt = findViewById(R.id.telefonoEt);

        TexViNmbEditContacto = findViewById(R.id.TexViNmbEditContacto);
        TexViCorreoEditContacto = findViewById(R.id.TexViCorreoEditContacto);
        TexViTelefonoEditContacto = findViewById(R.id.TexViTelefonoEditContacto);

        guardarRegistoEt = findViewById(R.id.guardarRegistoEt);
        floatingActionButtonVectorEditar = findViewById(R.id.floatingActionButtonVectorEditar);
        floatingActionButtonVectorEditar.setVisibility(View.INVISIBLE);
        floatingActionButtonVectorBorrar = findViewById(R.id.floatingActionButtonVectorBorrar);
        floatingActionButtonVectorBorrar.setVisibility(View.INVISIBLE);

        // Obtener el ID del contacto desde los extras o savedInstanceState
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                id = Integer.parseInt(null);
            }else {
                id = extras.getInt("ID");
            }
        }else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        // Obtener datos del contacto desde la base de datos
        DbContactos dbContactos = new DbContactos(EditarContactoActivity.this);
        contactos = dbContactos.verContactos(id);

        // Rellenar campos con los datos del contacto
        if (contactos != null){
            nombreEt.setText(contactos.getNombre());
            correoEt.setText(contactos.getCorreo_electronico());
            telefonoEt.setText(contactos.getTelefono());
        }

        // Configurar el botón para guardar cambios
        guardarRegistoEt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Verificar que los campos obligatorios no estén vacíos
                if (!nombreEt.getText().toString().isEmpty() && !correoEt.getText().toString().isEmpty()) {
                    // Intentar editar el contacto en la base de datos
                    correcto = dbContactos.editarContactos(id, nombreEt.getText().toString(), correoEt.getText().toString(), telefonoEt.getText().toString());

                    // Mostrar mensaje según el resultado de la edición
                    if (correcto) {
                        Toast.makeText(EditarContactoActivity.this, "CONTACTO MODIFICADO", Toast.LENGTH_LONG).show();
                        verContactoActivity();
                    } else {
                        Toast.makeText(EditarContactoActivity.this, "ERROR AL MODIFICAR CONTACTO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(EditarContactoActivity.this, "DEBE LLENAR LOS CAMPOS OBLIGATORIOS", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    // Método para iniciar la actividad de visualización del contacto después de la edición
    private void verContactoActivity(){
        Intent intent = new Intent(this, Menu.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }
}
