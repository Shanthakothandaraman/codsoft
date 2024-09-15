import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.net.HttpURLConnection;  
import java.net.URL;  
import java.util.Scanner;  

class UniversalCurrencyConverter {  
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your API key  
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/";  

    public static void main(String[] args) {  
        Scanner scanner = new Scanner(System.in);  
        
        // User selects base currency  
        System.out.print("Enter base currency (e.g., USD, EUR): ");  
        String baseCurrency = scanner.nextLine().toUpperCase();  

        // User selects target currency  
        System.out.print("Enter target currency (e.g., JPY, GBP): ");  
        String targetCurrency = scanner.nextLine().toUpperCase();  
        
        // User enters amount  
        System.out.print("Enter amount to convert: ");  
        double amount = scanner.nextDouble();  

        try {  
            // Fetch the exchange rate  
            double exchangeRate = getExchangeRate(baseCurrency, targetCurrency);  
            if (exchangeRate > 0) {  
                // Convert the amount  
                double convertedAmount = amount * exchangeRate;  
                System.out.printf("%.2f %s is equal to %.2f %s%n", amount, baseCurrency, convertedAmount, targetCurrency);  
            } else {  
                System.out.println("Error fetching exchange rate. Please check the currency codes.");  
            }  
        } catch (IOException e) {  
            System.out.println("Error fetching exchange rates: " + e.getMessage());  
        }  
    }  

    private static double getExchangeRate(String baseCurrency, String targetCurrency) throws IOException {  
        URL url = new URL(BASE_URL + baseCurrency);  
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
        connection.setRequestMethod("GET");  

        if (connection.getResponseCode() != 200) {  
            throw new IOException("Failed to get response from API");  
        }  

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));  
        StringBuilder jsonResponse = new StringBuilder();  
        String line;  
        while ((line = reader.readLine()) != null) {  
            jsonResponse.append(line);  
        }  
        reader.close();  

        // Parse the JSON response to extract the exchange rate  
        String json = jsonResponse.toString();  
        String rateString = json.replaceAll(".*\"" + targetCurrency + "\":([0-9.]+).*", "$1");  
        return Double.parseDouble(rateString);  
    }  
}