package com.example.agendify;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Pantalla_De_carga extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Establece el diseño de la actividad a partir del archivo XML 'activity_pantalla_de_carga'
        setContentView(R.layout.activity_pantalla_de_carga);

        // Define el tiempo de espera en milisegundos (en este caso, 3000 ms o 3 segundos)
        int Tiempo = 3000;

        // Utiliza un objeto Handler para realizar una operación después de un retraso
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Crea una nueva intención para iniciar la actividad MainActivity
                startActivity(new Intent(Pantalla_De_carga.this, MainActivity.class));
                // Finaliza la actividad actual (Pantalla_De_carga)
                finish();
            }
        }, Tiempo); // El tiempo de retraso antes de ejecutar la operación definida en run()
    }
}
