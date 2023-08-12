package Codigo;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ExchangeRateAPI {

    public static void main(String[] args) {
        String apiKey = "5979f4ad7dcd995a9686a61ca9f9ba83"; 
        String baseCurrency = "USD";

        try {
            URL url = new URL("https://api.apilayer.com/exchangerates_data/latest?base=" + baseCurrency + "&apikey=" + apiKey);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println(response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
