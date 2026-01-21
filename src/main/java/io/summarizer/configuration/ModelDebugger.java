//package io.summarizer.configuration;
//
//import java.net.URI;
//import java.net.http.HttpClient;
//import java.net.http.HttpRequest;
//import java.net.http.HttpResponse;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class ModelDebugger {
//
//	@Value("${GOOGLE_API_KEY}")
//	private String apiKey;
//
//	@Bean
//	public CommandLineRunner listModels() {
//		return args -> {
//			System.out.println("----- Checking Available Gemini Models -----");
//			try {
//				// Manually call the Google API to see what is available to your key
//				String url = "https://generativelanguage.googleapis.com/v1beta/models?key=" + apiKey;
//
//				HttpClient client = HttpClient.newHttpClient();
//				HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
//
//				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//
//				System.out.println("Status Code: " + response.statusCode());
//				System.out.println("Response: " + response.body());
//				System.out.println("--------------------------------------------");
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		};
//	}
//}
