import java.io.IOException;
import java.net.*;
import java.net.http.*;
import java.util.Date;

import static java.lang.System.currentTimeMillis;


public class Pv {
    public static String currentData(){
        String ausgabe = "";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://community-open-weather-map.p.rapidapi.com/weather?q=Unterstinkenbrunn%2C%20AT&lat=0&lon=0&callback=Current&id=2762632&lang=de&units=metric"))
                .header("x-rapidapi-key", "ff49babd40msh68b0db0bc714a9bp1b3109jsnba51ba82b540")
                .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            ausgabe = response.body();
        }catch(Exception e){

        }
        return ausgabe;
    }

    public static String historicalData(long dt){
        String link = "https://community-open-weather-map.p.rapidapi.com/onecall/timemachine?lat=48.6671698&lon=16.3425561&dt=";
        link = link + dt;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .header("x-rapidapi-key", "ff49babd40msh68b0db0bc714a9bp1b3109jsnba51ba82b540")
                .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }catch(Exception e){

        }
        return "";
    }

    /*
        Weather: "main", "description" Clouds: "all"
     */
    public static String futureData(){
        String ausgabe = "";
        String data;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://community-open-weather-map.p.rapidapi.com/forecast?q=Unterstinkenbrunn%2C%20AT&id=2762632"))
                .header("x-rapidapi-key", "ff49babd40msh68b0db0bc714a9bp1b3109jsnba51ba82b540")
                .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            //System.out.println(response.body());
            ausgabe = response.body();
        }catch(Exception e){

        }
        data = "Weather: ";
        data = data + ausgabe.split(ausgabe.indexOf("Weather"));
        return "";
    }

    public static void main(String[] args){
        long unixTime = currentTimeMillis()/1000-(86400*2); //Vorgestern
        //currentData();
        //historicalData(unixTime);
        futureData();
        Date time = new Date((long)1618758000*1000);
        System.out.println(time);
    }
}
