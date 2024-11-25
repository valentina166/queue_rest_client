package queue_rest_client;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
public class QueueRestClient {
	private static final String API_URL = "http://localhost:8080/api/turnos/fila"; // Cambia esta URL
   public static void main(String[] args) {
       while (true) {
           try {
               List<Turno> turnos = fetchTurnos();
               displayTurnos(turnos);
               Thread.sleep(2000); // Espera 2 segundos
           } catch (InterruptedException e) {
               Thread.currentThread().interrupt();
               break;
           }
       }
   }
   private static List<Turno> fetchTurnos() {
       try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
           HttpGet request = new HttpGet(API_URL);
           try (CloseableHttpResponse response = httpClient.execute(request);
                InputStream inputStream = response.getEntity().getContent()) {
               ObjectMapper objectMapper = new ObjectMapper();
               return objectMapper.readValue(inputStream, new TypeReference<List<Turno>>() {});
           }
       } catch (IOException e) {
           e.printStackTrace();
           return List.of(); // Retornar lista vacía en caso de error
       }
   }
   private static void displayTurnos(List<Turno> turnos) {
       System.out.println("Fila de Espera:");
       for (Turno turno : turnos) {
           System.out.println(turno.getNumeroTurno() + ": " + turno.getNombre() + " " + turno.getApellido());
       }
   }
}
