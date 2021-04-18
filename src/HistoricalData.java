import java.net.*;
import java.net.http.*;

public class HistoricalData {
    public static void main(String[] args){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://community-open-weather-map.p.rapidapi.com/onecall/timemachine?lat=48.6671698&lon=16.3425561&dt=1618217000"))
                .header("x-rapidapi-key", "ff49babd40msh68b0db0bc714a9bp1b3109jsnba51ba82b540")
                .header("x-rapidapi-host", "community-open-weather-map.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        try {
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
        }catch(Exception e){

        }
    }
}
