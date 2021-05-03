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

    /**
     * Gibt den Durchschnitt des Cloud lvls vom übergebenen date an
     * @param date der Tag von dem das Cloud lvl erechnet werden soll
     * @return Durchschnitt des Cloud lvls
     */
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

    /**
     * Holt die Daten von OpenWeatherMap
     * ausgabe = {"lat":48.6672,"lon":16.3426,"timezone":"Europe/Vienna","timezone_offset":7200,"current":{"dt":1618587585,"sunrise":1618545710,"sunset":1618595193,"temp":280.41,"feels_like":275.44,"pressure":1021,"humidity":57,"dew_point":272.55,"uvi":3.76,"clouds":75,"visibility":30000,"wind_speed":4.12,"wind_deg":300,"weather":[{"id":803,"main":"Clouds","description":"broken clouds","icon":"04d"}]}
     * @param dt Unix date vom Tag von den die Daten geholt werden sollen
     * @return Datensätze
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

    /**
     *
     * @param dt
     * @return
     */
    public static String[] getWeatherMain(long dt){
        String getData = historicalData(dt);
        String[] weatherStatus = new String[24];
        String[] parts;
        int id = 0;

        parts = getData.split("\"dt\"");
        for(int i = 2; i < parts.length; i++){
            weatherStatus[id] = parts[i].substring(parts[i].indexOf("main")+7, parts[i].indexOf("description")-3);
            id++;
        }

        //System.out.println(weatherStatus[0]); //nur fürs testen
        return weatherStatus;
    }

    public static String[] getWeatherDescription(long dt){
        String getData = historicalData(dt);
        String[] weatherDes = new String[24];
        String[] parts;
        int id = 0;

        parts = getData.split("\"dt\"");
        for(int i = 2; i < parts.length; i++){
            weatherDes[id] = parts[i].substring(parts[i].indexOf("description")+14, parts[i].indexOf("icon")-3);
            id++;
        }

        //System.out.println(weatherStatus[0]); //nur fürs testen
        return weatherDes;
    }

    public static void main(String[] args){
        long date = 1619858454;
        System.out.println(historicalData(date)+"\n");
        System.out.println(getCloudlvl(date));
        System.out.println("\n----------------------Main----------------------");
        String[] testMain = getWeatherMain(date);
        for(int i = 0; i < testMain.length; i++){
            System.out.print((i+1)+". "+testMain[i]+", ");
        }
        System.out.println("\n----------------------Description----------------------");
        String[] testDes = getWeatherDescription(date);
        for(int j = 0; j < testDes.length; j++){
            System.out.print((j+1)+". "+testDes[j]+", ");
        }
    }
}
