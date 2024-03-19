package com.example.agendify.ApiTiempo;

import com.google.gson.annotations.SerializedName;

public class WeatherResponse {

    // Anotación SerializedName se utiliza para vincular el campo "main" del JSON con la variable "main"
    @SerializedName("main")
    private MainWeatherInfo main;

    // Método para obtener la información principal del clima (temperatura y más)
    public MainWeatherInfo getMain() {
        return main;
    }
}
