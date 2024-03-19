package com.example.agendify;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.agendify.Entidades.Contactos;
import com.example.agendify.db.DbContactos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditContacto extends AppCompatActivity {

    EditText nombreEt, correoEt, telefonoEt;

    TextView TexViNmbEditContacto, TexViCorreoEditContacto, TexViTelefonoEditContacto;

    Button guardarRegistoEt;

    Contactos contactos;

    FloatingActionButton floatingActionButtonVectorEditar, floatingActionButtonVectorBorrar, floatingVolver, floatingHome;

    int id = 0;

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
        floatingActionButtonVectorBorrar = findViewById(R.id.floatingActionButtonVectorBorrar);
        floatingVolver = findViewById(R.id.floatingVolver);
        floatingHome = findViewById(R.id.floatingHome);

        // Obtener el ID del contacto desde los extras o savedInstanceState
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                id = Integer.parseInt(null);
            } else {
                id = extras.getInt("ID");
            }
        } else {
            id = (int) savedInstanceState.getSerializable("ID");
        }

        // Obtener datos del contacto desde la base de datos
        DbContactos dbContactos = new DbContactos(EditContacto.this);
        contactos = dbContactos.verContactos(id);

        // Rellenar campos con los datos del contacto y deshabilitar la edición
        if (contactos != null) {
            nombreEt.setText(contactos.getNombre());
            correoEt.setText(contactos.getCorreo_electronico());
            telefonoEt.setText(contactos.getTelefono());
            guardarRegistoEt.setVisibility(View.INVISIBLE);
            nombreEt.setInputType(InputType.TYPE_NULL);
            correoEt.setInputType(InputType.TYPE_NULL);
            telefonoEt.setInputType(InputType.TYPE_NULL);
        }

        // Configuración de los botones flotantes
        floatingVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditContacto.this, LeerRegistro.class));
            }
        });

        floatingHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditContacto.this, Menu.class));
            }
        });

        floatingActionButtonVectorEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir la actividad de edición de contacto
                Intent intent = new Intent(EditContacto.this, EditarContactoActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });

        floatingActionButtonVectorBorrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mostrar un diálogo de confirmación para eliminar el contacto
                AlertDialog.Builder builder = new AlertDialog.Builder(EditContacto.this);
                builder.setMessage("¿Desea eliminar este contacto?").setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Eliminar el contacto de la base de datos y actualizar la lista
                        if (dbContactos.eliminarContactos(id)) {
                            lista();
                        }
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // No hacer nada si el usuario selecciona "NO"
                    }
                }).show();
            }
        });
    }

    // Método para iniciar la actividad de lista después de eliminar un contacto
    private void lista() {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
