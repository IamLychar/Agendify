package com.example.agendify.ApiTiempo;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    // Anotación GET indica que esta interfaz define un método que realizará una solicitud HTTP GET
    @GET("weather")
    // Método para obtener datos meteorológicos, devuelve un objeto Call con una respuesta de tipo WeatherResponse
    Call<WeatherResponse> getWeatherData(
            // Parámetro de consulta "q" para especificar la ciudad
            @Query("q") String city,
            // Parámetro de consulta "appid" para especificar la clave de API
            @Query("appid") String apiKey
    );

    // Método adicional sin anotaciones, posiblemente no sea necesario y podría ser un error
    void getWeatherData(Class<WeatherApi> weatherApiClass);
}
