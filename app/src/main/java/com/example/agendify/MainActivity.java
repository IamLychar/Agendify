package com.example.agendify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

import com.example.agendify.db.DbHerramientas;

public class MainActivity extends AppCompatActivity {

    // Elementos de la interfaz
    Button BtnIniciarApp;
    TextView tvDiasTranscurridos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de elementos de la interfaz
        BtnIniciarApp = findViewById(R.id.BtnIniciarApp);
        tvDiasTranscurridos = findViewById(R.id.tvDiasTranscurridos);

        try {
            // Obtén la fecha actual
            LocalDate fechaActual = LocalDate.now();

            // Crea la fecha límite (19/02/2024)
            LocalDate fechaLimite = LocalDate.of(2024, 2, 21);

            // Calcula los días restantes
            long diasRestantes = fechaActual.until(fechaLimite).getDays();

            // Muestra los días restantes en el TextView
            tvDiasTranscurridos.setText("Días restantes: " + diasRestantes);

        } catch (Exception e) {
            // Manejar cualquier excepción y mostrar un mensaje de error
            Toast.makeText(MainActivity.this, "ERROR AL MOSTRAR HORA", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

        // Configuración del botón de inicio de la aplicación
        BtnIniciarApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear una instancia de la clase de herramientas de base de datos
                DbHerramientas dbHerramientas = new DbHerramientas(MainActivity.this);

                try {
                    // Obtener una instancia de la base de datos en modo escritura
                    SQLiteDatabase db = dbHerramientas.getWritableDatabase();

                    // Mostrar un mensaje de bienvenida
                    Toast.makeText(MainActivity.this, "¡Bienvenido a nuestra app!", Toast.LENGTH_LONG).show();

                } catch (Exception e) {
                    // Manejar cualquier excepción al iniciar la aplicación y mostrar un mensaje de error
                    Toast.makeText(MainActivity.this, "ERROR AL INICIAR APP", Toast.LENGTH_LONG).show();
                    e.printStackTrace(); // Registrar la excepción para fines de depuración
                }

                // Iniciar la actividad del menú después de configurar la base de datos (si es necesario)
                startActivity(new Intent(MainActivity.this, Menu.class));
            }
        });
    }
}
