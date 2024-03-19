package com.example.agendify.ApiTiempo;

import com.google.gson.annotations.SerializedName;

public class MainWeatherInfo {

    // Anotación SerializedName se utiliza para vincular el campo "temp" del JSON con la variable "temperature"
    @SerializedName("temp")
    private double temperature;

    // Método para obtener la temperatura
    public double getTemperature() {
        return temperature;
    }
}
