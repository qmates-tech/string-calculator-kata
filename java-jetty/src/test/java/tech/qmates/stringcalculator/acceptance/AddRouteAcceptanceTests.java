package tech.qmates.stringcalculator.acceptance;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import tech.qmates.stringcalculator.Application;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddRouteAcceptanceTests {

    private static Thread serverThread;
    private static final HttpClient httpClient = HttpClient.newHttpClient();

    @BeforeAll
    static void startServer() throws Exception {
        serverThread = new Thread(() -> {
            try {
                Application.main(new String[]{});
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        serverThread.setDaemon(true);
        serverThread.start();
        Thread.sleep(2000);
    }

    @AfterAll
    static void stopServer() {
        if (serverThread != null) {
            serverThread.interrupt();
        }
    }

    @Test
    void postRequestReturnsBodyWithStatus200() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .header("Content-Type", "text/plain")
            .POST(BodyPublishers.ofString("1"))
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals("1", response.body());
        assertEquals(200, response.statusCode());
    }

    @Test
    void getRequestShouldReturns405() throws Exception {
        var request = HttpRequest.newBuilder()
            .uri(URI.create("http://localhost:8080/add"))
            .GET()
            .build();

        var response = httpClient.send(request, BodyHandlers.ofString());

        assertEquals(405, response.statusCode());
    }
}
