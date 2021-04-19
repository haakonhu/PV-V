import java.net.*;
import java.net.http.*;
import java.util.ArrayList;

import static java.lang.System.currentTimeMillis;

public class HistoricalData {

    public static long getCurrentDate(){
        return currentTimeMillis()/1000;
    }

    public static double getPower(long date) {
        return 0;
    }

    public static int getCloudlvl(long date) {
        int cloudlvl,sunr,suns,test;
        //String data = "";
        String fill, cloud;
        String[] parts;
        ArrayList<String> dt = new ArrayList<String>();
        String getData = historicalData(date);

        fill = getData.substring(getData.indexOf("sunrise")+9, getData.indexOf("sunset")-2);
        try{
            sunr = Integer.parseInt(fill);
        }catch(Exception e){
            sunr = 0;
        }

        fill = getData.substring(getData.indexOf("sunset")+8, getData.indexOf("temp")-2);
        try{
            suns = Integer.parseInt(fill);
        }catch(Exception e){
            suns = 0;
        }

        cloudlvl = 0;
        parts = getData.split("\"dt\"");
        for(int i = 2; i < parts.length; i++) {
            //data = data + "dt: " + parts[i].substring(1, parts[i].indexOf(",")) + parts[i].substring(parts[i].indexOf(","), parts[i].length());
            fill = parts[i].substring(1, parts[i].indexOf(","));
            test = Integer.parseInt(fill);
            if(test > sunr && test < suns) {
                fill = parts[i].substring(parts[i].indexOf("\"clouds\"")+8, parts[i].indexOf("wind_speed")-2);
                cloud = fill.substring(1, fill.indexOf(","));
                dt.add(cloud);
            }
        }

        for(int j = 0; j < dt.size(); j++){
            cloudlvl = cloudlvl + Integer.parseInt(dt.get(j));
        }

        System.out.println(dt);
        return cloudlvl/dt.size();
    }
    /*
    ausgabe = {"lat":48.6672,"lon":16.3426,"timezone":"Europe/Vienna","timezone_offset":7200,"current":{"dt":1618587585,"sunrise":1618545710,"sunset":1618595193,"temp":280.41,"feels_like":275.44,"pressure":1021,"humidity":57,"dew_point":272.55,"uvi":3.76,"clouds":75,"visibility":30000,"wind_speed":4.12,"wind_deg":300,"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}]}
     */
    public static String historicalData(long dt){
        String ausgabe = "";
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
            //System.out.println(response.body());
            ausgabe = response.body();
        }catch(Exception e){

        }

        return ausgabe;
    }

    public static void main(String[] args){
        System.out.println(historicalData(1618587585));
        System.out.println("\n"+getCloudlvl(1618587585));
    }
}
