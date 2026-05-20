package CODSOFT;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConv {

    // Keep your API key here
    private static final String API_KEY = "c7d346c718adc453875ffe19";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("====== CURRENCY CONVERTER (No Libs) ======");

        System.out.print("Enter Base Currency (e.g., USD): ");
        String baseCurrency = scanner.next().toUpperCase();

        System.out.print("Enter Target Currency (e.g., INR): ");
        String targetCurrency = scanner.next().toUpperCase();

        System.out.print("Enter the amount to convert: ");
        double amount = scanner.nextDouble();

        try {
            double exchangeRate = fetchExchangeRate(baseCurrency, targetCurrency);

            if (exchangeRate != -1) {
                double convertedAmount = amount * exchangeRate;

                System.out.println("\n--------------------------------");
                System.out.printf("%.2f %s = %.2f %s\n", amount, baseCurrency, convertedAmount, targetCurrency);
                System.out.printf("Exchange Rate: 1 %s = %.4f %s\n", baseCurrency, exchangeRate, targetCurrency);
                System.out.println("--------------------------------");
            } else {
                System.out.println("Error: Could not retrieve rate. Check your API key or connection.");
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }

    private static double fetchExchangeRate(String base, String target) throws Exception {
        String urlString = BASE_URL + base + "/" + target;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        if (connection.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // MANUAL PARSING: We look for "conversion_rate": and grab the number after it
            String responseStr = response.toString();
            String key = "\"conversion_rate\":";
            int startIndex = responseStr.indexOf(key) + key.length();
            int endIndex = responseStr.indexOf("}", startIndex);
            
            // Clean up commas or extra characters if they exist
            String rateStr = responseStr.substring(startIndex, endIndex).replace(",", "").trim();
            
            return Double.parseDouble(rateStr);
        }
        return -1;
    }
}