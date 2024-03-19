package com.example.agendify;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agendify.db.DbContactos;
import com.example.agendify.db.DbNotas;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.Date;

public class Nota extends AppCompatActivity {

    Button BtnGuardaNota;

    EditText EdTvTitulo, EdTvDescripcion;

    // Apartado para el escáner
    FloatingActionButton floatingActionButtonScanner, floatingHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nota);

        // Apartado del título y descripción
        EdTvTitulo = findViewById(R.id.EdTvTitulo);
        EdTvDescripcion = findViewById(R.id.EdTvDescripcion);

        // Apartado de los botones guardar registro
        BtnGuardaNota = findViewById(R.id.BtnGuardaNota);

        // Apartado para el escáner
        floatingActionButtonScanner = findViewById(R.id.floatingActionButtonScanner);
        floatingHome = findViewById(R.id.floatingHome);

        // Configurar el botón de inicio
        floatingHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Nota.this, Menu.class));
            }
        });

        // Apartado para el evento de guardar
        BtnGuardaNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbNotas dbNotas = new DbNotas(Nota.this);
                long idNota = dbNotas.insertaNota(EdTvTitulo.getText().toString(), EdTvDescripcion.getText().toString());

                if (idNota > 0) {
                    Toast.makeText(Nota.this, "NOTA GUARDADA", Toast.LENGTH_LONG).show();
                    limpiar();
                } else {
                    Toast.makeText(Nota.this, "ERROR AL GUARDAR NOTA", Toast.LENGTH_LONG).show();
                }
            }
        });

        // Botón de escáner
        floatingActionButtonScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(Nota.this);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Lector - CDP");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.setBarcodeImageEnabled(true);
                integrator.initiateScan();
            }
        });
    }

    // Método para limpiar los campos de texto
    private void limpiar() {
        EdTvTitulo.setText("");
        EdTvDescripcion.setText("");
    }

    // Método para manejar el resultado del escáner
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "LECTOR CANCELADO", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                // Concatenar el nuevo resultado con el contenido actual del EditText
                String currentText = EdTvDescripcion.getText().toString();
                String newResult = result.getContents();

                if (!currentText.isEmpty()) {
                    // Agregar una nueva línea antes de concatenar si ya hay contenido
                    currentText += "\n";
                }

                EdTvDescripcion.setText(currentText + newResult);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
