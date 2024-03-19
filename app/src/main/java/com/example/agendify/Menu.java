package com.example.agendify;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agendify.ApiTiempo.WeatherApi;
import com.example.agendify.ApiTiempo.WeatherResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Menu extends AppCompatActivity {

    // Api tiempo
    private TextView temperatureTextView;
    private EditText cityEditText;
    private EditText countryEditText;
    private WeatherApi weatherApi;
    private VideoView videoView;

    private Button BtnCrearNota, BtnCrearContacto, BtnLeerNota, BtnLeerContacto, getWeatherButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Inicialización de vistas
        initializeViews();

        // Inicialización de la API del tiempo
        initializeWeatherApi();

        // Construir la ruta del video
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.nubesss;

        // Configurar el VideoView con la ruta del video
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // Configurar el bucle del video
        videoView.setOnCompletionListener(mp -> videoView.start());

        // Iniciar la reproducción del video
        videoView.start();

        // Establecer valores predeterminados para España, Madrid
        cityEditText.setText("Madrid");
        countryEditText.setText("Spain");

        // Realizar la llamada a la API al iniciar la aplicación
        getWeatherData("Madrid,Spain", "ddb5a8da48cfaaa963216f69632c27e6");

        // Para Api tiempo
        getWeatherButton.setOnClickListener(v -> {
            String city = cityEditText.getText().toString().trim();
            String country = countryEditText.getText().toString().trim();

            if (!city.isEmpty() && !country.isEmpty()) {
                getWeatherData(city + "," + country, "ddb5a8da48cfaaa963216f69632c27e6");
            } else {
                temperatureTextView.setText("Por favor, ingresa la ciudad y el país");
            }
        });

        // ACCIONES DE LOS BOTONES DE MENU
        BtnCrearNota.setOnClickListener(v -> startActivity(new Intent(Menu.this, Nota.class)));
        BtnLeerNota.setOnClickListener(v -> startActivity(new Intent(Menu.this, LeerNota.class)));
        BtnCrearContacto.setOnClickListener(v -> startActivity(new Intent(Menu.this, Registro.class)));
        BtnLeerContacto.setOnClickListener(v -> startActivity(new Intent(Menu.this, LeerRegistro.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Reanudar la reproducción del video al volver a la actividad
        if (videoView != null && !videoView.isPlaying()) {
            videoView.start();
        }
    }

    // Inicializar las vistas
    private void initializeViews() {
        BtnCrearNota = findViewById(R.id.BtnCrearNota);
        BtnCrearContacto = findViewById(R.id.BtnCrearContacto);
        BtnLeerNota = findViewById(R.id.BtnLeerNota);
        BtnLeerContacto = findViewById(R.id.BtnLeerContacto);

        // Para Api tiempo
        videoView = findViewById(R.id.videoView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        cityEditText = findViewById(R.id.cityEditText);
        countryEditText = findViewById(R.id.countryEditText);
        getWeatherButton = findViewById(R.id.getWeatherButton);
    }

    // Inicializar la API del tiempo
    private void initializeWeatherApi() {
        // Configuración de Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Crear instancia de WeatherApi
        weatherApi = retrofit.create(WeatherApi.class);
    }

    // Obtener datos del tiempo desde la API
    private void getWeatherData(String location, String apiKey) {
        Call<WeatherResponse> call = weatherApi.getWeatherData(location, apiKey);
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(@NonNull Call<WeatherResponse> call, @NonNull Response<WeatherResponse> response) {
                if (response.isSuccessful()) {
                    WeatherResponse weatherResponse = response.body();
                    if (weatherResponse != null) {
                        double temperaturaKelvin = weatherResponse.getMain().getTemperature();
                        double temperaturaCelsius = temperaturaKelvin - 273.15;
                        String temperaturaString = String.format("%.2f", temperaturaCelsius);
                        temperatureTextView.setText("Temperatura: " + temperaturaString + " °C");
                    }
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        temperatureTextView.setText("Error en la respuesta: " + errorBody);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<WeatherResponse> call, @NonNull Throwable t) {
                temperatureTextView.setText("Error en la conexión");
            }
        });
    }
}
