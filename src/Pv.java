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
    /*
    Date, Clouds, Weather: main, description
     */
    public static String historicalData(long dt){
        String ausgabe;
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
        String part;
        String[] parts;
        String test;
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
        parts = ausgabe.split("\"dt\"");
        data = "Date: ";
        data = "\t Weather: ";
        part = ausgabe.substring(ausgabe.indexOf("id"), ausgabe.length());
        part = part.substring(part.indexOf("main"), part.length());
        data = data + part.substring(part.indexOf(":")+1, part.indexOf(","));
        data = data + "\t Description: ";
        part = ausgabe.substring(ausgabe.indexOf("description"), ausgabe.length());
        data = data + part.substring(part.indexOf(":")+1, part.indexOf(","));
        data = data + "\t Clouds: ";
        part = ausgabe.substring(ausgabe.indexOf("all"));
        data = data + part.substring(part.indexOf(":")+1, part.indexOf("}")) + "%";

        return parts[1];
    }

    public static void main(String[] args){
        String s;
        long unixTime = currentTimeMillis()/1000-(86400*2); //Vorgestern
        //currentData();
        historicalData(unixTime);
       // s = futureData();
        Date time = new Date((long)1618758000*1000);
        //System.out.println(s);
    }
}
